package br.unitins.topicos1.api.mapper;

import br.unitins.topicos1.api.dto.request.AnuncioDto;
import br.unitins.topicos1.api.dto.response.AnuncioResponse;
import br.unitins.topicos1.domain.model.Anuncio;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "jakarta")
public interface AnuncioMapper {

    AnuncioMapper INSTANCE = Mappers.getMapper( AnuncioMapper.class );
     Anuncio toEntity(AnuncioDto dto);

     AnuncioResponse toResponse(Anuncio anuncio);

     Anuncio longToEntity(Long id);
}
