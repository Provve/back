package tech.provve.api.server.generated.api;

import tech.provve.api.server.generated.dto.AddCommentRequest;
import tech.provve.api.server.generated.dto.CastVoteRequest;
import tech.provve.api.server.generated.dto.CollectionRequest;
import tech.provve.api.server.generated.dto.Comments;
import tech.provve.api.server.generated.dto.DeleteCommentRequest;
import tech.provve.api.server.generated.dto.EditCommentRequest;
import tech.provve.api.server.generated.dto.Error;
import tech.provve.api.server.generated.dto.ExamAddVote;
import tech.provve.api.server.generated.dto.ListCommentsRequest;
import tech.provve.api.server.generated.dto.ReplyCommentRequest;
import tech.provve.api.server.generated.dto.SkillAddVote;
import tech.provve.api.server.generated.dto.SkillDelVote;
import tech.provve.api.server.generated.dto.Votes;

import tech.provve.api.server.generated.ApiResponse;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;

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
