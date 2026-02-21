package tech.provve.api.server.generated.api;

import tech.provve.api.server.generated.dto.AddCommentOnVoteRequest;
import tech.provve.api.server.generated.dto.CastVoteRequest;
import tech.provve.api.server.generated.dto.CommentResponse;
import tech.provve.api.server.generated.dto.Error;
import tech.provve.api.server.generated.dto.ExamAddVote;
import tech.provve.api.server.generated.dto.Filter;
import tech.provve.api.server.generated.dto.Pagination;
import tech.provve.api.server.generated.dto.SkillAddVote;
import tech.provve.api.server.generated.dto.SkillDelVote;
import tech.provve.api.server.generated.dto.VoteResponse;

import tech.provve.api.server.generated.ApiResponse;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;

public interface VotesApi {

    Future<ApiResponse<Void>> addCommentOnVote(String name, AddCommentOnVoteRequest addCommentOnVoteRequest);

    Future<ApiResponse<Void>> castVote(String name, CastVoteRequest castVoteRequest);

    Future<ApiResponse<Void>> createExamAddVote(ExamAddVote examAddVote);

    Future<ApiResponse<Void>> createSkillAddVote(SkillAddVote skillAddVote);

    Future<ApiResponse<Void>> createSkillDelVote(SkillDelVote skillDelVote);

    Future<ApiResponse<Void>> deleteCommentOnVote(String voteName, Integer commentId);

    Future<ApiResponse<Void>> editCommentOnVote(String voteName, Integer commentId);

    Future<ApiResponse<List<CommentResponse>>> listCommentsOnVote(String name);

    Future<ApiResponse<List<VoteResponse>>> listVotes(Pagination pagination, Filter filter);
}
