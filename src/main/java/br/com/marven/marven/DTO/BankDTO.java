package br.com.marven.marven.DTO;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankDTO {
    @NotBlank
    private Long value;
    @NotBlank
    private Long qtd;
}
