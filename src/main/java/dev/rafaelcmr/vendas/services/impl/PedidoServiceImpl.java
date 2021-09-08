package dev.rafaelcmr.vendas.services.impl;

import dev.rafaelcmr.vendas.dto.ItemPedidoDTO;
import dev.rafaelcmr.vendas.dto.PedidoDTO;
import dev.rafaelcmr.vendas.enums.StatusPedido;
import dev.rafaelcmr.vendas.exceptions.RegraNegocioException;
import dev.rafaelcmr.vendas.models.Cliente;
import dev.rafaelcmr.vendas.models.ItemPedido;
import dev.rafaelcmr.vendas.models.Pedido;
import dev.rafaelcmr.vendas.models.Produto;
import dev.rafaelcmr.vendas.repositories.ClienteRepository;
import dev.rafaelcmr.vendas.repositories.ItemPedidoRepository;
import dev.rafaelcmr.vendas.repositories.PedidoRepository;
import dev.rafaelcmr.vendas.repositories.ProdutoRepository;
import dev.rafaelcmr.vendas.services.PedidoService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository repository;
    private final ClienteRepository clientesRepository;
    private final ProdutoRepository produtosRepository;
    private final ItemPedidoRepository itemsPedidoRepository;

    public PedidoServiceImpl(PedidoRepository repository, ClienteRepository clientesRepository,
                             ProdutoRepository produtosRepository, ItemPedidoRepository itemsPedidoRepository) {
        this.repository = repository;
        this.clientesRepository = clientesRepository;
        this.produtosRepository = produtosRepository;
        this.itemsPedidoRepository = itemsPedidoRepository;
    }

    @Override
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRepository
                .findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido."));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itemsPedido = converterItems(pedido, dto.getItems());
        repository.save(pedido);
        itemsPedidoRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);
        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return Optional.empty();
    }

    @Override
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {

    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items){
        if(items.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar um pedido sem items.");
        }

        return items
                .stream()
                .map( dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtosRepository
                            .findById(idProduto)
                            .orElseThrow(
                                    () -> new RegraNegocioException(
                                            "Código de produto inválido: "+ idProduto
                                    ));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());

    }
}
