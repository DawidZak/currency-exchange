package com.exchange.task.user.infrastructure;

import com.exchange.task.user.domain.model.Account;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "NUMBER", nullable = false)
    private String number;
    @Column(name = "CURRENCY", nullable = false)
    private String currency;
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity owner;
    @Column(name = "BALANCE")
    private BigDecimal balance;

    public AccountEntity(String currency, UUID id, String number, BigDecimal balance) {
        this.currency = currency;
        this.id = id;
        this.number = number;
        this.balance = balance;
    }

    public AccountEntity() {
    }

    void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public Account toDomain() {
        return Account.builder().balance(balance).currency(currency).id(id).number(number).build();
    }

    public static AccountEntity fromDomain(Account account) {
        return new AccountEntity(account.getCurrency(), account.getId(), account.getNumber(), account.getBalance());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountEntity that = (AccountEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
