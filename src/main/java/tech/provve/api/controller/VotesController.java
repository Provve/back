package tech.provve.api.controller;

import io.vertx.core.Future;
import tech.provve.api.ApiResponse;
import tech.provve.api.exception.HttpException;
import tech.provve.api.exception.ValidationError;
import tech.provve.api.generated.api.VotesApi;
import tech.provve.api.generated.dto.*;
import tech.provve.api.generated.dto.*;
import tech.provve.constants.Entity;
import tech.provve.skill.exception.*;
import tech.provve.skill.XssSanitizer;
import tech.provve.skill.domain.Vote;
import tech.provve.statemachine.exception.StatemachineAlreadyExists;
import tech.provve.util.Jackson;
import tech.provve.util.Validation;

import java.util.Map;

public class VotesController implements VotesApi {

    @Override
    public Future<ApiResponse<Void>> castVote(String name, CastVoteRequest castVoteRequest) {
        try {
            Map<String, Object> params = Jackson.convertToMap(castVoteRequest);
            params.put(Entity.Vote.NAME, name);

            String failureMessage = Validation.validateCastVote(params);
            if (failureMessage != null && !failureMessage.isEmpty()) throw new ValidationError(failureMessage);

            Vote.cast(name, castVoteRequest);
            return Future.succeededFuture(new ApiResponse<>(200));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        } catch (AuthorCannotVote e) {
            return Future.failedFuture(new HttpException(e, 403));
        } catch (VoteNotFound e) {
            return Future.failedFuture(new HttpException(e, 404));
        } catch (CastAlreadyExists e) {
            return Future.failedFuture(new HttpException(e, 409));
        }
    }

    @Override
    public Future<ApiResponse<Void>> createExamAddVote(ExamAddVote examAddVote) {
        try {
            Map<String, Object> params = Jackson.convertToMap(examAddVote);
            String failureMessage = Validation.validateExamAddVote(params);
            if (failureMessage != null && !failureMessage.isEmpty()) throw new ValidationError(failureMessage);

            Vote.create(examAddVote);
            return Future.succeededFuture(new ApiResponse<>(202));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        } catch (SkillNotFound e) {
            return Future.failedFuture(new HttpException(e, 404));
        } catch (VoteAlreadyExists | StatemachineAlreadyExists e) {
            return Future.failedFuture(new HttpException(e, 409));
        }
    }

    @Override
    public Future<ApiResponse<Void>> createSkillAddVote(SkillAddVote skillAddVote) {
        try {
            Map<String, Object> params = Jackson.convertToMap(skillAddVote);
            String failureMessage = Validation.validateSkillAddVote(params);
            if (failureMessage != null && !failureMessage.isEmpty()) throw new ValidationError(failureMessage);

            Vote.create(skillAddVote);
            return Future.succeededFuture(new ApiResponse<>(200));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        } catch (VoteAlreadyExists e) {
            return Future.failedFuture(new HttpException(e, 409));
        }
    }

    @Override
    public Future<ApiResponse<Void>> createSkillDelVote(SkillDelVote skillDelVote) {
        try {
            Map<String, Object> params = Jackson.convertToMap(skillDelVote);
            String failureMessage = Validation.validateSkillDelVote(params);
            if (failureMessage != null && !failureMessage.isEmpty()) throw new ValidationError(failureMessage);

            Vote.create(skillDelVote);
            return Future.succeededFuture(new ApiResponse<>(200));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        } catch (VoteAlreadyExists e) {
            return Future.failedFuture(new HttpException(e, 409));
        }
    }

    @Override
    public Future<ApiResponse<Void>> deleteComment(DeleteCommentRequest deleteCommentRequest) {
        try {
            Map<String, Object> params = Jackson.convertToMap(deleteCommentRequest);
            String failureMessage = Validation.validateDeleteCommentRequest(params);
            if (failureMessage != null && !failureMessage.isEmpty()) throw new ValidationError(failureMessage);


            Vote.deleteComment(deleteCommentRequest);
            return Future.succeededFuture(new ApiResponse<>(200));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        } catch (CommentFromAnotherAuthor e) {
            return Future.failedFuture(new HttpException(e, 403));
        }
    }

    @Override
    public Future<ApiResponse<Void>> editComment(EditCommentRequest editCommentRequest) {
        try {
            editCommentRequest.setContent(XssSanitizer.sanitize(editCommentRequest.getContent()));
            Map<String, Object> params = Jackson.convertToMap(editCommentRequest);
            String failureMessage = Validation.validateEditCommentRequest(params);
            if (failureMessage != null && !failureMessage.isEmpty()) throw new ValidationError(failureMessage);

            Vote.editComment(editCommentRequest);
            return Future.succeededFuture(new ApiResponse<>(200));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        } catch (CommentFromAnotherAuthor e) {
            return Future.failedFuture(new HttpException(e, 403));
        }
    }

    @Override
    public Future<ApiResponse<Comments>> listComments(ListCommentsRequest listCommentsRequest) {
        return Future.succeededFuture(new ApiResponse<>(200, Vote.listComments(listCommentsRequest)));
    }

    @Override
    public Future<ApiResponse<Void>> addComment(AddCommentRequest addCommentRequest) {
        try {
            addCommentRequest.setContent(XssSanitizer.sanitize(addCommentRequest.getContent()));
            Map<String, Object> params = Jackson.convertToMap(addCommentRequest);
            String failureMessage = Validation.validateAddCommentRequest(params);
            if (failureMessage != null && !failureMessage.isEmpty()) throw new ValidationError(failureMessage);

            Vote.addComment(addCommentRequest, addCommentRequest.getVoteName());
            return Future.succeededFuture(new ApiResponse<>(200));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        }
    }

    @Override
    public Future<ApiResponse<Void>> replyOnComment(ReplyCommentRequest replyCommentRequest) {
        try {
            replyCommentRequest.setContent(XssSanitizer.sanitize(replyCommentRequest.getContent()));
            Map<String, Object> params = Jackson.convertToMap(replyCommentRequest);
            String failureMessage = Validation.validateReplyCommentRequest(params);
            if (failureMessage != null && !failureMessage.isEmpty()) throw new ValidationError(failureMessage);

            Vote.replyOnComment(replyCommentRequest);
            return Future.succeededFuture(new ApiResponse<>(200));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        }
    }

    @Override
    public Future<ApiResponse<Votes>> listVotes(CollectionRequest collectionRequest) {
        try {
            Map<String, Object> params = Jackson.convertToMap(collectionRequest);
            String failureMessage = Validation.validateCollectionRequest(params);
            if (failureMessage != null && !failureMessage.isEmpty()) throw new ValidationError(failureMessage);

            return Future.succeededFuture(new ApiResponse<>(200, Vote.list(collectionRequest)));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        }
    }

}
