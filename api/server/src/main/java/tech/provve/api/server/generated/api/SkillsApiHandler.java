package tech.provve.api.server.generated.api;

import com.fasterxml.jackson.core.type.TypeReference;
import io.vertx.core.json.jackson.DatabindCodec;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.openapi.RouterBuilder;
import io.vertx.ext.web.validation.RequestParameters;
import io.vertx.ext.web.validation.ValidationHandler;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.provve.api.server.RouteHandler;
import tech.provve.api.server.generated.dto.Filter;
import tech.provve.api.server.generated.dto.Pagination;

import java.util.UUID;

@Singleton
public class SkillsApiHandler implements RouteHandler {

        private static final Logger logger = LoggerFactory.getLogger(SkillsApiHandler.class);

        private final SkillsApi api;

        public SkillsApiHandler(SkillsApi api) {
                this.api = api;
        }

        public void mount(RouterBuilder builder) {
                builder.operation("getResultsBySkill")
                       .handler(this::getResultsBySkill);
                builder.operation("listExamsBySkill")
                       .handler(this::listExamsBySkill);
                builder.operation("listSkills")
                       .handler(this::listSkills);
                builder.operation("submitExamSolution")
                       .handler(this::submitExamSolution);
                builder.operation("viewExamResult")
                       .handler(this::viewExamResult);
        }

        private void getResultsBySkill(RoutingContext routingContext) {
                logger.info("getResultsBySkill()");

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

                api.getResultsBySkill(pagination, filter)
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

        private void listExamsBySkill(RoutingContext routingContext) {
                logger.info("listExamsBySkill()");

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

                api.listExamsBySkill(pagination, filter)
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

                api.listSkills(pagination, filter)
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

                UUID id =
                        requestParameters.pathParameter("id") != null ? UUID.fromString(requestParameters.pathParameter(
                                                                                                                 "id")
                                                                                                         .getString())
                                : null;
                FileUpload solution = routingContext.fileUploads()
                                                    .iterator()
                                                    .next();

            logger.debug("Parameter id is {}", id);
            logger.debug("Parameter solution is {}", solution);

                api.submitExamSolution(id, solution)
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

        private void viewExamResult(RoutingContext routingContext) {
                logger.info("viewExamResult()");

        // Param extraction
        RequestParameters requestParameters = routingContext.get(ValidationHandler.REQUEST_CONTEXT_KEY);

                UUID examId = requestParameters.pathParameter("exam_id") != null
                        ? UUID.fromString(requestParameters.pathParameter("exam_id")
                                                           .getString()) : null;

            logger.debug("Parameter examId is {}", examId);

                api.viewExamResult(examId)
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
