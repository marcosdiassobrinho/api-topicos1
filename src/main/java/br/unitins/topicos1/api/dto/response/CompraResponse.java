package br.unitins.topicos1.api.dto.response;


import br.unitins.topicos1.api.dto.view.AnuncioView;
import br.unitins.topicos1.api.dto.view.EnderecoView;
import br.unitins.topicos1.api.dto.view.PagamentoView;
import br.unitins.topicos1.api.dto.view.VariacaoView;
import lombok.Getter;
import lombok.Value;

import java.util.List;

@Value
public class CompraResponse {
    Long id;

    Double valor;

    Integer quantidade;

    PerfilResponseDto comprador;

    AnuncioView anuncio;

    List<VariacaoView> variacoes;

    PagamentoView pagamento;

    EnderecoView endereco;

}
