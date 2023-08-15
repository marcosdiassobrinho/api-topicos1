package br.unitins.topicos1.domain.model;

import br.unitins.topicos1.api.exception.BadRequestException;
import br.unitins.topicos1.domain.model.enums.Role;
import br.unitins.topicos1.domain.model.enums.StatusUsuario;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Parameters;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@ToString
@RequiredArgsConstructor

public class Usuario extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long id;

    public String login;

    public String password;

    public String nomeImagem;

    @Column(name = "email")
    public String email;

    @Column(name = "data_de_nascimento")
    public Date dataDeNascimento;

    @Transient
    public Long reputacao;

    @CreationTimestamp
    @Column(name = "data_criacao")
    public LocalDateTime dataCriacao;

    public String nome;

    public String cpf;

    public Boolean denunciado = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_usuario")
    public StatusUsuario statusUsuario = StatusUsuario.INATIVO;

    @OneToMany(fetch = FetchType.EAGER)
    @ToString.Exclude
    public List<Endereco> enderecos;


    @ElementCollection
    @Enumerated(EnumType.STRING)
    public Set<Role> roles = Collections.singleton(Role.USER);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario login = (Usuario) o;
        return Objects.equals(id, login.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void addRoleAdm() {
        this.roles.add(Role.ADMIN);
    }

    public void removeRoleAdm() {
        this.roles.remove(Role.ADMIN);
    }

    public void desativar() {
        this.statusUsuario = StatusUsuario.INATIVO;
    }

    public void ativar() {
        this.statusUsuario = StatusUsuario.COMPLETO;
    }


    public Endereco getEnderecoPrincipal(){
        return this.enderecos.stream()
                .filter(endereco -> endereco.principal)
                .findFirst()
                .orElseThrow(() -> new  BadRequestException("Usuario não possui um endereco."));
    }


    public static Optional<Usuario> buscarPorId(Long id) {
        return find("id = ?1", id)
                .firstResultOptional();
    }

    public static Optional<Usuario> buscarPorLoginESenha(String login, String senha) {
        return find("login = ?1 and password = ?2 and statusUsuario != INATIVO ", login, senha)
                .firstResultOptional();
    }

    public static Optional<Usuario> buscarPorLogin(String login) {
        return find("login = ?1", login)
                .firstResultOptional();
    }



    public static Optional<Usuario> buscarUsuarioCompleto(Long id) {
        return find("id = ?1 and statusUsuario = COMPLETO", id)
                .firstResultOptional();
    }

    public static boolean emailExiste(String email) {
        return count("email = ?1 ", email) > 0;
    }
}
