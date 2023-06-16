package com.web.pi3s.SpringWeb.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.web.pi3s.SpringWeb.Enum.StatusPedido;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    private Double frete;
    private List<String> enderecosEntrega = new ArrayList<>();
    
    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    

    private String numeroPedido;
    


    public void setFrete(Double frete) {
        this.frete = frete;
    }

 
    public String getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

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

    public double getFrete() {
        if (frete != null) {
            return frete.doubleValue();
        } else {
            // Trate o caso em que o frete é nulo, se necessário.
            return 0.0; // Por exemplo, retorne um valor padrão de 0.0 caso o frete seja nulo.
        }
    }

    public void setFrete(double frete) {
        this.frete = frete;
    }


    


    public StatusPedido getStatus() {
        return status;
    }


    public void setStatus(StatusPedido status) {
        this.status = status;
    }


    public List<String> getEnderecosEntrega() {
        return enderecosEntrega;
    }


    public void setEnderecosEntrega(List<String> enderecosEntrega) {
        this.enderecosEntrega = enderecosEntrega;
    }

    

    

   

   

}
