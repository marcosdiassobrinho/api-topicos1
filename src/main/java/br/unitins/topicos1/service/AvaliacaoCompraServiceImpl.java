//package br.unitins.topicos1.service;
//
//import br.unitins.topicos1.api.exception.BadRequestException;
//import br.unitins.topicos1.domain.model.AvaliacaoCompra;
//import br.unitins.topicos1.domain.model.PreCompra;
//import br.unitins.topicos1.domain.model.Anuncio;
//import br.unitins.topicos1.domain.model.Perfil;
//import br.unitins.topicos1.domain.repository.AvaliacaoCompraRepository;
//import br.unitins.topicos1.domain.exception.AvaliacaoCompraConflitoException;
//import br.unitins.topicos1.domain.exception.AvaliacaoCompraNaoEncontradaException;
//
//import br.unitins.topicos1.api.dto.request.AvaliacaoCompraDto;
//import br.unitins.topicos1.api.dto.response.AvaliacaoCompraReponseDto;
//import br.unitins.topicos1.api.util.RequestValidator;
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.inject.Inject;
//import jakarta.persistence.NoResultException;
//import jakarta.transaction.Transactional;
//
//import java.util.List;
//
//import static java.time.LocalDateTime.now;
//
//@ApplicationScoped
//public class AvaliacaoCompraServiceImpl implements AvaliacaoCompraService {
//    @Inject
//    AvaliacaoCompraRepository avaliacaoCompraRepository;
//    @Inject
//    PerfilService perfilService;
//
//
//    @Inject
//    AnuncioService anuncioService;
//
//    @Inject
//    RequestValidator requestValidator;
//
//    public AvaliacaoCompra buscarEntidadePorId(Long id) {
//        try {
//            return avaliacaoCompraRepository.buscarEntidadePorId(id);
//        } catch (NoResultException e) {
//            throw new AvaliacaoCompraNaoEncontradaException(String.format("Avaliação de preCompra %d não encontrada.", id));
//        }
//    }
//
//    @Transactional
//    public Long salvar(Long compraId, AvaliacaoCompraDto dto) {
//        requestValidator.validate(dto);
//        Perfil perfil = perfilService.buscarEntidadePorId(dto.perfilId());
//        PreCompra preCompra = compraService.buscarEntidadePorId(compraId);
//        Anuncio anuncio = anuncioService.buscarOuFalharEntidadePorId(preCompra.anuncio.id);
//
//        if (!anuncio.perfil.equals(perfil)) {
//            if (!preCompra.perfil.equals(perfil)) {
//                throw new BadRequestException("Perfil não corresponde a preCompra.");
//            }
//        }
//
//        boolean vendedor = anuncio.perfil == perfil;
//
//        seAvaliacaoExisteFalha(compraId, vendedor);
//
//        AvaliacaoCompra avaliacaoCompra = new AvaliacaoCompra();
//        avaliacaoCompra.preCompra = preCompra;
//        avaliacaoCompra.dataAvaliacao = now();
//        avaliacaoCompra.comentario = dto.comentario();
//        avaliacaoCompra.recomenda = dto.recomenda();
//        avaliacaoCompra.vendedor = vendedor;
//
//        AvaliacaoCompra.persist(avaliacaoCompra);
//        return avaliacaoCompra.id;
//    }
//
//    public List<AvaliacaoCompraReponseDto> buscarTodos(Long compraId) {
//        return avaliacaoCompraRepository.buscarTodos(compraId);
//    }
//
//    public void seAvaliacaoExisteFalha(Long idCompra, boolean vendedor) {
//        if (avaliacaoCompraRepository.avaliacaoCompraExiste(idCompra, vendedor)) {
//            String perfil = vendedor ? "Vendedor" : "Comprador";
//            throw new AvaliacaoCompraConflitoException(String.format("Já existe uma avaliação do %s para essa preCompra %d.", perfil, idCompra));
//        }
//    }
//
//
//    public AvaliacaoCompraReponseDto buscarPorID(Long avaliacaoId) {
//        try {
//            return avaliacaoCompraRepository.buscarPorId(avaliacaoId);
//        } catch (NoResultException e) {
//            throw new AvaliacaoCompraNaoEncontradaException(String.format("Avaliação %d não encontrada.", avaliacaoId));
//        }
//    }
//
//    @Transactional
//    public AvaliacaoCompraReponseDto denunciar(Long avaliacaoCompraId, Long compraId) {
//        PreCompra preCompra = compraService.buscarEntidadePorId(compraId);
//        AvaliacaoCompra avaliacaoCompra = buscarEntidadePorId(avaliacaoCompraId);
//
//        if (!avaliacaoCompra.preCompra.equals(preCompra)) {
//            throw new BadRequestException("PreCompra não corresponde a avaliação.");
//        }
//        avaliacaoCompra.denunciado = true;
//        return buscarPorID(avaliacaoCompra.id);
//    }
//
//    @Transactional
//    public void deletar(Long avaliacaoCompraId, Long perfilId) {
//        Perfil perfil = perfilService.buscarEntidadePorId(perfilId);
//        AvaliacaoCompra avaliacaoCompra = buscarEntidadePorId(avaliacaoCompraId);
//        if (!perfil.nome.equals(avaliacaoCompra.preCompra.perfil.nome)) {
//            throw new BadRequestException("Perfil não corresponde a avaliacão.");
//        }
//        avaliacaoCompraRepository.deleteById(avaliacaoCompraId);
//    }
//
//
//    public Long buscarReputacaoPorPerfil(Perfil perfil, boolean vendedor) {
//        return avaliacaoCompraRepository.buscarReputacao(perfil, vendedor);
//    }
//
//}
