//package br.unitins;
//
//import br.unitins.topicos1.api.resources.refazer.AnunciarResource;
//import br.unitins.topicos1.api.dto.request.AnuncioDto;
//import io.quarkus.test.junit.QuarkusTest;
//import io.restassured.http.ContentType;
//import jakarta.ws.rs.Path;
//import org.junit.jupiter.api.Test;
//
//import static io.restassured.RestAssured.given;
//import static org.hamcrest.Matchers.*;
//
//@QuarkusTest
//public class AnuncioResourceTest {
//
//    String path = AnunciarResource.class.getAnnotation(Path.class).value();
//
//    @Test
//    public void testBuscarAnuncios() {
//        given()
//                .when().get(path)
//                .then()
//                .statusCode(anyOf(equalTo(200), equalTo(204)));
//    }
//
//    @Test
//    public void testCriarAnuncio() {
//        AnuncioDto anuncioDto = new AnuncioDto(
//                "Anuncio de Teste"
//        );
//
//        Long perfilId = 1L;
//
//        given()
//                .contentType(ContentType.JSON)
//                .body(anuncioDto)
//                .queryParam("perfilId", perfilId)
//                .when().post(path)
//                .then()
//                .statusCode(201)
//                .body(notNullValue());
//    }
//
//    @Test
//    public void testAtualizarAnuncio() {
//        AnuncioDto anuncioDtoAtualizado = new AnuncioDto(
//                "Titulo Novo"
//        );
//
//
//        given()
//                .contentType(ContentType.JSON)
//                .pathParam("id", 1)
//                .body(anuncioDtoAtualizado)
//                .when()
//                .put(path + "/{id}")
//                .then()
//                .statusCode(200)
//                .body("idAnuncio", equalTo(1),
//                        "nomeVendedor", equalTo("Wesley Dias"),
//                        "titulo", equalTo("Titulo Novo"),
//                        "valorMinimo", equalTo(1000.0F),
//                        "valorMaximo", equalTo(1260.0F),
//                        "quantidadeDisponivel", equalTo(20));
//    }
//
//    @Test
//    public void testAtualizarStatusAnuncio() {
//        long anuncioId = 4L;
//        String statusAnuncio = "ATIVO";
//
//        given()
//                .contentType(ContentType.JSON)
//                .body(statusAnuncio)
//                .when().patch(path + "/" + anuncioId + "/status")
//                .then()
//                .statusCode(200)
//                .body("idAnuncio", equalTo(4),
//                        "nomeVendedor", equalTo("Wesley Dias"),
//                        "titulo", equalTo("ZSN 10"),
//                        "valorMinimo", equalTo(240.0f),
//                        "valorMaximo", equalTo(300.0f),
//                        "quantidadeDisponivel", equalTo(2));
//    }
//
//    @Test
//    public void testDeletarAnuncio() {
//
//        AnuncioDto anuncioDto = new AnuncioDto(
//                "Excluir"
//        );
//        Long perfilId = 1L;
//
//        Long anuncioId = given()
//                .contentType(ContentType.JSON)
//                .body(anuncioDto)
//                .queryParam("perfilId", perfilId)
//                .when().post(path)
//                .then()
//                .statusCode(201)
//                .extract()
//                .as(Long.class);
//
//        given()
//                .queryParam("anuncioId", anuncioId)
//                .when().delete(path)
//                .then()
//                .statusCode(204);
//    }
//
//    @Test
//    public void testDeletarAnuncioInexistente() {
//
//        given()
//                .queryParam("anuncioId", 30)
//                .when().delete(path)
//                .then()
//                .statusCode(404)
//                .body("type", equalTo("https://localhost:8080/not-found"),
//                        "title", equalTo("Recurso não encontrado."),
//                        "status", equalTo(404),
//                        "detail", equalTo("Anuncio de id: 30 não foi encontrado"));
//
//    }
//
//    @Test
//    public void testSalvarAnuncioPerfilIncompleto() {
//        Long perfilIdIncompleto = 3L;
//
//        AnuncioDto anuncioDto = new AnuncioDto(
//                "Perfil Incompleto"
//        );
//
//        given()
//                .contentType(ContentType.JSON)
//                .body(anuncioDto)
//                .queryParam("perfilId", perfilIdIncompleto)
//                .when()
//                .post(path)
//                .then()
//                .statusCode(409)
//                .body("type", equalTo("https://localhost:8080/conflict"),
//                        "title", equalTo("Conflito de recursos."),
//                        "status", equalTo(409),
//                        "detail", equalTo("Perfil de Braian Guilherme não está completo."));
//
//    }
//
//    @Test
//    public void testSalvarAnuncioPerfilInexistente() {
//
//        AnuncioDto anuncioDto = new AnuncioDto(
//                "Perfil Inexistente"
//        );
//
//        Long perfilId = 999L;
//
//        given()
//                .contentType(ContentType.JSON)
//                .body(anuncioDto)
//                .queryParam("perfilId", perfilId)
//                .when().post(path)
//                .then()
//                .statusCode(404)
//                .body("type", equalTo("https://localhost:8080/not-found"),
//                        "title", equalTo("Recurso não encontrado."),
//                        "status", equalTo(404),
//                        "detail", equalTo("Perfil com id 999 não encontrado."));
//
//    }
//
//    @Test
//    public void testCriarAnuncioSemPerfilId() {
//        AnuncioDto anuncioDto = new AnuncioDto(
//                "id vazio."
//        );
//
//        given()
//                .contentType(ContentType.JSON)
//                .body(anuncioDto)
//                .when().post(path)
//                .then()
//                .statusCode(404)
//                .body("type", equalTo("https://localhost:8080/not-found"),
//                        "title", equalTo("Recurso não encontrado."),
//                        "status", equalTo(404),
//                        "detail", equalTo("Perfil com id null não encontrado."));
//    }
//
//    @Test
//    public void testCriarAnuncioSemTitulo() {
//        AnuncioDto anuncioDto = new AnuncioDto(
//                null
//        );
//
//        given()
//                .contentType(ContentType.JSON)
//                .body(anuncioDto)
//                .when().post(path)
//                .then()
//                .statusCode(400)
//                .body("type", equalTo("https://localhost:8080/bad-request"),
//                        "title", equalTo("Requisição inválida."),
//                        "status", equalTo(400),
//                        "detail", equalTo("Erro de validação: titulo: não deve ser nulo."));
//    }
//
//}
//
//
