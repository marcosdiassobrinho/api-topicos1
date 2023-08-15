//package br.unitins.topicos1.api.resources;
//
//import br.unitins.topicos1.api.dto.request.AvaliacaoCompraDto;
//import br.unitins.topicos1.api.dto.response.AvaliacaoCompraReponseDto;
//import br.unitins.topicos1.service.AvaliacaoCompraService;
//
//import jakarta.ws.rs.*;
//import jakarta.ws.rs.core.MediaType;
//import jakarta.ws.rs.core.Response;
//import java.util.List;
//
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)
//@Path("/avaliacoes-compras")
//public class AvaliacaoCompraResource {
//    private final AvaliacaoCompraService avaliacaoCompraService;
//
//    public AvaliacaoCompraResource(AvaliacaoCompraService avaliacaoCompraService) {
//        this.avaliacaoCompraService = avaliacaoCompraService;
//    }
//
//    @POST
//    public Response criarAvaliacaoCompra(@PathParam("idCompra") Long idCompra, AvaliacaoCompraDto dto) {
//        return Response.status(Response.Status.CREATED).entity(avaliacaoCompraService.salvar(idCompra, dto)).build();
//    }
//
//    @GET
//    public Response buscarAvaliacoes(@PathParam("idCompra") Long idCompra) {
//        List<AvaliacaoCompraReponseDto> listaAvaliacoes = avaliacaoCompraService.buscarTodos(idCompra);
//        return Response.status(listaAvaliacoes.isEmpty() ? Response.Status.NO_CONTENT : Response.Status.OK)
//                .entity(listaAvaliacoes)
//                .build();
//    }
//
//    @DELETE
//    public Response deletarAvaliacaoCompra(@QueryParam("id") Long variacaoId, @QueryParam("id") Long perfilId) {
//        avaliacaoCompraService.deletar(variacaoId, perfilId);
//        return Response.status(Response.Status.NO_CONTENT).build();
//    }
//
//    @PATCH
//    @Path("/{id}/denunciar")
//    public Response denuciarAvaliacaoCompra(@PathParam("id") Long variacaoId, @PathParam("idCompra") Long compraId) {
//        return Response.ok(avaliacaoCompraService.denunciar(variacaoId, compraId)).build();
//    }
//}
