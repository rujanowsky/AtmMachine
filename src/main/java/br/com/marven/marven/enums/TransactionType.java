package br.com.marven.marven.enums;

public enum TransactionType {
    BALANCE(1),
    WITHDRAW(2),
    deposit(3),
    OTHER(4);

    private int type;

    private TransactionType(int type) {
        this.type = type;
    }

    public int getCode() {
        return type;
    }
}
