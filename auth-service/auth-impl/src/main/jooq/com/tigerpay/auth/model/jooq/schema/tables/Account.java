/*
 * This file is generated by jOOQ.
 */
package com.tigerpay.auth.model.jooq.schema.tables;


import com.tigerpay.auth.model.jooq.schema.Keys;
import com.tigerpay.auth.model.jooq.schema.Public;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * Represents user account as a general entity in our payment system
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Account extends TableImpl<Record> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.account</code>
     */
    public static final Account ACCOUNT = new Account();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<Record> getRecordType() {
        return Record.class;
    }

    /**
     * The column <code>public.account.uuid</code>. Account UUID
     */
    public final TableField<Record, java.util.UUID> UUID = createField(DSL.name("uuid"), SQLDataType.UUID.nullable(false).defaultValue(DSL.field(DSL.raw("gen_random_uuid()"), SQLDataType.UUID)), this, "Account UUID");

    /**
     * The column <code>public.account.first_name</code>. First name, must not
     * be null
     */
    public final TableField<Record, String> FIRST_NAME = createField(DSL.name("first_name"), SQLDataType.VARCHAR(50).nullable(false), this, "First name, must not be null");

    /**
     * The column <code>public.account.last_name</code>. Last name, must not be
     * null
     */
    public final TableField<Record, String> LAST_NAME = createField(DSL.name("last_name"), SQLDataType.VARCHAR(50).nullable(false), this, "Last name, must not be null");

    /**
     * The column <code>public.account.middle_name</code>. Middle name, can be
     * null
     */
    public final TableField<Record, String> MIDDLE_NAME = createField(DSL.name("middle_name"), SQLDataType.VARCHAR(50), this, "Middle name, can be null");

    /**
     * The column <code>public.account.email_address</code>. Email address, must
     * be unique and not null
     */
    public final TableField<Record, String> EMAIL_ADDRESS = createField(DSL.name("email_address"), SQLDataType.VARCHAR(200).nullable(false), this, "Email address, must be unique and not null");

    /**
     * The column <code>public.account.phone_number</code>. Phone number, must
     * be unique and not null
     */
    public final TableField<Record, String> PHONE_NUMBER = createField(DSL.name("phone_number"), SQLDataType.VARCHAR(20).nullable(false), this, "Phone number, must be unique and not null");

    /**
     * The column <code>public.account.password</code>. Encrypted password
     */
    public final TableField<Record, String> PASSWORD = createField(DSL.name("password"), SQLDataType.CLOB.nullable(false), this, "Encrypted password");

    /**
     * The column <code>public.account.role_uuid</code>. Role UUID that belongs
     * to this particular account
     */
    public final TableField<Record, java.util.UUID> ROLE_UUID = createField(DSL.name("role_uuid"), SQLDataType.UUID.nullable(false), this, "Role UUID that belongs to this particular account");

    private Account(Name alias, Table<Record> aliased) {
        this(alias, aliased, null);
    }

    private Account(Name alias, Table<Record> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("Represents user account as a general entity in our payment system"), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.account</code> table reference
     */
    public Account(String alias) {
        this(DSL.name(alias), ACCOUNT);
    }

    /**
     * Create an aliased <code>public.account</code> table reference
     */
    public Account(Name alias) {
        this(alias, ACCOUNT);
    }

    /**
     * Create a <code>public.account</code> table reference
     */
    public Account() {
        this(DSL.name("account"), null);
    }

    public <O extends Record> Account(Table<O> child, ForeignKey<O, Record> key) {
        super(child, key, ACCOUNT);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<Record> getPrimaryKey() {
        return Keys.ACCOUNT_PK;
    }

    @Override
    public List<UniqueKey<Record>> getUniqueKeys() {
        return Arrays.asList(Keys.ACCOUNT_EMAIL_ADDRESS_UQ, Keys.ACCOUNT_PHONE_NUMBER_UQ);
    }

    @Override
    public List<ForeignKey<Record, ?>> getReferences() {
        return Arrays.asList(Keys.ACCOUNT__ACCOUNT_ACCOUNT_ROLE_FK);
    }

    private transient AccountRole _accountRole;

    /**
     * Get the implicit join path to the <code>public.account_role</code> table.
     */
    public AccountRole accountRole() {
        if (_accountRole == null)
            _accountRole = new AccountRole(this, Keys.ACCOUNT__ACCOUNT_ACCOUNT_ROLE_FK);

        return _accountRole;
    }

    @Override
    public Account as(String alias) {
        return new Account(DSL.name(alias), this);
    }

    @Override
    public Account as(Name alias) {
        return new Account(alias, this);
    }

    @Override
    public Account as(Table<?> alias) {
        return new Account(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Account rename(String name) {
        return new Account(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Account rename(Name name) {
        return new Account(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Account rename(Table<?> name) {
        return new Account(name.getQualifiedName(), null);
    }
}