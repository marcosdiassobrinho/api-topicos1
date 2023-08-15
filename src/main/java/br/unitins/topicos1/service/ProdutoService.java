package br.unitins.topicos1.service;

import br.unitins.topicos1.api.dto.response.ProdutoResponse;
import br.unitins.topicos1.domain.model.Produto;
import br.unitins.topicos1.api.dto.request.ProdutoDto;

import java.util.List;

public interface ProdutoService {

    Produto buscarOuFalharEntidadePorId(Long produtoId);

    ProdutoResponse buscarProduto(Long idAnuncio);

    ProdutoResponse salvar(ProdutoDto dto);

    void atualizar(Long produtoId, ProdutoDto dto);

    void denunciarProduto(Long produtoId);
    List<ProdutoResponse> buscarProdutos();
    List<ProdutoResponse> buscarProdutosDenunciados();
    void deletar(Long produtoId);
}
