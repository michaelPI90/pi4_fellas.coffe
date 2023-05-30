package com.web.pi3s.SpringWeb.models;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "TB_COMPRA")
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int id;

    @ManyToOne
    private Clientemodels cliente;

    @Temporal(value = TemporalType.DATE)
    private Date dataCompra = new Date();
    private String formaDePagamnto;
    private Double valorTotal=0.0;

    

    public Compra() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Clientemodels getCliente() {
        return cliente;
    }

    public void setCliente(Clientemodels cliente) {
        this.cliente = cliente;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public String getFormaDePagamnto() {
        return formaDePagamnto;
    }

    public void setFormaDePagamnto(String formaDePagamnto) {
        this.formaDePagamnto = formaDePagamnto;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

   

   

}
