package br.unitins.topicos1.api.resources;

import br.unitins.topicos1.api.dto.request.CadastroRequest;
import br.unitins.topicos1.api.dto.response.UsuarioResponse;
import br.unitins.topicos1.api.dto.view.UsuarioView;
import br.unitins.topicos1.api.exception.EntidadeNaoEncontradaException;
import br.unitins.topicos1.api.exception.NegocioException;
import br.unitins.topicos1.service.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/usuarios")
public class UsuariosResouce {
    @Inject
    UsuarioService usuarioService;

    private static final Logger LOG = Logger.getLogger(UsuariosResouce.class);

    @POST
    public void cadastrarUsuario(CadastroRequest request) {
        usuarioService.cadastrarUsuario(request);
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "USER"})
    public Response buscarUsuario(@PathParam("id") Long id) {
        LOG.infof("Buscando usuario com id: %d", id);
        try {
            UsuarioResponse response = usuarioService.buscarPorId(id);
            LOG.infof("Usuario encontrado: %s", response.nome());
            return Response.ok(response).build();
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @GET
    @RolesAllowed({"ADMIN"})
    public Response buscarUsuarios() {
        LOG.info("Buscando todos os usuarios");
        List<UsuarioView> list = usuarioService.buscarTodos();
        return Response.status(list.isEmpty() ? Response.Status.NO_CONTENT : Response.Status.OK)
                .entity(list)
                .build();
    }

    @PATCH
    @Path("/{id}/adicionar-adm")
    @RolesAllowed({"ADMIN"})
    public void adicionarAdm(@PathParam("id") Long id) {
        LOG.infof("Adicionando adm para o Usuario com id: %d", id);
        try {
            usuarioService.adicionarAdmin(id);
            LOG.infof("adm adicionado com sucesso");
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PATCH
    @Path("/{id}/remover-adm")
    @RolesAllowed({"ADMIN"})
    public void removerAdm(@PathParam("id") Long id) {
        try {
            usuarioService.removerAdmin(id);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PATCH
    @Path("/{id}/desativar")
    @RolesAllowed({"ADMIN"})
    public void desativarUsuario(@PathParam("id") Long id) {
        try {
            usuarioService.desativarUsuario(id);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PATCH
    @Path("/{id}/ativar")
    @RolesAllowed({"ADMIN"})
    public void ativarUsuario(@PathParam("id") Long id) {
        try {
            usuarioService.ativarUsuario(id);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }
}
