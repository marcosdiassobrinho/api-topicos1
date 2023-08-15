package br.unitins.topicos1.api.resources;

import br.unitins.topicos1.api.dto.request.AmplificadorDto;
import br.unitins.topicos1.api.dto.request.InearDto;
import br.unitins.topicos1.api.dto.response.ProdutoResponse;
import br.unitins.topicos1.domain.model.Inear;
import br.unitins.topicos1.service.PreCompraService;
import br.unitins.topicos1.service.ProdutoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/produtos")
public class ProdutoResource {
    @Inject
    ProdutoService produtoService;

    private static final Logger LOG = Logger.getLogger(ProdutoResource.class);

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "USER"})
    public Response buscarProduto(@PathParam("id") Long id) {
        LOG.infof("Buscando um produto de id: %d", id);
        ProdutoResponse response = produtoService.buscarProduto(id);
        LOG.infof("Produto de id: %d buscado com sucesso", id);
        return Response.ok(response).build();
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    public Response buscarProdutos() {
        LOG.infof("Buscando uma lista de produtos.");
        List<ProdutoResponse> listaProdutos = produtoService.buscarProdutos();
        return Response.status(listaProdutos.isEmpty() ? Response.Status.NO_CONTENT : Response.Status.OK)
                .entity(listaProdutos)
                .build();
    }

    @POST
    @RolesAllowed({"USER"})
    @Path("/inear")
    public Response criarInear(InearDto dto) {
        LOG.infof("Criando um Inear.");
        ProdutoResponse inear = produtoService.salvar(dto);
        LOG.infof("Inear %s criado com sucesso.", dto.nome);
        return Response.status(Response.Status.CREATED)
                .entity(inear).build();
    }

    @POST
    @RolesAllowed({"USER"})
    @Path("/amplificador")
    public Response criarAmplificador(AmplificadorDto dto) {
        LOG.infof("Criando um amplificador.");
        ProdutoResponse amplificador = produtoService.salvar(dto);
        LOG.infof("Amplificador %s criado com sucesso.", dto.nome);
        return Response.status(Response.Status.CREATED)
                .entity(amplificador).build();
    }

    @PUT
    @Path("{id}/amplificador")
    @RolesAllowed({"USER"})
    public Response atualizarAmplificador(@PathParam("id") Long produtoId, AmplificadorDto dto) {
        LOG.infof("Atualizando um aplificador de id: %d", produtoId);
        produtoService.atualizar(produtoId, dto);
        LOG.infof("Amplificador de id: %d atualizado com sucesso.", produtoId);
        return Response.ok().build();
    }

    @PUT
    @Path("{id}/inear")
    @RolesAllowed({"USER"})
    public Response atualizarInear(@PathParam("id") Long produtoId, InearDto dto) {
        LOG.infof("Atualizando um inear de id: %d", produtoId);
        produtoService.atualizar(produtoId, dto);
        LOG.infof("Inear de id: %d atualizado com sucesso.", produtoId);
        return Response.ok().build();
    }

    @DELETE
    @RolesAllowed({"ADMIN", "USER"})
    public Response deletarProduto(@QueryParam("produtoId") Long produtoId) {
        LOG.infof("Deletando um produto de id: %d", produtoId);
        produtoService.deletar(produtoId);
        LOG.infof("Produto de id: %d deletado com sucesso.", produtoId);
        return Response.ok().build();
    }

    @PATCH
    @Path("/{id}/denunciar")
    @RolesAllowed({"USER"})
    public Response denunciarProduto(@PathParam("id") Long id) {
        LOG.infof("Denunciando um produto de id: %d", id);
        produtoService.denunciarProduto(id);
        LOG.infof("Produto de id: %d denunciado com sucesso.", id);
        return Response.ok().build();
    }

    @GET
    @Path("/denunciados")
    @RolesAllowed({"ADMIN"})
    public Response buscarProdutosDenunciados() {
        LOG.infof("Buscando uma lista de produtos denunciados.");
        List<ProdutoResponse> listaProdutos = produtoService.buscarProdutosDenunciados();
        return Response.status(listaProdutos.isEmpty() ? Response.Status.NO_CONTENT : Response.Status.OK)
                .entity(listaProdutos)
                .build();
    }
}
