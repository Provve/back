package tech.provve.api.server.generated.api;

import tech.provve.api.server.generated.dto.AddCommentOnVoteRequest;
import tech.provve.api.server.generated.dto.CastVoteRequest;
import tech.provve.api.server.generated.dto.CommentResponse;
import tech.provve.api.server.generated.dto.CreateVoteRequest;
import tech.provve.api.server.generated.dto.Filter;
import tech.provve.api.server.generated.dto.Notification;
import tech.provve.api.server.generated.dto.Pagination;
import tech.provve.api.server.generated.dto.VoteResponse;

import tech.provve.api.server.generated.ApiResponse;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.handler.HttpException;

import java.util.List;
import java.util.Map;

// Implement this class

public class VotesApiImpl implements VotesApi {

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
