package tech.provve.skill.service.application;

import io.avaje.inject.test.InjectTest;
import jakarta.inject.Inject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import tech.provve.accounts.service.JwsParsingService;
import tech.provve.api.server.generated.dto.CreateSessionRequest;
import tech.provve.skill.exception.ExamPassTwice;
import tech.provve.skill.repository.ResultRepository;
import tech.provve.skill.repository.SessionRepository;
import tech.provve.skill.repository.VoteRepository;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@InjectTest
class SessionServiceTest {

    @Mock
    VoteRepository voteRepository;

    @Mock
    JwsParsingService jwsParsingService;

    @Mock
    SessionRepository sessionRepository;

    @Mock
    ResultRepository resultRepository;

    @Inject
    SessionService sessionService;

    @Test
    void create_sessionAlreadyExists_exception() {
        // arrange
        when(sessionRepository.exists(any())).thenReturn(true);
        var request = new CreateSessionRequest("", URI.create(""), "");

        // act assert
        assertThrows(ExamPassTwice.class, () -> sessionService.create(request));
    }

    @Test
    void create_examResultAlreadyExists_exception() {
        // arrange
        when(resultRepository.exists(any())).thenReturn(true);
        var request = new CreateSessionRequest("", URI.create(""), "");

        // act assert
        assertThrows(ExamPassTwice.class, () -> sessionService.create(request));
    }

    @Test
    void create_new_uniqueNoncePerSession() {
        // arrange
        var request = new CreateSessionRequest("", URI.create(""), "");

        // act
        var res1 = sessionService.create(request);
        var res2 = sessionService.create(request);

        // assert
        assertNotSame(res1.getNonce(), res2.getNonce());
    }

}