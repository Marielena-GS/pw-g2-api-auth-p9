package uce.edu.web.api.intefaces;

import java.time.Instant;
import java.util.Set;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import uce.edu.web.api.application.MatriculaClient;
import uce.edu.web.api.application.representation.LoginRequest;
import uce.edu.web.api.application.representation.Token;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {
    @Inject
    @RestClient
    MatriculaClient matriculaClient;

    @POST
    @Path("/token")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public TokenResponse token(LoginRequest request) {
        // donde se compara el password y el usuario contra la base de datos
        // de tarea hacer la comparacion con la base de datos
        // creando un usario admin/admin en la api de matricula para las pruebas se crea
        // un endpoint para validar
        // despues de eso usamos ese endpoint para validar en vez de la comparacion
        // estatica enviamos el objeto request y recibimos un boolean
        // se cifra el token JWT y se retorna
        Token token = matriculaClient.validar(request);
        String role = token.getRole();
        if (token.getIsValid()) {

            // base de datos tabla usuario id/usuario/password/rol agregar eso
            String issuer = "cursos-auth";
            long ttl = 3600;

            Instant now = Instant.now();
            Instant exp = now.plusSeconds(ttl);

            String jwt = Jwt.issuer(issuer)
                    .subject(request.username)
                    .groups(Set.of(role)) // roles: user / admin
                    .issuedAt(now)
                    .expiresAt(exp)
                    .sign();

            return new TokenResponse(jwt, exp.getEpochSecond(), role);
        } else {
            return null;
        }

    }

    public static class TokenResponse {
        public String accessToken;
        public long expiresAt;
        public String role;

        public TokenResponse() {
        }

        public TokenResponse(String accessToken, long expiresAt, String role) {
            this.accessToken = accessToken;
            this.expiresAt = expiresAt;
            this.role = role;
        }
    }

}
