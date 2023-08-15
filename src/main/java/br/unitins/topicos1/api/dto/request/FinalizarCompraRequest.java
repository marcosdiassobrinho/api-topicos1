package br.unitins.topicos1.api.dto.request;

public record FinalizarCompraRequest(PreCompraDto preCompraDto, PagamentoRequest pagamentoRequest) {
}
