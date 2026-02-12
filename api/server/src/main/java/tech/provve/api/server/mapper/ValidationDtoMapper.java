package tech.provve.api.server.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import tech.provve.api.server.validation.dto.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ValidationDtoMapper {

    ValidationDtoMapper INSTANCE = Mappers.getMapper(ValidationDtoMapper.class);

    RegisterAccountRequest map(tech.provve.api.server.generated.dto.RegisterAccountRequest from);

    AuthenticateUserRequest map(tech.provve.api.server.generated.dto.AuthenticateUserRequest from);

    DeleteAccountRequest map(tech.provve.api.server.generated.dto.DeleteAccountRequest from);

    UpdateAvatarRequest map(tech.provve.api.server.generated.dto.UpdateAvatarRequest from);

    UpdateEmailRequest map(tech.provve.api.server.generated.dto.UpdateEmailRequest from);

    UpdatePasswordRequest map(tech.provve.api.server.generated.dto.UpdatePasswordRequest from);

    UpdatePersonalDataConsentRequest map(tech.provve.api.server.generated.dto.UpdatePersonalDataConsentRequest from);

}
