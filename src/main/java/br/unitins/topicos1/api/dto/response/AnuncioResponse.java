package br.unitins.topicos1.api.dto.response;

import br.unitins.topicos1.api.dto.view.ParcelaView;
import br.unitins.topicos1.api.dto.view.VariacaoView;

import java.util.List;


public class AnuncioResponse {
    public Long id;
    public String titulo;
    public List<VariacaoView> variacoes;
    public ProdutoResponse produto;
}
