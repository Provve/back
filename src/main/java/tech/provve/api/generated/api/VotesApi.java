package tech.provve.api.generated.api;

import tech.provve.api.generated.dto.AddCommentRequest;
import tech.provve.api.generated.dto.CastVoteRequest;
import tech.provve.api.generated.dto.CollectionRequest;
import tech.provve.api.generated.dto.Comments;
import tech.provve.api.generated.dto.DeleteCommentRequest;
import tech.provve.api.generated.dto.EditCommentRequest;
import tech.provve.api.generated.dto.Error;
import tech.provve.api.generated.dto.ListCommentsRequest;
import tech.provve.api.generated.dto.ReplyCommentRequest;
import tech.provve.api.generated.dto.SkillAddVote;
import tech.provve.api.generated.dto.SkillArchiveVote;
import tech.provve.api.generated.dto.Votes;

import tech.provve.api.ApiResponse;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;

public interface VotesApi {
    Future<ApiResponse<Void>> addComment(AddCommentRequest addCommentRequest);
    Future<ApiResponse<Void>> castVote(String name, CastVoteRequest castVoteRequest);
    Future<ApiResponse<Void>> createSkillAddVote(SkillAddVote skillAddVote);

    Future<ApiResponse<Void>> createSkillArchiveVote(SkillArchiveVote skillArchiveVote);
    Future<ApiResponse<Void>> deleteComment(DeleteCommentRequest deleteCommentRequest);
    Future<ApiResponse<Void>> editComment(EditCommentRequest editCommentRequest);
    Future<ApiResponse<Comments>> listComments(ListCommentsRequest listCommentsRequest);
    Future<ApiResponse<Votes>> listVotes(CollectionRequest collectionRequest);
    Future<ApiResponse<Void>> replyOnComment(ReplyCommentRequest replyCommentRequest);
}
