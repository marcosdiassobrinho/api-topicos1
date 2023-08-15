package br.unitins.topicos1.api.dto.response;


public class MarcaResponseDto {
    public Long id;
    public String nome;

    public MarcaResponseDto(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
}
