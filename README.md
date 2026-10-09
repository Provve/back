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
        String description
        String privateArchiveUrl
        String publicArchiveUrl
        List~String~ tags
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
        SkillAddVote skill
        VoteReactions reactions
    }

    class SkillAddVote {
        String description
        String privateArchiveUrl
        String publicArchiveUrl
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
        String skillName
        String examinee
        Duration durationMinutes
    }

    class Session {
        String owner
        String skillName
        Instant started
    }

    Skill "1" --> "*" Result: has (name = skillName)
    Skill "1" --> "*" Session: has (name = skillName)
    Vote "1" --> "*" Comment : has
    Vote "1" --> "1" VoteReactions : has reactions
    Vote "1" --> "0..1" SkillAddVote: has payload (ADD_SKILL)
    Vote "0..1" --> "1" Skill: creates (name)
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

    class AuthoredSkillSaved {
        RecipientRequisites requisites
        String skillName
    }

    class AuthoredSkillNotSaved {
        RecipientRequisites requisites
        String skillName
    }

    class VoteStarted {
        RecipientRequisites requisites
        String voteName
    }

    NotifyCommand <|-- ResetCode
    NotifyCommand <|-- AccountUpgraded
    NotifyCommand <|-- AccountDowngraded
    NotifyCommand <|-- AuthoredSkillSaved
    NotifyCommand <|-- AuthoredSkillNotSaved
    NotifyCommand <|-- VoteStarted
    NotifyCommand "1" --> "1" RecipientRequisites: sends to
    ResetCode --> RecipientRequisites
    AccountUpgraded --> RecipientRequisites
    AccountDowngraded --> RecipientRequisites
    AuthoredSkillSaved --> RecipientRequisites
    AuthoredSkillNotSaved --> RecipientRequisites
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

    class SaveSkill {
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
        String skillName
        String containerId
    }
```
