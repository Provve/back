package tech.provve.api;

import io.vertx.ext.web.openapi.RouterBuilder;

/**
 * Marker for all generated handlers.
 */
public interface RouteHandler {

    void mount(RouterBuilder builder);

}
