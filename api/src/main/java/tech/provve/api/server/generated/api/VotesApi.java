package tech.provve.api.server.generated.api;

import io.vertx.core.Future;
import tech.provve.api.server.generated.ApiResponse;
import tech.provve.api.server.generated.dto.*;

public interface VotesApi {
    Future<ApiResponse<Void>> addComment(AddCommentRequest addCommentRequest);
    Future<ApiResponse<Void>> castVote(String name, CastVoteRequest castVoteRequest);
    Future<ApiResponse<Void>> createExamAddVote(ExamAddVote examAddVote);
    Future<ApiResponse<Void>> createSkillAddVote(SkillAddVote skillAddVote);
    Future<ApiResponse<Void>> createSkillDelVote(SkillDelVote skillDelVote);
    Future<ApiResponse<Void>> deleteComment(DeleteCommentRequest deleteCommentRequest);
    Future<ApiResponse<Void>> editComment(EditCommentRequest editCommentRequest);
    Future<ApiResponse<Comments>> listComments(ListCommentsRequest listCommentsRequest);
    Future<ApiResponse<Votes>> listVotes(CollectionRequest collectionRequest);
    Future<ApiResponse<Void>> replyOnComment(ReplyCommentRequest replyCommentRequest);
}
