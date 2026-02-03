package tech.provve.payment.repository;

import io.avaje.inject.External;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import tech.provve.payment.db.generated.tables.records.InvoicesRobokassaRecord;
import tech.provve.payment.domain.value.Invoice;

import java.sql.Connection;
import java.util.Optional;

import static tech.provve.payment.db.generated.tables.InvoicesRobokassa.INVOICES_ROBOKASSA;

@Singleton
@RequiredArgsConstructor
public class RobokassaInvoiceRepository {

    @External
    private final Connection connection;

    private final RecordMapper<Record, Invoice> outputMapper = result ->
            new Invoice(
                    result.get(INVOICES_ROBOKASSA.ACCOUNT_LOGIN),
                    result.get(INVOICES_ROBOKASSA.SIGNATURE)
            );

    public void save(Invoice invoice) {
        DSL.using(connection, SQLDialect.POSTGRES)
           .insertInto(INVOICES_ROBOKASSA)
           .set(new InvoicesRobokassaRecord(invoice.accountLogin(), invoice.signature()))
           .execute();
    }

    public Optional<Invoice> findBy(String accountLogin) {
        return DSL.using(connection, SQLDialect.POSTGRES)
                  .select()
                  .from(INVOICES_ROBOKASSA)
                  .where(INVOICES_ROBOKASSA.ACCOUNT_LOGIN.eq(accountLogin))
                  .fetchOptional(outputMapper);
    }

}
