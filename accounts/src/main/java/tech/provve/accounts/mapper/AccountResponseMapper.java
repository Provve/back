package tech.provve.accounts.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import tech.provve.accounts.domain.model.Account;
import tech.provve.api.server.generated.dto.ProfilePrivateView;

@Mapper
public interface AccountResponseMapper {

    AccountResponseMapper INST = Mappers.getMapper(AccountResponseMapper.class);

    ProfilePrivateView map(Account from);

}
