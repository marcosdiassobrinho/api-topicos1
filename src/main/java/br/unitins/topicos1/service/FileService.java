package br.unitins.topicos1.service;

import br.unitins.topicos1.domain.model.Usuario;
import br.unitins.topicos1.domain.model.Variacao;

import java.io.File;
import java.io.IOException;

public interface FileService {
    String salvarImagemUsuario(byte[] imagem, String nomeImagem) throws IOException;

    File downloadImagemUsuario(Usuario usuario);

    String salvarImagemVariacao(byte[] imagem, String nomeImagem) throws IOException;
    File downloadImagemVariacao(Variacao variacao);
}
