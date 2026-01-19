package tech.provve.api.server.configuration;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import tech.provve.api.server.RouteHandler;
import tech.provve.api.server.controller.*;
import tech.provve.api.server.generated.api.*;

public class RouteHandlersGuiceConfiguration extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<RouteHandler> binder = Multibinder.newSetBinder(binder(), RouteHandler.class);
        binder.addBinding()
              .to(AccountsApiHandler.class);
        binder.addBinding()
              .to(NotificationsApiHandler.class);
        binder.addBinding()
              .to(SessionsApiHandler.class);
        binder.addBinding()
              .to(SkillsApiHandler.class);
        binder.addBinding()
              .to(VotesApiHandler.class);

        bind(AccountsApi.class).to(AccountsApiImpl.class);
        bind(NotificationsApi.class).to(NotificationsApiImpl.class);
        bind(SessionsApi.class).to(SessionsApiImpl.class);
        bind(SkillsApi.class).to(SkillsApiImpl.class);
        bind(VotesApi.class).to(VotesApiImpl.class);
    }
}
