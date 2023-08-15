package br.unitins.topicos1.api.resources;

import br.unitins.topicos1.api.dto.request.AnuncioDto;
import br.unitins.topicos1.api.dto.request.VariacaoDto;
import br.unitins.topicos1.api.dto.response.AnuncioResponse;
import br.unitins.topicos1.api.dto.response.VariacaoResponseDto;
import br.unitins.topicos1.api.exception.EntidadeNaoEncontradaException;
import br.unitins.topicos1.api.exception.NegocioException;
import br.unitins.topicos1.api.form.ImageForm;
import br.unitins.topicos1.domain.exception.AnuncioNaoEncontradoException;
import br.unitins.topicos1.domain.model.Variacao;
import br.unitins.topicos1.service.AnuncioService;
import br.unitins.topicos1.service.FileService;
import br.unitins.topicos1.service.VariacaoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/anuncios")
public class AnuncioResource {
    private static final Logger LOG = Logger.getLogger(AnuncioResource.class);
    @Inject
    AnuncioService anuncioService;
    @Inject
    VariacaoService variacaoService;
    @Inject
    FileService fileService;

    @Inject
    JsonWebToken jwt;


    @GET
    public Response buscarAnuncios() {
        List<AnuncioResponse> listaAnuncios = anuncioService.buscarTodos();
        LOG.info("Buscando todos os anuncios.");
        return Response.status(listaAnuncios.isEmpty() ? Response.Status.NO_CONTENT : Response.Status.OK)
                .entity(listaAnuncios)
                .build();
    }

    @GET
    @Path("/{id}")
    public Response buscarAnuncio(@PathParam("id") Long id) {
        LOG.infof("buscando um anuncio de id: %d", id);
        try {
            AnuncioResponse anuncioResponse = anuncioService.buscarOuFalhar(id);
            LOG.infof("anuncio (%d) buscado com sucesso.", anuncioResponse.id);
            return Response.ok(anuncioResponse).build();
        } catch (NoResultException e) {
            throw new AnuncioNaoEncontradoException(String.format("Anuncio de id: %s não foi encontrado", id));
        }
    }

    @GET
    @Path("/pessoais")
    @RolesAllowed({"USER"})
    public Response buscarAnunciosPessoais() {
        LOG.infof("Buscando todos os anuncios pessoais de %s.", jwt.getSubject());
        List<AnuncioResponse> listaAnuncios = anuncioService.buscarAnunciosPessoais();
        return Response.status(listaAnuncios.isEmpty() ? Response.Status.NO_CONTENT : Response.Status.OK)
                .entity(listaAnuncios)
                .build();
    }


    @GET
    @Path("/analise")
    @RolesAllowed({"ADMIN"})
    public Response buscarAnunciosEmAnalise() {
        LOG.info("Buscando todos os anuncios em analise.");
        List<AnuncioResponse> listaAnuncios = anuncioService.buscarAnunciosEmAnalise();
        return Response.status(listaAnuncios.isEmpty() ? Response.Status.NO_CONTENT : Response.Status.OK)
                .entity(listaAnuncios)
                .build();
    }


    @DELETE
    @RolesAllowed({"ADMIN", "USER"})
    public Response deletarAnuncio(@QueryParam("anuncioId") Long anuncioId) {
        LOG.infof("Deletando o anuncio %d.", anuncioId);
        anuncioService.deletar(anuncioId);
        LOG.info("Anuncio deletado com sucesso.");
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"USER"})
    public Response atualizarAnuncio(@PathParam("id") Long anuncioId, AnuncioDto dto) {
        LOG.infof("Atualizando o anuncio %d.", anuncioId);
        try {
            anuncioService.atualizar(dto, anuncioId);
            LOG.infof("Anuncio %s atualizado com sucesso", dto.titulo());
            return Response.ok().build();
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PATCH
    @Path("/{id}/desativar")
    @RolesAllowed({"ADMIN", "USER"})
    @Transactional
    public Response desativarAnuncio(@PathParam("id") Long anuncioId) {
        LOG.infof("Desativando o anuncio %d.", anuncioId);
        try {
            anuncioService.desativarAnuncio(anuncioId);
            LOG.infof("Anuncio %d desativado com sucesso", anuncioId);
            return Response.ok().build();
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }


    }

    @PATCH
    @Path("/{id}/ativar")
    @RolesAllowed({"ADMIN"})
    public Response ativarAnuncio(@PathParam("id") Long anuncioId) {
        LOG.infof("Ativando o anuncio %d.", anuncioId);
        try {
            anuncioService.ativarAnuncio(anuncioId);
            LOG.infof("Anuncio %d ativado com sucesso", anuncioId);
            return Response.ok().build();
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }

    }


    @GET
    @RolesAllowed({"USER"})
    @Path("/{idAnuncio}/variacoes")
    public Response buscarVariacoes(@PathParam("idAnuncio") Long anuncioId) {
        LOG.infof("Buscando variacoes do anuncio %d.", anuncioId);
        List<VariacaoResponseDto> listaVariacoes = variacaoService.buscarTodos(anuncioId);
        return Response.status(listaVariacoes.isEmpty() ? Response.Status.NO_CONTENT : Response.Status.OK)
                .entity(listaVariacoes).build();
    }


    @POST
    @Path("/{idAnuncio}/variacoes")
    @RolesAllowed({"USER"})
    public Response criarVariacao(@PathParam("idAnuncio") Long anuncioId, VariacaoDto dto) {
        LOG.infof("Cadastrando variacao para o anuncio %d.", anuncioId);
        try {
            variacaoService.salvar(anuncioId, dto);
            LOG.infof("Variacao %s cadastrada com sucesso.", dto.descricao());
            return Response.status(Response.Status.CREATED).build();
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PUT
    @Path("/{idAnuncio}/variacoes/{id}")
    @RolesAllowed({"USER"})
    public Response atualizarVariacao(@PathParam("idAnuncio") Long anuncioId, @PathParam("id") Long id, VariacaoDto dto) {
        LOG.infof("Atualizando variacao para o anuncio %d.", anuncioId);
        try {
            variacaoService.atualizar(anuncioId, id, dto);
            LOG.infof("Variacao %s atualizada com sucesso.", dto.descricao());
            return Response.ok().build();
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }


    @PATCH
    @Path("/{idAnuncio}/variacoes/{id}/imagem")
    @RolesAllowed({"USER"})
    public Response adicionarImagemVariacao(@PathParam("idAnuncio") Long anuncioId, @PathParam("id") Long id, ImageForm form) {
        try {
            String nomeImagem = fileService.salvarImagemVariacao(form.getImagem(), form.getNomeImagem());
            variacaoService.atualizarVariacaoImagem(anuncioId, nomeImagem, id);
            return Response.ok().build();
        } catch (IOException e) {
            throw new BadRequestException(e.getMessage());
        }
    }


    @GET
    @Path("/variacoes/{id}/imagem")
    @RolesAllowed({"USER", "ADMIN"})
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response buscarFoto(@PathParam("id") Long id) {
        Variacao variacao = variacaoService.buscarOuFalharEntidadePorId(id);
        Response.ResponseBuilder response = Response.ok(fileService.downloadImagemVariacao(variacao));
        response.header("Content-Disposition", "attachment;filename=" + variacao.nomeImagem);
        return response.build();
    }


}
