package br.unitins.topicos1.api.resources;


import br.unitins.topicos1.api.dto.request.FinalizarCompraRequest;
import br.unitins.topicos1.api.dto.request.PreCompraDto;
import br.unitins.topicos1.api.dto.response.CompraResponse;
import br.unitins.topicos1.service.PreCompraService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import java.util.List;


@Path("/pre-compra")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PreCompraResource {
    @Inject
    PreCompraService preCompraService;

    @Inject
    JsonWebToken jwt;

    private static final Logger LOG = Logger.getLogger(PreCompraService.class);

    @POST
    @RolesAllowed({"USER"})
    public Response adicionarProdutos(PreCompraDto dto) {
        LOG.infof("Adicionando %d produtos a pre-compra ", dto.variacoes().size());
        preCompraService.adicionarProdutos(dto);
        LOG.infof("%d produtos foram adicionados a pre-compra ", dto.variacoes().size());
        return Response.status(Response.Status.CREATED)
                .build();
    }

    @DELETE
    @RolesAllowed({"USER"})
    public Response removerProdutosPreCompra(PreCompraDto dto) {
        LOG.infof("Removendo %d produtos de pre-compra ", dto.variacoes().size());
        preCompraService.removerProdutos(dto);
        LOG.infof("%d produtos foram removidos da pre-compra ", dto.variacoes().size());
        return Response.status(Response.Status.OK)
                .build();
    }

    @GET
    @RolesAllowed({"USER"})
    @Path("/{id}")
    public Response buscarPreCompra(@PathParam("id") Long id) {
        LOG.infof("Buscando a pre-compra %d.", id);
        CompraResponse compraResponse = preCompraService.buscarPorId(id);
        LOG.infof("Buscando da pre-compra %d realizada com sucesso.", id);
        return Response.status(Response.Status.CREATED).entity(compraResponse)
                .build();
    }


    @GET
    @RolesAllowed({"USER"})
    public Response buscarPreCompras() {
        LOG.infof("Buscando a lista de pre-compra pessoais do login %s.", jwt.getSubject());
        List<CompraResponse> listaPreCompras = preCompraService.buscarPreCompraPessoais();
        return Response.status(listaPreCompras.isEmpty() ? Response.Status.NO_CONTENT : Response.Status.OK)
                .entity(listaPreCompras)
                .build();
    }


}


