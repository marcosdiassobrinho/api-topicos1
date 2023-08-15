package br.unitins.topicos1.api.resources;


import br.unitins.topicos1.api.dto.request.MarcaDto;
import br.unitins.topicos1.api.dto.response.MarcaResponseDto;
import br.unitins.topicos1.domain.model.Marca;
import br.unitins.topicos1.service.MarcaService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.util.List;

@Path("/marcas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MarcaResource {
    @Inject
    MarcaService marcaService;
    private static final Logger LOG = Logger.getLogger(MarcaResource.class);

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    public Response buscarMarcas() {
        LOG.info("Buscando todas as marcas.");
        List<MarcaResponseDto> listaMarcas = marcaService.buscarMarcas();
        LOG.info("Busca realizada com sucesso.");
        return Response.status(listaMarcas.isEmpty() ? Response.Status.NO_CONTENT : Response.Status.OK)
                .entity(listaMarcas)
                .build();
    }

    @GET
    @Path("{id}/")
    @RolesAllowed({"ADMIN", "USER"})
    public Response buscarMarca(@PathParam("id") Long id) {
        LOG.infof("Buscando a marca de id: %d.", id);
        MarcaResponseDto marca = marcaService.buscarPorId(id);
        LOG.info("Busca realizada com sucesso.");
        return Response.ok(marca).build();
    }

    @POST
    @RolesAllowed({"ADMIN", "USER"})
    public Response salvarMarca(MarcaDto dto) {
        LOG.infof("Salvando a marca %s.", dto.nome());
        marcaService.salvar(dto);
        LOG.infof("marca %s salva com sucesso.", dto.nome());
        return Response.status(Response.Status.CREATED)
                .build();
    }

    @PUT
    @Path("{id}/")
    @RolesAllowed({"ADMIN", "USER"})
    public Response atualizarMarca(@PathParam("id") Long idMarca, MarcaDto dto) {
        LOG.infof("Atualizando a marca %s.", dto.nome());
        marcaService.atualizar(idMarca, dto);
        LOG.infof("marca %s atualizada com sucesso.", dto.nome());
        return Response.ok().build();
    }

    @DELETE
    @Path("{id}/")
    @RolesAllowed({"ADMIN", "USER"})
    public Response deletarMarca(@PathParam("id") Long idMarca) {
        LOG.infof("Deletando a marca de id: %d.", idMarca);
        marcaService.deletar(idMarca);
        LOG.info("Marca deletada com sucesso.");
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
