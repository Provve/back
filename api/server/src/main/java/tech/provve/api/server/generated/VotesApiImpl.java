package tech.provve.api.server.generated;

import io.vertx.core.Future;
import io.vertx.ext.web.handler.HttpException;
import tech.provve.api.server.ApiResponse;
import tech.provve.api.server.generated.dto.*;

import java.util.List;

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
