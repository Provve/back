package tech.provve.validation.domain.value;

/**
 * Представляет Docker-контейнер, в котором проверяется решение. В один момент может существовать один контейнер для {@link #examinee}
 *
 * @param examinee    автор решения.
 * @param examName    название экзамена (по какому экзамену сделано решение).
 * @param containerId ID контейнера (как в docker ps).
 */
public record ContainerView(String examinee, String examName, String containerId) {

}
