//package br.unitins;
//
//import br.unitins.topicos1.api.resources.PerfilResource;
//import br.unitins.topicos1.api.dto.request.UsuarioRequest;
//import io.quarkus.test.junit.QuarkusTest;
//import io.restassured.http.ContentType;
//import jakarta.ws.rs.Path;
//import org.junit.jupiter.api.Test;
//
//import java.util.Date;
//
//import static io.restassured.RestAssured.given;
//import static org.hamcrest.Matchers.equalTo;
//
//@QuarkusTest
//public class LoginResourceTest {
//    String path = PerfilResource.class.getAnnotation(Path.class).value();
//
//    @Test
//    public void testBuscarUsuarioPorPerfilId() {
//        Long idPerfil = 1L;
//
//        given()
//                .contentType(ContentType.JSON)
//                .pathParam("idPerfil", idPerfil)
//                .when()
//                .get(path + "/{idPerfil}/usuario")
//                .then()
//                .statusCode(200)
//                .body("id", equalTo(1),
//                        "nome", equalTo("Wesley Dias"));
//
//    }
//
//
//    @Test
//    public void testSalvarUsuario() {
//        Long idPerfil = 4L;
//        UsuarioRequest dto = new UsuarioRequest("Nome Teste", "057.148.321-67", new Date());
//
//        given()
//                .contentType(ContentType.JSON)
//                .pathParam("idPerfil", idPerfil)
//                .body(dto)
//                .when().post(path + "/{idPerfil}/usuario")
//                .then()
//                .statusCode(201);
//    }
//
//    @Test
//    public void testAtualizarUsuario() {
//        Long idPerfil = 4L;
//        UsuarioRequest dto = new UsuarioRequest("Nome Teste", "057.148.321-67", new Date());
//
//        Long idUsuario = given()
//                .contentType(ContentType.JSON)
//                .pathParam("idPerfil", idPerfil)
//                .body(dto)
//                .when().post(path + "/{idPerfil}/usuario")
//                .then()
//                .statusCode(201)
//                .extract().as(Long.class);
//
//
//        UsuarioRequest dtoUpdate = new UsuarioRequest("Tudo mudado", "057.148.321-67", new Date());
//
//        given()
//                .contentType(ContentType.JSON)
//                .pathParam("idPerfil", idUsuario)
//                .body(dtoUpdate)
//                .when().put(path + "/{idPerfil}/usuario")
//                .then()
//                .statusCode(200)
//                .body("id", equalTo(4),
//                        "nome", equalTo("Tudo mudado"));
//
//    }
//
//    @Test
//    public void testAtualizarUsuarioParcial() {
//        Long idPerfil = 4L;
//        UsuarioRequest dto = new UsuarioRequest("Nome Teste", "057.148.321-67", new Date());
//
//        Long idUsuario = given()
//                .contentType(ContentType.JSON)
//                .pathParam("idPerfil", idPerfil)
//                .body(dto)
//                .when().post(path + "/{idPerfil}/usuario")
//                .then()
//                .statusCode(201)
//                .extract().as(Long.class);
//
//        UsuarioRequest dtoUpdate = new UsuarioRequest("Parcialmente Atualizado", null, null);
//
//        given()
//                .contentType(ContentType.JSON)
//                .pathParam("idPerfil", idUsuario)
//                .body(dtoUpdate)
//                .when().patch(path + "/{idPerfil}/usuario")
//                .then()
//                .statusCode(200)
//                .body("id", equalTo(4),
//                        "nome", equalTo("Parcialmente Atualizado"));
//
//    }
//
//    @Test
//    public void testAtualizarUsuarioParcialTodosCamposVazios() {
//        Long idPerfil = 4L;
//        UsuarioRequest dto = new UsuarioRequest("Nome Teste", "057.148.321-67", new Date());
//
//        Long idUsuario = given()
//                .contentType(ContentType.JSON)
//                .pathParam("idPerfil", idPerfil)
//                .body(dto)
//                .when().post(path + "/{idPerfil}/usuario")
//                .then()
//                .statusCode(201)
//                .extract().as(Long.class);
//
//        UsuarioRequest dtoUpdate = new UsuarioRequest(null, null, null);
//
//        given()
//                .contentType(ContentType.JSON)
//                .pathParam("idPerfil", idUsuario)
//                .body(dtoUpdate)
//                .when().patch(path + "/{idPerfil}/usuario")
//                .then()
//                .statusCode(400)
//                .body("type", equalTo("https://localhost:8080/bad-request"),
//                        "title", equalTo("Requisição inválida."),
//                        "status", equalTo(400),
//                        "detail", equalTo("Todos os campos se encontram vazios."));
//
//    }
//
//}
