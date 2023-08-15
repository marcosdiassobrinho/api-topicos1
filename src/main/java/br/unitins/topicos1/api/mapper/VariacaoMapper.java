package br.unitins.topicos1.api.mapper;

import br.unitins.topicos1.api.dto.request.VariacaoDto;
import br.unitins.topicos1.api.dto.response.VariacaoResponseDto;
import br.unitins.topicos1.domain.model.Variacao;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "jakarta")
public interface VariacaoMapper {

    Variacao toEntity(VariacaoDto dto);

    Variacao longToEntity(Long id);

    List<Variacao> longsToListEntity(List<Long> id);

    List<Variacao> listToEntity(List<VariacaoDto> dto);

    VariacaoResponseDto toDto(Variacao entity);

    List<VariacaoResponseDto> toListDto(List<Variacao> listEntity);
}
