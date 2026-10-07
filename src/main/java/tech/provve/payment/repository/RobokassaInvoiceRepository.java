package tech.provve.payment.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tech.provve.constants.Entity;

import java.util.Map;
import java.util.Optional;

@Mapper
public interface RobokassaInvoiceRepository {

    String INVOICE = "invoice";

    @Insert("""
            INSERT INTO payment.invoices_robokassa (account_login, signature)
            VALUES (
                #{invoice.accountLogin, typeHandler=org.apache.ibatis.type.StringTypeHandler},
                #{invoice.signature, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            )
            """)
    void save(@Param("invoice") Map<String, Object> invoice);

    @Select("""
            SELECT account_login, signature
            FROM payment.invoices_robokassa
            WHERE account_login = #{accountLogin, typeHandler=org.apache.ibatis.type.StringTypeHandler}
            """)
    @Results(id = INVOICE, value = {
            @Result(property = Entity.Invoice.ACCOUNT_LOGIN, column = "account_login", typeHandler = org.apache.ibatis.type.StringTypeHandler.class),
            @Result(property = Entity.Invoice.SIGNATURE, column = "signature", typeHandler = org.apache.ibatis.type.StringTypeHandler.class)
    })
    Optional<Map<String, Object>> findBy(@Param("accountLogin") String accountLogin);

}
