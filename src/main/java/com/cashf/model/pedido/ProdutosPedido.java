/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cashf.model.pedido;

import com.cashf.model.produto.Produto;
import java.math.BigDecimal;

/**
 *
 * @author Aluno
 */
public class ProdutosPedido {
    private long idPrdodutoPedido;
    private int qtde;
    private BigDecimal valorUnitario;
    private Produto produto;

    public ProdutosPedido() {
    }

    public ProdutosPedido(long idPrdodutoPedido, int qtde, BigDecimal valorUnitario, Produto produto) {
        this.idPrdodutoPedido = idPrdodutoPedido;
        this.qtde = qtde;
        this.valorUnitario = valorUnitario;
        this.produto = produto;
    }

    public long getIdPrdodutoPedido() {
        return idPrdodutoPedido;
    }

    public void setIdPrdodutoPedido(long idPrdodutoPedido) {
        this.idPrdodutoPedido = idPrdodutoPedido;
    }

    public int getQtde() {
        return qtde;
    }

    public void setQtde(int qtde) {
        this.qtde = qtde;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (int) (this.idPrdodutoPedido ^ (this.idPrdodutoPedido >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProdutosPedido other = (ProdutosPedido) obj;
        if (this.idPrdodutoPedido != other.idPrdodutoPedido) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return  produto .getDescriao();
    }
    
}
