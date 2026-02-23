package tech.provve.api.server.generated.api;

import tech.provve.api.server.generated.dto.AddCommentOnVoteRequest;
import tech.provve.api.server.generated.dto.CastVoteRequest;
import tech.provve.api.server.generated.dto.CommentResponse;
import tech.provve.api.server.generated.dto.Error;
import tech.provve.api.server.generated.dto.ExamAddVote;
import tech.provve.api.server.generated.dto.Filter;
import tech.provve.api.server.generated.dto.Pagination;
import tech.provve.api.server.generated.dto.SkillAddVote;
import tech.provve.api.server.generated.dto.SkillDelVote;
import tech.provve.api.server.generated.dto.VoteResponse;

import tech.provve.api.server.RouteHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import io.vertx.core.json.jackson.DatabindCodec;
import io.vertx.ext.web.openapi.RouterBuilder;
import io.vertx.ext.web.validation.RequestParameters;
import io.vertx.ext.web.validation.RequestParameter;
import io.vertx.ext.web.validation.ValidationHandler;
import io.vertx.ext.web.RoutingContext;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Map;

@Singleton
public class VotesApiHandler implements RouteHandler {

    private static final Logger logger = LoggerFactory.getLogger(VotesApiHandler.class);

    private final VotesApi api;

    public VotesApiHandler(VotesApi api) {
        this.api = api;
    }

    public void mount(RouterBuilder builder) {
        builder.operation("addCommentOnVote")
               .handler(this::addCommentOnVote);
        builder.operation("castVote")
               .handler(this::castVote);
        builder.operation("createExamAddVote")
               .handler(this::createExamAddVote);
        builder.operation("createSkillAddVote")
               .handler(this::createSkillAddVote);
        builder.operation("createSkillDelVote")
               .handler(this::createSkillDelVote);
        builder.operation("deleteCommentOnVote")
               .handler(this::deleteCommentOnVote);
        builder.operation("editCommentOnVote")
               .handler(this::editCommentOnVote);
        builder.operation("listCommentsOnVote")
               .handler(this::listCommentsOnVote);
        builder.operation("listVotes")
               .handler(this::listVotes);
    }

    private void addCommentOnVote(RoutingContext routingContext) {
        logger.info("addCommentOnVote()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        String name = requestParameters.pathParameter("name") != null ? requestParameters.pathParameter("name")
                                                                                         .getString() : null;
        RequestParameter body = requestParameters.body();
        AddCommentOnVoteRequest addCommentOnVoteRequest = body != null ? DatabindCodec.mapper()
                                                                                      .convertValue(
                                                                                              body.get(), new TypeReference<AddCommentOnVoteRequest>() {
                                                                                              }
                                                                                      ) : null;

        logger.debug("Parameter name is {}", name);
        logger.debug("Parameter addCommentOnVoteRequest is {}", addCommentOnVoteRequest);

        api.addCommentOnVote(name, addCommentOnVoteRequest)
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

        String name = requestParameters.pathParameter("name") != null ? requestParameters.pathParameter("name")
                                                                                         .getString() : null;
        RequestParameter body = requestParameters.body();
        CastVoteRequest castVoteRequest = body != null ? DatabindCodec.mapper()
                                                                      .convertValue(
                                                                              body.get(), new TypeReference<CastVoteRequest>() {
                                                                              }
                                                                      ) : null;

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
        ExamAddVote examAddVote = body != null ? DatabindCodec.mapper()
                                                              .convertValue(
                                                                      body.get(), new TypeReference<ExamAddVote>() {
                                                                      }
                                                              ) : null;

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
        SkillAddVote skillAddVote = body != null ? DatabindCodec.mapper()
                                                                .convertValue(
                                                                        body.get(), new TypeReference<SkillAddVote>() {
                                                                        }
                                                                ) : null;

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
        SkillDelVote skillDelVote = body != null ? DatabindCodec.mapper()
                                                                .convertValue(
                                                                        body.get(), new TypeReference<SkillDelVote>() {
                                                                        }
                                                                ) : null;

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

    private void deleteCommentOnVote(RoutingContext routingContext) {
        logger.info("deleteCommentOnVote()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        String voteName = requestParameters.pathParameter("vote_name") != null ? requestParameters.pathParameter("vote_name")
                                                                                                  .getString() : null;
        Integer commentId = requestParameters.pathParameter("comment_id") != null ? requestParameters.pathParameter("comment_id")
                                                                                                     .getInteger() : null;

        logger.debug("Parameter voteName is {}", voteName);
        logger.debug("Parameter commentId is {}", commentId);

        api.deleteCommentOnVote(voteName, commentId)
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

    private void editCommentOnVote(RoutingContext routingContext) {
        logger.info("editCommentOnVote()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        String voteName = requestParameters.pathParameter("vote_name") != null ? requestParameters.pathParameter("vote_name")
                                                                                                  .getString() : null;
        Integer commentId = requestParameters.pathParameter("comment_id") != null ? requestParameters.pathParameter("comment_id")
                                                                                                     .getInteger() : null;

        logger.debug("Parameter voteName is {}", voteName);
        logger.debug("Parameter commentId is {}", commentId);

        api.editCommentOnVote(voteName, commentId)
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

    private void listCommentsOnVote(RoutingContext routingContext) {
        logger.info("listCommentsOnVote()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        String name = requestParameters.pathParameter("name") != null ? requestParameters.pathParameter("name")
                                                                                         .getString() : null;

        logger.debug("Parameter name is {}", name);

        api.listCommentsOnVote(name)
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

        Pagination pagination = requestParameters.queryParameter("pagination") != null ? DatabindCodec.mapper()
                                                                                                      .convertValue(
                                                                                                              requestParameters.queryParameter("pagination")
                                                                                                                               .get(),
                                                                                                              new TypeReference<Pagination>() {
                                                                                                              }
                                                                                                      ) : null;
        Filter filter = requestParameters.queryParameter("filter") != null ? DatabindCodec.mapper()
                                                                                          .convertValue(
                                                                                                  requestParameters.queryParameter("filter")
                                                                                                                   .get(), new TypeReference<Filter>() {
                                                                                                  }
                                                                                          ) : null;

        logger.debug("Parameter pagination is {}", pagination);
        logger.debug("Parameter filter is {}", filter);

        api.listVotes(pagination, filter)
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
