package com.exchange.task.user.infrastructure;

import com.exchange.task.user.domain.model.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
class UserEntity {

    @Id
    @Column(name = "PESEL")
    private String pesel;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<AccountEntity> accounts = new ArrayList<>();

    UserEntity() {
    }


    UserEntity(String firstName, String lastName, String pesel, List<AccountEntity> accounts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.accounts = accounts;
        accounts.forEach(accountEntity -> accountEntity.setOwner(this));
    }

    public User toDomain() {
        return new User(firstName, lastName, pesel, accounts.stream().map(AccountEntity::toDomain).toList());
    }


    static UserEntity fromDomain(User user) {
        return new UserEntity(
                user.getFirstName(),
                user.getLastName(),
                user.getPesel(),
                user.getAccounts().stream().map(AccountEntity::fromDomain).toList());
    }
}
