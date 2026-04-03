package tech.provve.api.server.generated.api;

import tech.provve.api.server.generated.dto.CollectionAuthenticatedRequest;
import tech.provve.api.server.generated.dto.CollectionRequest;
import tech.provve.api.server.generated.dto.Error;
import tech.provve.api.server.generated.dto.Exams;
import tech.provve.api.server.generated.dto.ResultResponse;
import tech.provve.api.server.generated.dto.Results;
import tech.provve.api.server.generated.dto.Skills;
import tech.provve.api.server.generated.dto.SubmitExamSolutionRequest;

import tech.provve.api.server.generated.ApiResponse;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;

public interface SkillsApi {
    Future<ApiResponse<Exams>> listExams(String skillName, CollectionRequest collectionRequest);
    Future<ApiResponse<Results>> listResults(String skillName, CollectionAuthenticatedRequest collectionAuthenticatedRequest);
    Future<ApiResponse<Skills>> listSkills(CollectionRequest collectionRequest);

    Future<ApiResponse<Void>> submitExamSolution(String name, SubmitExamSolutionRequest submitExamSolutionRequest);
    Future<ApiResponse<ResultResponse>> viewExamResult(String examName);
}
