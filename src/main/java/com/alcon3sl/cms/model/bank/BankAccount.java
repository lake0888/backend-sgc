package com.alcon3sl.cms.model.bank;

import com.alcon3sl.cms.model.util.coin.Coin;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "bank_account")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String number;
    private String iban;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "coin_id", referencedColumnName = "id")
    private Coin coin;
    @ManyToOne
    @JoinColumn(name = "bank_id", referencedColumnName = "id")
    private Bank bank;

    public BankAccount() {
        this("", "", new Coin(), new Bank());
    }
    public BankAccount(String number, String iban, Coin coin, Bank bank) {
        this(0L, number, iban, coin, bank);
    }

    public BankAccount(Long id, String number, String iban, Coin coin, Bank bank) {
        this.id = id;
        this.number = number;
        this.iban = iban;
        this.coin = coin;
        this.bank = bank;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Coin getCoin() {
        return coin;
    }

    public void setCoin(Coin coin) {
        this.coin = coin;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        return Objects.equals(id, that.id) && Objects.equals(number, that.number) && Objects.equals(iban, that.iban) && Objects.equals(coin, that.coin) && Objects.equals(bank, that.bank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, iban, coin, bank);
    }

    @Override
    public String toString() {
        return "Bank_Account{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", iban='" + iban + '\'' +
                ", coin=" + coin +
                ", bank=" + bank +
                '}';
    }
}
