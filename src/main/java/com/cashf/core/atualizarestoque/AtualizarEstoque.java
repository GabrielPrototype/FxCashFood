/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cashf.core.atualizarestoque;

import com.cashf.dao.ajusteestoque.AjusteEstoqueDAO;
import com.cashf.dao.produto.ProdutoDAO;
import com.cashf.model.ajusteestoque.AjusteEstoque;
import com.cashf.model.ajusteestoque.TipoAjuste;
import com.cashf.model.prepreparo.PrePreparo;
import com.cashf.model.produto.Produto;
import com.cashf.model.produto.UnidadeMedida;
import java.math.BigDecimal;
import util.UnitConverter;

/**
 *
 * @author joao
 */
public class AtualizarEstoque {

    private final AjusteEstoqueDAO ajusteEstoqueDAO;
    private final ProdutoDAO produtoDAO;
    private Produto produto;
    private PrePreparo prePreparo;
    private AjusteEstoque ajusteEstoque;
    private TipoAjuste tipoAjuste;

    public AtualizarEstoque() {
        this.produtoDAO = new ProdutoDAO(Produto.class);
        this.ajusteEstoqueDAO = new AjusteEstoqueDAO(AjusteEstoque.class);
        this.produto = new Produto();
        produto.setIdProduto(0l);
        this.prePreparo = new PrePreparo();
        prePreparo.setIdPrepreparo(0l);
    }

    public Boolean retirarProduto(Produto produto, BigDecimal qtdeAjuste, UnidadeMedida unidadeMedida) {
        boolean flag = true;
        try {
            BigDecimal qtdeAtual = produto.getQtdeProduto();
            if (produto.getUnidadeMedida().equals(unidadeMedida)) {
                if (qtdeAtual.compareTo(qtdeAjuste) >= 0) {//Se unidades iguáis e qtde>= a ajuste
                    produto.setQtdeProduto(qtdeAtual.subtract(qtdeAjuste));
                }
            } else {
                produto.setQtdeProduto(qtdeAtual.subtract(UnitConverter.convertTo(produto.getUnidadeMedida(), unidadeMedida, qtdeAjuste)));
            }
            
            produtoDAO.update(produto);
        } catch (Exception ex) {
            System.out.println("Erro ao retirar Produto:--->>>> " + ex);
            flag = false;
        }
        return flag;
    }

    public Boolean adicionarProduto(Produto produto, BigDecimal qtdeAjuste, UnidadeMedida unidadeMedida) {
        boolean flag = true;
        try {
            BigDecimal qtdeAtual = produto.getQtdeProduto();
            if (produto.getUnidadeMedida().equals(unidadeMedida)) {
                if (qtdeAjuste.compareTo(BigDecimal.ZERO) > 0) {// qtdeAjuste>= 0
                    produto.setQtdeProduto(qtdeAtual.add(qtdeAjuste));
                }
            } else {
                produto.setQtdeProduto(qtdeAtual.add(UnitConverter.convertTo(produto.getUnidadeMedida(), unidadeMedida, qtdeAjuste)));
            }
            produtoDAO.update(produto);
        } catch (Exception ex) {
            System.out.println("Erro ao adicionar Produto:--->>>> " + ex);
            flag = false;
        }
        return flag;
    }

    public PrePreparo getPrePreparo() {
        return prePreparo;
    }

    public void setPrePreparo(PrePreparo prePreparo) {
        this.prePreparo = prePreparo;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public AjusteEstoque getAjusteEstoque() {
        return ajusteEstoque;
    }

    public void setAjusteEstoque(AjusteEstoque ajusteEstoque) {
        this.ajusteEstoque = ajusteEstoque;
    }

    public TipoAjuste getTipoAjuste() {
        return tipoAjuste;
    }

    public void setTipoAjuste(TipoAjuste tipoAjuste) {
        this.tipoAjuste = tipoAjuste;
    }

}
