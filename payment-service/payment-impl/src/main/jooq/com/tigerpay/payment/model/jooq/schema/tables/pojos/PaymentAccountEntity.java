/*
 * This file is generated by jOOQ.
 */
package com.tigerpay.payment.model.jooq.schema.tables.pojos;


import java.io.Serializable;
import java.util.UUID;


/**
 * Represents the payment account
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class PaymentAccountEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer code;
    private Integer ledger;
    private String phoneNumber;
    private UUID profileUuid;

    public PaymentAccountEntity() {}

    public PaymentAccountEntity(PaymentAccountEntity value) {
        this.id = value.id;
        this.code = value.code;
        this.ledger = value.ledger;
        this.phoneNumber = value.phoneNumber;
        this.profileUuid = value.profileUuid;
    }

    public PaymentAccountEntity(
        Long id,
        Integer code,
        Integer ledger,
        String phoneNumber,
        UUID profileUuid
    ) {
        this.id = id;
        this.code = code;
        this.ledger = ledger;
        this.phoneNumber = phoneNumber;
        this.profileUuid = profileUuid;
    }

    /**
     * Getter for <code>public.payment_account.id</code>. Represents ID of
     * account that is being stored in TigerBeetle
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Setter for <code>public.payment_account.id</code>. Represents ID of
     * account that is being stored in TigerBeetle
     */
    public PaymentAccountEntity setId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Getter for <code>public.payment_account.code</code>. Represents the
     * category of payment account
     */
    public Integer getCode() {
        return this.code;
    }

    /**
     * Setter for <code>public.payment_account.code</code>. Represents the
     * category of payment account
     */
    public PaymentAccountEntity setCode(Integer code) {
        this.code = code;
        return this;
    }

    /**
     * Getter for <code>public.payment_account.ledger</code>. Partition that
     * this particular payment account belongs to and represents currency group
     */
    public Integer getLedger() {
        return this.ledger;
    }

    /**
     * Setter for <code>public.payment_account.ledger</code>. Partition that
     * this particular payment account belongs to and represents currency group
     */
    public PaymentAccountEntity setLedger(Integer ledger) {
        this.ledger = ledger;
        return this;
    }

    /**
     * Getter for <code>public.payment_account.phone_number</code>. Phone
     * number; used to directly transfer money by phone number
     */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * Setter for <code>public.payment_account.phone_number</code>. Phone
     * number; used to directly transfer money by phone number
     */
    public PaymentAccountEntity setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    /**
     * Getter for <code>public.payment_account.profile_uuid</code>. User profile
     * UUID that owns this and probably other accounts
     */
    public UUID getProfileUuid() {
        return this.profileUuid;
    }

    /**
     * Setter for <code>public.payment_account.profile_uuid</code>. User profile
     * UUID that owns this and probably other accounts
     */
    public PaymentAccountEntity setProfileUuid(UUID profileUuid) {
        this.profileUuid = profileUuid;
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
        final PaymentAccountEntity other = (PaymentAccountEntity) obj;
        if (this.id == null) {
            if (other.id != null)
                return false;
        }
        else if (!this.id.equals(other.id))
            return false;
        if (this.code == null) {
            if (other.code != null)
                return false;
        }
        else if (!this.code.equals(other.code))
            return false;
        if (this.ledger == null) {
            if (other.ledger != null)
                return false;
        }
        else if (!this.ledger.equals(other.ledger))
            return false;
        if (this.phoneNumber == null) {
            if (other.phoneNumber != null)
                return false;
        }
        else if (!this.phoneNumber.equals(other.phoneNumber))
            return false;
        if (this.profileUuid == null) {
            if (other.profileUuid != null)
                return false;
        }
        else if (!this.profileUuid.equals(other.profileUuid))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.code == null) ? 0 : this.code.hashCode());
        result = prime * result + ((this.ledger == null) ? 0 : this.ledger.hashCode());
        result = prime * result + ((this.phoneNumber == null) ? 0 : this.phoneNumber.hashCode());
        result = prime * result + ((this.profileUuid == null) ? 0 : this.profileUuid.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("PaymentAccountEntity (");

        sb.append(id);
        sb.append(", ").append(code);
        sb.append(", ").append(ledger);
        sb.append(", ").append(phoneNumber);
        sb.append(", ").append(profileUuid);

        sb.append(")");
        return sb.toString();
    }
}