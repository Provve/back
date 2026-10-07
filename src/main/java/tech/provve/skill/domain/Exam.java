package tech.provve.skill.domain;

import tech.provve.api.generated.dto.CollectionRequest;
import tech.provve.api.generated.dto.Cursor;
import tech.provve.api.generated.dto.ExamResponse;
import tech.provve.api.generated.dto.Exams;
import tech.provve.util.Jackson;
import tech.provve.util.Storage;

import java.util.List;

public class Exam {

    public static Exams list(CollectionRequest request) {
        List<ExamResponse> all = Storage.getAllExams(request.getFilter(),
                                                     request.getPagination()
                                                            .getPrevious(),
                                                     request.getPagination()
                                                            .getSize())
                                        .stream()
                                        .map(exam -> Jackson.convertToClass(exam, ExamResponse.class))
                                        .toList();
        if (all.isEmpty()) {
            return new Exams(all, new Cursor(""));
        }

        var cursor = new Cursor(all.getLast()
                                   .getName());
        return new Exams(all, cursor);
    }
}
