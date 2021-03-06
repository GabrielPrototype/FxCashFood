/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cashf.core.venda;

import com.cashf.controller.caixa.CaixaController;
import com.cashf.dao.contareceber.ContaReceberDAO;
import com.cashf.model.contareceber.ContaReceber;
import com.cashf.model.contasPagar.StatusPagto;
import com.cashf.model.meiopagamento.MeioPagamento;
import com.cashf.model.venda.Venda;
import controller.GenericController;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author joao
 */
public class ContaReceberController implements GenericController<ContaReceber> {

    public static ContaReceberController contaReceberController;
    private ContaReceber contaReceber;
    private ObservableList<ContaReceber> lista;
    private final ContaReceberDAO contaReceberDAO;

    private ContaReceberController() {
        this.contaReceberDAO = new ContaReceberDAO(ContaReceber.class);
        this.lista = FXCollections.observableList(contaReceberDAO.listAll());
        this.contaReceber = new ContaReceber();
        this.contaReceber.setIdContaReceber(0l);
    }

    public static synchronized ContaReceberController getInstance() {
        if (contaReceberController == null) {
            contaReceberController = new ContaReceberController();
        }
        return contaReceberController;
    }

    public ContaReceber getContaReceber() {
        return contaReceber;
    }

    public void setContaReceber(ContaReceber contaReceber) {
        this.contaReceber = contaReceber;
    }

    public void setContaReceber(LocalDate dataVencimento, LocalDate dataPagamento, String favorecido, String descricao, BigDecimal valorBruto, BigDecimal encargos, BigDecimal desconto, BigDecimal acrecimo, BigDecimal valorPago, MeioPagamento meioPagamento, Venda venda, StatusPagto statusPagto) {
        this.contaReceber.setDataVencimento(dataVencimento);
        this.contaReceber.setDataPagamento(dataPagamento);
        this.contaReceber.setFavorecido(favorecido);
        this.contaReceber.setDescricao(descricao);
        this.contaReceber.setValorBruto(valorBruto);
        this.contaReceber.setEncargos(encargos);
        this.contaReceber.setDesconto(desconto);
        this.contaReceber.setAcrecimo(acrecimo);
        this.contaReceber.setValorPago(valorPago);
        this.contaReceber.setMeioPagamento(meioPagamento);
        this.contaReceber.setCaixa(CaixaController.getInstance().getCaixaAberto());
        this.contaReceber.setVenda(venda);
        this.contaReceber.setStatusPagto(statusPagto);
    }
    public void quitarContaReceber(LocalDate dataPagamento,BigDecimal encargos, BigDecimal desconto, BigDecimal acrecimo, BigDecimal valorPago, MeioPagamento meioPagamento,StatusPagto statusPagto){
    
    }
    @Override
    public void insert() {
        contaReceber.setIdContaReceber(contaReceberDAO.save(contaReceber));
    }

    @Override
    public void update() {
        contaReceberDAO.update(contaReceber);
    }

    @Override
    public void delete() {
        contaReceberDAO.delete(contaReceber);
        this.contaReceber = new ContaReceber();
        this.contaReceber.setIdContaReceber(0l);
    }

    @Override
    public void flushObject() {
        this.contaReceber = new ContaReceber();
        this.contaReceber.setIdContaReceber(0l);
        this.lista = FXCollections.observableList(new ArrayList<>());
    }

    @Override
    public ObservableList<ContaReceber> getLista() {
        return lista;
    }

    @Override
    public void setLista(ObservableList<ContaReceber> lista) {
        this.lista = lista;
    }

    @Override
    public void setItenLista(ContaReceber obj) {
        lista.add(obj);
    }
}
