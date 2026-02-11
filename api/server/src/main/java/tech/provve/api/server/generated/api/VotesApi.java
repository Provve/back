package tech.provve.api.server.generated.api;

import io.vertx.core.Future;
import tech.provve.api.server.generated.ApiResponse;
import tech.provve.api.server.generated.dto.*;

import java.util.List;

public interface VotesApi {
    Future<ApiResponse<Void>> addCommentOnVote(java.util.UUID id, AddCommentOnVoteRequest addCommentOnVoteRequest);
    Future<ApiResponse<Void>> castVote(java.util.UUID id, CastVoteRequest castVoteRequest);
    Future<ApiResponse<Void>> createVote(CreateVoteRequest createVoteRequest);
    Future<ApiResponse<Void>> deleteCommentOnVote(java.util.UUID voteId, java.util.UUID commentId);
    Future<ApiResponse<Void>> editCommentOnVote(java.util.UUID voteId, java.util.UUID commentId);
    Future<ApiResponse<List<CommentResponse>>> listCommentsOnVote(java.util.UUID id);
    Future<ApiResponse<List<VoteResponse>>> listVotes(Pagination pagination, Filter filter);
}
