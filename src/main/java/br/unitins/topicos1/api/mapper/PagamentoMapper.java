package br.unitins.topicos1.api.mapper;

import br.unitins.topicos1.api.dto.request.PagamentoRequest;
import br.unitins.topicos1.domain.model.Pagamento;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jakarta")
public interface PagamentoMapper {
    Pagamento toEntity(PagamentoRequest dto);
}
