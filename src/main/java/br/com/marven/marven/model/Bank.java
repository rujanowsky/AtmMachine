package br.com.marven.marven.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private int value;
    @Column(nullable = false)
    private int amount;

    public boolean CheckTakeNote() {
        if (this.amount > 0)
            return true;

        return false;
    }

    public int getValue() {
        return this.value;
    }

    public boolean TakeNotes(int qtyofnotes) {
        if (this.amount >= qtyofnotes) {
            this.amount -= qtyofnotes;
            return true;
        }
        return false;
    }
}
