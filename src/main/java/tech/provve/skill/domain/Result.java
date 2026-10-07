package tech.provve.skill.domain;

import alekseyvideman.dop.Collection;
import tech.provve.accounts.exception.AccessDenied;
import tech.provve.accounts.JwsParsing;
import tech.provve.accounts.Account;
import tech.provve.api.generated.dto.*;
import tech.provve.constants.Entity;
import tech.provve.util.Jackson;
import tech.provve.util.Storage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static tech.provve.accounts.JwsParsing.PREMIUM;

public class Result {

    public static Results list(CollectionAuthenticatedRequest request) {
        var login = JwsParsing.parseAuth(request.getAuthToken(), JwsParsing.JWT_SUBJECT);
        var pagination = request.getPagination();
        List<ResultResponse> all = Storage.getAllResultsForExaminee(request.getFilter(), login, pagination.getPrevious(), pagination.getSize())
                                          .stream()
                                          .map(result -> Jackson.convertToClass(result, ResultResponse.class))
                                          .toList();
        if (all.isEmpty()) {
            return new Results(all, new Cursor(""));
        }

        var cursor = new Cursor(all.getLast()
                                   .getExamName());
        return new Results(all, cursor);
    }

    public static Examinees listExaminees(CollectionAuthenticatedRequest request) throws AccessDenied {
        boolean premium = Boolean.parseBoolean(JwsParsing.parseAuth(request.getAuthToken(), PREMIUM));
        if (!premium) {
            throw new AccessDenied("You have to buy premium access first");
        }

        var pagination = request.getPagination();
        List<Map<String, Object>> results = Storage.getAllResults(request.getFilter(), pagination.getPrevious(), pagination.getSize())
                                                   .stream()
                                                   .toList();

        Map<Map<String, Object>, ProfilePublicView> profiles = HashMap.newHashMap(results.size());
        results.stream()
               .map(result -> Map.of(
                       result,
                       Account.viewPublicProfile(Collection.get(result, Entity.Result.EXAMINEE))
               ))
               .forEach(profiles::putAll);

        List<Examinee> examinees = profiles.entrySet()
                                           .stream()
                                           .map(entry -> {
                                               Map<String, Object> examinee = Jackson.convertToMap(entry.getKey());
                                               examinee.putAll(Jackson.convertToMap(entry.getValue()));
                                               return Jackson.convertToClass(examinee, Examinee.class);
                                           })
                                           .toList();
        if (results.isEmpty()) {
            return new Examinees(examinees, new Cursor(""));
        }
        var cursor = new Cursor(Collection.get(results.getLast(), Entity.Result.EXAMINEE));
        return new Examinees(examinees, cursor);
    }
}
