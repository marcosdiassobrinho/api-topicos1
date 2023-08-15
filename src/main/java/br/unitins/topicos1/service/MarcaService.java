package br.unitins.topicos1.service;

import br.unitins.topicos1.domain.model.Marca;
import br.unitins.topicos1.api.dto.request.MarcaDto;
import br.unitins.topicos1.api.dto.response.MarcaResponseDto;

import java.util.List;

public interface MarcaService {

    MarcaResponseDto buscarPorId(Long id);

    List<MarcaResponseDto> buscarMarcas();

    void deletar(Long idMarca);

    MarcaResponseDto atualizar(Long idMarca, MarcaDto dto);

    Marca buscarEntidadeOuFalhar(Long id);

    MarcaResponseDto salvar(MarcaDto dto);

}
