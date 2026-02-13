package uce.edu.web.api.application;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import uce.edu.web.api.application.representation.LoginRequest;
import uce.edu.web.api.application.representation.Token;

@RegisterRestClient(configKey = "matricula-api") // registro del cliente rest
@Path("/matricula/api/v1.0/usuarios") // ruta base del servicio remoto
@Consumes(MediaType.APPLICATION_JSON) // indica que el cliente envia json
@Produces(MediaType.APPLICATION_JSON) // indica que el cliente espera json
public interface MatriculaClient {

    @POST
    @Path("/validar") // ruta del recurso remoto
    Token validar(LoginRequest request); // metodo para validar usuario
}
