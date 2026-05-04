package tech.provve.skill.service.domain;

import tech.provve.api.server.generated.dto.*;
import tech.provve.skill.exception.*;

public interface VoteService {

    void create(SkillAddVote skillAddVote) throws VoteAlreadyExists;
    void create(SkillDelVote skillDelVote) throws VoteAlreadyExists;
    void create(ExamAddVote examAddVote) throws VoteAlreadyExists;
    Votes list(CollectionRequest collectionRequest);

    void addComment(AddCommentRequest request, String voteName);
    void editComment(EditCommentRequest request) throws CommentFromAnotherAuthor;

    void deleteComment(DeleteCommentRequest request) throws CommentFromAnotherAuthor;

    /**
     * Give a vote on vote.
     */
    void cast(String voteName, CastVoteRequest castVoteRequest) throws VoteNotFound, CastAlreadyExists, AuthorCannotVote;

    /**
     * Complete the vote
     *
     * @return true if the vote is succeeded
     */
    boolean end(String voteName);

}
