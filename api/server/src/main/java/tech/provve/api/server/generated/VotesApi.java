package tech.provve.api.server.generated;

import io.vertx.core.Future;
import tech.provve.api.server.ApiResponse;
import tech.provve.api.server.generated.dto.*;

import java.util.List;

public interface VotesApi {

    Future<ApiResponse<List<VoteResponse>>> votesGet(Pagination pagination, Filter filter);

    Future<ApiResponse<List<CommentResponse>>> votesIdCommentsGet(java.util.UUID id);

    Future<ApiResponse<Void>> votesIdCommentsPost(java.util.UUID id, VotesIdCommentsPostRequest votesIdCommentsPostRequest);

    Future<ApiResponse<Void>> votesIdVotingPost(java.util.UUID id, VotesIdVotingPostRequest votesIdVotingPostRequest);

    Future<ApiResponse<Void>> votesPost(VotesPostRequest votesPostRequest);

    Future<ApiResponse<Void>> votesVoteIdCommentsCommentIdDelete(java.util.UUID voteId, java.util.UUID commentId);

    Future<ApiResponse<Void>> votesVoteIdCommentsCommentIdPut(java.util.UUID voteId, java.util.UUID commentId);
}
