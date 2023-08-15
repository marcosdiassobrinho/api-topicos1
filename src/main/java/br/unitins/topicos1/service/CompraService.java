package br.unitins.topicos1.service;

import br.unitins.topicos1.api.dto.request.CompraRequest;
import br.unitins.topicos1.api.dto.response.CompraResponse;

import java.util.List;

public interface CompraService {
    void finalizarCompra(CompraRequest request, String login);

    List<CompraResponse> buscarComprasPessoais(String login);

    List<CompraResponse> buscarVendasPessoais(String login);
}
