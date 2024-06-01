package com.tigerpay.payment.config;

import lombok.val;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;

public class ExceptionTranslator implements ExecuteListener {

    public void exception(final ExecuteContext context) {
        val dialect = context.configuration().dialect();
        val translator = new SQLErrorCodeSQLExceptionTranslator(dialect.name());
        context.exception(translator.translate(
                "Access database using JOOQ",
                context.sql(),
                context.sqlException()
        ));
    }
}
