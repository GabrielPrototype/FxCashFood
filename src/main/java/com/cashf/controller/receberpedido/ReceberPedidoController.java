/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cashf.controller.receberpedido;

import com.cashf.core.gerarContasPagar.GerarContasPagar;
import com.cashf.dao.fornecedor.FornecedorDAO;
import com.cashf.dao.notafiscal.NotaFiscalDAO;
import com.cashf.dao.produto.ProdutoDAO;
import com.cashf.model.contasPagar.ContaPagar;
import com.cashf.model.contasPagar.StatusPagto;
import com.cashf.model.fornecedor.Fornecedor;
import com.cashf.model.notafiscal.NotaFiscal;
import com.cashf.model.notafiscal.ProdutoNotaFiscal;
import com.cashf.model.produto.Produto;
import com.cashf.model.produto.UnidadeMedida;
import controller.GenericController;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author joao
 */
public class ReceberPedidoController implements GenericController<NotaFiscal> {
    
    private final ProdutoDAO produtoDAO;
    private final FornecedorDAO fornecedorDAO;
    private final NotaFiscalDAO notaFiscalDAO;
    private final GerarContasPagar gerarContasPagar;
    private Fornecedor fornecedor;
    private Produto produtoAtual;
    private NotaFiscal notaFiscal;
    private ContaPagar contaPagar;
    private UnidadeMedida unidadeMedida;
    private BigDecimal valTotalNota;
    private BigDecimal valTotalProd;
    private BigDecimal valTotalDescontos;
    private BigDecimal valTotalAcrecimos;
    private BigDecimal valTotalIPI;
    private BigDecimal valTotalIcms;
    private BigDecimal valTotalIcmsSubst;
    private ObservableList<ProdutoNotaFiscal> listaProdutosNota;
    private ObservableList<NotaFiscal> lista;
    
    public ReceberPedidoController() {
        this.produtoDAO = new ProdutoDAO(Produto.class);
        this.fornecedorDAO = new FornecedorDAO(Fornecedor.class);
        this.notaFiscalDAO = new NotaFiscalDAO(NotaFiscal.class);
        this.gerarContasPagar = new GerarContasPagar();
        this.fornecedor = new Fornecedor();
        this.produtoAtual = new Produto();
        this.listaProdutosNota = FXCollections.observableList(new ArrayList<>());
        this.lista = FXCollections.observableList(notaFiscalDAO.listAll());
        this.contaPagar = new ContaPagar();
        this.notaFiscal = new NotaFiscal();
        this.notaFiscal.setIdNota(0l);
        this.produtoAtual.setIdProduto(0l);
        this.fornecedor.setIdFornecedor(0l);
        
    }
    
    public ContaPagar getContaPagar() {
        return contaPagar;
    }
    
    public void setContaPagar(ContaPagar contaPagar) {
        this.contaPagar = contaPagar;
    }
    
    public Fornecedor getFornecedor() {
        return fornecedor;
    }
    
    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }
    
    public Produto getProdutoAtual() {
        return produtoAtual;
    }
    
    public void setProdutoAtual(Produto produtoAtual) {
        this.produtoAtual = produtoAtual;
    }
    
    public ObservableList<Fornecedor> loadComboFornecedor() {
        return FXCollections.observableList(fornecedorDAO.listAll());
    }
    
    public ObservableList<Produto> loadComboProduto() {
        return FXCollections.observableList(produtoDAO.listAll());
    }
    
    public List<UnidadeMedida> loadComboUnidadeMedida() {
        return Arrays.asList(UnidadeMedida.values());
    }
    
    @Override
    public void insert() {
        this.notaFiscal.setIdNota(notaFiscalDAO.save(notaFiscal));
    }
    
    @Override
    public void update() {
        notaFiscalDAO.update(notaFiscal);
    }
    
    @Override
    public void delete() {
        notaFiscalDAO.delete(notaFiscal);
    }
    
    @Override
    public void flushObject() {
        this.fornecedor = new Fornecedor();
        this.produtoAtual = new Produto();
        this.listaProdutosNota = FXCollections.observableList(new ArrayList<>());
        this.notaFiscal = new NotaFiscal();
        this.notaFiscal.setIdNota(0l);
        this.produtoAtual.setIdProduto(0l);
        this.fornecedor.setIdFornecedor(0l);
    }
    
    @Override
    public ObservableList<NotaFiscal> getLista() {
        return this.lista;
    }
    
    @Override
    public void setLista(ObservableList<NotaFiscal> lista) {
        this.lista = lista;
    }
    
    @Override
    public void setItenLista(NotaFiscal obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public NotaFiscal getNotaFiscal() {
        return notaFiscal;
    }
    
    public void setNotaFiscal(NotaFiscal notaFiscal) {
        this.notaFiscal = notaFiscal;
    }
    
    public void setNotaFiscal(long idNota, String numero_nota, LocalDate dataNota, LocalDate dataRecebimento, BigDecimal base_calc_icms, BigDecimal valor_icms, BigDecimal base_icms_subst, BigDecimal valor_icms_subst, BigDecimal valTotalIpi, BigDecimal outrasDespesas, BigDecimal desconto, BigDecimal valorTotalProdutos, BigDecimal valorTotalNota, String observacao) {
        this.notaFiscal = new NotaFiscal(idNota, numero_nota, fornecedor, contaPagar, dataNota, dataRecebimento, base_calc_icms, valor_icms, base_icms_subst, valor_icms_subst, outrasDespesas, desconto, valorTotalProdutos, valTotalIpi, valorTotalNota, observacao, listaProdutosNota);
    }
    
    public ObservableList<ProdutoNotaFiscal> getListaProdutosNota() {
        return listaProdutosNota;
    }
    
    public void setListaProdutosNota(ObservableList<ProdutoNotaFiscal> listaProdutosNota) {
        this.listaProdutosNota = listaProdutosNota;
    }
    
    public void setListaProdutosNota(Long idProdutoNotaFiscal, Integer qtdeProduto, BigDecimal valorIpi, BigDecimal valorIcmsSubst, BigDecimal valoruUnitario, BigDecimal despesas, BigDecimal descontos, BigDecimal embalagemCompra, UnidadeMedida unidadeMedida) {
        ProdutoNotaFiscal pn = new ProdutoNotaFiscal(idProdutoNotaFiscal, notaFiscal, produtoAtual, qtdeProduto, valorIpi, valorIcmsSubst, valoruUnitario, despesas, descontos, valoruUnitario.multiply(BigDecimal.valueOf(qtdeProduto.doubleValue())).subtract(descontos).add(despesas));
        pn.getProduto().setUnidadeMedida(unidadeMedida);
        pn.getProduto().setQtdeEmbalagem(embalagemCompra);
        pn.getProduto().setQtdeProduto(BigDecimal.valueOf(qtdeProduto));
        pn.getProduto().setUnidadesEstoque(pn.getProduto().getQtdeEmbalagem().multiply(BigDecimal.valueOf(qtdeProduto)));
        this.listaProdutosNota.add(pn);
    }
    
    public UnidadeMedida getUnidadeMedida() {
        return unidadeMedida;
    }
    
    public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }
    
    public BigDecimal getValTotalNota() {
        return valTotalNota;
    }
    
    public BigDecimal getValTotalProd() {
        return valTotalProd;
    }
    
    public BigDecimal getValTotalIcms() {
        return valTotalIcms;
    }
    
    public BigDecimal getValTotalIcmsSubst() {
        return valTotalIcmsSubst;
    }

    /**
     * Retorna o valor total do IPI para os produtos de uma Nota Fiscal Calcula
     * o valor od IPI utilizando o valor informado na Nota Fiscal do produto
     * efetuando sua soma através de um acumulador que é retornado pela função.
     *
     * @return BigDecimal contendo o valot total do IPI dos produtos.
     */
    public BigDecimal getValTotalIPI() {
        valTotalIPI = BigDecimal.ZERO;
        listaProdutosNota.forEach((prod) -> {
            valTotalIPI = valTotalIPI.add((prod.getValorIpi()));
        });
        return valTotalIPI;
    }

    /**
     * Calcula o preço de custo para a lista de produtos de uma Nota Fiscal
     * Calcula o valor utilizando a aliquota de IPI, qtde comprada, descontos e
     * acrecimos. Realiza o Update na tabela Produtos
     */
    public void updateDataProdLista() {
        listaProdutosNota.forEach((ProdutoNotaFiscal prod) -> {
            prod.getProduto().setPreco_custo(prod.getValorUnitario().add(prod.getValorTotal().multiply(prod.getValorIpi())).subtract(prod.getDescontos()).add(prod.getDespesas()).divide(BigDecimal.valueOf(prod.getQtdeProduto())));
            prod.getProduto().setPreco_venda(prod.getProduto().getPreco_custo());
            produtoDAO.update(prod.getProduto());
        });
    }

    /**
     * Calcula o valor total do Icms para os produtos de uma Nota Fiscal Calcula
     * o valor od ICMS utilizando a aliquota informada no cadastro do produto ou
     * utiliza o valor do ICMS Subst se informado.
     *
     */
    private void calcValTotalIcmsProd() {
        valTotalIcms = BigDecimal.ZERO;
        valTotalIcmsSubst = BigDecimal.ZERO;
        listaProdutosNota.forEach((prod) -> {
            if (prod.getValorIcmsSubst().compareTo(BigDecimal.ZERO) == 0) {
                valTotalIcms = valTotalIcms.add(((prod.getProduto().getAliquotasProduto().getAliquotaIcms().divide(BigDecimal.valueOf(100))).multiply((prod.getValorUnitario().subtract(prod.getDescontos()))).add(prod.getDespesas())).multiply(BigDecimal.valueOf(prod.getQtdeProduto())));
            } else {
                valTotalIcmsSubst = valTotalIcmsSubst.add(prod.getValorIcmsSubst());
            }
        });
    }
    
    private void calcValTotalProd() {
        valTotalProd = BigDecimal.ZERO;
        listaProdutosNota.forEach((prod) -> {
            valTotalProd = valTotalProd.add(prod.getValorUnitario().multiply(BigDecimal.valueOf(prod.getQtdeProduto().doubleValue())).add(prod.getDespesas()).subtract(prod.getDescontos()));
            
        });
    }
    
    public void calcTotalDespesasDescontos() {
        valTotalDescontos = BigDecimal.ZERO;
        valTotalAcrecimos = BigDecimal.ZERO;
        listaProdutosNota.forEach((prod) -> {
            valTotalDescontos = valTotalDescontos.add(prod.getDescontos());
            valTotalAcrecimos = valTotalAcrecimos.add(prod.getDespesas());
        });
    }
    
    public void calcValTotalNota() {
        valTotalNota = BigDecimal.ZERO;
        this.valTotalIPI = getValTotalIPI();
        calcValTotalIcmsProd();
        calcValTotalProd();
        calcTotalDespesasDescontos();
        valTotalNota = valTotalNota.add(valTotalIPI);
        valTotalNota = valTotalNota.add(valTotalIcms);
        valTotalNota = valTotalNota.add(valTotalIcmsSubst);
        valTotalNota = valTotalNota.add(valTotalProd);
    }
    
    public void gerarContaPagar() {
        gerarContasPagar.gerarContaPagar(
                valTotalProd,
                valTotalIPI.add(valTotalIcms).add(valTotalIcmsSubst),
                valTotalDescontos,
                valTotalAcrecimos,
                valTotalNota,
                LocalDate.now(),
                fornecedor.getNomefantasia());
        setContaPagar(gerarContasPagar.getContaPagar());
        notaFiscal.setContaPagar(getContaPagar());
    }
    
}
