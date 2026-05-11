package tech.provve.libs.events;

public record ExamSourceUploadedEvent(String examName, String author, String delayedVoteJson) {

}
