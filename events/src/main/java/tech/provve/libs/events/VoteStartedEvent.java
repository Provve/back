package tech.provve.libs.events;

public record VoteStartedEvent(String login, String email, String voteName) {

}
