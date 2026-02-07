package tech.provve.accounts.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import tech.provve.accounts.db.generated.tables.records.AccountsRecord;
import tech.provve.accounts.domain.model.Account;
import tech.provve.api.server.generated.dto.RegisterAccountRequest;

@Mapper
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Mapping(target = "consentPersonalData", source = "isConsentPersonalData")
    @Mapping(target = "premium", source = "isPremium")
    AccountsRecord map(Account from);

    @Mapping(target = "isConsentPersonalData", source = "consentPersonalData")
    @Mapping(target = "isPremium", expression = "java(Boolean.FALSE)")
    @Mapping(target = "avatarUrl", expression = "java(null)")
    Account map(RegisterAccountRequest from);

}
