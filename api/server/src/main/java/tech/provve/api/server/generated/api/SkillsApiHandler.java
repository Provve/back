package tech.provve.api.server.generated.api;

import com.fasterxml.jackson.core.type.TypeReference;
import io.vertx.core.json.jackson.DatabindCodec;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.openapi.RouterBuilder;
import io.vertx.ext.web.validation.RequestParameters;
import io.vertx.ext.web.validation.ValidationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.provve.api.server.generated.dto.Filter;
import tech.provve.api.server.generated.dto.Pagination;

import java.util.UUID;

public class SkillsApiHandler {

    private static final Logger logger = LoggerFactory.getLogger(SkillsApiHandler.class);

    private final SkillsApi api;

    public SkillsApiHandler(SkillsApi api) {
        this.api = api;
    }

    public void mount(RouterBuilder builder) {
        builder.operation("examsExamIdResultGet")
               .handler(this::examsExamIdResultGet);
        builder.operation("examsIdSolutionPost")
               .handler(this::examsIdSolutionPost);
        builder.operation("skillsGet")
               .handler(this::skillsGet);
        builder.operation("skillsSkillIdExamsGet")
               .handler(this::skillsSkillIdExamsGet);
        builder.operation("skillsSkillIdResultsGet")
               .handler(this::skillsSkillIdResultsGet);
    }

    private void examsExamIdResultGet(RoutingContext routingContext) {
        logger.info("examsExamIdResultGet()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        java.util.UUID examId =
                requestParameters.pathParameter("exam_id") != null ? UUID.fromString(requestParameters.pathParameter(
                                                                                                              "exam_id")
                                                                                                      .getString())
                        : null;

        logger.debug("Parameter examId is {}", examId);

        api.examsExamIdResultGet(examId)
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

    private void examsIdSolutionPost(RoutingContext routingContext) {
        logger.info("examsIdSolutionPost()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

        java.util.UUID id = requestParameters.pathParameter("id") != null
                ? UUID.fromString(requestParameters.pathParameter("id")
                                                   .getString()) : null;
        FileUpload solution = routingContext.fileUploads()
                                            .iterator()
                                            .next();

        logger.debug("Parameter id is {}", id);
        logger.debug("Parameter solution is {}", solution);

        api.examsIdSolutionPost(id, solution)
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

    private void skillsGet(RoutingContext routingContext) {
        logger.info("skillsGet()");

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

        api.skillsGet(pagination, filter)
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

    private void skillsSkillIdExamsGet(RoutingContext routingContext) {
        logger.info("skillsSkillIdExamsGet()");

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

        api.skillsSkillIdExamsGet(pagination, filter)
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

    private void skillsSkillIdResultsGet(RoutingContext routingContext) {
        logger.info("skillsSkillIdResultsGet()");

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

        api.skillsSkillIdResultsGet(pagination, filter)
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
