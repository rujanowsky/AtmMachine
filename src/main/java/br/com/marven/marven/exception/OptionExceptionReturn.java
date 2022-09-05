package br.com.marven.marven.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionExceptionReturn {
    private Object entity;
    private String message;
    private int type;
}
