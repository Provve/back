package tech.provve.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.avaje.config.Config;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.flywaydb.core.Flyway;
import tech.provve.accounts.repository.AccountRepository;
import tech.provve.api.generated.dto.Filter;
import tech.provve.notification.repository.NotificationRepository;
import tech.provve.payment.repository.RobokassaInvoiceRepository;
import tech.provve.skill.repository.*;
import tech.provve.statemachine.domain.value.CheckSolutionState;
import tech.provve.statemachine.domain.value.SaveSkillState;
import tech.provve.statemachine.repository.CheckSolutionRepository;
import tech.provve.statemachine.repository.SaveSkillRepository;
import tech.provve.validation.repository.ContainerRepository;
import tech.provve.validation.repository.ObservationRepository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Storage {

    public static DataSource dataSource;

    private static SqlSessionFactory sqlSessionFactory = null;

    private Storage() {
    }

    public static void saveAccount(Map<String, Object> account) {
        try (var session = sqlSessionFactory.openSession(true)) {
            session.getMapper(AccountRepository.class)
                   .save(account);
        }
    }

    public static void deleteAccount(String login) {
        try (var session = sqlSessionFactory.openSession(true)) {
            session.getMapper(AccountRepository.class)
                   .delete(login);
        }
    }

    public static Optional<Map<String, Object>> findAccountByLogin(String login) {
        try (var session = sqlSessionFactory.openSession(false)) {
            return session.getMapper(AccountRepository.class)
                          .findByLogin(login);
        }
    }

    public static Optional<Map<String, Object>> findAccountByEmail(String email) {
        try (var session = sqlSessionFactory.openSession(false)) {
            return session.getMapper(AccountRepository.class)
                          .findByEmail(email);
        }
    }

    public static List<Map<String, Object>> findAccountsInterestedIn(String skillName) {
        try (var session = sqlSessionFactory.openSession(false)) {
            return session.getMapper(AccountRepository.class)
                          .findInterestedIn(skillName);
        }
    }

    public static void updateAccountPasswordHash(String passwordHash, String login) {
        try (var session = sqlSessionFactory.openSession(true)) {
            session.getMapper(AccountRepository.class)
                   .updatePasswordHash(passwordHash, login);
        }
    }

    public static void updateAccountEmail(String login, String email) {
        try (var session = sqlSessionFactory.openSession(true)) {
            session.getMapper(AccountRepository.class)
                   .updateEmail(login, email);
        }
    }

    public static void updateAccountAvatarUrl(String login, String avatarUrl) {
        try (var session = sqlSessionFactory.openSession(true)) {
            session.getMapper(AccountRepository.class)
                   .updateAvatarUrl(login, avatarUrl);
        }
    }

    public static void updateAccountContactInfo(String login, String contactInfo) {
        try (var session = sqlSessionFactory.openSession(true)) {
            session.getMapper(AccountRepository.class)
                   .updateContactInfo(login, contactInfo);
        }
    }

    public static void updateAccountInterests(String login, List<String> interests) {
        try (var session = sqlSessionFactory.openSession(true)) {
            session.getMapper(AccountRepository.class)
                   .updateInterests(login, interests);
        }
    }

    public static void updateAccountPremium(String login, boolean premium) {
        try (var session = sqlSessionFactory.openSession(true)) {
            session.getMapper(AccountRepository.class)
                   .updatePremium(login, premium);
        }
    }

    public static void updateAccountPersonalDataConsent(String login, boolean personalDataConsent) {
        try (var session = sqlSessionFactory.openSession(true)) {
            session.getMapper(AccountRepository.class)
                   .updatePersonalDataConsent(login, personalDataConsent);
        }
    }

    public static void saveCheckSolution(Map<String, Object> checkSolution) {
        try (var session = sqlSessionFactory.openSession(true)) {
            session.getMapper(CheckSolutionRepository.class)
                   .save(checkSolution);
        }
    }

    public static void updateCheckSolutionState(String name, CheckSolutionState state) {
        try (var session = sqlSessionFactory.openSession(true)) {
            session.getMapper(CheckSolutionRepository.class)
                   .updateState(name, state);
        }
    }

    public static List<Map<String, Object>> listCheckSolutions() {
        try (var session = sqlSessionFactory.openSession(false)) {
            return session.getMapper(CheckSolutionRepository.class)
                          .list();
        }
    }

    public static boolean checkSolutionExists(String name) {
        try (var session = sqlSessionFactory.openSession(false)) {
            return session.getMapper(CheckSolutionRepository.class)
                          .exists(name);
        }
    }

    public static void deleteCheckSolution(String name) {
        try (var session = sqlSessionFactory.openSession(true)) {
            session.getMapper(CheckSolutionRepository.class)
                   .delete(name);
        }
    }

    public static void saveComment(Map<String, Object> comment) {
        try (var session = sqlSessionFactory.openSession(true)) {
            session.getMapper(CommentRepository.class)
                   .save(comment);
        }
    }

    public static void updateComment(Integer id, String content) {
        try (var session = sqlSessionFactory.openSession(true)) {
            session.getMapper(CommentRepository.class)
                   .update(id, content);
        }
    }

    public static void deleteComment(Integer id) {
        try (var session = sqlSessionFactory.openSession(true)) {
            session.getMapper(CommentRepository.class)
                   .delete(id);
        }
    }

    public static Optional<Map<String, Object>> getComment(Integer id) {
        try (var session = sqlSessionFactory.openSession(false)) {
            return session.getMapper(CommentRepository.class)
                          .get(id);
        }
    }

    public static List<Map<String, Object>> selectCommentTree(String previous, int pageSize, String voteName) {
        try (var session = sqlSessionFactory.openSession(false)) {
            return session.getMapper(CommentRepository.class)
                          .selectCommentTree(previous, pageSize, voteName);
        }
    }

    public static Map<Map<String, Object>, List<Map<String, Object>>> getAllComments(String previous, int pageSize, String voteName) {
        try (var session = sqlSessionFactory.openSession(false)) {
            return session.getMapper(CommentRepository.class)
                          .getAll(previous, pageSize, voteName);
        }
    }

    public static void saveContainer(Map<String, Object> container) {
        try (var session = sqlSessionFactory.openSession(true)) {
            session.getMapper(ContainerRepository.class)
                   .save(container);
        }
    }

    public static void deleteContainer(String examinee) {
        try (var session = sqlSessionFactory.openSession(true)) {
            session.getMapper(ContainerRepository.class)
                   .delete(examinee);
        }
    }

    public static Optional<Map<String, Object>> findContainer(String examinee) {
        try (var session = sqlSessionFactory.openSession(false)) {
            return session.getMapper(ContainerRepository.class)
                          .find(examinee);
        }
    }

    public static void saveNotification(Map<String, Object> inputNotification) {
        try (var session = sqlSessionFactory.openSession(true)) {
            session.getMapper(NotificationRepository.class)
                   .save(inputNotification);
        }
    }

    public static List<Map<String, Object>> findAllNotificationsBy(String login, String previous, int pageSize) {
        try (var session = sqlSessionFactory.openSession(false)) {
            return session.getMapper(NotificationRepository.class)
                          .findAllBy(login, previous, pageSize);
        }
    }

    public static void saveObservation(Map<String, Object> observation) {
        try (var session = sqlSessionFactory.openSession(true)) {
            session.getMapper(ObservationRepository.class)
                   .save(observation);
        }
    }

    public static Optional<Map<String, Object>> findObservation(String examinee) {
        try (var session = sqlSessionFactory.openSession(false)) {
            return session.getMapper(ObservationRepository.class)
                          .find(examinee);
        }
    }

    public static void saveResult(Map<String, Object> result) {
        try (var session = sqlSessionFactory.openSession(true)) {
            session.getMapper(ResultRepository.class)
                   .save(result);
        }
    }

    public static Optional<Map<String, Object>> findResult(String skillName, String examinee) {
        try (var session = sqlSessionFactory.openSession(false)) {
            return session.getMapper(ResultRepository.class)
                          .find(skillName, examinee);
        }
    }

    public static List<Map<String, Object>> getAllResults(Filter filter, String previous, int pageSize) {
        try (var session = sqlSessionFactory.openSession(false)) {
            return session.getMapper(ResultRepository.class)
                          .getAll(filter, previous, pageSize);
        }
    }

    public static List<Map<String, Object>> getAllResultsForExaminee(Filter filter, String examinee, String previous, int pageSize) {
        try (var session = sqlSessionFactory.openSession(false)) {
            return session.getMapper(ResultRepository.class)
                          .getAllForExaminee(filter, examinee, previous, pageSize);
        }
    }

    public static void saveInvoice(Map<String, Object> invoice) {
        try (var session = sqlSessionFactory.openSession(true)) {
            session.getMapper(RobokassaInvoiceRepository.class)
                   .save(invoice);
        }
    }

    public static Optional<Map<String, Object>> findInvoiceBy(String accountLogin) {
        try (var session = sqlSessionFactory.openSession(false)) {
            return session.getMapper(RobokassaInvoiceRepository.class)
                          .findBy(accountLogin);
        }
    }

    public static void saveSaveSkill(Map<String, Object> saveSkill) {
        try (var session = sqlSessionFactory.openSession(true)) {
            session.getMapper(SaveSkillRepository.class)
                   .save(saveSkill);
        }
    }

    public static void updateSaveSkillState(String name, SaveSkillState state) {
        try (var session = sqlSessionFactory.openSession(true)) {
            session.getMapper(SaveSkillRepository.class)
                   .updateState(name, state);
        }
    }

    public static List<Map<String, Object>> listSaveSkills() {
        try (var session = sqlSessionFactory.openSession(false)) {
            return session.getMapper(SaveSkillRepository.class)
                          .list();
        }
    }

    public static boolean saveSkillExists(String name) {
        try (var session = sqlSessionFactory.openSession(false)) {
            return session.getMapper(SaveSkillRepository.class)
                          .exists(name);
        }
    }

    public static void deleteSaveSkill(String name) {
        try (var session = sqlSessionFactory.openSession(true)) {
            session.getMapper(SaveSkillRepository.class)
                   .delete(name);
        }
    }

    public static void saveSession(Map<String, Object> session) {
        try (var s = sqlSessionFactory.openSession(true)) {
            s.getMapper(SessionRepository.class)
             .save(session);
        }
    }

    public static Optional<Map<String, Object>> findSession(String owner) {
        try (var session = sqlSessionFactory.openSession(false)) {
            return session.getMapper(SessionRepository.class)
                          .find(owner);
        }
    }

    public static void deleteSession(String owner) {
        try (var session = sqlSessionFactory.openSession(true)) {
            session.getMapper(SessionRepository.class)
                   .delete(owner);
        }
    }

    public static void saveSkill(Map<String, Object> skill) {
        try (var session = sqlSessionFactory.openSession(true)) {
            session.getMapper(SkillRepository.class)
                   .save(skill);
        }
    }

    public static Optional<Map<String, Object>> findSkill(String name) {
        try (var session = sqlSessionFactory.openSession(false)) {
            return session.getMapper(SkillRepository.class)
                          .find(name);
        }
    }

    public static boolean skillExists(String name) {
        try (var session = sqlSessionFactory.openSession(false)) {
            return session.getMapper(SkillRepository.class)
                          .exists(name);
        }
    }

    public static boolean skillNameTaken(String name) {
        try (var session = sqlSessionFactory.openSession(false)) {
            return session.getMapper(SkillRepository.class)
                          .nameTaken(name);
        }
    }

    public static List<Map<String, Object>> getAllSkills(Filter filter, String previous, int pageSize) {
        try (var session = sqlSessionFactory.openSession(false)) {
            return session.getMapper(SkillRepository.class)
                          .getAll(filter, previous, pageSize);
        }
    }

    public static void archiveSkill(String name) {
        try (var session = sqlSessionFactory.openSession(true)) {
            session.getMapper(SkillRepository.class)
                   .archive(name);
        }
    }

    public static void saveVote(Map<String, Object> vote) {
        try (var session = sqlSessionFactory.openSession(true)) {
            session.getMapper(VoteRepository.class)
                   .save(vote);
        }
    }

    public static void insertVote(Map<String, Object> vote) {
        try (var session = sqlSessionFactory.openSession(true)) {
            session.getMapper(VoteRepository.class)
                   .insertVote(vote);
        }
    }

    public static void insertSkillAddVote(Map<String, Object> vote) {
        try (var session = sqlSessionFactory.openSession(true)) {
            session.getMapper(VoteRepository.class)
                   .insertSkillAddVote(vote);
        }
    }

    public static void deleteVote(String name) {
        try (var session = sqlSessionFactory.openSession(true)) {
            session.getMapper(VoteRepository.class)
                   .delete(name);
        }
    }

    public static Optional<Map<String, Object>> findVoteByName(String name) {
        try (var session = sqlSessionFactory.openSession(false)) {
            return session.getMapper(VoteRepository.class)
                          .findByName(name);
        }
    }

    public static Map<String, Object> selectVoteReactionsTotal(String name) {
        try (var session = sqlSessionFactory.openSession(false)) {
            return session.getMapper(VoteRepository.class)
                          .selectReactionsTotal(name);
        }
    }

    public static Map<String, Object> selectSkillAddVote(String name) {
        try (var session = sqlSessionFactory.openSession(false)) {
            return session.getMapper(VoteRepository.class)
                          .selectSkillAddVote(name);
        }
    }

    public static boolean voteExists(String name, boolean active) {
        try (var session = sqlSessionFactory.openSession(false)) {
            return session.getMapper(VoteRepository.class)
                          .exists(name, active);
        }
    }

    public static List<Map<String, Object>> getAllVotes(Filter filter, String previous, int pageSize) {
        try (var session = sqlSessionFactory.openSession(false)) {
            return session.getMapper(VoteRepository.class)
                          .getAll(filter, previous, pageSize);
        }
    }

    public static void setVoteReaction(String name, String voter, boolean reaction) {
        try (var session = sqlSessionFactory.openSession(true)) {
            session.getMapper(VoteRepository.class)
                   .setReaction(name, voter, reaction);
        }
    }

    public static boolean voteReactionExists(String name, String voter) {
        try (var session = sqlSessionFactory.openSession(false)) {
            return session.getMapper(VoteRepository.class)
                          .reactionExists(name, voter);
        }
    }

    public static void updateVoteSuccess(String name, boolean success) {
        try (var session = sqlSessionFactory.openSession(true)) {
            session.getMapper(VoteRepository.class)
                   .updateSuccess(name, success);
        }
    }

    public static class Initialization {

        public static void init() {
            initDbSchema(initDataSource(Config.get("storage.jdbc-url"), Config.get("storage.username"), Config.get("storage.password")));
            Storage.sqlSessionFactory = sqlSessionFactory(dataSource);
        }

        private static DataSource initDataSource(String jdbcUrl, String username, String password) {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(jdbcUrl);
            config.setUsername(username);
            config.setPassword(password);

            Storage.dataSource = new HikariDataSource(config);

            return dataSource;
        }

        private static void initDbSchema(DataSource dataSource) {
            Flyway.configure(Storage.class.getClassLoader())
                  .locations("db/migration")
                  .dataSource(dataSource)
                  .cleanDisabled(false)
                  .load()
                  .migrate();
        }

        private static SqlSessionFactory sqlSessionFactory(DataSource dataSource) {
            TransactionFactory transactionFactory = new JdbcTransactionFactory();
            Environment environment = new Environment("1", transactionFactory, dataSource);
            Configuration configuration = new Configuration(environment);
            configuration.setMapUnderscoreToCamelCase(true);

            configuration.addMapper(AccountRepository.class);
            configuration.addMapper(CheckSolutionRepository.class);
            configuration.addMapper(CommentRepository.class);
            configuration.addMapper(ContainerRepository.class);
            configuration.addMapper(NotificationRepository.class);
            configuration.addMapper(ObservationRepository.class);
            configuration.addMapper(ResultRepository.class);
            configuration.addMapper(RobokassaInvoiceRepository.class);
            configuration.addMapper(SaveSkillRepository.class);
            configuration.addMapper(SessionRepository.class);
            configuration.addMapper(SkillRepository.class);
            configuration.addMapper(VoteRepository.class);


            return new SqlSessionFactoryBuilder().build(configuration);
        }
    }
}
