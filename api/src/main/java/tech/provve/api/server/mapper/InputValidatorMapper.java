package tech.provve.api.server.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import tech.provve.api.server.generated.dto.Filter;
import tech.provve.api.server.generated.dto.Pagination;
import tech.provve.api.server.validation.dto.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface InputValidatorMapper {

    InputValidatorMapper INSTANCE = Mappers.getMapper(InputValidatorMapper.class);

    RegisterAccountRequest map(tech.provve.api.server.generated.dto.RegisterAccountRequest from);

    AuthenticateUserRequest map(tech.provve.api.server.generated.dto.AuthenticateUserRequest from);

    DeleteAccountRequest map(tech.provve.api.server.generated.dto.DeleteAccountRequest from);

    UpdateAvatarRequest map(tech.provve.api.server.generated.dto.UpdateAvatarRequest from);

    UpdateEmailRequest map(tech.provve.api.server.generated.dto.UpdateEmailRequest from);

    UpdatePasswordRequest map(tech.provve.api.server.generated.dto.UpdatePasswordRequest from);

    UpdatePersonalDataConsentRequest map(tech.provve.api.server.generated.dto.UpdatePersonalDataConsentRequest from);

    ExamAddVote map(tech.provve.api.server.generated.dto.ExamAddVote from);

    CreateSessionRequest map(tech.provve.api.server.generated.dto.CreateSessionRequest from);

    CollectionRequest map(tech.provve.api.server.generated.dto.CollectionRequest from);

    CollectionRequest.Filter map(Filter from);

    CollectionRequest.Pagination map(Pagination from);

    default CollectionAuthenticatedRequest map(tech.provve.api.server.generated.dto.CollectionAuthenticatedRequest from) {
        return new CollectionAuthenticatedRequest(new CollectionRequest(map(from.getFilter()),
                                                                        map(from.getPagination())),
                                                  from.getAuthToken());
    }

    AddCommentRequest map(tech.provve.api.server.generated.dto.AddCommentRequest from);

    EditCommentRequest map(tech.provve.api.server.generated.dto.EditCommentRequest from);

    DeleteCommentRequest map(tech.provve.api.server.generated.dto.DeleteCommentRequest from);

    ReplyCommentRequest map(tech.provve.api.server.generated.dto.ReplyCommentRequest from);

    UpdateInterestsRequest map(tech.provve.api.server.generated.dto.UpdateInterestsRequest from);

}
