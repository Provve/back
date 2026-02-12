package tech.provve.api.server.generated.api;

import tech.provve.api.server.generated.dto.AddCommentOnVoteRequest;
import tech.provve.api.server.generated.dto.CastVoteRequest;
import tech.provve.api.server.generated.dto.CommentResponse;
import tech.provve.api.server.generated.dto.CreateVoteRequest;
import tech.provve.api.server.generated.dto.Filter;
import tech.provve.api.server.generated.dto.Pagination;
import tech.provve.api.server.generated.dto.VoteResponse;

import tech.provve.api.server.generated.ApiResponse;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;

public interface VotesApi {
    Future<ApiResponse<Void>> addCommentOnVote(java.util.UUID id, AddCommentOnVoteRequest addCommentOnVoteRequest);
    Future<ApiResponse<Void>> castVote(java.util.UUID id, CastVoteRequest castVoteRequest);
    Future<ApiResponse<Void>> createVote(CreateVoteRequest createVoteRequest);
    Future<ApiResponse<Void>> deleteCommentOnVote(java.util.UUID voteId, java.util.UUID commentId);
    Future<ApiResponse<Void>> editCommentOnVote(java.util.UUID voteId, java.util.UUID commentId);
    Future<ApiResponse<List<CommentResponse>>> listCommentsOnVote(java.util.UUID id);
    Future<ApiResponse<List<VoteResponse>>> listVotes(Pagination pagination, Filter filter);
}
