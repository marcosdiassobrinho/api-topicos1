package br.unitins.topicos1.api.dto.response;

import br.unitins.topicos1.api.dto.view.EnderecoView;

import java.util.List;

public record UsuarioResponse(Long id, String nome, List<EnderecoView> enderecos, String email, String cpf,
                              String nomeImagem, String dataDeNascimento) {
}
