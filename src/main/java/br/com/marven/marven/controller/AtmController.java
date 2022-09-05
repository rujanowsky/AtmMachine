package br.com.marven.marven.controller;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.marven.marven.DTO.UserAccountDTO;
import br.com.marven.marven.exception.OptionExceptionReturn;
import br.com.marven.marven.model.Bank;
import br.com.marven.marven.model.UserAccount;
import br.com.marven.marven.repository.AtmRepository;
import br.com.marven.marven.repository.BankRepository;

@Controller
@Transactional
public class AtmController {

    @Autowired
    AtmRepository atmRepository;

    @Autowired
    BankRepository bankRepository;

    @Autowired
    BankController bankController;

    public Boolean validatePin(Long accountNumber, Long pin) {
        return findUser(accountNumber, pin).isPresent();
    }

    public Optional<UserAccount> findUser(Long accountNumber, Long pin) {
        return atmRepository.findAccountNumberAndPin(accountNumber, pin);
    }

    public Optional<UserAccount> findByAccountNumber(Long accountNumber) {
        return atmRepository.findByAccountNumber(accountNumber);
    };

    public OptionExceptionReturn getBalance(UserAccountDTO userAccount) {
        Optional<UserAccount> balance = atmRepository.findAccountNumberAndPin(userAccount.getAccountNumber(),
                userAccount.getPin());

        if (balance.isPresent()) {
            return new OptionExceptionReturn(balance.get().getOpeningBalance(), "Pesquisa do saque obtido.", 1);
        } else {
            return new OptionExceptionReturn(null, "Pin ou número da conta estão incorretos.", 5);
        }
    }

    public OptionExceptionReturn withdraw(UserAccountDTO uAccountDTO) {
        Optional<UserAccount> userAccount = atmRepository.findAccountNumberAndPin(uAccountDTO.getAccountNumber(),
                uAccountDTO.getPin());

        if (userAccount.isPresent()) {
            UserAccount user = userAccount.get();
            Double balance = user.getOpeningBalance();

            if (uAccountDTO.getValueWithdraw() != null) {
                if (bankController.getTotalMonetaryMachine() >= balance) {
                    Double totalBalance = (balance + user.getOverdraft());

                    if (uAccountDTO.getValueWithdraw() <= totalBalance) {
                        user.setOpeningBalance(balance - uAccountDTO.getValueWithdraw());
                        String notes = "";

                        List<Bank> listBank = bankRepository.findAll();
                        Double amount = uAccountDTO.getValueWithdraw();

                        for (Bank noteBucket : listBank) {
                            if (amount >= noteBucket.getValue() && noteBucket.CheckTakeNote()) {
                                double remainder = amount % noteBucket.getValue();
                                if (remainder < amount) {
                                    notes += "Amount of: " + Integer.toString(noteBucket.getValue()) + " : "
                                            + Double.toString((amount - remainder) / noteBucket.getValue()) + "\n";

                                    int updateAmountNote = (int) (noteBucket.getAmount()
                                            - ((amount - remainder) / noteBucket.getValue()));
                                    bankRepository.updateAmountByNotes(noteBucket.getValue(), updateAmountNote);

                                    amount = remainder;
                                }

                            }
                        }

                        return new OptionExceptionReturn(atmRepository.save(user),
                                "Saque foi realizado e separado em notas de " + notes, 2);
                    } else {
                        return new OptionExceptionReturn(null,
                                "Valor de saque está além do permitido, contabilizando o total em sua conta, mais cheque especial.",
                                4);
                    }
                }
            } else {
                return new OptionExceptionReturn(null,
                        "Não existe valor para sacar.",
                        4);
            }
        }
        return new OptionExceptionReturn(null, "Não foi possível sacar.", 4);
    }

    public OptionExceptionReturn deposit(UserAccountDTO uAccountDTO) {
        Optional<UserAccount> userAccount = atmRepository.findByAccountNumber(uAccountDTO.getAccountNumber());

        if (userAccount.isPresent()) {
            UserAccount user = userAccount.get();

            if (uAccountDTO.getValueDeposit() != null) {
                user.setOpeningBalance(user.getOpeningBalance() + uAccountDTO.getValueDeposit());
                return new OptionExceptionReturn(atmRepository.save(user),
                        "Depósito feito com exito, valor total: " + user.getOpeningBalance(), 3);
            } else {
                return new OptionExceptionReturn(null, "Não possui valor para depositar.", 4);
            }
        }
        return new OptionExceptionReturn(null, "Não foi possível depositar.", 4);
    }

}
