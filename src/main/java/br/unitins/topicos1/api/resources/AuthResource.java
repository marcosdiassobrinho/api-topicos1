package br.unitins.topicos1.api.resources;

import br.unitins.topicos1.api.dto.request.AuthUsuarioDTO;
import br.unitins.topicos1.api.dto.request.LoginDto;
import br.unitins.topicos1.api.security.HashCrypt;
import br.unitins.topicos1.api.security.TokenJwt;
import br.unitins.topicos1.domain.model.Usuario;
import br.unitins.topicos1.service.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {
    private static final Logger LOG = Logger.getLogger(AuthResource.class);
    @Inject
    HashCrypt hashCrypt;

    @Inject
    TokenJwt tokenJwt;

    @Inject
    UsuarioService usuarioService;

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(AuthUsuarioDTO authDTO) {
        LOG.infof("Tentativa de login %s", authDTO.login());
        String hash = hashCrypt.getHashSenha(authDTO.password());

        Usuario login = usuarioService.buscarPorLoginESenha(authDTO.login(), hash);

        return login == null ? Response.ok().entity("Login não existe ou não encontrado.").build() : Response.ok()
                .header("Authorization", tokenJwt.generateJwt(login)).build();
    }

}
