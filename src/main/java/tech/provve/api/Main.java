package tech.provve.api;

import alekseyvideman.dop.Collection;
import io.vertx.core.Launcher;
import tech.provve.util.Jackson;
import tech.provve.util.Storage;
import tech.provve.util.TinylogLogDelegateFactory;


public final class Main extends Launcher {

    static {
        System.setProperty(
                "vertx.logger-delegate-factory-class-name",
                TinylogLogDelegateFactory.class.getName()
        );
    }

    public static void main(String[] args) {
        Storage.Initialization.init();
        Collection.init(Jackson.json);

        new Main().dispatch(args);
    }
}
