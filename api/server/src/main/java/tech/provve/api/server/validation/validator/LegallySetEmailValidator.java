package tech.provve.api.server.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import tech.provve.api.server.validation.dto.RegisterAccountRequest;

import static tech.provve.api.server.predicate.StringPredicate.isBlank;

public class LegallySetEmailValidator implements ConstraintValidator<LegalEmail, RegisterAccountRequest> {

    private static final String EMAIL_PATTERN =
            "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    private static int RFC_EMAIL_LENGTH = 254; //  RFC 5321

    @Override
    public boolean isValid(RegisterAccountRequest value, ConstraintValidatorContext context) {
        return isBlank(value.email())
                || (value.email()
                         .length() <= RFC_EMAIL_LENGTH
                && value.email()
                        .matches(EMAIL_PATTERN)
                && value.consentPersonalData());
    }
}
