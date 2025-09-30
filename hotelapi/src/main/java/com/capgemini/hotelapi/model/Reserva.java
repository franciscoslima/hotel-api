package com.capgemini.hotelapi.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Double valorTotal;

    @ManyToOne
    private Propriedade propriedade;

    @ManyToOne
    private Quarto quarto;

    @ManyToOne
    private User user;

    public Reserva() {
    }

    //acredito que nao precise passar o id, então removi
    public Reserva(Long id, LocalDate checkIn, LocalDate checkOut, Double valorTotal, Propriedade propriedade, Quarto quarto, User user) {
        this.id = id;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.valorTotal = valorTotal;
        this.propriedade = propriedade;
        this.quarto = quarto;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Propriedade getPropriedade() {
        return propriedade;
    }

    public void setPropriedade(Propriedade propriedade) {
        this.propriedade = propriedade;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
