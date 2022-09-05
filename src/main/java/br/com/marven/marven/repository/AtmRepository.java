package br.com.marven.marven.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.marven.marven.model.UserAccount;

@Repository
public interface AtmRepository extends JpaRepository<UserAccount, Long> {
    @Query("select ua from UserAccount ua where ua.accountNumber = :accountNumber and ua.pin = :pin")
    Optional<UserAccount> findAccountNumberAndPin(@Param("accountNumber") Long accountNumber, @Param("pin") Long pin);

    @Query("select ua from UserAccount ua where ua.accountNumber = :accountNumber")
    Optional<UserAccount> findByAccountNumber(@Param("accountNumber") Long accountNumber);
}