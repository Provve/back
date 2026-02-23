package tech.provve.api.server.validation.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Проверяет:
 * <ol>
 *     <li>Без согласия на обработку Пд email нельзя сохранять</li>
 *     <li>Формат email</li>
 * </ol>
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = LegallySetEmailValidator.class)
public @interface LegalEmail {

    String message() default "Email cannot be set before an user personal data consent.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
