package tech.provve.api.controller;

import io.vertx.core.Future;
import tech.provve.accounts.JwsParsing;
import tech.provve.accounts.exception.AccessDenied;
import tech.provve.api.ApiResponse;
import tech.provve.api.exception.HttpException;
import tech.provve.api.exception.ValidationError;
import tech.provve.api.generated.api.SkillsApi;
import tech.provve.api.generated.dto.*;
import tech.provve.skill.domain.Result;
import tech.provve.skill.domain.Skill;
import tech.provve.util.Jackson;
import tech.provve.util.Validation;

import java.nio.file.Path;
import java.util.Map;

import static tech.provve.accounts.JwsParsing.JWT_SUBJECT;
import static tech.provve.accounts.JwsParsing.PREMIUM;

public class SkillsController implements SkillsApi {

    @Override
    public Future<ApiResponse<ResultResponse>> getExamResult(String skillName) {
        return null;
    }

    @Override
    public Future<ApiResponse<Examinees>> listExaminees(CollectionAuthenticatedRequest collectionAuthenticatedRequest) {
        try {
            Map<String, Object> params = Jackson.convertToMap(collectionAuthenticatedRequest);
            String failureMessage = Validation.validateCollectionAuthenticatedRequest(params);
            if (failureMessage != null && !failureMessage.isEmpty()) throw new ValidationError(failureMessage);

            boolean premium = Boolean.parseBoolean(JwsParsing.parseAuth(collectionAuthenticatedRequest.getAuthToken(), PREMIUM));
            if (!premium) {
                throw new AccessDenied("You have to buy premium access first");
            }

            return Future.succeededFuture(new ApiResponse<>(200, Result.listExaminees(collectionAuthenticatedRequest)));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        } catch (AccessDenied e) {
            return Future.failedFuture(new HttpException(e, 403));
        }
    }

    @Override
    public Future<ApiResponse<Results>> listResults(String skillName, CollectionAuthenticatedRequest request) {
        try {
            Map<String, Object> params = Jackson.convertToMap(request);
            String failureMessage = Validation.validateCollectionAuthenticatedRequest(params);
            if (failureMessage != null && !failureMessage.isEmpty()) throw new ValidationError(failureMessage);

            return Future.succeededFuture(new ApiResponse<>(200, Result.list(request)));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        }
    }

    @Override
    public Future<ApiResponse<Skills>> listSkills(CollectionRequest collectionRequest) {
        try {
            Map<String, Object> params = Jackson.convertToMap(collectionRequest);
            String failureMessage = Validation.validateCollectionRequest(params);
            if (failureMessage != null && !failureMessage.isEmpty()) throw new ValidationError(failureMessage);

            return Future.succeededFuture(new ApiResponse<>(200, Skill.list(collectionRequest)));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        }
    }

    @Override
    public Future<ApiResponse<Void>> submitExamSolution(String skillName, SubmitExamSolutionRequest submitExamSolutionRequest) {
        Map<String, Object> params = Jackson.convertToMap(submitExamSolutionRequest);
        var examinee = JwsParsing.parseTrust(submitExamSolutionRequest.getTrustToken(), JWT_SUBJECT);
        var accepted = tech.provve.validation.Validation.validate(examinee,
                                                                  skillName,
                                                                  Path.of(submitExamSolutionRequest.getSolution()
                                                                                                   .uploadedFileName()));
        if (accepted) {
            return Future.succeededFuture(new ApiResponse<>(200));
        }

        return Future.succeededFuture(new ApiResponse<>(202));
    }

}
