package tech.provve.skill.jooq_binding;

import org.jooq.*;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Types;


/**
 * биндинг для bit(1) из Postgres
 */
public class BooleanBitBinding implements Binding<String, Boolean> {

    @Override
    public Converter<String, Boolean> converter() {
        return new Converter<>() {
            @Override
            public Boolean from(String t) {
                return "1".equals(t);
            }

            @Override
            public String to(Boolean u) {
                return u ? "1" : "0";
            }

            @Override
            public Class<String> fromType() {
                return String.class;
            }

            @Override
            public Class<Boolean> toType() {
                return Boolean.class;
            }
        };
    }

    @Override
    public void sql(BindingSQLContext<Boolean> ctx) {
        if (ctx.render()
               .paramType() == ParamType.INLINED)
            ctx.render()
               .visit(DSL.inline(ctx.convert(converter())
                                    .value()))
               .sql("::bit");
        else
            ctx.render()
               .sql(ctx.variable())
               .sql("::bit");
    }


    // Registering BIT types for JDBC CallableStatement OUT parameters
    @Override
    public void register(BindingRegisterContext<Boolean> ctx) throws SQLException {
        ctx.statement()
           .registerOutParameter(ctx.index(), Types.BIT);
    }

    // Converting the Boolean to a String value and setting that on a JDBC PreparedStatement
    @Override
    public void set(BindingSetStatementContext<Boolean> ctx) throws SQLException {
        var val = ctx.convert(converter())
                     .value();
        ctx.statement()
           .setString(ctx.index(), val);
    }

    // Getting a String value from a JDBC ResultSet and converting that to a Boolean
    @Override
    public void get(BindingGetResultSetContext<Boolean> ctx) throws SQLException {
        ctx.convert(converter())
           .value(ctx.resultSet()
                     .getString(ctx.index()));
    }

    // Getting a String value from a JDBC CallableStatement and converting that to a Boolean
    @Override
    public void get(BindingGetStatementContext<Boolean> ctx) throws SQLException {
        ctx.convert(converter())
           .value(ctx.statement()
                     .getString(ctx.index()));
    }

    @Override
    public void set(BindingSetSQLOutputContext<Boolean> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void get(BindingGetSQLInputContext<Boolean> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
}