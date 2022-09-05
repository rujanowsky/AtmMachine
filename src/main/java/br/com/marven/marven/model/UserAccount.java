package br.com.marven.marven.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table
@Entity
@NoArgsConstructor
public class UserAccount implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private Long accountNumber;
    @Column(nullable = false)
    private Long pin;
    @Column(nullable = false)
    private Double openingBalance;
    @Column(nullable = false)
    private Double overdraft;

    public UserAccount(Long accountNumber,
                       Long pin,
                       Double openingBalance,
                       Double overdraft) {

        this.accountNumber = accountNumber;
        this.pin = pin;
        this.openingBalance = openingBalance;
        this.overdraft = overdraft;
    }
}
