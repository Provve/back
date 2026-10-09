package tech.provve.util;

import com.networknt.schema.*;
import com.networknt.schema.Error;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@UtilityClass
public class Validation {

    private static final SchemaRegistry schemaRegistry = SchemaRegistry.withDefaultDialect(SpecificationVersion.DRAFT_2020_12,
                                                                                           builder -> builder.schemaRegistryConfig(SchemaRegistryConfig.builder()
                                                                                                                                                       .formatAssertionsEnabled(
                                                                                                                                                               true)
                                                                                                                                                       .locale(Locale.ENGLISH)
                                                                                                                                                       .build()));

    public static String validateAddCommentRequest(Map<String, Object> addCommentRequest) {
        var singleKey = "q";
        var schemaLocations = Map.of(singleKey, "classpath:schema/AddCommentRequest.json");
        Map<String, String> serialized = Map.of(singleKey, Jackson.json.writeValueAsString(addCommentRequest));
        return Validation.validate(serialized, schemaLocations);
    }

    public static String validateAuthenticateUserRequest(Map<String, Object> authenticateUserRequest) {
        var singleKey = "q";
        var schemaLocations = Map.of(singleKey, "classpath:schema/AuthenticateUserRequest.json");
        Map<String, String> serialized = Map.of(singleKey, Jackson.json.writeValueAsString(authenticateUserRequest));
        return Validation.validate(serialized, schemaLocations);
    }

    public static String validateCastVote(Map<String, Object> castVote) {
        var singleKey = "q";
        var schemaLocations = Map.of(singleKey, "classpath:schema/CastVote.json");
        Map<String, String> serialized = Map.of(singleKey, Jackson.json.writeValueAsString(castVote));
        return Validation.validate(serialized, schemaLocations);
    }

    public static String validateCollectionAuthenticatedRequest(Map<String, Object> collectionAuthenticatedRequest) {
        var singleKey = "q";
        var schemaLocations = Map.of(singleKey, "classpath:schema/CollectionAuthenticatedRequest.json");
        Map<String, String> serialized = Map.of(singleKey, Jackson.json.writeValueAsString(collectionAuthenticatedRequest));
        return Validation.validate(serialized, schemaLocations);
    }

    public static String validateCollectionRequest(Map<String, Object> collectionRequest) {
        var singleKey = "q";
        var schemaLocations = Map.of(singleKey, "classpath:schema/CollectionRequest.json");
        Map<String, String> serialized = Map.of(singleKey, Jackson.json.writeValueAsString(collectionRequest));
        return Validation.validate(serialized, schemaLocations);
    }

    public static String validateCreateSessionRequest(Map<String, Object> createSessionRequest) {
        var singleKey = "q";
        var schemaLocations = Map.of(singleKey, "classpath:schema/CreateSessionRequest.json");
        Map<String, String> serialized = Map.of(singleKey, Jackson.json.writeValueAsString(createSessionRequest));
        return Validation.validate(serialized, schemaLocations);
    }

    public static String validateDeleteAccountRequest(Map<String, Object> deleteAccountRequest) {
        var singleKey = "q";
        var schemaLocations = Map.of(singleKey, "classpath:schema/DeleteAccountRequest.json");
        Map<String, String> serialized = Map.of(singleKey, Jackson.json.writeValueAsString(deleteAccountRequest));
        return Validation.validate(serialized, schemaLocations);
    }

    public static String validateDeleteCommentRequest(Map<String, Object> deleteCommentRequest) {
        var singleKey = "q";
        var schemaLocations = Map.of(singleKey, "classpath:schema/DeleteCommentRequest.json");
        Map<String, String> serialized = Map.of(singleKey, Jackson.json.writeValueAsString(deleteCommentRequest));
        return Validation.validate(serialized, schemaLocations);
    }

    public static String validateEditCommentRequest(Map<String, Object> editCommentRequest) {
        var singleKey = "q";
        var schemaLocations = Map.of(singleKey, "classpath:schema/EditCommentRequest.json");
        Map<String, String> serialized = Map.of(singleKey, Jackson.json.writeValueAsString(editCommentRequest));
        return Validation.validate(serialized, schemaLocations);
    }

    public static String validateRegisterAccountRequest(Map<String, Object> registerAccountRequest) {
        var singleKey = "q";
        var schemaLocations = Map.of(singleKey, "classpath:schema/RegisterAccountRequest.json");
        Map<String, String> serialized = Map.of(singleKey, Jackson.json.writeValueAsString(registerAccountRequest));
        return Validation.validate(serialized, schemaLocations);
    }

    public static String validateReplyCommentRequest(Map<String, Object> replyCommentRequest) {
        var singleKey = "q";
        var schemaLocations = Map.of(singleKey, "classpath:schema/ReplyCommentRequest.json");
        Map<String, String> serialized = Map.of(singleKey, Jackson.json.writeValueAsString(replyCommentRequest));
        return Validation.validate(serialized, schemaLocations);
    }

    public static String validateRequestResetCode(Map<String, Object> requestResetCode) {
        var singleKey = "q";
        var schemaLocations = Map.of(singleKey, "classpath:schema/RequestResetCode.json");
        Map<String, String> serialized = Map.of(singleKey, Jackson.json.writeValueAsString(requestResetCode));
        return Validation.validate(serialized, schemaLocations);
    }

    public static String validateSkillAddVote(Map<String, Object> skillAddVote) {
        var singleKey = "q";
        var schemaLocations = Map.of(singleKey, "classpath:schema/SkillAddVote.json");
        Map<String, String> serialized = Map.of(singleKey, Jackson.json.writeValueAsString(skillAddVote));
        return Validation.validate(serialized, schemaLocations);
    }

    public static String validateSkillDelVote(Map<String, Object> skillDelVote) {
        var singleKey = "q";
        var schemaLocations = Map.of(singleKey, "classpath:schema/SkillDelVote.json");
        Map<String, String> serialized = Map.of(singleKey, Jackson.json.writeValueAsString(skillDelVote));
        return Validation.validate(serialized, schemaLocations);
    }

    public static String validateUpdateAvatarRequest(Map<String, Object> updateAvatarRequest) {
        var singleKey = "q";
        var schemaLocations = Map.of(singleKey, "classpath:schema/UpdateAvatarRequest.json");
        Map<String, String> serialized = Map.of(singleKey, Jackson.json.writeValueAsString(updateAvatarRequest));
        return Validation.validate(serialized, schemaLocations);
    }

    public static String validateUpdateEmailRequest(Map<String, Object> updateEmailRequest) {
        var singleKey = "q";
        var schemaLocations = Map.of(singleKey, "classpath:schema/UpdateEmailRequest.json");
        Map<String, String> serialized = Map.of(singleKey, Jackson.json.writeValueAsString(updateEmailRequest));
        return Validation.validate(serialized, schemaLocations);
    }

    public static String validateUpdateInterestsRequest(Map<String, Object> updateInterestsRequest) {
        var singleKey = "q";
        var schemaLocations = Map.of(singleKey, "classpath:schema/UpdateInterestsRequest.json");
        Map<String, String> serialized = Map.of(singleKey, Jackson.json.writeValueAsString(updateInterestsRequest));
        return Validation.validate(serialized, schemaLocations);
    }

    public static String validateUpdatePasswordRequest(Map<String, Object> updatePasswordRequest) {
        var singleKey = "q";
        var schemaLocations = Map.of(singleKey, "classpath:schema/UpdatePasswordRequest.json");
        Map<String, String> serialized = Map.of(singleKey, Jackson.json.writeValueAsString(updatePasswordRequest));
        return Validation.validate(serialized, schemaLocations);
    }

    public static String validateUpdatePersonalDataConsentRequest(Map<String, Object> updatePersonalDataConsentRequest) {
        var singleKey = "q";
        var schemaLocations = Map.of(singleKey, "classpath:schema/UpdatePersonalDataConsentRequest.json");
        Map<String, String> serialized = Map.of(singleKey, Jackson.json.writeValueAsString(updatePersonalDataConsentRequest));
        return Validation.validate(serialized, schemaLocations);
    }

    /**
     * Validate Map against schemas entry by entry. Keys of validation subject and schemas must be equal! <br>
     *
     * @param subject         subject to validate, in JSON
     * @param schemaLocations schema locations <br>
     *                        <code>classpath:</code> prefix is used to load a schema from the jar.
     * @return errors
     */
    public static String validate(Map<String, String> subject, Map<String, String> schemaLocations) {
        return schemaLocations.entrySet()
                              .stream()
                              .map(schema -> {
                                  var schemaLocation = SchemaLocation.of(schema.getValue());
                                  var validator = schemaRegistry.getSchema(schemaLocation);
                                  List<Error> errors = validator.validate(subject.get(schema.getKey()), InputFormat.JSON);
                                  if (errors.isEmpty()) {
                                      return "";
                                  }
                                  return formatErrorMessages(errors);
                              })
                              .filter(s -> !s.isEmpty())
                              .collect(Collectors.joining(System.lineSeparator()));
    }

    private static String formatErrorMessages(List<Error> errorMessages) {
        return errorMessages.stream()
                            .map(Error::toString)
                            .collect(Collectors.joining(System.lineSeparator()));
    }

}
