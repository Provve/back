package tech.provve.skill.service.domain;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import tech.provve.api.server.generated.dto.CollectionRequest;
import tech.provve.api.server.generated.dto.Cursor;
import tech.provve.api.server.generated.dto.ExamResponse;
import tech.provve.api.server.generated.dto.Exams;
import tech.provve.skill.mapper.exam.ExamResponseMapper;
import tech.provve.skill.repository.ExamRepository;

import java.util.List;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;

    @Override
    public Exams list(CollectionRequest request) {
        List<ExamResponse> all = examRepository.getAll(request.getFilter(),
                                                       request.getPagination()
                                                              .getPrevious(),
                                                       request.getPagination()
                                                              .getSize())
                                               .stream()
                                               .map(ExamResponseMapper.INST::map)
                                               .toList();
        if (all.isEmpty()) {
            return new Exams(all, new Cursor(""));
        }

        var cursor = new Cursor(all.getLast()
                                   .getName());
        return new Exams(all, cursor);
    }
}
