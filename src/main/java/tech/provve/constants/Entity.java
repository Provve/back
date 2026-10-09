package tech.provve.constants;

public final class Entity {

    private Entity() {
    }

    // ==================== Accounts Domain ====================

    public static final class Account {

        private Account() {
        }

        public static final String LOGIN = "login";
        public static final String EMAIL = "email";
        public static final String PASSWORD_HASH = "passwordHash";
        public static final String IS_CONSENT_PERSONAL_DATA = "isConsentPersonalData";
        public static final String USERNAME = "username";
        public static final String AVATAR_URL = "avatarUrl";
        public static final String CONTACT_INFO = "contactInfo";
        public static final String INTERESTS = "interests";
        public static final String IS_PREMIUM = "isPremium";
    }

    public static final class PremiumExpiration {

        private PremiumExpiration() {
        }

        public static final String LOGIN = "login";
        public static final String EXPIRY = "expiry";
    }

    public static final class Invoice {

        private Invoice() {
        }

        public static final String ACCOUNT_LOGIN = "accountLogin";
        public static final String SIGNATURE = "signature";
    }

    // ==================== Skills Domain ====================

    public static final class Skill {

        private Skill() {
        }

        public static final String NAME = "name";
        public static final String TAGS = "tags";
        public static final String DESCRIPTION = "description";
        public static final String PRIVATE_ARCHIVE_URL = "privateArchiveUrl";
        public static final String PUBLIC_ARCHIVE_URL = "publicArchiveUrl";
        public static final String ARCHIVED = "archived";
    }

    public static final class Vote {

        private Vote() {
        }

        public static final String NAME = "name";
        public static final String ACTIVE = "active";
        public static final String SUCCESS = "success";
        public static final String AUTHOR = "author";
        public static final String DEADLINE = "deadline";
        public static final String ARGUMENTS = "arguments";
        public static final String TYPE = "type";
        public static final String TAGS = "tags";
        public static final String SKILL = "skill";
        public static final String REACTIONS = "reactions";
    }

    public static final class VoteReactions {

        private VoteReactions() {
        }

        public static final String POSITIVE = "positive";
        public static final String NEGATIVE = "negative";
    }

    public static final class Comment {

        private Comment() {
        }

        public static final String ID = "id";
        public static final String AUTHOR = "author";
        public static final String CONTENT = "content";
        public static final String CREATED = "created";
        public static final String VOTE_NAME = "voteName";
        public static final String PARENT_ID = "parentId";
    }

    public static final class Result {

        private Result() {
        }

        public static final String SKILL_NAME = "skillName";
        public static final String EXAMINEE = "examinee";
        public static final String DURATION_MINUTES = "durationMinutes";
        public static final String SUCCESS = "success";
        public static final String CREATED_AT = "createdAt";
    }

    public static final class Session {

        private Session() {
        }

        public static final String OWNER = "owner";
        public static final String SKILL_NAME = "skillName";
        public static final String STARTED = "started";
    }

    // ==================== Notifications Domain ====================

    public static final class Notification {

        private Notification() {
        }

        public static final String ID = "id";
        public static final String RECEIVER = "receiver";
        public static final String LEVEL = "level";
        public static final String MESSAGE = "message";
        public static final String CREATED_AT = "createdAt";
    }

    public static final class NotifyCommand {

        private NotifyCommand() {
        }

        public static final String SUBJECT = "subject";
        public static final String LEVEL = "level";
        public static final String REQUISITES = "requisites";
        public static final String ADDRESSES = "addresses";
    }

    public static final class RecipientRequisites {

        private RecipientRequisites() {
        }

        public static final String LOGIN = "login";
        public static final String EMAIL = "email";
    }

    public static final class ResetCode {

        private ResetCode() {
        }

        public static final String REQUISITES = "requisites";
        public static final String RESET_TOKEN = "resetToken";
    }

    public static final class AccountUpgraded {

        private AccountUpgraded() {
        }

        public static final String REQUISITES = "requisites";
    }

    public static final class AccountDowngraded {

        private AccountDowngraded() {
        }

        public static final String REQUISITES = "requisites";
    }

    public static final class AuthoredSkillSaved {

        private AuthoredSkillSaved() {
        }

        public static final String REQUISITES = "requisites";
        public static final String SKILL_NAME = "skillName";
    }

    public static final class AuthoredSkillNotSaved {

        private AuthoredSkillNotSaved() {
        }

        public static final String REQUISITES = "requisites";
        public static final String SKILL_NAME = "skillName";
    }

    public static final class VoteStarted {

        private VoteStarted() {
        }

        public static final String REQUISITES = "requisites";
        public static final String VOTE_NAME = "voteName";
    }

    // ==================== Payments Domain ====================

    public static final class PaymentRequest {

        private PaymentRequest() {
        }

        public static final String MERCHANT_LOGIN = "merchantLogin";
        public static final String OUT_SUM = "outSum";
        public static final String INVOICE_TYPE = "invoiceType";
        public static final String USER_FIELDS = "userFields";
        public static final String ACCOUNT_FIELD = "account";
    }

    // ==================== State Machine Domain ====================

    public static final class CheckSolution {

        private CheckSolution() {
        }

        public static final String NAME = "name";
        public static final String STATE = "state";
        public static final String EXAMINEE = "examinee";
    }

    public static final class SaveSkill {

        private SaveSkill() {
        }

        public static final String NAME = "name";
        public static final String STATE = "state";
        public static final String AUTHOR = "author";
        public static final String DELAYED_VOTE_JSON = "delayedVoteJson";
    }

    public static final class PrivateArchive {

        private PrivateArchive() {
        }

        public static final String INJECT_SECRET_PLACEHOLDER = "${SECRET}";
        public static final String INJECT_SECRET_FILE = "secret";
        public static final String IGNORE_FILE = "ignore.txt";
        public static final String DOCKER_FILE = "Dockerfile";
    }

    // ==================== Validation Domain ====================

    public static final class Container {

        private Container() {
        }

        public static final String EXAMINEE = "examinee";
        public static final String SKILL_NAME = "skillName";
        public static final String CONTAINER_ID = "containerId";
    }

    public static final class Observation {

        private Observation() {
        }

        public static final String EXAMINEE = "examinee";
        public static final String VIOLATIONS = "violations";
        public static final String CHEATED = "cheated";
    }
}
