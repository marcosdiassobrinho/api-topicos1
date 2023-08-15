//package br.unitins;
//
//import br.unitins.topicos1.api.resources.refazer.AnunciarResource;
//import br.unitins.topicos1.api.dto.request.ProdutoDto;
//import io.quarkus.test.junit.QuarkusTest;
//import io.restassured.http.ContentType;
//import jakarta.ws.rs.Path;
//import org.junit.jupiter.api.Test;
//
//import static io.restassured.RestAssured.given;
//import static org.hamcrest.Matchers.equalTo;
//import static org.hamcrest.Matchers.notNullValue;
//
//@QuarkusTest
//public class ProdutoResourceTest {
//    String anuncioResourcePath = AnunciarResource.class.getAnnotation(Path.class).value();
//
//    @Test
//    public void testBuscarProdutoPorIdAnuncio() {
//        Long anuncioId = 1L;
//
//        given()
//                .pathParam("idAnuncio", anuncioId)
//                .when().get(anuncioResourcePath + "/{idAnuncio}/produto")
//                .then()
//                .statusCode(200)
//                .contentType(ContentType.JSON)
//                .body("idProduto", equalTo(1),
//                        "nomeProduto", equalTo("Timeless"),
//                        "nomeMarca", equalTo("7hz"));
//
//    }
//
//    @Test
//    public void testCriarProduto() {
//        Long anuncioId = 5L;
//        Long marcaId = 1L;
//        ProdutoDto produtoDto = new ProdutoDto("Produto Teste");
//
//        given()
//                .contentType(ContentType.JSON)
//                .pathParam("idAnuncio", anuncioId)
//                .queryParam("idMarca", marcaId)
//                .body(produtoDto)
//                .when().post(anuncioResourcePath + "/{idAnuncio}/produto")
//                .then()
//                .statusCode(201)
//                .body(notNullValue());
//    }
//
//
//    @Test
//    public void testAtualizarMarca() {
//        Long anuncioId = 2L;
//        Long marcaId = 2L;
//
//        given()
//                .contentType(ContentType.JSON)
//                .pathParam("idAnuncio", anuncioId)
//                .queryParam("idMarca", marcaId)
//                .when().patch(anuncioResourcePath + "/{idAnuncio}/produto")
//                .then()
//                .statusCode(200)
//                .body("idProduto", equalTo(2),
//                        "nomeProduto", equalTo("Dioko"),
//                        "nomeMarca", equalTo("Letshuer"));
//    }
//
//    @Test
//    public void testBuscarProdutoInexistente() {
//        Long anuncioId = 999L;
//        given()
//                .contentType(ContentType.JSON)
//                .pathParam("idAnuncio", anuncioId)
//                .when().get(anuncioResourcePath + "/{idAnuncio}/produto")
//                .then()
//                .statusCode(404)
//                .body("type", equalTo("https://localhost:8080/not-found"),
//                        "title", equalTo("Recurso não encontrado."),
//                        "status", equalTo(404),
//                        "detail", equalTo("Produto de id 999 não encontrado."));
//
//    }
//
//    @Test
//    public void testCriarProdutoNomeProdutoBranco() {
//        Long anuncioId = 1L;
//        Long marcaId = 2L;
//        ProdutoDto produtoDto = new ProdutoDto("");
//
//        given()
//                .contentType(ContentType.JSON)
//                .pathParam("idAnuncio", anuncioId)
//                .queryParam("idMarca", marcaId)
//                .body(produtoDto)
//                .when().post(anuncioResourcePath + "/{idAnuncio}/produto")
//                .then()
//                .statusCode(400)
//                .body("type", equalTo("https://localhost:8080/bad-request"),
//                        "title", equalTo("Requisição inválida."),
//                        "status", equalTo(400),
//                        "detail", equalTo("Erro de validação: nome: não deve estar em branco."));
//
//    }
//
//    @Test
//    public void testCriarProdutoNomeProdutoVazio() {
//        Long anuncioId = 1L;
//        Long marcaId = 2L;
//        ProdutoDto produtoDto = new ProdutoDto(null);
//
//        given()
//                .contentType(ContentType.JSON)
//                .pathParam("idAnuncio", anuncioId)
//                .queryParam("idMarca", marcaId)
//                .body(produtoDto)
//                .when().post(anuncioResourcePath + "/{idAnuncio}/produto")
//                .then()
//                .statusCode(400)
//                .body("type", equalTo("https://localhost:8080/bad-request"),
//                        "title", equalTo("Requisição inválida."),
//                        "status", equalTo(400),
//                        "detail", equalTo("Erro de validação: nome: não deve estar em branco."));
//
//    }
//
//    @Test
//    public void testCriarProdutoConflito() {
//        Long anuncioId = 2L;
//        Long marcaId = 2L;
//        ProdutoDto produtoDto = new ProdutoDto("Conflito");
//
//        given()
//                .contentType(ContentType.JSON)
//                .pathParam("idAnuncio", anuncioId)
//                .queryParam("idMarca", marcaId)
//                .body(produtoDto)
//                .when().post(anuncioResourcePath + "/{idAnuncio}/produto")
//                .then()
//                .statusCode(409)
//                .body("type", equalTo("https://localhost:8080/conflict"),
//                        "title", equalTo("Conflito de recursos."),
//                        "status", equalTo(409),
//                        "detail", equalTo("Anuncio Letshuer D13 já possui um produto associado."));
//
//    }
//
//    @Test
//    public void testCriarProdutoMarcaIdInexistente() {
//        Long anuncioId = 1L;
//        Long marcaId = 999L;
//        ProdutoDto produtoDto = new ProdutoDto("Nome do Produto");
//
//        given()
//                .contentType(ContentType.JSON)
//                .pathParam("idAnuncio", anuncioId)
//                .queryParam("idMarca", marcaId)
//                .body(produtoDto)
//                .when().post(anuncioResourcePath + "/{idAnuncio}/produto")
//                .then()
//                .statusCode(404)
//                .body("type", equalTo("https://localhost:8080/not-found"),
//                        "title", equalTo("Recurso não encontrado."),
//                        "status", equalTo(404),
//                        "detail", equalTo("A marca de id 999 não foi encontrada"));
//
//    }
//
//}
