package tech.provve.api.server.generated;

import io.vertx.core.Future;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.handler.HttpException;
import tech.provve.api.server.ApiResponse;
import tech.provve.api.server.generated.dto.*;

import java.util.List;

// Implement this class

public class SkillsApiImpl implements SkillsApi {

    public Future<ApiResponse<Void>> examsExamIdResultGet(java.util.UUID examId) {
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<Notification>> examsIdSolutionPost(java.util.UUID id, FileUpload solution) {
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<List<SkillResponse>>> skillsGet(Pagination pagination, Filter filter) {
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<ExamResponse>> skillsSkillIdExamsGet(Pagination pagination, Filter filter) {
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<List<ResultResponse>>> skillsSkillIdResultsGet(Pagination pagination, Filter filter) {
        return Future.failedFuture(new HttpException(501));
    }

}
