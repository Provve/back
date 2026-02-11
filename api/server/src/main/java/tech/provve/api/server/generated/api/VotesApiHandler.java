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

import java.util.UUID;

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
        builder.operation("createVote")
               .handler(this::createVote);
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

            UUID id = requestParameters.pathParameter("id") != null ? UUID.fromString(requestParameters.pathParameter(
                                                                                                               "id")
                                                                                                       .getString())
                    : null;
            RequestParameter body = requestParameters.body();
            AddCommentOnVoteRequest addCommentOnVoteRequest = body != null ? DatabindCodec.mapper()
                                                                                          .convertValue(
                                                                                                  body.get(),
                                                                                                  new TypeReference<AddCommentOnVoteRequest>() {
                                                                                                  }
                                                                                          ) : null;

            logger.debug("Parameter id is {}", id);
            logger.debug("Parameter addCommentOnVoteRequest is {}", addCommentOnVoteRequest);

            api.addCommentOnVote(id, addCommentOnVoteRequest)
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

            UUID id = requestParameters.pathParameter("id") != null ? UUID.fromString(requestParameters.pathParameter(
                                                                                                               "id")
                                                                                                       .getString())
                    : null;
            RequestParameter body = requestParameters.body();
            CastVoteRequest castVoteRequest = body != null ? DatabindCodec.mapper()
                                                                          .convertValue(
                                                                                  body.get(),
                                                                                  new TypeReference<CastVoteRequest>() {
                                                                                  }
                                                                          ) : null;

            logger.debug("Parameter id is {}", id);
            logger.debug("Parameter castVoteRequest is {}", castVoteRequest);

            api.castVote(id, castVoteRequest)
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

        private void createVote(RoutingContext routingContext) {
            logger.info("createVote()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

                RequestParameter body = requestParameters.body();
            CreateVoteRequest createVoteRequest = body != null ? DatabindCodec.mapper()
                                                                              .convertValue(
                                                                                      body.get(),
                                                                                      new TypeReference<CreateVoteRequest>() {
                                                                                      }
                                                                              ) : null;

            logger.debug("Parameter createVoteRequest is {}", createVoteRequest);

            api.createVote(createVoteRequest)
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

            UUID voteId = requestParameters.pathParameter("vote_id") != null
                    ? UUID.fromString(requestParameters.pathParameter("vote_id")
                                                       .getString()) : null;
            UUID commentId = requestParameters.pathParameter("comment_id") != null
                    ? UUID.fromString(requestParameters.pathParameter("comment_id")
                                                       .getString()) : null;

            logger.debug("Parameter voteId is {}", voteId);
            logger.debug("Parameter commentId is {}", commentId);

            api.deleteCommentOnVote(voteId, commentId)
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

            UUID voteId = requestParameters.pathParameter("vote_id") != null
                    ? UUID.fromString(requestParameters.pathParameter("vote_id")
                                                       .getString()) : null;
            UUID commentId = requestParameters.pathParameter("comment_id") != null
                    ? UUID.fromString(requestParameters.pathParameter("comment_id")
                                                       .getString()) : null;

            logger.debug("Parameter voteId is {}", voteId);
            logger.debug("Parameter commentId is {}", commentId);

            api.editCommentOnVote(voteId, commentId)
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

            UUID id = requestParameters.pathParameter("id") != null ? UUID.fromString(requestParameters.pathParameter(
                                                                                                               "id")
                                                                                                       .getString())
                    : null;

            logger.debug("Parameter id is {}", id);

            api.listCommentsOnVote(id)
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
                                                                                                                  requestParameters.queryParameter(
                                                                                                                                           "pagination")
                                                                                                                                   .get(),
                                                                                                                  new TypeReference<Pagination>() {
                                                                                                                  }
                                                                                                          ) : null;
            Filter filter = requestParameters.queryParameter("filter") != null ? DatabindCodec.mapper()
                                                                                              .convertValue(
                                                                                                      requestParameters.queryParameter(
                                                                                                                               "filter")
                                                                                                                       .get(),
                                                                                                      new TypeReference<Filter>() {
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
