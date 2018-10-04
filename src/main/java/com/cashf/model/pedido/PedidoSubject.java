/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cashf.model.pedido;

/**
 *
 * @author Aluno
 */
public interface PedidoSubject {
    public void add(ClienteObserver cliente);
    public void remove(ClienteObserver cliente);
    public void notifyClientes();
    
}
