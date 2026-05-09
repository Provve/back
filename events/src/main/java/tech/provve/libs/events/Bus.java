package tech.provve.libs.events;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

public class Bus {

    private static final Bus INSTANCE = new Bus();

    private final Subject<Object> subjectBus = PublishSubject.create();

    public static Bus get() {
        return INSTANCE;
    }

    public <T> Disposable register(final Class<T> eventClass, Consumer<T> onNext) {
        return subjectBus
                .filter(event -> event.getClass()
                                      .equals(eventClass))
                .cast(eventClass)
                .subscribe(onNext);
    }

    public void post(Object event) {
        subjectBus.onNext(event);
    }

}
