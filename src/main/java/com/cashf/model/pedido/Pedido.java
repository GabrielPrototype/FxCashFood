/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cashf.model.pedido;

import com.cashf.model.cliente.Cliente;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Aluno
 */
public class Pedido implements Serializable,PedidoSubject{
    private Long idPedido;
    private LocalDate dataPedido;
    private LocalTime horaPedido;
    private BigDecimal valorTotal;
    private Cliente cliente;
    private ObservableList<ProdutosPedido> itensPedido;
    private List<ClienteObserver> observadores;

    public Pedido() {
        this.observadores=new ArrayList<>();
        this.itensPedido=FXCollections.observableList(new ArrayList<>());
    }

    public Pedido(Long idPedido, LocalDate dataPedido, LocalTime horaPedido, BigDecimal valorTotal, Cliente cliente, ObservableList<ProdutosPedido> itensPedido) {
        this.idPedido = idPedido;
        this.dataPedido = dataPedido;
        this.horaPedido = horaPedido;
        this.valorTotal = valorTotal;
        this.cliente = cliente;
        this.itensPedido = itensPedido;
        this.observadores=new ArrayList<>();
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    public LocalTime getHoraPedido() {
        return horaPedido;
    }

    public void setHoraPedido(LocalTime horaPedido) {
        this.horaPedido = horaPedido;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ObservableList<ProdutosPedido> getItensPedido() {
        return itensPedido;
    }

    public void setItensPedido(ObservableList<ProdutosPedido> itensPedido) {
        this.itensPedido = itensPedido;
    }

    @Override
    public void add(ClienteObserver cliente) {
        this.observadores.add(cliente);
    }

    @Override
    public void remove(ClienteObserver cliente) {
        this.observadores.remove(cliente);
    }

    @Override
    public void notifyClientes() {
        for(ClienteObserver obs:observadores){
            obs.update();
        };
    }
    
}
