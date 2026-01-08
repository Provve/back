package tech.provve.api.server.generated;

import com.fasterxml.jackson.core.type.TypeReference;
import io.vertx.core.json.jackson.DatabindCodec;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.openapi.RouterBuilder;
import io.vertx.ext.web.validation.RequestParameter;
import io.vertx.ext.web.validation.RequestParameters;
import io.vertx.ext.web.validation.ValidationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.provve.api.server.generated.dto.*;

import java.util.UUID;

public class VotesApiHandler {

    private static final Logger logger = LoggerFactory.getLogger(VotesApiHandler.class);

    private final VotesApi api;

    @Deprecated
    public VotesApiHandler() {
        this(new VotesApiImpl());
    }

    public VotesApiHandler(VotesApi api) {
        this.api = api;
    }

    public void mount(RouterBuilder builder) {
        builder.operation("votesGet")
               .handler(this::votesGet);
        builder.operation("votesIdCommentsGet")
               .handler(this::votesIdCommentsGet);
        builder.operation("votesIdCommentsPost")
               .handler(this::votesIdCommentsPost);
        builder.operation("votesIdVotingPost")
               .handler(this::votesIdVotingPost);
        builder.operation("votesPost")
               .handler(this::votesPost);
        builder.operation("votesVoteIdCommentsCommentIdDelete")
               .handler(this::votesVoteIdCommentsCommentIdDelete);
        builder.operation("votesVoteIdCommentsCommentIdPut")
               .handler(this::votesVoteIdCommentsCommentIdPut);
    }

    private void votesGet(RoutingContext routingContext) {
        logger.info("votesGet()");

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

        api.votesGet(pagination, filter)
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

    private void votesIdCommentsGet(RoutingContext routingContext) {
        logger.info("votesIdCommentsGet()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        java.util.UUID id =
                requestParameters.pathParameter("id") != null ? UUID.fromString(requestParameters.pathParameter("id")
                                                                                                 .getString()) : null;

        logger.debug("Parameter id is {}", id);

        api.votesIdCommentsGet(id)
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

    private void votesIdCommentsPost(RoutingContext routingContext) {
        logger.info("votesIdCommentsPost()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        java.util.UUID id =
                requestParameters.pathParameter("id") != null ? UUID.fromString(requestParameters.pathParameter("id")
                                                                                                 .getString()) : null;
        RequestParameter body = requestParameters.body();
        VotesIdCommentsPostRequest votesIdCommentsPostRequest = body != null ? DatabindCodec.mapper()
                                                                                            .convertValue(
                                                                                                    body.get(),
                                                                                                    new TypeReference<VotesIdCommentsPostRequest>() {
                                                                                                    }
                                                                                            ) : null;

        logger.debug("Parameter id is {}", id);
        logger.debug("Parameter votesIdCommentsPostRequest is {}", votesIdCommentsPostRequest);

        api.votesIdCommentsPost(id, votesIdCommentsPostRequest)
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

    private void votesIdVotingPost(RoutingContext routingContext) {
        logger.info("votesIdVotingPost()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        java.util.UUID id =
                requestParameters.pathParameter("id") != null ? UUID.fromString(requestParameters.pathParameter("id")
                                                                                                 .getString()) : null;
        RequestParameter body = requestParameters.body();
        VotesIdVotingPostRequest votesIdVotingPostRequest = body != null ? DatabindCodec.mapper()
                                                                                        .convertValue(
                                                                                                body.get(),
                                                                                                new TypeReference<VotesIdVotingPostRequest>() {
                                                                                                }
                                                                                        ) : null;

        logger.debug("Parameter id is {}", id);
        logger.debug("Parameter votesIdVotingPostRequest is {}", votesIdVotingPostRequest);

        api.votesIdVotingPost(id, votesIdVotingPostRequest)
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

    private void votesPost(RoutingContext routingContext) {
        logger.info("votesPost()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        RequestParameter body = requestParameters.body();
        VotesPostRequest votesPostRequest = body != null ? DatabindCodec.mapper()
                                                                        .convertValue(
                                                                                body.get(),
                                                                                new TypeReference<VotesPostRequest>() {
                                                                                }
                                                                        ) : null;

        logger.debug("Parameter votesPostRequest is {}", votesPostRequest);

        api.votesPost(votesPostRequest)
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

    private void votesVoteIdCommentsCommentIdDelete(RoutingContext routingContext) {
        logger.info("votesVoteIdCommentsCommentIdDelete()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        java.util.UUID voteId =
                requestParameters.pathParameter("vote_id") != null ? UUID.fromString(requestParameters.pathParameter(
                                                                                                              "vote_id")
                                                                                                      .getString())
                        : null;
        java.util.UUID commentId = requestParameters.pathParameter("comment_id") != null ? UUID.fromString(
                requestParameters.pathParameter("comment_id")
                                 .getString()) : null;

        logger.debug("Parameter voteId is {}", voteId);
        logger.debug("Parameter commentId is {}", commentId);

        api.votesVoteIdCommentsCommentIdDelete(voteId, commentId)
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

    private void votesVoteIdCommentsCommentIdPut(RoutingContext routingContext) {
        logger.info("votesVoteIdCommentsCommentIdPut()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        java.util.UUID voteId =
                requestParameters.pathParameter("vote_id") != null ? UUID.fromString(requestParameters.pathParameter(
                                                                                                              "vote_id")
                                                                                                      .getString())
                        : null;
        java.util.UUID commentId = requestParameters.pathParameter("comment_id") != null ? UUID.fromString(
                requestParameters.pathParameter("comment_id")
                                 .getString()) : null;

        logger.debug("Parameter voteId is {}", voteId);
        logger.debug("Parameter commentId is {}", commentId);

        api.votesVoteIdCommentsCommentIdPut(voteId, commentId)
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
