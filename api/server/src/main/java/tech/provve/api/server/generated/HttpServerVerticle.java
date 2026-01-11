package tech.provve.api.server.generated;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import tech.provve.api.server.RouteHandler;
import tech.provve.api.server.generated.api.*;

import tech.provve.api.server.generated.api.AccountsApiHandler;
import tech.provve.api.server.generated.api.AccountsApiImpl;
import tech.provve.api.server.generated.api.NotificationsApiHandler;
import tech.provve.api.server.generated.api.NotificationsApiImpl;
import tech.provve.api.server.generated.api.SessionsApiHandler;
import tech.provve.api.server.generated.api.SessionsApiImpl;
import tech.provve.api.server.generated.api.SkillsApiHandler;
import tech.provve.api.server.generated.api.SkillsApiImpl;
import tech.provve.api.server.generated.api.VotesApiHandler;
import tech.provve.api.server.generated.api.VotesApiImpl;

// костыль для динамической подтягивания всех гененрируемых api handler`ов
public class HttpServerVerticle extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<RouteHandler> binder = Multibinder.newSetBinder(binder(), RouteHandler.class);


        binder.addBinding()
              .toInstance(new AccountsApiHandler(new AccountsApiImpl()));

        binder.addBinding()
              .toInstance(new NotificationsApiHandler(new NotificationsApiImpl()));

        binder.addBinding()
              .toInstance(new SessionsApiHandler(new SessionsApiImpl()));

        binder.addBinding()
              .toInstance(new SkillsApiHandler(new SkillsApiImpl()));

        binder.addBinding()
              .toInstance(new VotesApiHandler(new VotesApiImpl()));

    }
}