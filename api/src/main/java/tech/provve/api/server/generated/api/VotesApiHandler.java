package tech.provve.api.server.generated.api;

import com.fasterxml.jackson.core.type.TypeReference;
import io.vertx.core.json.jackson.DatabindCodec;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.openapi.RouterBuilder;
import io.vertx.ext.web.validation.RequestParameter;
import io.vertx.ext.web.validation.RequestParameters;
import io.vertx.ext.web.validation.ValidationHandler;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.provve.api.server.RouteHandler;
import tech.provve.api.server.generated.dto.*;

@Singleton
public class VotesApiHandler implements RouteHandler {

    private static final Logger logger = LoggerFactory.getLogger(VotesApiHandler.class);

    private final VotesApi api;

    public VotesApiHandler(VotesApi api) {
        this.api = api;
    }

    public void mount(RouterBuilder builder) {
        builder.operation("addComment")
               .handler(this::addComment);
        builder.operation("castVote")
               .handler(this::castVote);
        builder.operation("createExamAddVote")
               .handler(this::createExamAddVote);
        builder.operation("createSkillAddVote")
               .handler(this::createSkillAddVote);
        builder.operation("createSkillDelVote")
               .handler(this::createSkillDelVote);
        builder.operation("deleteComment")
               .handler(this::deleteComment);
        builder.operation("editComment")
               .handler(this::editComment);
        builder.operation("listComments")
               .handler(this::listComments);
        builder.operation("listVotes")
               .handler(this::listVotes);
        builder.operation("replyOnComment")
               .handler(this::replyOnComment);
    }

    private void addComment(RoutingContext routingContext) {
        logger.info("addComment()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        RequestParameter body = requestParameters.body();
        AddCommentRequest addCommentRequest = body != null
                                              ? DatabindCodec.mapper()
                                                             .convertValue(body.get(), new TypeReference<AddCommentRequest>() {
                                                             })
                                              : null;

        logger.debug("Parameter addCommentRequest is {}", addCommentRequest);

        api.addComment(addCommentRequest)
           .onSuccess(apiResponse -> {
               routingContext.response()
                             .setStatusCode(apiResponse.getStatusCode());
               if (apiResponse.hasData()) {
                   routingContext.json(apiResponse.getData());
               } else {
                   routingContext.response()
                                 .end();
               }
           })
           .onFailure(routingContext::fail);
    }

    private void castVote(RoutingContext routingContext) {
        logger.info("castVote()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        String name = requestParameters.pathParameter("name") != null
                      ? requestParameters.pathParameter("name")
                                         .getString()
                      : null;
        RequestParameter body = requestParameters.body();
        CastVoteRequest castVoteRequest = body != null
                                          ? DatabindCodec.mapper()
                                                         .convertValue(body.get(), new TypeReference<CastVoteRequest>() {
                                                         })
                                          : null;

        logger.debug("Parameter name is {}", name);
        logger.debug("Parameter castVoteRequest is {}", castVoteRequest);

        api.castVote(name, castVoteRequest)
           .onSuccess(apiResponse -> {
               routingContext.response()
                             .setStatusCode(apiResponse.getStatusCode());
               if (apiResponse.hasData()) {
                   routingContext.json(apiResponse.getData());
               } else {
                   routingContext.response()
                                 .end();
               }
           })
           .onFailure(routingContext::fail);
    }

    private void createExamAddVote(RoutingContext routingContext) {
        logger.info("createExamAddVote()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        RequestParameter body = requestParameters.body();
        ExamAddVote examAddVote = body != null
                                  ? DatabindCodec.mapper()
                                                 .convertValue(body.get(), new TypeReference<ExamAddVote>() {
                                                 })
                                  : null;

        logger.debug("Parameter examAddVote is {}", examAddVote);

        api.createExamAddVote(examAddVote)
           .onSuccess(apiResponse -> {
               routingContext.response()
                             .setStatusCode(apiResponse.getStatusCode());
               if (apiResponse.hasData()) {
                   routingContext.json(apiResponse.getData());
               } else {
                   routingContext.response()
                                 .end();
               }
           })
           .onFailure(routingContext::fail);
    }

    private void createSkillAddVote(RoutingContext routingContext) {
        logger.info("createSkillAddVote()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        RequestParameter body = requestParameters.body();
        SkillAddVote skillAddVote = body != null
                                    ? DatabindCodec.mapper()
                                                   .convertValue(body.get(), new TypeReference<SkillAddVote>() {
                                                   })
                                    : null;

        logger.debug("Parameter skillAddVote is {}", skillAddVote);

        api.createSkillAddVote(skillAddVote)
           .onSuccess(apiResponse -> {
               routingContext.response()
                             .setStatusCode(apiResponse.getStatusCode());
               if (apiResponse.hasData()) {
                   routingContext.json(apiResponse.getData());
               } else {
                   routingContext.response()
                                 .end();
               }
           })
           .onFailure(routingContext::fail);
    }

    private void createSkillDelVote(RoutingContext routingContext) {
        logger.info("createSkillDelVote()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        RequestParameter body = requestParameters.body();
        SkillDelVote skillDelVote = body != null
                                    ? DatabindCodec.mapper()
                                                   .convertValue(body.get(), new TypeReference<SkillDelVote>() {
                                                   })
                                    : null;

        logger.debug("Parameter skillDelVote is {}", skillDelVote);

        api.createSkillDelVote(skillDelVote)
           .onSuccess(apiResponse -> {
               routingContext.response()
                             .setStatusCode(apiResponse.getStatusCode());
               if (apiResponse.hasData()) {
                   routingContext.json(apiResponse.getData());
               } else {
                   routingContext.response()
                                 .end();
               }
           })
           .onFailure(routingContext::fail);
    }

    private void deleteComment(RoutingContext routingContext) {
        logger.info("deleteComment()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        RequestParameter body = requestParameters.body();
        DeleteCommentRequest deleteCommentRequest = body != null
                                                    ? DatabindCodec.mapper()
                                                                   .convertValue(body.get(), new TypeReference<DeleteCommentRequest>() {
                                                                   })
                                                    : null;

        logger.debug("Parameter deleteCommentRequest is {}", deleteCommentRequest);

        api.deleteComment(deleteCommentRequest)
           .onSuccess(apiResponse -> {
               routingContext.response()
                             .setStatusCode(apiResponse.getStatusCode());
               if (apiResponse.hasData()) {
                   routingContext.json(apiResponse.getData());
               } else {
                   routingContext.response()
                                 .end();
               }
           })
           .onFailure(routingContext::fail);
    }

    private void editComment(RoutingContext routingContext) {
        logger.info("editComment()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        RequestParameter body = requestParameters.body();
        EditCommentRequest editCommentRequest = body != null
                                                ? DatabindCodec.mapper()
                                                               .convertValue(body.get(), new TypeReference<EditCommentRequest>() {
                                                               })
                                                : null;

        logger.debug("Parameter editCommentRequest is {}", editCommentRequest);

        api.editComment(editCommentRequest)
           .onSuccess(apiResponse -> {
               routingContext.response()
                             .setStatusCode(apiResponse.getStatusCode());
               if (apiResponse.hasData()) {
                   routingContext.json(apiResponse.getData());
               } else {
                   routingContext.response()
                                 .end();
               }
           })
           .onFailure(routingContext::fail);
    }

    private void listComments(RoutingContext routingContext) {
        logger.info("listComments()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        String voteName = requestParameters.pathParameter("vote_name") != null
                          ? requestParameters.pathParameter("vote_name")
                                             .getString()
                          : null;

        logger.debug("Parameter voteName is {}", voteName);

        api.listComments(voteName)
           .onSuccess(apiResponse -> {
               routingContext.response()
                             .setStatusCode(apiResponse.getStatusCode());
               if (apiResponse.hasData()) {
                   routingContext.json(apiResponse.getData());
               } else {
                   routingContext.response()
                                 .end();
               }
           })
           .onFailure(routingContext::fail);
    }

    private void listVotes(RoutingContext routingContext) {
        logger.info("listVotes()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        RequestParameter body = requestParameters.body();
        CollectionRequest collectionRequest = body != null
                                              ? DatabindCodec.mapper()
                                                             .convertValue(body.get(), new TypeReference<CollectionRequest>() {
                                                             })
                                              : null;

        logger.debug("Parameter collectionRequest is {}", collectionRequest);

        api.listVotes(collectionRequest)
           .onSuccess(apiResponse -> {
               routingContext.response()
                             .setStatusCode(apiResponse.getStatusCode());
               if (apiResponse.hasData()) {
                   routingContext.json(apiResponse.getData());
               } else {
                   routingContext.response()
                                 .end();
               }
           })
           .onFailure(routingContext::fail);
    }

    private void replyOnComment(RoutingContext routingContext) {
        logger.info("replyOnComment()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        RequestParameter body = requestParameters.body();
        ReplyCommentRequest replyCommentRequest = body != null
                                                  ? DatabindCodec.mapper()
                                                                 .convertValue(body.get(), new TypeReference<ReplyCommentRequest>() {
                                                                 })
                                                  : null;

        logger.debug("Parameter replyCommentRequest is {}", replyCommentRequest);

        api.replyOnComment(replyCommentRequest)
           .onSuccess(apiResponse -> {
               routingContext.response()
                             .setStatusCode(apiResponse.getStatusCode());
               if (apiResponse.hasData()) {
                   routingContext.json(apiResponse.getData());
               } else {
                   routingContext.response()
                                 .end();
               }
           })
           .onFailure(routingContext::fail);
    }

}
