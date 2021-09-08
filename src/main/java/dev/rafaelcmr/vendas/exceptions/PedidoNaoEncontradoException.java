package dev.rafaelcmr.vendas.exceptions;

public class PedidoNaoEncontradoException extends RuntimeException{

    public PedidoNaoEncontradoException() {
        super("Pedido não encontrado.");
    }
}
