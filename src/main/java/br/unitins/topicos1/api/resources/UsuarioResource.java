package br.unitins.topicos1.api.resources;

import br.unitins.topicos1.api.dto.response.AnuncioResponse;
import br.unitins.topicos1.api.dto.response.EnderecoResponse;
import br.unitins.topicos1.api.dto.response.UsuarioResponse;
import br.unitins.topicos1.api.form.ImageForm;
import br.unitins.topicos1.api.dto.request.EnderecoRequest;
import br.unitins.topicos1.api.dto.request.SenhaNovaRequest;
import br.unitins.topicos1.api.dto.request.UsuarioRequest;
import br.unitins.topicos1.api.exception.EntidadeNaoEncontradaException;
import br.unitins.topicos1.api.exception.NegocioException;
import br.unitins.topicos1.domain.model.Usuario;
import br.unitins.topicos1.service.FileService;
import br.unitins.topicos1.service.UsuarioService;
import io.quarkus.logging.Log;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import java.io.IOException;
import java.util.List;

@Path("/usuario")
public class UsuarioResource {

    private static final Logger LOG = Logger.getLogger(UsuarioResource.class);
    @Inject
    UsuarioService usuarioService;

    @Inject
    FileService fileService;
    @Inject
    JsonWebToken jwt;

    @GET
    @RolesAllowed({"USER"})
    public Response buscarDados() {
        LOG.info("Buscando dados do usuário logado");
        try {
            UsuarioResponse response = usuarioService.buscarUsuarioLogado(jwt.getSubject());
            LOG.infof("Dados do usuário %s buscado com sucesso", response.nome());
            return Response.ok().build();
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PUT
    @RolesAllowed({"USER"})
    public Response atualizarUsuario(UsuarioRequest request) {
        LOG.infof("Atualizando dados do usuário %s", request.nome());
        try {
            usuarioService.atualizarUsuario(request, jwt.getSubject());
            LOG.infof("Dados do usuário %s atualizados com sucesso", request.nome());
            return Response.ok()
                    .build();
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PATCH
    @Path("/email")
    @RolesAllowed({"USER"})
    public Response atualizarEmail(String email) {
        LOG.infof("Atualizando email do usuário %s", jwt.getSubject());
        try {
            usuarioService.atualizarEmail(email, jwt.getSubject());
            LOG.infof("Email do usuário %s atualizado com sucesso", jwt.getSubject());
            return Response.ok().build();
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PATCH
    @Path("/senha")
    @RolesAllowed({"USER"})
    public Response atualizarSenha(SenhaNovaRequest request) {
        LOG.infof("Atualizando senha do usuário %s", jwt.getSubject());
        try {
            usuarioService.atualizarSenha(request, jwt.getSubject());
            LOG.infof("Senha do usuário %s atualizada com sucesso", jwt.getSubject());
            return Response.ok().build();
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }


    @POST
    @Path("/endereco")
    @RolesAllowed({"USER"})
    public Response cadastrarEndereco(EnderecoRequest request) {
        LOG.infof("Cadastrando endereço do usuário %s", jwt.getSubject());
        try {
            usuarioService.cadastrarEndereco(request, jwt.getSubject());
            LOG.infof("Endereço do usuário %s cadastrado com sucesso", jwt.getSubject());
            return Response.status(Response.Status.CREATED).build();
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }


    @GET
    @Path("/enderecos")
    @RolesAllowed({"USER"})
    public Response buscarAnunciosPessoais() {
        LOG.infof("Buscando endereços do usuário %s", jwt.getSubject());
        List<EnderecoResponse> enderecoResponses = usuarioService.buscarEnderecos(jwt.getSubject());
        return Response.status(enderecoResponses.isEmpty() ? Response.Status.NO_CONTENT : Response.Status.OK)
                .entity(enderecoResponses)
                .build();
    }


    @GET
    @Path("/imagem")
    @RolesAllowed({"USER", "ADMIN"})
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response buscarFoto() {
        LOG.infof("Buscando foto do usuário %s", jwt.getSubject());
        Usuario usuario = usuarioService.buscarPorLogin(jwt.getSubject());
        Response.ResponseBuilder response = Response.ok(fileService.downloadImagemUsuario(usuario));
        response.header("Content-Disposition", "attachment;filename=" + usuario.nomeImagem);
        LOG.infof("Foto do usuário %s buscada com sucesso", jwt.getSubject());
        return response.build();
    }

    @PATCH
    @Path("/imagem")
    @RolesAllowed({"USER"})
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response cadastrarFotoUsuario(@MultipartForm ImageForm form) {
        try {
            String nomeImagem = fileService.salvarImagemUsuario(form.getImagem(), form.getNomeImagem());
            usuarioService.atualizarUsuarioImagem(nomeImagem, jwt.getSubject());
            return Response.ok().build();
        } catch (IOException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @PUT
    @Path("/endereco/{id}")
    @RolesAllowed({"USER"})
    public Response atualizarEndereco(@PathParam("id") Long enderecoId, EnderecoRequest request) {
        LOG.infof("Atualizando endereço %d do usuário %s", enderecoId, jwt.getSubject());
        try {
            usuarioService.atualizarEndereco(enderecoId, request, jwt.getSubject());
            LOG.infof("Endereço %d do usuário %s atualizado com sucesso", enderecoId, jwt.getSubject());
            return Response.ok().build();
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @DELETE
    @Path("/endereco/{id}")
    @RolesAllowed({"USER"})
    public Response deletarEndereco(@PathParam("id") Long enderecoId) {
        LOG.infof("Deletando endereço %d do usuário %s", enderecoId, jwt.getSubject());
        try {
            usuarioService.excluirEndereco(enderecoId, jwt.getSubject());
            LOG.infof("Endereço %d do usuário %s deletado com sucesso", enderecoId, jwt.getSubject());
            return Response.noContent()
                    .build();
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }
}
