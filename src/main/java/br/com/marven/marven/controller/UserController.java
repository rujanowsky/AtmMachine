package br.com.marven.marven.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.marven.marven.model.UserAccount;
import br.com.marven.marven.repository.AtmRepository;

@Controller
public class UserController {
    
    @Autowired
    AtmRepository atmRepository;

    public void insertedValues() {
        UserAccount user1 = new UserAccount();
        user1.setId(1L);
        user1.setAccountNumber(123456789L);
        user1.setPin(1234L);
        user1.setOpeningBalance(800.0);
        user1.setOverdraft(200.0);
        atmRepository.save(user1);
        
        UserAccount user2 = new UserAccount();
        user2.setId(1L);
        user2.setAccountNumber(987654321L);
        user2.setPin(4321L);
        user2.setOpeningBalance(1230.0);
        user2.setOverdraft(150.0);
        atmRepository.save(user2);

    }
}
