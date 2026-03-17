package tech.provve.api.server.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kagkarlsson.scheduler.Scheduler;
import dev.failsafe.RetryPolicy;
import io.avaje.inject.Bean;
import io.avaje.inject.External;
import io.avaje.inject.Factory;
import io.vertx.core.Vertx;
import io.vertx.ext.auth.jwt.JWTAuth;
import jakarta.inject.Named;
import org.jooq.DSLContext;
import org.simplejavamail.api.mailer.Mailer;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import tech.provve.accounts.repository.AccountRepository;
import tech.provve.accounts.service.*;
import tech.provve.accounts.service.application.AccountService;
import tech.provve.accounts.service.application.AccountServiceImpl;
import tech.provve.libs.scheduling.Scheduling;
import tech.provve.notification.repository.NotificationRepository;
import tech.provve.notification.service.NotificationSendingService;
import tech.provve.notification.service.NotificationSendingServiceImpl;
import tech.provve.payment.gateway.robokassa.ApiClient;
import tech.provve.payment.repository.RobokassaInvoiceRepository;
import tech.provve.payment.service.application.PaymentService;
import tech.provve.payment.service.application.PaymentServiceImpl;
import tech.provve.skill.repository.ExamRepository;
import tech.provve.skill.repository.SkillRepository;
import tech.provve.skill.repository.VoteRepository;
import tech.provve.skill.service.domain.SkillService;
import tech.provve.skill.service.domain.SkillServiceImpl;
import tech.provve.skill.service.domain.VoteService;
import tech.provve.skill.service.domain.VoteServiceImpl;
import tech.provve.statemachine.repository.CheckSolutionRepository;
import tech.provve.statemachine.repository.SaveExamRepository;
import tech.provve.statemachine.service.ZipManipulator;
import tech.provve.statemachine.service.domain.StatemachineService;
import tech.provve.statemachine.service.domain.StatemachineServiceImpl;
import tech.provve.statemachine.specification.PrivateArchiveSpecification;
import terch.provve.libs.s3.S3Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.LocalDateTime;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static tech.provve.api.server.factory.HttpClientFactory.GET_PAYMENT_LINK_URL;
import static tech.provve.statemachine.SaveExamMachine.*;

@Factory
public class Services {

    @Bean
    public StatemachineService statemachineService(SaveExamRepository saveExamRepository,
                                                   CheckSolutionRepository checkSolutionRepository,
                                                   @Named(DELAYED_EXAM_VOTE_CREATOR)
                                                   Consumer<String> delayedExamVoteCreator,
                                                   @Named(VALIDATION_ERROR_NOTIFICATION_SENDER)
                                                   BiConsumer<String, String> validationErrorNotificationSender,
                                                   @Named(EXAM_SAVED_NOTIFICATION_SENDER)
                                                   BiConsumer<String, String> examSavedNotificationSender,
                                                   PrivateArchiveSpecification privateArchiveSpecification,
                                                   S3Service s3Service
    ) {
        return new StatemachineServiceImpl(
                saveExamRepository, checkSolutionRepository, delayedExamVoteCreator, validationErrorNotificationSender, examSavedNotificationSender,
                privateArchiveSpecification, s3Service
        );
    }

    @Bean
    public SaveExamRepository saveExamRepository(DSLContext dsl) {
        return new SaveExamRepository(dsl);
    }

    @Bean
    public CheckSolutionRepository checkSolutionRepository(DSLContext dsl) {
        return new CheckSolutionRepository(dsl);
    }

    @Bean
    public PrivateArchiveSpecification privateArchiveSpecification() {
        return new PrivateArchiveSpecification(new ZipManipulator());
    }

    @Bean
    public SkillService skillService(SkillRepository skillRepository) {
        return new SkillServiceImpl(skillRepository);
    }

    @Bean
    public ExamRepository examRepository(DSLContext dsl) {
        return new ExamRepository(dsl);
    }

    @Bean
    public SkillRepository skillRepository(DSLContext dsl) {
        return new SkillRepository(dsl);
    }

    @Bean
    public VoteService voteService(VoteRepository voteRepository,
                                   SkillRepository skillRepository,
                                   JwsParsingService jwsParsingService,
                                   Supplier<LocalDateTime> deadlineSupplier,
                                   Scheduling scheduling,
                                   S3Service s3Service,
                                   Vertx vertx,
                                   StatemachineService statemachineService,
                                   ObjectMapper objectMapper) {
        return new VoteServiceImpl(
                voteRepository, skillRepository, objectMapper, deadlineSupplier, jwsParsingService, scheduling, s3Service, vertx, statemachineService);
    }

    @Bean
    public VoteRepository voteRepository(DSLContext dsl) {
        return new VoteRepository(dsl);
    }

    @Bean
    public S3Service s3Service(S3Client s3Client, S3AsyncClient s3AsyncClient) {
        return new S3Service(s3Client, s3AsyncClient);
    }

    @Bean
    public RobokassaInvoiceRepository robokassaInvoiceRepository(@External DSLContext dsl) {
        return new RobokassaInvoiceRepository(dsl);
    }

    @Bean
    public HttpRequest.Builder getPaymentLinkBuilder() {
        return HttpRequest.newBuilder()
                          .uri(URI.create(GET_PAYMENT_LINK_URL));
    }

    @Bean
    public ApiClient apiClient(HttpRequest.Builder getPaymentLinkBuilder, HttpClient httpClient, RetryPolicy<String> retryPolicy) {
        return new ApiClient(getPaymentLinkBuilder, httpClient, retryPolicy);
    }

    @Bean
    public PaymentService paymentService(ApiClient apiClient,
                                         @External RobokassaInvoiceRepository invoiceRepository,
                                         @External AccountRepository accountRepository) {
        return new PaymentServiceImpl(apiClient, invoiceRepository, accountRepository);
    }

    @Bean
    public AccountService accountService(AccountRepository repository,
                                         JwtIssuingService jwtIssuingService,
                                         JwsParsingService jwsParsingService,
                                         PasswordHashingService passwordHashingService,
                                         NotificationSendingService notificationSendingService,
                                         Scheduling scheduling,
                                         Vertx vertx,
                                         S3Service s3Service) {
        return new AccountServiceImpl(
                repository,
                jwtIssuingService,
                jwsParsingService,
                passwordHashingService,
                notificationSendingService,
                scheduling,
                vertx,
                s3Service
        );
    }

    @Bean
    public Scheduling scheduling(Scheduler scheduler) {
        return new Scheduling(scheduler);
    }

    @Bean
    public PasswordHashingService passwordHashingService() {
        return new PasswordHashingService();
    }

    @Bean
    public AccountRepository accountRepository(DSLContext dsl) {
        return new AccountRepository(dsl);
    }

    @Bean
    public JwtIssuingService jwtIssuingService(@Named("auth") JWTAuth jwtAuth,
                                               @Named("reset") JWTAuth jwtReset) {
        return new JwtIssuingServiceImpl(jwtAuth, jwtReset);
    }

    @Bean
    public JwsParsingService jwsParsingService() {
        return new JwsParsingServiceImpl();
    }

    @Bean
    public NotificationSendingService notificationSendingService(NotificationRepository notificationRepository, Mailer mailer) {
        return new NotificationSendingServiceImpl(notificationRepository, mailer);
    }

    @Bean
    public NotificationRepository notificationRepository(DSLContext dsl) {
        return new NotificationRepository(dsl);
    }
}
