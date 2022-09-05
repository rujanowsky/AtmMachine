package br.com.marven.marven.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.marven.marven.model.Bank;
import br.com.marven.marven.repository.BankRepository;

@Controller
@Transactional
public class BankController {
    
    @Autowired
    BankRepository bankRepository;

    public void insertedValues() {
        Bank n1 = new Bank();
        n1.setId(1L);
        n1.setValue(50);
        n1.setAmount(10);
        bankRepository.save(n1);
        
        Bank n2 = new Bank();
        n2.setId(2L);
        n2.setValue(20);
        n2.setAmount(30);
        bankRepository.save(n2);

        Bank n3 = new Bank();
        n3.setId(3L);
        n3.setValue(10);
        n3.setAmount(30);
        bankRepository.save(n3);

        Bank n4 = new Bank();
        n4.setId(4L);
        n4.setValue(5);
        n4.setAmount(20);
        bankRepository.save(n4);
    }
    
    public Double getTotalMonetaryMachine() {
        return bankRepository.totalMoneyInBank();
    }
}
