package br.unitins.topicos1.service;

import br.unitins.topicos1.api.dto.request.FinalizarCompraRequest;
import br.unitins.topicos1.api.dto.request.PreCompraDto;
import br.unitins.topicos1.api.dto.response.CompraResponse;
import br.unitins.topicos1.domain.model.Compra;

import java.util.List;

public interface PreCompraService {
    Compra buscarOuFalharEntidadePorId(Long id);

    CompraResponse buscarPorId(Long id);

    void adicionarProdutos(PreCompraDto dto);

    void deletarPreCompra(Long id);

    List<CompraResponse> buscarPreCompraPessoais();


    void removerProdutos(PreCompraDto dto);

}
