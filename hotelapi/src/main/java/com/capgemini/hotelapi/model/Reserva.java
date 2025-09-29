package com.capgemini.hotelapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate checkIn;
    private LocalDate checkOut;
    private Double valorTotal;

    //quando tiver implementado na develop
    // descomenta para fazer o relacioncionamento entre as classes

    //@ManyToOne
    //private Propriedade propriedade;

    //@ManyToOne
    //private Quarto quarto;

    //@ManyToOne
    //private Usuario usuario;

    public Reserva() {
    }

    //acredito que nao precise passar o id, então removi
    public Reserva( LocalDate checkIn, LocalDate checkOut, Double valorTotal) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.valorTotal = valorTotal;

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
}
