package tech.provve.api.server.generated.api;

import tech.provve.api.server.generated.dto.CommentResponse;
import tech.provve.api.server.generated.dto.Filter;
import tech.provve.api.server.generated.dto.Notification;
import tech.provve.api.server.generated.dto.Pagination;
import tech.provve.api.server.generated.dto.VoteResponse;
import tech.provve.api.server.generated.dto.VotesIdCommentsPostRequest;
import tech.provve.api.server.generated.dto.VotesIdVotingPostRequest;
import tech.provve.api.server.generated.dto.VotesPostRequest;

import tech.provve.api.server.generated.ApiResponse;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;

public interface VotesApi {
    Future<ApiResponse<List<VoteResponse>>> votesGet(Pagination pagination, Filter filter);
    Future<ApiResponse<List<CommentResponse>>> votesIdCommentsGet(java.util.UUID id);
    Future<ApiResponse<Void>> votesIdCommentsPost(java.util.UUID id, VotesIdCommentsPostRequest votesIdCommentsPostRequest);
    Future<ApiResponse<Void>> votesIdVotingPost(java.util.UUID id, VotesIdVotingPostRequest votesIdVotingPostRequest);
    Future<ApiResponse<Void>> votesPost(VotesPostRequest votesPostRequest);
    Future<ApiResponse<Void>> votesVoteIdCommentsCommentIdDelete(java.util.UUID voteId, java.util.UUID commentId);
    Future<ApiResponse<Void>> votesVoteIdCommentsCommentIdPut(java.util.UUID voteId, java.util.UUID commentId);
}
