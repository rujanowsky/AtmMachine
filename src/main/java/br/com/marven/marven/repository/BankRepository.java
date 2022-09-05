package br.com.marven.marven.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.marven.marven.model.Bank;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {
    
    @Query("select SUM(ba.value * ba.amount) from Bank ba")
    Double totalMoneyInBank();

    @Modifying
    @Query("update Bank set amount = :amount where value = :value")
    void updateAmountByNotes(@Param("value") int value, @Param("amount") int amount);
}
