package tech.provve.api.server.generated.api;

import io.vertx.core.Future;
import io.vertx.ext.web.FileUpload;
import tech.provve.api.server.generated.ApiResponse;
import tech.provve.api.server.generated.dto.*;

public interface SkillsApi {
    Future<ApiResponse<Exams>> listExams(String skillName, CollectionRequest collectionRequest);
    Future<ApiResponse<Results>> listResults(String skillName, CollectionAuthenticatedRequest collectionAuthenticatedRequest);
    Future<ApiResponse<Skills>> listSkills(CollectionRequest collectionRequest);
    Future<ApiResponse<Void>> submitExamSolution(String name, FileUpload solution);
    Future<ApiResponse<ResultResponse>> viewExamResult(String examName);
}
