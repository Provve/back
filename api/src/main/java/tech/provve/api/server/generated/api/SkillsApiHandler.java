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
import tech.provve.api.server.generated.dto.CollectionAuthenticatedRequest;
import tech.provve.api.server.generated.dto.CollectionRequest;
import tech.provve.api.server.generated.dto.SubmitExamSolutionRequest;

@Singleton
public class SkillsApiHandler implements RouteHandler {

    private static final Logger logger = LoggerFactory.getLogger(SkillsApiHandler.class);

    private final SkillsApi api;

    public SkillsApiHandler(SkillsApi api) {
        this.api = api;
    }

    public void mount(RouterBuilder builder) {
        builder.operation("getExamResult")
               .handler(this::getExamResult);
        builder.operation("listExaminees")
               .handler(this::listExaminees);
        builder.operation("listExams")
               .handler(this::listExams);
        builder.operation("listResults")
               .handler(this::listResults);
        builder.operation("listSkills")
               .handler(this::listSkills);
        builder.operation("submitExamSolution")
               .handler(this::submitExamSolution);
    }

    private void getExamResult(RoutingContext routingContext) {
        logger.info("getExamResult()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        String examName = requestParameters.pathParameter("exam_name") != null
                          ? requestParameters.pathParameter("exam_name")
                                             .getString()
                          : null;

        logger.debug("Parameter examName is {}", examName);

        api.getExamResult(examName)
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

    private void listExaminees(RoutingContext routingContext) {
        logger.info("listExaminees()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        RequestParameter body = requestParameters.body();
        CollectionAuthenticatedRequest collectionAuthenticatedRequest = body != null
                                                                        ? DatabindCodec.mapper()
                                                                                       .convertValue(body.get(),
                                                                                                     new TypeReference<CollectionAuthenticatedRequest>() {
                                                                                                     })
                                                                        : null;

        logger.debug("Parameter collectionAuthenticatedRequest is {}", collectionAuthenticatedRequest);

        api.listExaminees(collectionAuthenticatedRequest)
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

    private void listExams(RoutingContext routingContext) {
        logger.info("listExams()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        String skillName = requestParameters.pathParameter("skill_name") != null
                           ? requestParameters.pathParameter("skill_name")
                                              .getString()
                           : null;
        RequestParameter body = requestParameters.body();
        CollectionRequest collectionRequest = body != null
                                              ? DatabindCodec.mapper()
                                                             .convertValue(body.get(), new TypeReference<CollectionRequest>() {
                                                             })
                                              : null;

        logger.debug("Parameter skillName is {}", skillName);
        logger.debug("Parameter collectionRequest is {}", collectionRequest);

        api.listExams(skillName, collectionRequest)
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

    private void listResults(RoutingContext routingContext) {
        logger.info("listResults()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        String skillName = requestParameters.pathParameter("skill_name") != null
                           ? requestParameters.pathParameter("skill_name")
                                              .getString()
                           : null;
        RequestParameter body = requestParameters.body();
        CollectionAuthenticatedRequest collectionAuthenticatedRequest = body != null
                                                                        ? DatabindCodec.mapper()
                                                                                       .convertValue(body.get(),
                                                                                                     new TypeReference<CollectionAuthenticatedRequest>() {
                                                                                                     })
                                                                        : null;

        logger.debug("Parameter skillName is {}", skillName);
        logger.debug("Parameter collectionAuthenticatedRequest is {}", collectionAuthenticatedRequest);

        api.listResults(skillName, collectionAuthenticatedRequest)
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

    private void listSkills(RoutingContext routingContext) {
        logger.info("listSkills()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        RequestParameter body = requestParameters.body();
        CollectionRequest collectionRequest = body != null
                                              ? DatabindCodec.mapper()
                                                             .convertValue(body.get(), new TypeReference<CollectionRequest>() {
                                                             })
                                              : null;

        logger.debug("Parameter collectionRequest is {}", collectionRequest);

        api.listSkills(collectionRequest)
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

    private void submitExamSolution(RoutingContext routingContext) {
        logger.info("submitExamSolution()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        String examName = requestParameters.pathParameter("exam_name") != null
                          ? requestParameters.pathParameter("exam_name")
                                             .getString()
                          : null;
        RequestParameter body = requestParameters.body();
        SubmitExamSolutionRequest submitExamSolutionRequest = body != null
                                                              ? DatabindCodec.mapper()
                                                                             .convertValue(body.get(), new TypeReference<SubmitExamSolutionRequest>() {
                                                                             })
                                                              : null;

        logger.debug("Parameter examName is {}", examName);
        logger.debug("Parameter submitExamSolutionRequest is {}", submitExamSolutionRequest);

        api.submitExamSolution(examName, submitExamSolutionRequest)
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
