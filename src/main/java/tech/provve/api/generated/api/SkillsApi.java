package tech.provve.api.generated.api;

import tech.provve.api.generated.dto.CollectionAuthenticatedRequest;
import tech.provve.api.generated.dto.CollectionRequest;
import tech.provve.api.generated.dto.Error;
import tech.provve.api.generated.dto.Examinees;
import tech.provve.api.generated.dto.Exams;
import tech.provve.api.generated.dto.ResultResponse;
import tech.provve.api.generated.dto.Results;
import tech.provve.api.generated.dto.Skills;
import tech.provve.api.generated.dto.SubmitExamSolutionRequest;

import tech.provve.api.ApiResponse;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;

public interface SkillsApi {
    Future<ApiResponse<ResultResponse>> getExamResult(String examName);

    Future<ApiResponse<Examinees>> listExaminees(CollectionAuthenticatedRequest collectionAuthenticatedRequest);

    Future<ApiResponse<Exams>> listExams(String skillName, CollectionRequest collectionRequest);

    Future<ApiResponse<Results>> listResults(String skillName, CollectionAuthenticatedRequest collectionAuthenticatedRequest);

    Future<ApiResponse<Skills>> listSkills(CollectionRequest collectionRequest);

    Future<ApiResponse<Void>> submitExamSolution(String examName, SubmitExamSolutionRequest submitExamSolutionRequest);
}
