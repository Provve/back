package tech.provve.skill.service.application;

import tech.provve.api.server.generated.dto.ExamAddVote;
import tech.provve.api.server.generated.dto.SkillAddVote;
import tech.provve.api.server.generated.dto.SkillDelVote;
import tech.provve.skill.exception.SkillAlreadyExists;
import tech.provve.skill.exception.VoteAlreadyExists;

public interface VoteService {

    void create(SkillAddVote skillAddVote) throws VoteAlreadyExists, SkillAlreadyExists;

    void create(SkillDelVote skillDelVote) throws VoteAlreadyExists;

    void create(ExamAddVote examAddVote) throws VoteAlreadyExists;

}
