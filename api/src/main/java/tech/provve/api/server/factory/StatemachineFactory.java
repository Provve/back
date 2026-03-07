package tech.provve.api.server.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.avaje.inject.Bean;
import io.avaje.inject.Factory;
import jakarta.inject.Named;
import tech.provve.accounts.domain.model.Account;
import tech.provve.accounts.repository.AccountRepository;
import tech.provve.libs.scheduling.Scheduling;
import tech.provve.notification.domain.value.AuthoredExamNotSaved;
import tech.provve.notification.domain.value.AuthoredExamSaved;
import tech.provve.notification.domain.value.RecipientRequisites;
import tech.provve.notification.service.NotificationSendingService;
import tech.provve.skill.domain.entity.Vote;
import tech.provve.skill.repository.VoteRepository;
import tech.provve.statemachine.SaveExamMachine;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Factory
public class StatemachineFactory {

    @Bean
    @Named(SaveExamMachine.VALIDATION_ERROR_NOTIFICATION_SENDER)
    public BiConsumer<String, String> validationErrorNotificationSender(NotificationSendingService notificationSendingService, AccountRepository accountRepository) {
        return (exam, author) -> {
            String email = accountRepository.findByLogin(author)
                                            .map(Account::email)
                                            .orElse(null);
            notificationSendingService.send(new AuthoredExamNotSaved(new RecipientRequisites(author, email), exam));
        };
    }

    @Bean
    @Named(SaveExamMachine.EXAM_SAVED_NOTIFICATION_SENDER)
    public BiConsumer<String, String> examSavedNotificationSender(NotificationSendingService notificationSendingService, AccountRepository accountRepository) {
        return (exam, author) -> {
            String email = accountRepository.findByLogin(author)
                                            .map(Account::email)
                                            .orElse(null);
            notificationSendingService.send(new AuthoredExamSaved(new RecipientRequisites(author, email), exam));
        };
    }

    @Bean
    @Named(SaveExamMachine.DELAYED_EXAM_VOTE_CREATOR)
    public Consumer<String> delayedExamVoteCreator(VoteRepository voteRepository, Scheduling scheduling, ObjectMapper objectMapper, Supplier<LocalDateTime> deadlineSupplier) {
        return delayedVoteJson -> {
            try {
                var deadline = deadlineSupplier.get();
                var vote = objectMapper.readValue(delayedVoteJson, Vote.class)
                                       .toBuilder()
                                       .deadline(deadline)
                                       .build();
                voteRepository.save(vote);
                scheduling.addExam(vote.name(), deadline.toInstant(ZoneOffset.UTC));
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Couldn't create Vote from given json:" + e);
            }
        };
    }


}
