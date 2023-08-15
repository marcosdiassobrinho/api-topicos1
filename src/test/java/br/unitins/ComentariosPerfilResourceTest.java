//package br.unitins;
//
//import br.unitins.topicos1.api.resources.PerfilResource;
//import br.unitins.topicos1.api.dto.request.ComentarioPerfilDto;
//import io.quarkus.test.junit.QuarkusTest;
//import io.restassured.http.ContentType;
//import jakarta.ws.rs.Path;
//import org.junit.jupiter.api.Test;
//
//import static io.restassured.RestAssured.given;
//import static org.hamcrest.Matchers.equalTo;
//
//@QuarkusTest
//public class ComentariosPerfilResourceTest {
//    String path = PerfilResource.class.getAnnotation(Path.class).value();
//
//    @Test
//    public void testBuscarComentarios() {
//        Long idPerfil = 1L;
//
//        given()
//                .pathParam("idPerfil", idPerfil)
//                .when().get(path + "/{idPerfil}/comentarios")
//                .then()
//                .statusCode(200)
//                .contentType(ContentType.JSON);
//    }
//
//    @Test
//    public void testBuscarComentario() {
//        Long idPerfil = 1L;
//        Long idComentario = 1L;
//
//        given()
//                .pathParam("idPerfil", idPerfil)
//                .pathParam("id", idComentario)
//                .when().get(path + "/{idPerfil}/comentarios/{id}")
//                .then()
//                .statusCode(200)
//                .contentType(ContentType.JSON)
//                .body("id", equalTo(1),
//                        "destinatario", equalTo("Braian Guilherme"),
//                        "remetente", equalTo("Wesley Dias"),
//                        "comentario", equalTo("Bom vendedor, rápido igual bala."),
//                        "denunciado", equalTo(false)
//                );
//    }
//
//    @Test
//    public void testCriarComentario() {
//        Long idPerfil = 2L;
//        Long remetenteId = 1L;
//        ComentarioPerfilDto dto = new ComentarioPerfilDto("Comentário de teste");
//
//        given()
//                .contentType(ContentType.JSON)
//                .pathParam("idPerfil", idPerfil)
//                .queryParam("remetenteId", remetenteId)
//                .body(dto)
//                .when().post(path + "/{idPerfil}/comentarios")
//                .then()
//                .statusCode(201)
//                .contentType(ContentType.JSON);
//    }
//
//    @Test
//    public void testDeletarComentario() {
//        Long idPerfil = 1L;
//        Long comentarioId = 1L;
//
//        given()
//                .pathParam("idPerfil", idPerfil)
//                .queryParam("comentarioId", comentarioId)
//                .when().delete(path + "/{idPerfil}/comentarios")
//                .then()
//                .statusCode(204);
//    }
//
//
//    @Test
//    public void testDenunciarComentario() {
//        Long idPerfil = 1L;
//        Long comentarioId = 1L;
//
//        given()
//                .pathParam("idPerfil", idPerfil)
//                .queryParam("comentarioId", comentarioId)
//                .when().patch(path + "/{idPerfil}/comentarios")
//                .then()
//                .statusCode(200)
//                .contentType(ContentType.JSON)
//                .body("id", equalTo(1),
//                        "destinatario", equalTo("Braian Guilherme"),
//                        "remetente", equalTo("Wesley Dias"),
//                        "comentario", equalTo("Bom vendedor, rápido igual bala."),
//                        "denunciado", equalTo(true)
//                );
//    }
//
//
//    @Test
//    public void testCriarComentarioDuplicado() {
//        Long idPerfil = 1L;
//        Long remetenteId = 2L;
//        ComentarioPerfilDto dto = new ComentarioPerfilDto("Comentário de teste");
//
//        given()
//                .contentType(ContentType.JSON)
//                .pathParam("idPerfil", idPerfil)
//                .queryParam("remetenteId", remetenteId)
//                .body(dto)
//                .when().post(path + "/{idPerfil}/comentarios")
//                .then()
//                .statusCode(201);
//
//        given()
//                .contentType(ContentType.JSON)
//                .pathParam("idPerfil", idPerfil)
//                .queryParam("remetenteId", remetenteId)
//                .body(dto)
//                .when().post(path + "/{idPerfil}/comentarios")
//                .then()
//                .statusCode(409)
//                .contentType(ContentType.JSON)
//                .body("type", equalTo("https://localhost:8080/conflict"),
//                        "title", equalTo("Conflito de recursos."),
//                        "status", equalTo(409),
//                        "detail", equalTo("O login Marcos Dias já comentou no perfil de Wesley Dias."));
//    }
//
//
//    @Test
//    public void testCriarComentarioPerfilIncompleto() {
//        Long idPerfil = 1L;
//        Long remetenteId = 3L;
//        ComentarioPerfilDto dto = new ComentarioPerfilDto("Comentário teste");
//
//        given()
//                .contentType(ContentType.JSON)
//                .pathParam("idPerfil", idPerfil)
//                .queryParam("remetenteId", remetenteId)
//                .body(dto)
//                .when().post(path + "/{idPerfil}/comentarios")
//                .then()
//                .statusCode(409)
//                .body("type", equalTo("https://localhost:8080/conflict"),
//                        "title", equalTo("Conflito de recursos."),
//                        "status", equalTo(409),
//                        "detail", equalTo("Perfil de Braian Guilherme não está completo."));
//    }
//
//
//    @Test
//    public void testCriarComentarioDtoInvalido() {
//        Long idPerfil = 1L;
//        Long remetenteId = 2L;
//        ComentarioPerfilDto dto = new ComentarioPerfilDto("");
//
//        given()
//                .contentType(ContentType.JSON)
//                .pathParam("idPerfil", idPerfil)
//                .queryParam("remetenteId", remetenteId)
//                .body(dto)
//                .when().post(path + "/{idPerfil}/comentarios")
//                .then()
//                .statusCode(400)
//                .body("type", equalTo("https://localhost:8080/bad-request"),
//                        "title", equalTo("Requisição inválida."),
//                        "status", equalTo(400),
//                        "detail", equalTo("Erro de validação: comentario: não deve estar em branco."));
//    }
//}
