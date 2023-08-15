package br.unitins.topicos1.api.resources;

import br.unitins.topicos1.api.dto.request.CompraRequest;
import br.unitins.topicos1.api.dto.response.CompraResponse;
import br.unitins.topicos1.service.CompraService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import java.util.List;


@Path("/compras")
public class CompraResource {
    private static final Logger LOG = Logger.getLogger(CompraResource.class);
    @Inject
    CompraService compraService;
    @Inject
    JsonWebToken jwt;

    @GET
    @RolesAllowed({"USER"})
    public Response buscarComprasPessoais() {
        LOG.infof("Buscando lista de compras para o login %s.", jwt.getSubject());
        List<CompraResponse> listaCompras = compraService.buscarComprasPessoais(jwt.getSubject());
        LOG.info("busca bem sucedida");
        return Response.status(listaCompras.isEmpty() ? Response.Status.NO_CONTENT : Response.Status.OK)
                .entity(listaCompras)
                .build();
    }


    @GET
    @RolesAllowed({"USER"})
    @Path("/vendas")
    public Response buscarVendasPessoais() {
        LOG.infof("Buscando lista de vendas para o login %s.", jwt.getSubject());
        List<CompraResponse> listaCompras = compraService.buscarVendasPessoais(jwt.getSubject());
        LOG.info("busca bem sucedida");
        return Response.status(listaCompras.isEmpty() ? Response.Status.NO_CONTENT : Response.Status.OK)
                .entity(listaCompras)
                .build();
    }

    @POST
    @RolesAllowed({"USER"})
    @Path("/finalizar")
    public Response finalizarPreCompra(CompraRequest request) {
        LOG.infof("Finalizando a Pre-compra do login %s .", jwt.getSubject());
        compraService.finalizarCompra(request, jwt.getSubject());
        LOG.info("Compra realizada com sucesso.");
        return Response.status(Response.Status.CREATED)
                .build();
    }

}
