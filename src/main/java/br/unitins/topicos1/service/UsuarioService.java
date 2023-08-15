package br.unitins.topicos1.service;

import br.unitins.topicos1.api.dto.request.*;
import br.unitins.topicos1.api.dto.response.EnderecoResponse;
import br.unitins.topicos1.api.dto.response.UsuarioResponse;
import br.unitins.topicos1.api.dto.view.UsuarioView;
import br.unitins.topicos1.domain.model.Endereco;
import br.unitins.topicos1.domain.model.Usuario;

import java.util.List;

public interface UsuarioService {
    UsuarioResponse buscarUsuarioLogado(String login);

    List<EnderecoResponse> buscarEnderecos(String login);

    void cadastrarUsuario(CadastroRequest request);

    void atualizarUsuario(UsuarioRequest request, String login);


    void atualizarUsuarioImagem(String nomeImagem, String login);

    void cadastrarEndereco(EnderecoRequest request, String login);

    Usuario buscarOuFalharEntidadePorId(Long id);

    List<UsuarioView> buscarTodos();

    UsuarioResponse buscarPorId(Long id);

    void atualizarEndereco(Long enderecoId, EnderecoRequest request, String login);

    void excluirEndereco(Long enderecoId, String login);

    void atualizarEmail(String email, String login);

    void atualizarSenha(SenhaNovaRequest request, String login);

    Usuario buscarPorLogin(String login);

    Usuario buscarPorLoginESenha(String login, String senha);

    void adicionarAdmin(Long id);

    void removerAdmin(Long id);

    void desativarUsuario(Long id);

    void ativarUsuario(Long id);
}
