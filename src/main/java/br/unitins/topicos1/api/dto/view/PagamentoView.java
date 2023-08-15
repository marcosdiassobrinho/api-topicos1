package br.unitins.topicos1.api.dto.view;


import java.util.List;

public class PagamentoView {
    public Long id;
    public String formaPagamento;
    public List<ParcelaView> parcelas;
}
