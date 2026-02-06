package tech.provve.api.server.controller;

import io.vertx.core.Future;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.handler.HttpException;
import jakarta.inject.Singleton;
import tech.provve.api.server.generated.ApiResponse;
import tech.provve.api.server.generated.api.SkillsApi;
import tech.provve.api.server.generated.dto.*;

import java.util.List;

@Singleton
public class SkillsController implements SkillsApi {

    public Future<ApiResponse<List<ResultResponse>>> getResultsBySkill(Pagination pagination, Filter filter) {
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<ExamResponse>> listExamsBySkill(Pagination pagination, Filter filter) {
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<List<SkillResponse>>> listSkills(Pagination pagination, Filter filter) {
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<Notification>> submitExamSolution(java.util.UUID id, FileUpload solution) {
        // использовать метод multipart для загрузки архива в s3 и клиент AWS CRT-based
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<Void>> viewExamResult(java.util.UUID examId) {
        return Future.failedFuture(new HttpException(501));
    }

}
