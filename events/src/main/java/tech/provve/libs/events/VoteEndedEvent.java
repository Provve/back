package tech.provve.libs.events;

public record VoteEndedEvent(String voteName, boolean success) {

}
