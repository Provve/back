package tech.provve.api.generated.api;

import io.vertx.core.Future;
import tech.provve.api.ApiResponse;
import tech.provve.api.generated.dto.*;

public interface SkillsApi {
    Future<ApiResponse<ResultResponse>> getExamResult(String skillName);
    Future<ApiResponse<Examinees>> listExaminees(CollectionAuthenticatedRequest collectionAuthenticatedRequest);
    Future<ApiResponse<Results>> listResults(String skillName, CollectionAuthenticatedRequest collectionAuthenticatedRequest);
    Future<ApiResponse<Skills>> listSkills(CollectionRequest collectionRequest);
    Future<ApiResponse<Void>> submitExamSolution(String skillName, SubmitExamSolutionRequest submitExamSolutionRequest);
}
