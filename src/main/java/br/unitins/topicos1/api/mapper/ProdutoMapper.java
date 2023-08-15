package br.unitins.topicos1.api.mapper;

import br.unitins.topicos1.api.dto.request.AmplificadorDto;
import br.unitins.topicos1.api.dto.request.InearDto;
import br.unitins.topicos1.api.dto.request.ProdutoDto;
import br.unitins.topicos1.api.dto.response.AmplificadorResponseDto;
import br.unitins.topicos1.api.dto.response.InearResponseDto;
import br.unitins.topicos1.api.dto.response.ProdutoResponse;
import br.unitins.topicos1.domain.model.Amplificador;
import br.unitins.topicos1.domain.model.Inear;
import br.unitins.topicos1.domain.model.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassMapping;
import org.mapstruct.SubclassMappings;

@Mapper(componentModel = "jakarta", uses = {MarcaMapper.class})
public interface ProdutoMapper {

    @SubclassMappings({
            @SubclassMapping(source = InearDto.class, target = Inear.class),
            @SubclassMapping(source = AmplificadorDto.class, target = Amplificador.class),
    })
    Produto toEntity(ProdutoDto dto);


    @SubclassMappings({
            @SubclassMapping(source = Inear.class, target = InearResponseDto.class),
            @SubclassMapping(source = Amplificador.class, target = AmplificadorResponseDto.class),

    })
    ProdutoResponse toDto(Produto entity);
}
