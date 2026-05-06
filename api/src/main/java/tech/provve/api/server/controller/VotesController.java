package tech.provve.api.server.controller;

import io.vertx.core.Future;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import tech.provve.api.server.exception.HttpException;
import tech.provve.api.server.exception.ValidationError;
import tech.provve.api.server.generated.ApiResponse;
import tech.provve.api.server.generated.api.VotesApi;
import tech.provve.api.server.generated.dto.*;
import tech.provve.api.server.mapper.InputValidatorMapper;
import tech.provve.api.server.service.InputValidator;
import tech.provve.api.server.validation.dto.CastVote;
import tech.provve.skill.exception.*;
import tech.provve.skill.service.XssSanitizer;
import tech.provve.skill.service.domain.VoteService;
import tech.provve.statemachine.exception.StatemachineAlreadyExists;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class VotesController implements VotesApi {

    private final InputValidator validatingService;
    private final VoteService voteService;

    @Override
    public Future<ApiResponse<Void>> castVote(String name, CastVoteRequest castVoteRequest) {
        try {
            validatingService.validate(new CastVote(name, castVoteRequest.getAuthToken(), castVoteRequest.getPositiveReaction()));
            voteService.cast(name, castVoteRequest);
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
            validatingService.validate(InputValidatorMapper.INSTANCE.map(examAddVote));
            voteService.create(examAddVote);
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
            validatingService.validate(new tech.provve.api.server.validation.dto.SkillAddVote(
                    skillAddVote.getName(),
                    skillAddVote.getArguments(),
                    skillAddVote.getTags(),
                    skillAddVote.getAuthToken()
            ));
            voteService.create(skillAddVote);
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
            validatingService.validate(new tech.provve.api.server.validation.dto.SkillDelVote(
                    skillDelVote.getName(),
                    skillDelVote.getArguments(),
                    skillDelVote.getTags(),
                    skillDelVote.getAuthToken()
            ));
            voteService.create(skillDelVote);
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
            validatingService.validate(InputValidatorMapper.INSTANCE.map(deleteCommentRequest));

            voteService.deleteComment(deleteCommentRequest);
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
            validatingService.validate(InputValidatorMapper.INSTANCE.map(editCommentRequest));
            editCommentRequest.setContent(XssSanitizer.sanitize(editCommentRequest.getContent()));

            voteService.editComment(editCommentRequest);
            return Future.succeededFuture(new ApiResponse<>(200));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        } catch (CommentFromAnotherAuthor e) {
            return Future.failedFuture(new HttpException(e, 403));
        }
    }

    @Override
    public Future<ApiResponse<Comments>> listComments(ListCommentsRequest listCommentsRequest) {
        return Future.succeededFuture(new ApiResponse<>(200, voteService.listComments(listCommentsRequest)));
    }

    @Override
    public Future<ApiResponse<Void>> addComment(AddCommentRequest addCommentRequest) {
        try {
            validatingService.validate(InputValidatorMapper.INSTANCE.map(addCommentRequest));
            addCommentRequest.setContent(XssSanitizer.sanitize(addCommentRequest.getContent()));

            voteService.addComment(addCommentRequest, addCommentRequest.getVoteName());
            return Future.succeededFuture(new ApiResponse<>(200));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        }
    }

    @Override
    public Future<ApiResponse<Void>> replyOnComment(ReplyCommentRequest replyCommentRequest) {
        try {
            validatingService.validate(InputValidatorMapper.INSTANCE.map(replyCommentRequest));
            replyCommentRequest.setContent(XssSanitizer.sanitize(replyCommentRequest.getContent()));

            voteService.replyOnComment(replyCommentRequest);
            return Future.succeededFuture(new ApiResponse<>(200));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        }
    }

    @Override
    public Future<ApiResponse<Votes>> listVotes(CollectionRequest collectionRequest) {
        try {
            validatingService.validate(InputValidatorMapper.INSTANCE.map(collectionRequest));
            return Future.succeededFuture(new ApiResponse<>(200, voteService.list(collectionRequest)));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        }
    }

}
