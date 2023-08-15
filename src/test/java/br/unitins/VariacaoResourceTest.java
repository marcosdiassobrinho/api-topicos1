//package br.unitins;
//
//import br.unitins.topicos1.api.resources.refazer.AnunciarResource;
//import br.unitins.topicos1.api.dto.request.VariacaoDto;
//import io.quarkus.test.junit.QuarkusTest;
//import io.restassured.http.ContentType;
//import jakarta.ws.rs.Path;
//import org.junit.jupiter.api.Test;
//
//import static io.restassured.RestAssured.given;
//import static org.hamcrest.Matchers.anyOf;
//import static org.hamcrest.Matchers.equalTo;
//
//@QuarkusTest
//public class VariacaoResourceTest {
//
//    String path = AnunciarResource.class.getAnnotation(Path.class).value();
//
//    @Test
//    public void testBuscarVariacoes() {
//        given()
//                .pathParam("idAnuncio", 1)
//                .when().get(path + "/{idAnuncio}/produto/variacoes")
//                .then()
//                .statusCode(anyOf(equalTo(200), equalTo(204)));
//    }
//
//    @Test
//    public void testBuscarVariacao() {
//        given()
//                .pathParam("idAnuncio", 1)
//                .pathParam("variacaoId", 1)
//                .when().get(path + "/{idAnuncio}/produto/variacoes/{variacaoId}/")
//                .then()
//                .statusCode(200)
//                .body("produtoId", equalTo(1),
//                        "variacaoId", equalTo(1),
//                        "descricao", equalTo("Timeless 4.4 balanceado"),
//                        "peso", equalTo(0.5F),
//                        "preco", equalTo(900.0F),
//                        "quantidade", equalTo(10));
//    }
//
//    @Test
//    public void testCriarVariacao() {
//        VariacaoDto variacaoDto = new VariacaoDto(
//                "Teste Variacao",
//                1.0,
//                true,
//                100.0,
//                10
//        );
//
//        given()
//                .pathParam("idAnuncio", 1)
//                .contentType("application/json")
//                .body(variacaoDto)
//                .when().post(path + "/{idAnuncio}/produto/variacoes")
//                .then()
//                .statusCode(201);
//    }
//
//    @Test
//    public void testDeletarVariacao() {
//        given()
//                .pathParam("idAnuncio", 1)
//                .queryParam("variacaoId", 1)
//                .when().delete(path + "/{idAnuncio}/produto/variacoes")
//                .then()
//                .statusCode(204);
//    }
//
//
//    @Test
//    public void testDescricaoBranca() {
//        VariacaoDto invalidDto = new VariacaoDto("", 1.0, false, 10.0, 5);
//
//        given()
//                .pathParam("idAnuncio", 1)
//                .contentType("application/json")
//                .body(invalidDto)
//                .when().post(path + "/{idAnuncio}/produto/variacoes")
//                .then()
//                .statusCode(400)
//                .body("detail", equalTo("Erro de validação: descricao: não deve estar em branco."));
//    }
//
//    @Test
//    public void testPesoNegativo() {
//        VariacaoDto invalidDto = new VariacaoDto("Teste", -1.0, false, 10.0, 5);
//
//        given()
//                .pathParam("idAnuncio", 1)
//                .contentType("application/json")
//                .body(invalidDto)
//                .when().post(path + "/{idAnuncio}/produto/variacoes")
//                .then()
//                .statusCode(400)
//                .body("detail", equalTo("Erro de validação: peso: deve ser maior ou igual a 0."));
//    }
//
//    @Test
//    public void testValorNegativo() {
//        VariacaoDto invalidDto = new VariacaoDto("Teste", 1.0, false, -10.0, 5);
//
//        given()
//                .pathParam("idAnuncio", 1)
//                .contentType("application/json")
//                .body(invalidDto)
//                .when().post(path + "/{idAnuncio}/produto/variacoes")
//                .then()
//                .statusCode(400)
//                .body("detail", equalTo("Erro de validação: valorBruto: deve ser maior ou igual a 0."));
//    }
//
//    @Test
//    public void testQuantidadeNegativo() {
//        VariacaoDto invalidDto = new VariacaoDto("Teste", 1.0, false, 10.0, -5);
//
//        given()
//                .pathParam("idAnuncio", 1)
//                .contentType("application/json")
//                .body(invalidDto)
//                .when()
//                .post(path + "/{idAnuncio}/produto/variacoes/")
//                .then()
//                .statusCode(400)
//                .body("detail", equalTo("Erro de validação: quantidadeEstoque: deve ser maior ou igual a 0."));
//    }
//
//
//    @Test
//    public void testAtualizacaoParcial() {
//        VariacaoDto variacaoDto = new VariacaoDto(
//                "Teste Atualização Parcial",
//                1.0,
//                true,
//                100.0,
//                10
//        );
//
//        Long idVariacao = given()
//                .contentType(ContentType.JSON)
//                .pathParam("idAnuncio", 1)
//                .body(variacaoDto)
//                .when().post(path + "/{idAnuncio}/produto/variacoes")
//                .then()
//                .statusCode(201)
//                .extract()
//                .as(Long.class);
//
//        VariacaoDto variacaoDtoParcial = new VariacaoDto(
//                null,
//                5.0,
//                true,
//                1500.0,
//                10
//        );
//
//                given()
//                .contentType(ContentType.JSON)
//                .pathParam("idVariacao", idVariacao)
//                .pathParam("idAnuncio", 1)
//                .body(variacaoDtoParcial)
//                .when().patch(path + "/{idAnuncio}/produto/variacoes/{idVariacao}")
//                .then()
//                .statusCode(200)
//                .body("descricao", equalTo("Teste Atualização Parcial"),
//                        "peso", equalTo(1.0F),
//                        "usado", equalTo(true),
//                        "preco", equalTo(1500.0F),
//                        "quantidade", equalTo(10));
//    }
//
//
//
//    @Test
//    public void testAtualizacao() {
//        VariacaoDto variacaoDto = new VariacaoDto(
//                "Teste Atualização completa",
//                1.0,
//                true,
//                100.0,
//                10
//        );
//
//        Long idVariacao = given()
//                .contentType(ContentType.JSON)
//                .pathParam("idAnuncio", 1)
//                .body(variacaoDto)
//                .when().post(path + "/{idAnuncio}/produto/variacoes")
//                .then()
//                .statusCode(201)
//                .extract()
//                .as(Long.class);
//
//        VariacaoDto variacaoDtoParcial = new VariacaoDto(
//                "completa",
//                5.0,
//                true,
//                1500.0,
//                10
//        );
//
//        given()
//                .contentType(ContentType.JSON)
//                .pathParam("idVariacao", idVariacao)
//                .pathParam("idAnuncio", 1)
//                .body(variacaoDtoParcial)
//                .when().put(path + "/{idAnuncio}/produto/variacoes/{idVariacao}")
//                .then()
//                .statusCode(200)
//                .body("descricao", equalTo("completa"),
//                        "peso", equalTo(1.0F),
//                        "usado", equalTo(true),
//                        "preco", equalTo(1500.0F),
//                        "quantidade", equalTo(10));
//    }
//}
