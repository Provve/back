package tech.provve.skill.domain;

import alekseyvideman.dop.Collection;
import io.avaje.config.Config;
import lombok.SneakyThrows;
import tech.provve.accounts.JwsParsing;
import tech.provve.accounts.Account;
import tech.provve.api.generated.dto.*;
import tech.provve.constants.Entity;
import tech.provve.skill.domain.value.VoteType;
import tech.provve.skill.exception.*;
import tech.provve.skill.XssSanitizer;
import tech.provve.statemachine.domain.Statemachine;
import tech.provve.task.Scheduling;
import tech.provve.util.Jackson;
import tech.provve.util.S3;
import tech.provve.util.Storage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.function.Supplier;

import static java.util.Collections.emptyList;
import static tech.provve.accounts.JwsParsing.JWT_SUBJECT;
import static tech.provve.skill.XssSanitizer.sanitize;

public class Vote {

    private static final Supplier<LocalDateTime> DEADLINE_SUPPLIER = () -> LocalDateTime.now(ZoneOffset.UTC)
                                                                                        .plusMonths(1);

    public static void create(SkillAddVote skillAddVote) throws VoteAlreadyExists {
        Storage.findVoteByName(skillAddVote.getName())
               .ifPresent(_ -> {
                   throw new VoteAlreadyExists(skillAddVote.getName());
               });

        var author = JwsParsing.parseAuth(skillAddVote.getAuthToken(), JWT_SUBJECT);
        var deadline = DEADLINE_SUPPLIER.get();

        Map<String, Object> vote = new HashMap<>();
        vote.put(Entity.Vote.NAME, sanitize(skillAddVote.getName()));
        vote.put(Entity.Vote.ACTIVE, true);
        vote.put(Entity.Vote.SUCCESS, false);
        vote.put(Entity.Vote.AUTHOR, author);
        vote.put(Entity.Vote.DEADLINE, deadline);
        vote.put(Entity.Vote.ARGUMENTS, sanitize(skillAddVote.getArguments()));
        vote.put(Entity.Vote.TYPE, VoteType.ADD_SKILL);
        vote.put(Entity.Vote.TAGS, skillAddVote.getTags()
                                               .stream()
                                               .map(XssSanitizer::sanitize)
                                               .toList());
        Storage.saveVote(vote);

        String voteName = Collection.get(vote, Entity.Vote.NAME);
        Scheduling.addSkill(voteName, deadline.toInstant(ZoneOffset.UTC));
    }

    public static void create(SkillDelVote skillDelVote) throws VoteAlreadyExists {
        Storage.findVoteByName(skillDelVote.getName())
               .ifPresent(_ -> {
                   throw new VoteAlreadyExists(skillDelVote.getName());
               });

        var author = JwsParsing.parseAuth(skillDelVote.getAuthToken(), JWT_SUBJECT);
        var deadline = DEADLINE_SUPPLIER.get();

        Map<String, Object> vote = new HashMap<>();
        vote.put(Entity.Vote.NAME, sanitize(skillDelVote.getName()));
        vote.put(Entity.Vote.ACTIVE, true);
        vote.put(Entity.Vote.SUCCESS, false);
        vote.put(Entity.Vote.AUTHOR, author);
        vote.put(Entity.Vote.DEADLINE, deadline);
        vote.put(Entity.Vote.ARGUMENTS, sanitize(skillDelVote.getArguments()));
        vote.put(Entity.Vote.TYPE, VoteType.DEL_SKILL);
        vote.put(Entity.Vote.TAGS, skillDelVote.getTags()
                                               .stream()
                                               .map(XssSanitizer::sanitize)
                                               .toList());
        Storage.saveVote(vote);

        String voteName = Collection.get(vote, Entity.Vote.NAME);
        Scheduling.delSkill(voteName, deadline.toInstant(ZoneOffset.UTC));
    }

    @SneakyThrows
    public static void create(ExamAddVote examAddVote) throws VoteAlreadyExists {
        Storage.findVoteByName(examAddVote.getName())
               .ifPresent(_ -> {
                   throw new VoteAlreadyExists(examAddVote.getName());
               });
        if (!Storage.skillExists(examAddVote.getSkillName())) {
            throw new SkillNotFound(examAddVote.getSkillName());
        }

        String publicArchive = examAddVote.getPublicArchive()
                                          .uploadedFileName();
        String privateArchive = examAddVote.getPrivateArchive()
                                           .uploadedFileName();

        String bucket = Config.get("s3.buckets.exams");
        String privateArchiveUrl = S3.crtUpload(
                bucket, S3.privateArchiveKeygen(examAddVote.getName()),
                Files.readAllBytes(Path.of(privateArchive))
        );
        String publicArchiveUrl = S3.crtUpload(
                bucket, S3.publicArchiveKeygen(examAddVote.getName()),
                Files.readAllBytes(Path.of(publicArchive))
        );

        Map<String, Object> exam = new HashMap<>();
        exam.put(Entity.Exam.NAME, examAddVote.getName());
        exam.put(Entity.Exam.SKILL_NAME, examAddVote.getSkillName());
        exam.put(Entity.Exam.DESCRIPTION, examAddVote.getDescription());
        exam.put(Entity.Exam.PRIVATE_ARCHIVE_URL, privateArchiveUrl);
        exam.put(Entity.Exam.PUBLIC_ARCHIVE_URL, publicArchiveUrl);

        var author = JwsParsing.parseAuth(examAddVote.getAuthToken(), JWT_SUBJECT);

        Map<String, Object> vote = new HashMap<>();
        vote.put(Entity.Vote.NAME, sanitize(examAddVote.getName()));
        vote.put(Entity.Vote.ACTIVE, true);
        vote.put(Entity.Vote.SUCCESS, false);
        vote.put(Entity.Vote.AUTHOR, author);
        vote.put(Entity.Vote.ARGUMENTS, sanitize(examAddVote.getArguments()));
        vote.put(Entity.Vote.TYPE, VoteType.ADD_EXAM);
        vote.put(Entity.Vote.TAGS, examAddVote.getTags()
                                              .stream()
                                              .map(XssSanitizer::sanitize)
                                              .toList());
        vote.put(Entity.Vote.EXAM, exam);

        Statemachine.createSaveExam(examAddVote.getName(), author, Jackson.json.writeValueAsString(vote));
    }

    public static Votes list(CollectionRequest collectionRequest) {
        List<VoteResponse> all = Storage.getAllVotes(collectionRequest.getFilter(),
                                                     collectionRequest.getPagination()
                                                                      .getPrevious(),
                                                     collectionRequest.getPagination()
                                                                      .getSize())
                                        .stream()
                                        .map(vote -> Jackson.convertToClass(vote, VoteResponse.class))
                                        .toList();
        if (all.isEmpty()) {
            return new Votes(all, new Cursor(""));
        }

        var cursor = new Cursor(all.getLast()
                                   .getName());
        return new Votes(all, cursor);
    }

    public static void addComment(AddCommentRequest request, String voteName) {
        var author = JwsParsing.parseAuth(request.getAuthToken(), JWT_SUBJECT);

        Map<String, Object> comment = new HashMap<>();
        comment.put(Entity.Comment.AUTHOR, author);
        comment.put(Entity.Comment.CONTENT, request.getContent());
        comment.put(Entity.Comment.VOTE_NAME, voteName);
        Storage.saveComment(comment);
    }

    public static void editComment(EditCommentRequest request) {
        var author = JwsParsing.parseAuth(request.getAuthToken(), JWT_SUBJECT);
        Storage.getComment(request.getId())
               .ifPresent(comment -> {
                   String commentAuthor = Collection.get(comment, Entity.Comment.AUTHOR);
                   if (!Objects.equals(commentAuthor, author)) throw new CommentFromAnotherAuthor();

                   Storage.updateComment(request.getId(), request.getContent());
               });
    }

    public static void deleteComment(DeleteCommentRequest request) throws CommentFromAnotherAuthor {
        var author = JwsParsing.parseAuth(request.getAuthToken(), JWT_SUBJECT);
        Storage.getComment(request.getId())
               .ifPresent(comment -> {
                   String commentAuthor = alekseyvideman.dop.Collection.get(comment, Entity.Comment.AUTHOR);
                   if (!Objects.equals(commentAuthor, author)) throw new CommentFromAnotherAuthor();

                   Storage.deleteComment(request.getId());
               });
    }

    public static void replyOnComment(ReplyCommentRequest request) {
        var author = JwsParsing.parseAuth(request.getAuthToken(), JWT_SUBJECT);
        Storage.getComment(request.getTargetId())
               .ifPresent(comment -> {
                   Map<String, Object> reply = new HashMap<>();
                   reply.put(Entity.Comment.AUTHOR, author);
                   reply.put(Entity.Comment.CONTENT, request.getContent());
                   reply.put(Entity.Comment.PARENT_ID, request.getTargetId());
                   Storage.saveComment(reply);
               });
    }

    public static Comments listComments(ListCommentsRequest request) {
        Map<Map<String, Object>, List<Map<String, Object>>> tree = Storage.getAllComments(request.getPagination()
                                                                                                 .getPrevious(),
                                                                                          request.getPagination()
                                                                                                 .getSize(),
                                                                                          request.getVoteName());
        List<CommentResponse> all = tree.entrySet()
                                        .stream()
                                        .map(entry -> {
                                            Map<String, Object> comment = entry.getKey();
                                            var mappedReplies = entry.getValue()
                                                                     .stream()
                                                                     .map(Vote::mapReply)
                                                                     .toList();
                                            var author = Account.viewPublicProfile(Collection.get(comment, Entity.Comment.AUTHOR));
                                            LocalDateTime created = Collection.get(comment, Entity.Comment.CREATED);
                                            return new CommentResponse(
                                                    Collection.get(comment, Entity.Comment.ID),
                                                    author,
                                                    Collection.get(comment, Entity.Comment.CONTENT),
                                                    created.atOffset(ZoneOffset.UTC),
                                                    mappedReplies
                                            );
                                        })
                                        .toList();
        if (all.isEmpty()) {
            return new Comments(all, new Cursor(""));
        }

        var cursor = new Cursor(String.valueOf(all.getLast()
                                                  .getId()));
        return new Comments(all, cursor);
    }

    private static CommentResponse mapReply(Map<String, Object> from) {
        var author = Account.viewPublicProfile(Collection.get(from, Entity.Comment.AUTHOR));
        LocalDateTime created = Collection.get(from, Entity.Comment.CREATED);
        return new CommentResponse(
                Collection.get(from, Entity.Comment.ID),
                author,
                Collection.get(from, Entity.Comment.CONTENT),
                created.atOffset(ZoneOffset.UTC),
                emptyList());
    }

    public static void cast(String voteName, CastVoteRequest castVote) {
        var voter = JwsParsing.parseAuth(castVote.getAuthToken(), JWT_SUBJECT);
        Storage.findVoteByName(voteName)
               .ifPresentOrElse(
                       vote -> {
                           String author = Collection.get(vote, Entity.Vote.AUTHOR);
                           if (voter.equals(author)) {
                               throw new AuthorCannotVote();
                           }
                           if (Storage.voteReactionExists(voteName, voter)) {
                               throw new CastAlreadyExists();
                           }

                           Storage.setVoteReaction(voteName, voter, castVote.getPositiveReaction());
                       }, () -> {
                           throw new VoteNotFound(voteName);
                       }
               );
    }

    @SuppressWarnings("all")
    public static boolean end(String voteName) {
        Optional<Map<String, Object>> optionalVote = Storage.findVoteByName(voteName);
        if (optionalVote.isEmpty()) return false;

        Map<String, Object> vote = optionalVote.get();
        boolean succeeded = succeeded(vote);
        Storage.updateVoteSuccess(voteName, succeeded);

        return succeeded;
    }

    private static boolean succeeded(Map<String, Object> vote) {
        Map<String, Object> reactions = Collection.get(vote, Entity.Vote.REACTIONS);
        Integer positive = Collection.get(reactions, Entity.VoteReactions.POSITIVE);
        Integer negative = Collection.get(reactions, Entity.VoteReactions.NEGATIVE);

        int negativeValue = 0 == negative
                ? 1
                : negative;
        int positiveRelation = positive / negativeValue;
        return positiveRelation > 1;
    }
}
