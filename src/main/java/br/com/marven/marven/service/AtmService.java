package br.com.marven.marven.service;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import br.com.marven.marven.DTO.UserAccountDTO;
import br.com.marven.marven.controller.AtmController;
import br.com.marven.marven.controller.BankController;
import br.com.marven.marven.controller.UserController;
import br.com.marven.marven.exception.OptionExceptionReturn;
import br.com.marven.marven.model.UserAccount;

@RestController
@RequestMapping("/atm")
@CrossOrigin(origins = "*")
public class AtmService {

    @Autowired
    AtmController atmController;

    @Autowired
    BankController bankController;

    @Autowired
    UserController userController;

    @Autowired
    Gson gson;

    @GetMapping("/valid/{accountNumber}/{pin}")
    public ResponseEntity<?> valid(@PathVariable("accountNumber") Long accountNumber, @PathVariable("pin") Long pin) {
        return ResponseEntity.ok(gson.toJson(atmController.validatePin(accountNumber, pin)));
    }

    @GetMapping("/signin/{accountNumber}/{pin}")
    public ResponseEntity<?> signin(@PathVariable("accountNumber") Long accountNumber, @PathVariable("pin") Long pin) {
        Optional<UserAccount> user = atmController.findByAccountNumber(accountNumber);

        if (user.isPresent()) {
            UserAccount hasAccount = user.get();
            
            if (hasAccount.getPin().equals(pin)) {
                return ResponseEntity.ok(gson.toJson(hasAccount));
            } else {
                return ResponseEntity.badRequest().body("Pin code informado não corresponde com número da conta.");
            }
        }
        return ResponseEntity.badRequest().body("Não foi possível localizar conta corrente no banco de dados.");
    }

    @PostMapping("/opt")
    public ResponseEntity<?> option(@RequestBody UserAccountDTO uAccountDTO) {
        if (atmController.validatePin(uAccountDTO.getAccountNumber(),
                uAccountDTO.getPin())) {
            switch (uAccountDTO.getOptionType()) {
                case 1: // Balance
                    return ResponseEntity.ok(atmController.getBalance(uAccountDTO));

                case 2: // Withdraw
                    return ResponseEntity.ok(atmController.withdraw(uAccountDTO));

                case 3: // deposit
                    return ResponseEntity.ok(atmController.deposit(uAccountDTO));

                default: // Other
                    return ResponseEntity.ok(new OptionExceptionReturn(null, "Saindo do sistema.", 4));
            }
        } else {
            return ResponseEntity.badRequest().body("Pin da conta informada, não corresponde.");
        }
    }

    @GetMapping("/bank/total")
    public ResponseEntity<?> getMachineValue() {
        return ResponseEntity.ok(bankController.getTotalMonetaryMachine());
    }

    @GetMapping("/resetBank") // http://localhost:8080/atm/resetBank
    public void resetBank() {
        bankController.insertedValues();
    }
    
    @GetMapping("/resetUserAccount") // http://localhost:8080/atm/resetUserAccount
    public void resetUser() {
        userController.insertedValues();
    }
}
