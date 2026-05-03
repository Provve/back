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
    public Future<ApiResponse<Void>> addComment(String voteName, AddCommentRequest addCommentRequest) {
        return null;
    }

    @Override
    public Future<ApiResponse<Void>> deleteComment(String voteName, Integer commentId) {
        return null;
    }

    @Override
    public Future<ApiResponse<Void>> editComment(String voteName, Integer commentId) {
        return null;
    }

    @Override
    public Future<ApiResponse<Comments>> listComments(String name) {
        return null;
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
