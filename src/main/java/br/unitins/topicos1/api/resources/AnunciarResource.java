package br.unitins.topicos1.api.resources;

import br.unitins.topicos1.api.dto.request.AnuncioDto;
import br.unitins.topicos1.api.dto.request.VariacaoDto;
import br.unitins.topicos1.api.exception.ConflictException;
import br.unitins.topicos1.api.exception.EntidadeNaoEncontradaException;
import br.unitins.topicos1.api.exception.NegocioException;
import br.unitins.topicos1.service.AnunciarService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.util.List;

@Path("/anunciar")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AnunciarResource {

    @Inject
    AnunciarService anunciarService;

    private static final Logger LOG = Logger.getLogger(AnunciarResource.class);

    @POST
    @RolesAllowed({"USER"})
    public Response iniciarAnuncio(@QueryParam("produto") Long produtoId) {
        LOG.infof("Iniciando cadastro de anuncio com o produto de id %d.", produtoId);
        try {
            anunciarService.iniciarAnuncio(produtoId);
            LOG.infof("Cadastro realizado com sucesso para o produto de id %d.", produtoId);
            return Response.status(Response.Status.CREATED).build();
        } catch (NoResultException e) {
            throw new ConflictException("Erro ao tentar criar anuncio.");
        }
    }

    @PUT
    @Path("/configurar")
    @RolesAllowed({"USER"})
    public Response configurarAnuncio(AnuncioDto dto) {
        LOG.infof("Configurando o anuncio %s ", dto.titulo());
        try {
            anunciarService.configurarAnuncio(dto);
            LOG.info("Configuração bem sucedida.");
            return Response.status(Response.Status.OK).build();
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException("Erro ao tentar configurar anuncio.");
        }
    }


    @POST
    @Path("/adicionar-variacao")
    @RolesAllowed({"USER"})
    public Response adicionarVariacao(List<VariacaoDto> dto) {
        LOG.info("Adicionando variacoes");
        try {
            anunciarService.complementarVariacoesAnuncio(dto);
            LOG.info("varicoes adicionadas com sucesso.");
            return Response.status(Response.Status.CREATED).build();
        } catch (EntidadeNaoEncontradaException e) {
            throw new ConflictException("Erro ao tentar adicionar variações ao anuncio.");
        }
    }
}
