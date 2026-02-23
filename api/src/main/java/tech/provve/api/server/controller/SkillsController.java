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

    @Override
    public Future<ApiResponse<List<ResultResponse>>> getResultsBySkill(String skillName, Pagination pagination, Filter filter) {
        return null;
    }

    public Future<ApiResponse<ExamResponse>> listExamsBySkill(Pagination pagination, Filter filter) {
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<List<SkillResponse>>> listSkills(Pagination pagination, Filter filter) {
        return Future.failedFuture(new HttpException(501));
    }

    @Override
    public Future<ApiResponse<Void>> submitExamSolution(String name, FileUpload solution) {
        // использовать метод multipart для загрузки архива в s3 и клиент AWS CRT-based
        return null;
    }

    @Override
    public Future<ApiResponse<ResultResponse>> viewExamResult(String examName) {
        return null;
    }

}
