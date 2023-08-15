package br.unitins.topicos1.api.mapper;

import br.unitins.topicos1.api.dto.request.CadastroRequest;
import br.unitins.topicos1.api.dto.request.UsuarioRequest;
import br.unitins.topicos1.api.dto.response.UsuarioResponse;
import br.unitins.topicos1.api.dto.view.UsuarioView;
import br.unitins.topicos1.domain.model.Usuario;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "jakarta", uses = {MarcaMapper.class})
public interface UsuarioMapper {
    UsuarioResponse toResponse(Usuario usuario);

    Usuario toEntity(UsuarioRequest request);

    UsuarioView toView(Usuario usuario);

    Usuario cadastro(CadastroRequest request);

    List<UsuarioView> toListView(List <Usuario> usuarios);
}
