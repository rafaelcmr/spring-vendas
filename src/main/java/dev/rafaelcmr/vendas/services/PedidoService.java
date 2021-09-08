package dev.rafaelcmr.vendas.services;

import dev.rafaelcmr.vendas.dto.PedidoDTO;
import dev.rafaelcmr.vendas.enums.StatusPedido;
import dev.rafaelcmr.vendas.models.Pedido;

import java.util.Optional;

public interface PedidoService {
    Pedido salvar(PedidoDTO dto );
    Optional<Pedido> obterPedidoCompleto(Integer id);
    void atualizaStatus(Integer id, StatusPedido statusPedido);
}
