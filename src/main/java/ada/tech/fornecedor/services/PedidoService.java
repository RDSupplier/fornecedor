package ada.tech.fornecedor.services;

import ada.tech.fornecedor.domain.dto.EstoqueDto;
import ada.tech.fornecedor.domain.dto.LojaDto;
import ada.tech.fornecedor.domain.dto.PedidoDto;
import ada.tech.fornecedor.domain.dto.PedidoProdutoDto;
import ada.tech.fornecedor.domain.dto.exceptions.NotFoundException;
import ada.tech.fornecedor.domain.entities.*;
import ada.tech.fornecedor.domain.mappers.PedidoMapper;
import ada.tech.fornecedor.repositories.IEstoqueRepository;
import ada.tech.fornecedor.repositories.ILojaRepository;
import ada.tech.fornecedor.repositories.IPedidoRepository;
import ada.tech.fornecedor.repositories.IProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.json.JSONObject;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class PedidoService implements IPedidoService {

    private final IPedidoRepository repository;
    private final IProdutoRepository produtoRepository;
    private final ILojaRepository lojaRepository;

    private final IEstoqueRepository estoqueRepository;

    @Override
    public PedidoDto criarPedido(PedidoDto pedidoDto) {
        Pedido pedido = new Pedido();

        pedido.setData(LocalDate.now());
        pedido.setHorario(LocalTime.now());
        double totalPedido = 0.0;
        double volumeTotalProduto = 0.0;

        for (LojaDto lojaDto : pedidoDto.getLojas()) {
            Loja loja = lojaRepository.findById(lojaDto.getId())
                    .orElseThrow(() -> new RuntimeException("Loja não encontrada"));

            pedido.setLoja(loja);
        }

        PedidoProduto pedidoProduto = new PedidoProduto();

        for (EstoqueDto estoqueDto : pedidoDto.getEstoque()) {
            Estoque estoque = estoqueRepository.findById(estoqueDto.getId())
                    .orElseThrow(() -> new RuntimeException("Estoque não encontrado"));

            pedidoProduto.setEstoque(estoque);
        }

        List<PedidoProduto> pedidotest = new ArrayList<>();

        for (PedidoProdutoDto pedidoProdutoDto : pedidoDto.getProdutos()) {
            Produto produto = produtoRepository.findById(pedidoProdutoDto.getId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));


            if (pedidoProdutoDto.getQuantidade() <= 0) {
                throw new IllegalArgumentException("A quantidade do produto deve ser maior que zero");
            }


            volumeTotalProduto = produto.getVolume() * pedidoProdutoDto.getQuantidade();


            double precoTotalProduto = produto.getPreco() * pedidoProdutoDto.getQuantidade();


            totalPedido += precoTotalProduto;



            pedidoProduto.setPedidos(pedido);
            pedidoProduto.setProdutos(produto);
            pedidoProduto.setQuantidade(pedidoProdutoDto.getQuantidade());
            pedidoProduto.setVolumeTotal(volumeTotalProduto);

            pedido.getPedidoProduto().add(pedidoProduto);

            //pedidotest.add(pedidoProduto);

        }


        pedido.setTotal(totalPedido);
        pedido.setVolumeTotal(volumeTotalProduto);


        pedido = repository.save(pedido);

        return PedidoMapper.toDto(pedido);
    }

    @Override
    public List<PedidoDto> listarPedidos() {
        return repository.findAll().stream().map(PedidoMapper::toDto).toList();
    }

    @Override
    public PedidoDto listarPedido(int id) throws NotFoundException {
        return PedidoMapper.toDto(searchPedidoById(id));
    }

    @Override
    public PedidoDto atualizarPedido(int id, PedidoDto pedidoDto) throws NotFoundException {
        final Pedido pedido = repository.findById(id).orElseThrow(() -> new NotFoundException(Pedido.class, String.valueOf(id)));
        pedido.setData(pedidoDto.getData());
        pedido.setHorario(pedidoDto.getHorario());
        pedido.setTotal(pedidoDto.getTotal());
        return PedidoMapper.toDto(repository.save(pedido));
    }

    @Override
    public void deletarPedido(int id) throws NotFoundException {
        repository.deleteById(id);
    }

    private Pedido searchPedidoById(int id) throws NotFoundException {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(Pedido.class, String.valueOf(id)));
    }

    public String calcularDistanciaPedido(int id) throws NotFoundException {
        String chaveAPI = "fMFAIoNBzUAo1ioZKju6uzSbG4vdUYkLXMjEY2CYPl4noRGb6HZgnOO0mgCofVhG";

        final Pedido pedido = repository.findById(id).orElseThrow(() -> new NotFoundException(Pedido.class, String.valueOf(id)));
        int cepLoja = pedido.getLoja().getEnderecos().getCep();
        int cepFornecedor = pedido.getPedidoProduto()
                .get(0)
                .getProdutos()
                .getProdutoEstoques()
                .get(0)
                .getEstoques().getFornecedor()
                .getEnderecos()
                .getCep();

        HttpURLConnection conexao = null;

        try {
            URL api = new URL("https://api.distancematrix.ai/maps/api/distancematrix/json?origins=" + cepFornecedor +
                    "&destinations=" + cepLoja +
                    "&key=" + chaveAPI);

            conexao = (HttpURLConnection) api.openConnection();
            conexao.setRequestMethod("GET");

            Scanner scanner = new Scanner(conexao.getInputStream());
            StringBuilder resposta = new StringBuilder();
            while (scanner.hasNextLine()) {
                resposta.append(scanner.nextLine());
            }
            scanner.close();

            JSONObject json = new JSONObject(resposta.toString());
            JSONArray rowsJSON = json.getJSONArray("rows");
            JSONObject elementsJSON = rowsJSON.getJSONObject(0).getJSONArray("elements").getJSONObject(0);
            JSONObject distanceJSON = elementsJSON.getJSONObject("distance");

            String distancia = distanceJSON.getString("text");

            return distancia;
        } catch (MalformedURLException e) {
            throw new RuntimeException("Erro na formação da URL da API: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao conectar à API: " + e.getMessage());
        } catch (JSONException e) {
            throw new RuntimeException("Erro na resposta JSON da API: " + e.getMessage());
        } finally {
            if(conexao != null) {
                conexao.disconnect();
            }
        }
    }
}
