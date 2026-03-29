package tech.provve.api.server.generated.api;

import io.vertx.core.Future;
import tech.provve.api.server.generated.ApiResponse;
import tech.provve.api.server.generated.dto.*;

public interface VotesApi {
    Future<ApiResponse<Void>> addCommentOnVote(String voteName, AddCommentOnVoteRequest addCommentOnVoteRequest);
    Future<ApiResponse<Void>> castVote(String name, CastVoteRequest castVoteRequest);
    Future<ApiResponse<Void>> createExamAddVote(ExamAddVote examAddVote);
    Future<ApiResponse<Void>> createSkillAddVote(SkillAddVote skillAddVote);
    Future<ApiResponse<Void>> createSkillDelVote(SkillDelVote skillDelVote);
    Future<ApiResponse<Void>> deleteCommentOnVote(String voteName, Integer commentId);
    Future<ApiResponse<Void>> editCommentOnVote(String voteName, Integer commentId);
    Future<ApiResponse<Comments>> listComments(String voteName);
    Future<ApiResponse<Votes>> listVotes(CollectionRequest collectionRequest);
}
