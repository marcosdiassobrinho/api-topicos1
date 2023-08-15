package br.unitins.topicos1.api.mapper;

import br.unitins.topicos1.api.dto.request.InearDto;
import br.unitins.topicos1.domain.model.Inear;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jakarta", uses = {MarcaMapper.class})

public interface InearMapper {
    Inear toEntity(InearDto dto);
}
