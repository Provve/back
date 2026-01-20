package tech.provve.api.server.controller;

import io.vertx.core.Future;
import io.vertx.ext.web.handler.HttpException;
import jakarta.inject.Singleton;
import tech.provve.api.server.generated.ApiResponse;
import tech.provve.api.server.generated.api.VotesApi;
import tech.provve.api.server.generated.dto.*;

import java.util.List;

@Singleton
public class VotesController implements VotesApi {

    public Future<ApiResponse<Void>> addCommentOnVote(java.util.UUID id, AddCommentOnVoteRequest addCommentOnVoteRequest) {
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<Void>> castVote(java.util.UUID id, CastVoteRequest castVoteRequest) {
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<Void>> createVote(CreateVoteRequest createVoteRequest) {
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<Void>> deleteCommentOnVote(java.util.UUID voteId, java.util.UUID commentId) {
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<Void>> editCommentOnVote(java.util.UUID voteId, java.util.UUID commentId) {
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<List<CommentResponse>>> listCommentsOnVote(java.util.UUID id) {
        return Future.failedFuture(new HttpException(501));
    }

    public Future<ApiResponse<List<VoteResponse>>> listVotes(Pagination pagination, Filter filter) {
        return Future.failedFuture(new HttpException(501));
    }

}
