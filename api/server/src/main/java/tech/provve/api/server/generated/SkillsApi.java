package tech.provve.api.server.generated;

import io.vertx.core.Future;
import io.vertx.ext.web.FileUpload;
import tech.provve.api.server.ApiResponse;
import tech.provve.api.server.generated.dto.*;

import java.util.List;

public interface SkillsApi {

    Future<ApiResponse<Void>> examsExamIdResultGet(java.util.UUID examId);

    Future<ApiResponse<Notification>> examsIdSolutionPost(java.util.UUID id, FileUpload solution);

    Future<ApiResponse<List<SkillResponse>>> skillsGet(Pagination pagination, Filter filter);

    Future<ApiResponse<ExamResponse>> skillsSkillIdExamsGet(Pagination pagination, Filter filter);

    Future<ApiResponse<List<ResultResponse>>> skillsSkillIdResultsGet(Pagination pagination, Filter filter);
}
