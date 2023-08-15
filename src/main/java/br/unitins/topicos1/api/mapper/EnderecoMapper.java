package br.unitins.topicos1.api.mapper;

import br.unitins.topicos1.api.dto.request.EnderecoRequest;
import br.unitins.topicos1.api.dto.response.EnderecoResponse;
import br.unitins.topicos1.domain.model.Endereco;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "jakarta")
public interface EnderecoMapper {
    Endereco toEntity(EnderecoRequest request);

    List<EnderecoResponse> toListResponse(List<Endereco> enderecos);


    EnderecoResponse toResponse(Endereco endereco);

    Endereco longToEntity(Long id);
}
