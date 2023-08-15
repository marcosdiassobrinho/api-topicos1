package br.unitins;

import br.unitins.topicos1.api.resources.MarcaResource;
import br.unitins.topicos1.api.dto.request.MarcaDto;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import jakarta.ws.rs.Path;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class MarcaResourceTest {

    String path = MarcaResource.class.getAnnotation(Path.class).value();

    @Test
    public void testBuscarMarcas() {
        given()
                .when().get(path)
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(204)));
    }

    @Test
    public void testBuscarMarca() {
        given()
                .pathParam("id", 1)
                .when().get(path + "/{id}")
                .then()
                .statusCode(200)
                .body("idMarca", equalTo(1),
                        "nome", equalTo("7hz"));
    }

    @Test
    public void testCriarMarca() {
        MarcaDto marcaDto = new MarcaDto("Exemplo de Marca");

        given()
                .contentType(ContentType.JSON)
                .body(marcaDto)
                .when().post(path)
                .then()
                .statusCode(201)
                .body(notNullValue());
    }

    @Test
    public void testAtualizarMarca() {
        MarcaDto marcaDtoAtualizado = new MarcaDto("Marca Atualizada");

        given()
                .contentType(ContentType.JSON)
                .body(marcaDtoAtualizado)
                .when()
                .put(path + "/5")
                .then()
                .statusCode(200)
                .body("idMarca", equalTo(5),
                        "nome", equalTo("MARCA ATUALIZADA"));

    }


    @Test
    public void testDeletarMarca() {
        MarcaDto marcaDto = new MarcaDto("Exemplo de delete");

        Long marcaId = given()
                .contentType(ContentType.JSON)
                .body(marcaDto)
                .when().post(path)
                .then()
                .statusCode(201)
                .extract()
                .as(Long.class);

        given()
                .pathParam("id", marcaId)
                .when().delete(path + "/{id}")
                .then()
                .statusCode(204);
    }


    @Test
    public void testDeletarMarcaInexistente() {
        given()
                .pathParam("id", 30)
                .when().delete(path + "/{id}")
                .then()
                .statusCode(404)
                .body("type", equalTo("https://localhost:8080/not-found"),
                        "title", equalTo("Recurso não encontrado."),
                        "status", equalTo(404),
                        "detail", equalTo("A marca de id 30 não foi encontrada"));
        ;
    }

    @Test
    public void testBuscarMarcaInexistente() {
        given()
                .pathParam("id", 30)
                .when().get(path + "/{id}")
                .then()
                .statusCode(404)
                .body("type", equalTo("https://localhost:8080/not-found"),
                        "title", equalTo("Recurso não encontrado."),
                        "status", equalTo(404),
                        "detail", equalTo("A marca de id 30 não foi encontrada"));
    }

    @Test
    public void testDeletarMarcaEmUso() {
        given()
                .pathParam("id", 1)
                .when().delete(path + "/{id}")
                .then()
                .statusCode(409)
                .body("type", equalTo("https://localhost:8080/conflict"),
                        "title", equalTo("Conflito de recursos."),
                        "status", equalTo(409),
                        "detail", equalTo("Existem produtos com a marca de id 1."));
    }

    @Test
    public void testCriarMarcaSemNome() {
        MarcaDto marcaDto = new MarcaDto(null);

        given()
                .contentType(ContentType.JSON)
                .body(marcaDto)
                .when().post(path)
                .then()
                .statusCode(400)
                .body("type", equalTo("https://localhost:8080/bad-request"),
                        "title", equalTo("Requisição inválida."),
                        "status", equalTo(400),
                        "detail", equalTo("Erro de validação: nome: não deve estar em branco."));
    }
}