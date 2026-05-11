package tech.provve.libs.events;

import java.nio.file.Path;

public record SolutionSourceUploadedEvent(String examName, String examinee, Path solutionSourcePath) {

}
