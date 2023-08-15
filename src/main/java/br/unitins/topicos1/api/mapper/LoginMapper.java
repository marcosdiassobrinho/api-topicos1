package br.unitins.topicos1.api.mapper;

import br.unitins.topicos1.api.dto.request.LoginDto;
import br.unitins.topicos1.api.dto.response.LoginResponseDto;
import br.unitins.topicos1.domain.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jakarta")
public interface LoginMapper {
    Usuario toEntity(LoginDto dto);

    LoginResponseDto toDto(Usuario entity);
}
