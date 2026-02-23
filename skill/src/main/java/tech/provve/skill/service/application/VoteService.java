package tech.provve.skill.service.application;

import tech.provve.api.server.generated.dto.ExamAddVote;
import tech.provve.api.server.generated.dto.SkillAddVote;
import tech.provve.api.server.generated.dto.SkillDelVote;
import tech.provve.skill.exception.ExamAlreadyExists;
import tech.provve.skill.exception.SkillAlreadyExists;
import tech.provve.skill.exception.VoteAlreadyExists;

public interface VoteService {

    void create(SkillAddVote skillAddVote) throws VoteAlreadyExists, SkillAlreadyExists;

    void create(SkillDelVote skillDelVote) throws VoteAlreadyExists;

    void create(ExamAddVote examAddVote) throws VoteAlreadyExists, ExamAlreadyExists;

    /**
     * Complete the vote
     *
     * @return true if the vote is existing and has more positive reactions
     */
    boolean end(String voteName);

}
