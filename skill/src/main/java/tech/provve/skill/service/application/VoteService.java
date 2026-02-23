package tech.provve.skill.service.application;

import tech.provve.api.server.generated.dto.CastVoteRequest;
import tech.provve.api.server.generated.dto.ExamAddVote;
import tech.provve.api.server.generated.dto.SkillAddVote;
import tech.provve.api.server.generated.dto.SkillDelVote;
import tech.provve.skill.exception.*;

public interface VoteService {

    void create(SkillAddVote skillAddVote) throws VoteAlreadyExists, SkillAlreadyExists;

    void create(SkillDelVote skillDelVote) throws VoteAlreadyExists;

    void create(ExamAddVote examAddVote) throws VoteAlreadyExists, ExamAlreadyExists;

    /**
     * Give a vote on vote.
     */
    void cast(String voteName, CastVoteRequest castVoteRequest) throws VoteNotFound, CastAlreadyExists, AuthorCannotVote;

    /**
     * Complete the vote
     *
     * @return true if the vote is existing and has more positive reactions
     */
    boolean end(String voteName);

}
