package tech.provve.api.server.exception;

import io.netty.handler.codec.http.HttpResponseStatus;

/**
 * Created to bypass limitations of {@link io.vertx.ext.web.handler.HttpException}: message from a cause don`t used when forming detail message.
 */
public class HttpException extends RuntimeException {

    private final int statusCode;

    public HttpException(Throwable cause, int statusCode) {
        super(cause.getMessage(), cause);
        this.statusCode = statusCode;
    }

    public HttpException(int statusCode) {
        super(HttpResponseStatus.valueOf(statusCode)
                                .reasonPhrase());
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
