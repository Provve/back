package tech.provve.libs.events;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BusTest {

    @Test
    void register_eventPosted_received() {
        // arrange
        var event = new Event();
        var bus = Bus.get();
        var consumed = new AtomicBoolean(false);

        // act
        bus.register(Event.class, e -> {
            if (e != null) consumed.set(true);
        });

        // assert
        bus.post(event);
        assertTrue(consumed.get());
    }

    record Event() {

    }

}