package tech.provve.validation.domain.entity;

/**
 * @param cheated  Читерил ли экзаменуемый
 * @param examinee За кем велось наблюдение
 */
public record Observation(String examinee, boolean cheated) {

}
