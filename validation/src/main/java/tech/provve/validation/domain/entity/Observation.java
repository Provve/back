package tech.provve.validation.domain.entity;

/**
 * @param cheated  Читерил ли экзаменуемый
 * @param violations Что нарушено
 * @param examinee За кем велось наблюдение
 */
public record Observation(String examinee, String violations, boolean cheated) {

}
