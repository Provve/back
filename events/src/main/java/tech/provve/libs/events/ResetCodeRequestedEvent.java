package tech.provve.libs.events;

public record ResetCodeRequestedEvent(String login, String email, String resetToken) {

}
