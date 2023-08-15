package br.unitins.topicos1.api.security;

import br.unitins.topicos1.domain.model.Usuario;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;
@ApplicationScoped
public class TokenJwt {

    private static final Duration EXPIRATION_TIME = Duration.ofHours(24);

    public String generateJwt(Usuario login) {
        Instant now = Instant.now();
        Instant expiryDate = now.plus(EXPIRATION_TIME);

        Set<String> roles = login.roles
                .stream().map(Enum::name)
                .collect(Collectors.toSet());

        return Jwt.issuer("unitins-jwt")
                .subject(login.login)
                .groups(roles)
                .expiresAt(expiryDate)
                .sign();
    }
}
