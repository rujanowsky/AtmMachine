package br.com.marven.marven.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountDTO {
    private Long accountNumber;
    private Long pin;
    private Long openingBalance;
    private Long overdraft;
    private int optionType;
    private Double valueWithdraw;
    private Double valueDeposit;
}
