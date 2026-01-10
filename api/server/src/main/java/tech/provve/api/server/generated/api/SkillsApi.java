package tech.provve.api.server.generated.api;

import tech.provve.api.server.generated.dto.ExamResponse;
import io.vertx.ext.web.FileUpload;
import tech.provve.api.server.generated.dto.Filter;
import tech.provve.api.server.generated.dto.Notification;
import tech.provve.api.server.generated.dto.Pagination;
import tech.provve.api.server.generated.dto.ResultResponse;
import tech.provve.api.server.generated.dto.SkillResponse;

import tech.provve.api.server.generated.ApiResponse;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;

public interface SkillsApi {
    Future<ApiResponse<Void>> examsExamIdResultGet(java.util.UUID examId);
    Future<ApiResponse<Notification>> examsIdSolutionPost(java.util.UUID id, FileUpload solution);
    Future<ApiResponse<List<SkillResponse>>> skillsGet(Pagination pagination, Filter filter);
    Future<ApiResponse<ExamResponse>> skillsSkillIdExamsGet(Pagination pagination, Filter filter);
    Future<ApiResponse<List<ResultResponse>>> skillsSkillIdResultsGet(Pagination pagination, Filter filter);
}
