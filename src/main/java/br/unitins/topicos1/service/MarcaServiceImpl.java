package br.unitins.topicos1.service;

import br.unitins.topicos1.api.dto.request.MarcaDto;
import br.unitins.topicos1.api.dto.response.MarcaResponseDto;
import br.unitins.topicos1.api.mapper.MarcaMapper;
import br.unitins.topicos1.api.util.RequestValidator;
import br.unitins.topicos1.domain.exception.MarcaConflitoException;
import br.unitins.topicos1.domain.exception.MarcaNaoEncontradoException;
import br.unitins.topicos1.domain.model.Marca;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class MarcaServiceImpl implements MarcaService {

    @Inject
    RequestValidator requestValidator;

    @Inject
    MarcaMapper marcaMapper;

    public Marca buscarEntidadeOuFalhar(Long id) {
        return Marca.buscarPorId(id)
                .orElseThrow(() -> new MarcaNaoEncontradoException(String.format("A marca de id %d não foi encontrada", id)));
    }

    @Override
    public List<MarcaResponseDto> buscarMarcas() {
        return Marca.buscarMarcas().stream()
                .map(marcaMapper::toDto)
                .collect(Collectors.toList());
    }

    public MarcaResponseDto buscarPorId(Long id) {
        return marcaMapper.toDto(buscarEntidadeOuFalhar(id));
    }

    public MarcaResponseDto salvar(MarcaDto dto) {
        requestValidator.validate(dto);
        Marca marca = marcaMapper.toEntity(dto);
        String nome = dto.nome().toUpperCase();
        if (!Marca.existeMarcaPorNome(nome)) {
            marca.nome = nome;
            marca.persist();
        }
        return buscarPorId(marca.id);
    }

    @Transactional
    public MarcaResponseDto atualizar(Long id, MarcaDto dto) {
        requestValidator.validateNonNullProperties(dto);

        Marca marca = buscarEntidadeOuFalhar(id);
        marca.nome = dto.nome().toUpperCase();

        return buscarPorId(marca.id);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        Marca marca = buscarEntidadeOuFalhar(id);
        if (marca.produtoEstaEmUso()) {
            throw new MarcaConflitoException(String.format("Existem produtos com a marca de id %d.", id));
        } else {
            marca.delete();
        }
    }

}