package tech.provve.api.server.generated.api;

import io.vertx.core.Future;
import tech.provve.api.server.generated.ApiResponse;
import tech.provve.api.server.generated.dto.*;

public interface SkillsApi {
    Future<ApiResponse<Exams>> listExams(String skillName, CollectionRequest collectionRequest);
    Future<ApiResponse<Results>> listResults(String skillName, CollectionAuthenticatedRequest collectionAuthenticatedRequest);
    Future<ApiResponse<Skills>> listSkills(CollectionRequest collectionRequest);
    Future<ApiResponse<Void>> submitExamSolution(String name, SubmitExamSolutionRequest submitExamSolutionRequest);
    Future<ApiResponse<ResultResponse>> viewExamResult(String examName);
}
