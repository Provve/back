package tech.provve.accounts.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tech.provve.constants.Entity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface AccountRepository {

    String ACCOUNT = "account";

    /**
     * Save completely new account.
     */
    @Insert("""
            INSERT INTO accounts.accounts (login, email, password_hash, consent_personal_data, username, premium)
            VALUES (
                #{account.login, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{account.email, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{account.passwordHash, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{account.isConsentPersonalData, typeHandler=org.apache.ibatis.type.BooleanTypeHandler},
                #{account.username, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{account.isPremium, typeHandler=org.apache.ibatis.type.BooleanTypeHandler}
            )
            """)
    void save(@Param("account") Map<String, Object> account);

    /**
     * Delete row and all cascade tables, possibly
     */
    @Delete("""
            DELETE FROM accounts.accounts
            WHERE login = #{login, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            """)
    void delete(@Param("login") String login);

    @Select("""
            SELECT * FROM accounts.accounts
            WHERE login = #{login, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            """)
    @Results(id = ACCOUNT, value = {
            @Result(property = Entity.Account.LOGIN, column = "login", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.Account.EMAIL, column = "email", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.Account.PASSWORD_HASH, column = "password_hash", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.Account.IS_CONSENT_PERSONAL_DATA, column = "consent_personal_data", typeHandler = org.apache.ibatis.type.BooleanTypeHandler.class),
            @Result(property = Entity.Account.USERNAME, column = "username", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.Account.AVATAR_URL, column = "avatar_url", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.Account.CONTACT_INFO, column = "contact_info", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.Account.INTERESTS, column = "interests", typeHandler = org.apache.ibatis.type.ArrayTypeHandler.class),
            @Result(property = Entity.Account.IS_PREMIUM, column = "premium", typeHandler = org.apache.ibatis.type.BooleanTypeHandler.class)
    })
    Optional<Map<String, Object>> findByLogin(@Param("login") String login);

    @Select("""
            SELECT * FROM accounts.accounts
            WHERE email = #{email, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            """)
    @ResultMap(ACCOUNT)
    Optional<Map<String, Object>> findByEmail(@Param("email") String email);

    @Select("""
            SELECT * FROM accounts.accounts
            WHERE #{skillName, typeHandler=org.apache.ibatis.type.StringTypeHandler} = ANY(interests)
            """)
    @ResultMap(ACCOUNT)
    List<Map<String, Object>> findInterestedIn(@Param("skillName") String skillName);

    @Update("""
            UPDATE accounts.accounts
            SET password_hash = #{passwordHash, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            WHERE login = #{login, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            """)
    void updatePasswordHash(@Param("passwordHash") String passwordHash, @Param("login") String login);

    @Update("""
            UPDATE accounts.accounts
            SET email = #{email, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            WHERE login = #{login, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            """)
    void updateEmail(@Param("login") String login, @Param("email") String email);

    @Update("""
            UPDATE accounts.accounts
            SET avatar_url = #{avatarUrl, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            WHERE login = #{login, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            """)
    void updateAvatarUrl(@Param("login") String login, @Param("avatarUrl") String avatarUrl);

    @Update("""
            UPDATE accounts.accounts
            SET contact_info = #{contactInfo, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            WHERE login = #{login, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            """)
    void updateContactInfo(@Param("login") String login, @Param("contactInfo") String contactInfo);

    @Update("""
            UPDATE accounts.accounts
            SET interests = #{interests, typeHandler=org.apache.ibatis.type.ArrayTypeHandler}::varchar[]
            WHERE login = #{login, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            """)
    void updateInterests(@Param("login") String login, @Param("interests") List<String> interests);

    @Update("""
            UPDATE accounts.accounts
            SET premium = #{premium, typeHandler=org.apache.ibatis.type.BooleanTypeHandler}
            WHERE login = #{login, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            """)
    void updatePremium(@Param("login") String login, @Param("premium") boolean premium);

    @Update("""
            UPDATE accounts.accounts
            SET consent_personal_data = #{personalDataConsent, typeHandler=org.apache.ibatis.type.BooleanTypeHandler}
            WHERE login = #{login, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            """)
    void updatePersonalDataConsent(@Param("login") String login, @Param("personalDataConsent") boolean personalDataConsent);

}
