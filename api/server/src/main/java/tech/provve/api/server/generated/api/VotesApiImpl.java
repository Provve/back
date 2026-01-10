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
import io.vertx.ext.web.handler.HttpException;

import java.util.List;
import java.util.Map;

// Implement this class

public class VotesApiImpl implements VotesApi {
    public Future<ApiResponse<List<VoteResponse>>> votesGet(Pagination pagination, Filter filter) {
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<List<CommentResponse>>> votesIdCommentsGet(java.util.UUID id) {
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<Void>> votesIdCommentsPost(java.util.UUID id, VotesIdCommentsPostRequest votesIdCommentsPostRequest) {
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<Void>> votesIdVotingPost(java.util.UUID id, VotesIdVotingPostRequest votesIdVotingPostRequest) {
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<Void>> votesPost(VotesPostRequest votesPostRequest) {
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<Void>> votesVoteIdCommentsCommentIdDelete(java.util.UUID voteId, java.util.UUID commentId) {
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<Void>> votesVoteIdCommentsCommentIdPut(java.util.UUID voteId, java.util.UUID commentId) {
        return Future.failedFuture(new HttpException(501));
    }

}
