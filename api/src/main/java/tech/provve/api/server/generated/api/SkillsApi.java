package tech.provve.api.server.generated.api;

import io.vertx.core.Future;
import io.vertx.ext.web.FileUpload;
import tech.provve.api.server.generated.ApiResponse;
import tech.provve.api.server.generated.dto.*;

import java.util.List;

public interface SkillsApi {
    Future<ApiResponse<List<ResultResponse>>> getResultsBySkill(String skillName, Pagination pagination, Filter filter);
    Future<ApiResponse<ExamResponse>> listExamsBySkill(Pagination pagination, Filter filter);
    Future<ApiResponse<List<SkillResponse>>> listSkills(Pagination pagination, Filter filter);
    Future<ApiResponse<Void>> submitExamSolution(String name, FileUpload solution);
    Future<ApiResponse<ResultResponse>> viewExamResult(String examName);
}
