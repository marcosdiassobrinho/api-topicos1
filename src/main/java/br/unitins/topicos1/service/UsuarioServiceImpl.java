package br.unitins.topicos1.service;

import br.unitins.topicos1.api.dto.request.*;
import br.unitins.topicos1.api.dto.response.EnderecoResponse;
import br.unitins.topicos1.api.dto.response.UsuarioResponse;
import br.unitins.topicos1.api.dto.view.UsuarioView;
import br.unitins.topicos1.api.exception.BadRequestException;
import br.unitins.topicos1.api.exception.ForbiddenException;
import br.unitins.topicos1.api.mapper.EnderecoMapper;
import br.unitins.topicos1.api.mapper.LoginMapper;
import br.unitins.topicos1.api.mapper.UsuarioMapper;
import br.unitins.topicos1.api.security.HashCrypt;
import br.unitins.topicos1.api.util.BeanUtil;
import br.unitins.topicos1.api.util.RequestValidator;
import br.unitins.topicos1.domain.exception.PerfilConflitoException;
import br.unitins.topicos1.domain.exception.UsuarioNaoEncontradoException;
import br.unitins.topicos1.domain.model.Endereco;
import br.unitins.topicos1.domain.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {
    @Inject
    LoginMapper loginMapper;
    @Inject
    HashCrypt hashCrypt;
    @Inject
    RequestValidator requestValidator;
    @Inject
    UsuarioMapper usuarioMapper;

    @Inject
    EnderecoMapper enderecoMapper;

    public Usuario buscarOuFalharEntidadePorId(Long id) {
        return Usuario.buscarPorId(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(String.format("Usuario de id:  %d não encontrado.", id)));
    }

    public UsuarioResponse buscarPorId(Long id) {
        Usuario usuario = buscarOuFalharEntidadePorId(id);
        return usuarioMapper.toResponse(usuario);
    }


    public List<UsuarioView> buscarTodos() {
        List<Usuario> usuarios = Usuario.listAll();
        return usuarioMapper.toListView(usuarios);
    }

    public UsuarioResponse buscarUsuarioLogado(String login) {
        return usuarioMapper.toResponse(buscarPorLogin(login));
    }

    @Override
    public List<EnderecoResponse> buscarEnderecos(String login) {
        Usuario usuario = buscarPorLogin(login);


        return enderecoMapper.toListResponse(usuario.enderecos);
    }

    @Override
    @Transactional
    public void cadastrarUsuario(CadastroRequest request) {
        requestValidator.validate(request);
        verificarEmail(request.email());
        Usuario usuario = usuarioMapper.cadastro(request);
        usuario.password = hashCrypt.getHashSenha(request.senha());
        usuario.persistAndFlush();
    }

    @Override
    @Transactional
    public void atualizarUsuario(UsuarioRequest request, String login) {
        Usuario target = buscarPorLogin(login);
        requestValidator.validate(request);
        Usuario source = usuarioMapper.toEntity(request);
        BeanUtil.copyNonNullProperties(source, target);
    }

    @Override
    @Transactional
    public void atualizarUsuarioImagem(String nomeImagem, String login) {
        Usuario usuario = buscarPorLogin(login);
        usuario.nomeImagem = nomeImagem;
    }


    @Override
    @Transactional
    public void cadastrarEndereco(EnderecoRequest request, String login) {
        Usuario usuario = buscarPorLogin(login);
        Endereco endereco = enderecoMapper.toEntity(request);
        endereco.persist();

        if (usuario.enderecos == null) {
            usuario.enderecos = new ArrayList<>();
            endereco.principal = true;
        } else {
            if (endereco.principal) {
                usuario.enderecos.stream()
                        .filter(e -> e.principal)
                        .forEach(e -> e.principal = false);
            }
        }
        usuario.enderecos.add(endereco);
    }

    @Override
    @Transactional
    public void atualizarEndereco(Long enderecoId, EnderecoRequest request, String login) {
        Usuario usuario = buscarPorLogin(login);

        Endereco target = buscarOuFalharEndereco(enderecoId);
        permissaoAlterarEndereco(usuario, target);

        requestValidator.validate(request);
        Endereco source = enderecoMapper.toEntity(request);

        if (source.principal) {
            usuario.enderecos.stream()
                    .filter(e -> e.principal)
                    .forEach(e -> e.principal = false);
        }
        BeanUtil.copyNonNullProperties(source, target);

    }

    @Override
    @Transactional
    public void excluirEndereco(Long enderecoId, String login) {
        Usuario usuario = buscarPorLogin(login);

        Endereco endereco = buscarOuFalharEndereco(enderecoId);
        permissaoAlterarEndereco(usuario, endereco);

        if (endereco.principal) {
            endereco.delete();
            usuario.enderecos.stream()
                    .findFirst().ifPresent(e -> e.principal = true);
        }
    }

    private void permissaoAlterarEndereco(Usuario usuario, Endereco endereco) {
        if (usuario.enderecos.stream()
                .noneMatch(e -> usuario.enderecos.contains(endereco))) {
            throw new ForbiddenException("Usuario sem permissão para alterar Endereço.");
        }

    }

    public Endereco buscarOuFalharEndereco(Long enderecoId) {
        return Endereco.buscarPorId(enderecoId)
                .orElseThrow(() -> new BadRequestException("Endereço nao encontrado"));
    }

    @Override
    public Usuario buscarPorLogin(String login) {
        return Usuario.buscarPorLogin(login)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Login não encontrado."));
    }


    @Override
    public Usuario buscarPorLoginESenha(String login, String senha) {
        return Usuario.buscarPorLoginESenha(login, senha)
                .orElse(null);
    }

    @Override
    public void adicionarAdmin(Long id) {
        Usuario usuario = buscarOuFalharEntidadePorId(id);
        usuario.addRoleAdm();
    }

    @Override
    public void removerAdmin(Long id) {
        Usuario usuario = buscarOuFalharEntidadePorId(id);
        usuario.removeRoleAdm();
    }

    @Override
    public void desativarUsuario(Long id) {
        Usuario usuario = buscarOuFalharEntidadePorId(id);
        usuario.desativar();
    }

    @Override
    public void ativarUsuario(Long id) {
        Usuario usuario = buscarOuFalharEntidadePorId(id);
        usuario.ativar();
    }


    @Override
    @Transactional
    public void atualizarEmail(String email, String login) {
        Usuario usuario = buscarPorLogin(login);
        verificarEmail(email);
        usuario.email = email;
    }

    @Override
    @Transactional
    public void atualizarSenha(SenhaNovaRequest request, String login) {
        requestValidator.validate(request);
        Usuario usuario = buscarPorLogin(login);
        if (usuario.password.equals(request.senhaVelha())) {
            usuario.password = hashCrypt.getHashSenha(request.senhaNova());
        } else {
            throw new PerfilConflitoException("Senha incorreta.");
        }
    }


    private void verificarEmail(String email) {
        if (Usuario.emailExiste(email)) {
            throw new BadRequestException("Email já cadastrado.");
        }
    }

}
