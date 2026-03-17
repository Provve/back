package tech.provve.skill.domain.entity;

import java.time.Instant;

/**
 * @param owner    owning account
 * @param examName to which the session is attached to
 * @param started  when session is started
 */
public record Session(String owner, String examName, Instant started) {

}
