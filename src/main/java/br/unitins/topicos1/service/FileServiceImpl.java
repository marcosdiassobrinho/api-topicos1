package br.unitins.topicos1.service;

import br.unitins.topicos1.domain.model.Usuario;
import br.unitins.topicos1.domain.model.Variacao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
@ApplicationScoped
public class FileServiceImpl implements FileService {

    @Inject
    UsuarioService usuarioService;

    private final String PATH_USER = System.getProperty("user.home")
            + File.separator + "quarkus"
            + File.separator + "images"
            + File.separator + "usuario" + File.separator;

    private final String PATH_VARIACOES = System.getProperty("user.home")
            + File.separator + "quarkus"
            + File.separator + "images"
            + File.separator + "variacoes" + File.separator;

    @Override
    public String salvarImagemUsuario(byte[] imagem, String nomeImagem) throws IOException {

        String mimeType = Files.probeContentType(new File(nomeImagem).toPath());

        List<String> listMimeType = Arrays.asList("image/jpg", "image/png", "image/gif");
        if (!listMimeType.contains(mimeType)) {
            throw new IOException("Tipo de imagem não suportada.");
        }

        // verificando o tamanho do arquivo, não permitor maior que 10 megas
        if (imagem.length > (1024 * 1024 * 10))
            throw new IOException("Arquivo muito grande.");

        File diretorio = new File(PATH_USER);
        if (!diretorio.exists())
            diretorio.mkdirs();

        // gerando o nome do arquivo
        String nomeArquivo = UUID.randomUUID()
                + "." + mimeType.substring(mimeType.lastIndexOf("/") + 1);

        String path = PATH_USER + nomeArquivo;

        File file = new File(path);
        if (file.exists())
            file.delete();

        // criando um arquivo no S.O.
        file.createNewFile();

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(imagem);
        fos.flush();
        fos.close();

        return nomeArquivo;

    }


    @Override
    public String salvarImagemVariacao(byte[] imagem, String nomeImagem) throws IOException {

        String mimeType = Files.probeContentType(new File(nomeImagem).toPath());

        List<String> listMimeType = Arrays.asList("image/jpg", "image/png", "image/gif");
        if (!listMimeType.contains(mimeType)) {
            throw new IOException("Tipo de imagem não suportada.");
        }

        // verificando o tamanho do arquivo, não permitor maior que 10 megas
        if (imagem.length > (1024 * 1024 * 10))
            throw new IOException("Arquivo muito grande.");

        File diretorio = new File(PATH_VARIACOES);
        if (!diretorio.exists())
            diretorio.mkdirs();

        String nomeArquivo = UUID.randomUUID()
                + "." + mimeType.substring(mimeType.lastIndexOf("/") + 1);

        String path = PATH_VARIACOES + nomeArquivo;

        File file = new File(path);
        if (file.exists())
            file.delete();

        file.createNewFile();

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(imagem);
        fos.flush();
        fos.close();

        return nomeArquivo;
    }

    @Override
    public File downloadImagemUsuario(Usuario usuario) {
        return new File(PATH_USER + usuario.nomeImagem);
    }

    @Override
    public File downloadImagemVariacao(Variacao variacao) {
        return new File(PATH_VARIACOES + variacao.nomeImagem);
    }


}
