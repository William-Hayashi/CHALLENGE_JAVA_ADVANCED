package com.example.testeSpring.model;


import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "TB_CADASTRO")

public class ProductModel extends RepresentationModel<ProductModel> implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private Number cpf;
    private Number cep;
    private Number primeiroTratamento;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Number getCpf() {
        return cpf;
    }

    public void setCpf(Number cpf) {
        this.cpf = cpf;
    }

    public Number getCep() {
        return cep;
    }

    public void setCep(Number cep) {
        this.cep = cep;
    }

    public Number isPrimeiroTratamento() {
        return primeiroTratamento;
    }

    public void setPrimeiroTratamento(Number primeiroTratamento) {
        this.primeiroTratamento = primeiroTratamento;
    }
}
