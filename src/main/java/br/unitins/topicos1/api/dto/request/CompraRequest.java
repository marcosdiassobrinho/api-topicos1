package br.unitins.topicos1.api.dto.request;

import br.unitins.topicos1.domain.model.Variacao;

import java.util.List;

public record CompraRequest(List<Long> variacoes, PagamentoRequest pagamentoRequest, Long endereco) {
}
