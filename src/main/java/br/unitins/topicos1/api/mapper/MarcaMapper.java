package br.unitins.topicos1.api.mapper;

import br.unitins.topicos1.api.dto.request.MarcaDto;
import br.unitins.topicos1.api.dto.response.MarcaResponseDto;
import br.unitins.topicos1.domain.model.Marca;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "jakarta")
public interface MarcaMapper {

    Marca longToEntity(Long id);

    MarcaResponseDto toDto(Marca marca);

    Marca toEntity(MarcaDto dto);
}
