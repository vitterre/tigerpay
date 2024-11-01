/*
 * This file is generated by jOOQ.
 */
package com.tigerpay.auth.model.jooq.schema.tables.pojos;


import java.io.Serializable;
import java.util.UUID;


/**
 * Represents user account as a general entity in our payment system
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class AccountEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID uuid;
    private String firstName;
    private String lastName;
    private String middleName;
    private String emailAddress;
    private String phoneNumber;
    private String password;
    private UUID roleUuid;

    public AccountEntity() {}

    public AccountEntity(AccountEntity value) {
        this.uuid = value.uuid;
        this.firstName = value.firstName;
        this.lastName = value.lastName;
        this.middleName = value.middleName;
        this.emailAddress = value.emailAddress;
        this.phoneNumber = value.phoneNumber;
        this.password = value.password;
        this.roleUuid = value.roleUuid;
    }

    public AccountEntity(
        UUID uuid,
        String firstName,
        String lastName,
        String middleName,
        String emailAddress,
        String phoneNumber,
        String password,
        UUID roleUuid
    ) {
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.roleUuid = roleUuid;
    }

    /**
     * Getter for <code>public.account.uuid</code>. Account UUID
     */
    public UUID getUuid() {
        return this.uuid;
    }

    /**
     * Setter for <code>public.account.uuid</code>. Account UUID
     */
    public AccountEntity setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    /**
     * Getter for <code>public.account.first_name</code>. First name, must not
     * be null
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Setter for <code>public.account.first_name</code>. First name, must not
     * be null
     */
    public AccountEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * Getter for <code>public.account.last_name</code>. Last name, must not be
     * null
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Setter for <code>public.account.last_name</code>. Last name, must not be
     * null
     */
    public AccountEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * Getter for <code>public.account.middle_name</code>. Middle name, can be
     * null
     */
    public String getMiddleName() {
        return this.middleName;
    }

    /**
     * Setter for <code>public.account.middle_name</code>. Middle name, can be
     * null
     */
    public AccountEntity setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    /**
     * Getter for <code>public.account.email_address</code>. Email address, must
     * be unique and not null
     */
    public String getEmailAddress() {
        return this.emailAddress;
    }

    /**
     * Setter for <code>public.account.email_address</code>. Email address, must
     * be unique and not null
     */
    public AccountEntity setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    /**
     * Getter for <code>public.account.phone_number</code>. Phone number, must
     * be unique and not null
     */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * Setter for <code>public.account.phone_number</code>. Phone number, must
     * be unique and not null
     */
    public AccountEntity setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    /**
     * Getter for <code>public.account.password</code>. Encrypted password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Setter for <code>public.account.password</code>. Encrypted password
     */
    public AccountEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    /**
     * Getter for <code>public.account.role_uuid</code>. Role UUID that belongs
     * to this particular account
     */
    public UUID getRoleUuid() {
        return this.roleUuid;
    }

    /**
     * Setter for <code>public.account.role_uuid</code>. Role UUID that belongs
     * to this particular account
     */
    public AccountEntity setRoleUuid(UUID roleUuid) {
        this.roleUuid = roleUuid;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final AccountEntity other = (AccountEntity) obj;
        if (this.uuid == null) {
            if (other.uuid != null)
                return false;
        }
        else if (!this.uuid.equals(other.uuid))
            return false;
        if (this.firstName == null) {
            if (other.firstName != null)
                return false;
        }
        else if (!this.firstName.equals(other.firstName))
            return false;
        if (this.lastName == null) {
            if (other.lastName != null)
                return false;
        }
        else if (!this.lastName.equals(other.lastName))
            return false;
        if (this.middleName == null) {
            if (other.middleName != null)
                return false;
        }
        else if (!this.middleName.equals(other.middleName))
            return false;
        if (this.emailAddress == null) {
            if (other.emailAddress != null)
                return false;
        }
        else if (!this.emailAddress.equals(other.emailAddress))
            return false;
        if (this.phoneNumber == null) {
            if (other.phoneNumber != null)
                return false;
        }
        else if (!this.phoneNumber.equals(other.phoneNumber))
            return false;
        if (this.password == null) {
            if (other.password != null)
                return false;
        }
        else if (!this.password.equals(other.password))
            return false;
        if (this.roleUuid == null) {
            if (other.roleUuid != null)
                return false;
        }
        else if (!this.roleUuid.equals(other.roleUuid))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.uuid == null) ? 0 : this.uuid.hashCode());
        result = prime * result + ((this.firstName == null) ? 0 : this.firstName.hashCode());
        result = prime * result + ((this.lastName == null) ? 0 : this.lastName.hashCode());
        result = prime * result + ((this.middleName == null) ? 0 : this.middleName.hashCode());
        result = prime * result + ((this.emailAddress == null) ? 0 : this.emailAddress.hashCode());
        result = prime * result + ((this.phoneNumber == null) ? 0 : this.phoneNumber.hashCode());
        result = prime * result + ((this.password == null) ? 0 : this.password.hashCode());
        result = prime * result + ((this.roleUuid == null) ? 0 : this.roleUuid.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("AccountEntity (");

        sb.append(uuid);
        sb.append(", ").append(firstName);
        sb.append(", ").append(lastName);
        sb.append(", ").append(middleName);
        sb.append(", ").append(emailAddress);
        sb.append(", ").append(phoneNumber);
        sb.append(", ").append(password);
        sb.append(", ").append(roleUuid);

        sb.append(")");
        return sb.toString();
    }
}
