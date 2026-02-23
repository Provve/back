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
import tech.provve.api.server.service.DtoValidatingService;
import tech.provve.skill.exception.SkillAlreadyExists;
import tech.provve.skill.exception.VoteAlreadyExists;
import tech.provve.skill.service.application.VoteService;

import java.util.List;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class VotesController implements VotesApi {

    private final DtoValidatingService validatingService;
    private final VoteService voteService;

    @Override
    public Future<ApiResponse<Void>> addCommentOnVote(String name, AddCommentOnVoteRequest addCommentOnVoteRequest) {
        return null;
    }

    @Override
    public Future<ApiResponse<Void>> castVote(String name, CastVoteRequest castVoteRequest) {
        return null;
    }

    @Override
    public Future<ApiResponse<Void>> createExamAddVote(ExamAddVote examAddVote) {
        // использовать метод multipart для загрузки архива в s3 и клиент AWS CRT-based
        return null;
    }

    @Override
    public Future<ApiResponse<Void>> createSkillAddVote(SkillAddVote skillAddVote) {
        try {
            validatingService.validate(new tech.provve.api.server.validation.dto.SkillAddVote(
                    skillAddVote.getName(),
                    skillAddVote.getArguments(),
                    skillAddVote.getAuthToken()
            ));
            voteService.create(skillAddVote);
            return Future.succeededFuture(new ApiResponse<>(200));
        } catch (ValidationError e) {
            return Future.failedFuture(new HttpException(e, 400));
        } catch (VoteAlreadyExists | SkillAlreadyExists e) {
            return Future.failedFuture(new HttpException(e, 409));
        }
    }

    @Override
    public Future<ApiResponse<Void>> createSkillDelVote(SkillDelVote skillDelVote) {
        try {
            validatingService.validate(new tech.provve.api.server.validation.dto.SkillDelVote(
                    skillDelVote.getName(),
                    skillDelVote.getArguments(),
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
    public Future<ApiResponse<Void>> deleteCommentOnVote(String voteName, Integer commentId) {
        return null;
    }

    @Override
    public Future<ApiResponse<Void>> editCommentOnVote(String voteName, Integer commentId) {
        return null;
    }

    @Override
    public Future<ApiResponse<List<CommentResponse>>> listCommentsOnVote(String name) {
        return null;
    }

    @Override
    public Future<ApiResponse<List<VoteResponse>>> listVotes(Pagination pagination, Filter filter) {
        return null;
    }
}
