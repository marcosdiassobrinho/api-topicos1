package br.unitins.topicos1.api.mapper;

import br.unitins.topicos1.api.dto.request.CompraRequest;
import br.unitins.topicos1.api.dto.request.PreCompraDto;
import br.unitins.topicos1.api.dto.response.CompraResponse;
import br.unitins.topicos1.domain.model.Compra;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "jakarta", uses = {VariacaoMapper.class, EnderecoMapper.class})
public interface CompraMapper {
    CompraResponse toResponse(Compra compra);

    Compra toEntity(CompraRequest request);

    Compra toEntity(PreCompraDto dto);

    List<CompraResponse> toListResponse(List<Compra> compras);

}
