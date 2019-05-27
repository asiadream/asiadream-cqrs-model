package com.asiadream.cqrs.command.repository;

import com.asiadream.cqrs.command.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

}
