package br.unitins.topicos1.service;

import br.unitins.topicos1.api.dto.request.ProdutoDto;
import br.unitins.topicos1.api.dto.response.ProdutoResponse;
import br.unitins.topicos1.api.mapper.ProdutoMapper;
import br.unitins.topicos1.api.util.BeanUtil;
import br.unitins.topicos1.api.util.RequestValidator;
import br.unitins.topicos1.domain.exception.ProdutoConflitoException;
import br.unitins.topicos1.domain.exception.ProdutoNaoEncontradoException;
import br.unitins.topicos1.domain.model.Marca;
import br.unitins.topicos1.domain.model.Produto;
import br.unitins.topicos1.domain.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProdutoServiceImpl implements ProdutoService {
    @Inject
    MarcaService marcaService;
    @Inject
    JsonWebToken jwt;

    @Inject
    RequestValidator requestValidator;

    @Inject
    UsuarioService usuarioService;

    @Inject
    ProdutoMapper produtoMapper;

    @Override
    public Produto buscarOuFalharEntidadePorId(Long produtoId) {
        return Produto.buscarProduto(produtoId)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(String.format("Produto de id %d não encontrado.", produtoId)));
    }

    @Override
    public ProdutoResponse buscarProduto(Long produtoId) {
        return produtoMapper.toDto(buscarOuFalharEntidadePorId(produtoId));
    }

    public List<ProdutoResponse> buscarProdutos() {
        return Produto.buscarProdutos().stream()
                .map(produtoMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ProdutoResponse> buscarProdutosDenunciados() {
        return Produto.buscarProdutosDenunciados().stream()
                .map(produtoMapper::toDto)
                .collect(Collectors.toList());
    }


    @Transactional
    public ProdutoResponse salvar(ProdutoDto dto) {
        requestValidator.validate(dto);
        Usuario usuario = usuarioService.buscarPorLogin(jwt.getSubject());

        Produto produto = produtoMapper.toEntity(dto);
        Optional<Produto> produtoOp = Produto.buscarProdutoExistente(produto);

        Marca marca = marcaService.buscarEntidadeOuFalhar(produto.marca.id);
        if (produtoOp.isPresent()) {
            produto = produtoOp.get();
        } else {
            produto.resgitradoPor = usuario;
            produto.marca = marca;
            produto.persist();
        }

        return produtoMapper.toDto(produto);
    }


    @Transactional
    public void atualizar(Long produtoId, ProdutoDto dto) {
        Produto target = buscarOuFalharEntidadePorId(produtoId);
        Usuario usuario = usuarioService.buscarPorLogin(jwt.getSubject());

        requestValidator.validateNonNullProperties(dto);
        Produto source = produtoMapper.toEntity(dto);
        marcaService.buscarEntidadeOuFalhar(source.marca.id);

        if (target.produtoEstaEmUso()) {
            source.persist();
        } else {
            BeanUtil.copyNonNullProperties(source, target);
            if (!target.resgitradoPor.equals(usuario)) {
                target.alteradoPor = usuario;
            }
        }

    }

    @Transactional
    public void denunciarProduto(Long produtoId) {
        Produto produto = buscarOuFalharEntidadePorId(produtoId);
        produto.denunciar();
    }


    @Transactional
    public void deletar(Long produtoId) {
        Produto produto = buscarOuFalharEntidadePorId(produtoId);
        if (produto.produtoEstaEmUso()) {
            throw new ProdutoConflitoException(String.format("Produto %s não pode ser deletado, pois está em uso.", produto.nome));
        }
        produto.delete();
    }
}
