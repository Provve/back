# Provve Backend

# Entities

TODO in the future, it will be separated into code and data parts

## 1. Accounts Domain

```mermaid
classDiagram
    class Account {
        String login
        String email
        String passwordHash
        Boolean isConsentPersonalData
        String username
        String avatarUrl
        String contactInfo
        List~String~ interests
        Boolean isPremium
    }

    class PremiumExpiration {
        String login
        OffsetDateTime expiry
    }

    class Invoice {
        String accountLogin
        String signature
    }

    Account "1" --> "1" PremiumExpiration: has
    Account "1" --> "1" Invoice: owns (accountLogin)
```

---

## 2. Skills Domain

```mermaid
classDiagram
    class Skill {
        String name
        List~String~ tags
    }

    class Exam {
        String name
        String skillName
        String description
        String privateArchiveUrl
        String publicArchiveUrl
    }

    class Vote {
        String name
        boolean active
        boolean success
        String author
        LocalDateTime deadline
        String arguments
        String type
        List~String~ tags
        Exam exam
        VoteReactions reactions
    }

    class VoteReactions {
        int positive
        int negative
    }

    class Comment {
        Integer id
        String author
        String content
        LocalDateTime created
        String voteName
        Integer parentId
    }

    class Result {
        String examName
        String examinee
        Duration durationMinutes
    }

    class Session {
        String owner
        String examName
        Instant started
    }

    Skill "1" --> "*" Exam : checked by (name = skillName)
    Exam "1" --> "*" Result : has
    Exam "1" --> "*" Session : has
    Vote "1" --> "*" Comment : has
    Vote "1" --> "1" VoteReactions : has reactions
    Vote "1" --> "0..1" Exam : has exam (ADD_EXAM)
    Comment "1" --> "0..*" Comment : replies (parentId)
```

---

## 3. Notifications Domain

```mermaid
classDiagram
    class InputNotification {
        String receiver
        String level
        String message
    }

    class NotifyCommand {
        String subject
        String level
        RecipientRequisites requisites
        List~String~ addresses
    }

    class RecipientRequisites {
        String login
        String email
    }

    class ResetCode {
        RecipientRequisites requisites
        String resetToken
    }

    class AccountUpgraded {
        RecipientRequisites requisites
    }

    class AccountDowngraded {
        RecipientRequisites requisites
    }

    class AuthoredExamSaved {
        RecipientRequisites requisites
        String examName
    }

    class AuthoredExamNotSaved {
        RecipientRequisites requisites
        String examName
    }

    class VoteStarted {
        RecipientRequisites requisites
        String voteName
    }

    NotifyCommand <|-- ResetCode
    NotifyCommand <|-- AccountUpgraded
    NotifyCommand <|-- AccountDowngraded
    NotifyCommand <|-- AuthoredExamSaved
    NotifyCommand <|-- AuthoredExamNotSaved
    NotifyCommand <|-- VoteStarted
    NotifyCommand "1" --> "1" RecipientRequisites: sends to
    ResetCode --> RecipientRequisites
    AccountUpgraded --> RecipientRequisites
    AccountDowngraded --> RecipientRequisites
    AuthoredExamSaved --> RecipientRequisites
    AuthoredExamNotSaved --> RecipientRequisites
    VoteStarted --> RecipientRequisites
```

---

## 4. Payments Domain

```mermaid
classDiagram
    class PaymentRequest {
        String merchantLogin
        Integer outSum
        String invoiceType
        Map~String, String~ userFields
    }
```

---

## 5. State Machine Domain

```mermaid
classDiagram
    class CheckSolution {
        String name
        String state
        String examinee
    }

    class SaveExam {
        String name
        String state
        String author
        String delayedVoteJson
    }
```

---

## 6. Validation Domain

```mermaid
classDiagram
    class Observation {
        String examinee
        String violations
        boolean cheated
    }

    class ContainerView {
        String examinee
        String examName
        String containerId
    }
```
