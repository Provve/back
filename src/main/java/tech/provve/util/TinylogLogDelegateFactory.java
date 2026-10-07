package tech.provve.util;

import io.vertx.core.logging.SLF4JLogDelegate;
import io.vertx.core.spi.logging.LogDelegate;
import io.vertx.core.spi.logging.LogDelegateFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

/**
 * Vert.x delegate that routes its internal logging to tinylog through SLF4J.
 * <p>
 * Vert.x selects {@code SLF4JLogDelegateFactory} automatically whenever SLF4J is on the classpath. Its
 * {@code SLF4JLogDelegate} calls {@code LocationAwareLogger.log(fqcn, ...)}, passing
 * {@code io.vertx.core.logging.Logger} as that FQCN. FQCN is the fully qualified class name of the logging wrapper
 * that the backend must skip while walking the stack in order to find the real source file and line of a log
 * statement.
 * <p>
 * This is where the bug shows up: Vert.x reports {@code io.vertx.core.logging.Logger} as the caller, but it does not
 * actually log from that class, it logs from {@code SLF4JLogDelegate}. Tinylog scans the stack for
 * {@code io.vertx.core.logging.Logger}, does not find it there, and prints
 * {@code LOGGER ERROR: Logger class "io.vertx.core.logging.Logger" is missing in stack trace} for every Vert.x log
 * entry. This is a known Vert.x bug (see tinylog issue #191).
 * <p>
 * The workaround wraps the tinylog logger into a plain {@link Logger} that no longer implements
 * {@code LocationAwareLogger}. Vert.x then falls back to the regular, location-agnostic SLF4J methods, which tinylog
 * resolves by stack depth and therefore logs without errors.
 * <p>
 * Activated from {@link tech.provve.api.Main} via the
 * {@code vertx.logger-delegate-factory-class-name} system property.
 */
public final class TinylogLogDelegateFactory implements LogDelegateFactory {

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public LogDelegate createDelegate(String name) {
        Logger locationAware = LoggerFactory.getLogger(name);
        Logger locationAgnostic = (Logger) Proxy.newProxyInstance(
                Logger.class.getClassLoader(),
                new Class<?>[]{Logger.class},
                (_, method, args) -> {
                    try {
                        return method.invoke(locationAware, args);
                    } catch (InvocationTargetException e) {
                        throw e.getCause();
                    }
                }
        );
        return new SLF4JLogDelegate(locationAgnostic);
    }
}
