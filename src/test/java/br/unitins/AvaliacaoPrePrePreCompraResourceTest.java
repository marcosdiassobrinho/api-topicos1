package br.unitins;

import br.unitins.topicos1.api.dto.request.AvaliacaoCompraDto;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class AvaliacaoPrePrePreCompraResourceTest {

    @Test
    public void testBuscarAvaliacoesCompra() {
        AvaliacaoCompraDto dto = new AvaliacaoCompraDto(2L, "Ótimo comprador!", true);

        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when().post("/compras/1/avaliacoes-compra")
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON);

        given()
                .contentType(ContentType.JSON)
                .when().get("/compras/1/avaliacoes-compra/")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void testDeletarAvaliacaoCompra() {
        AvaliacaoCompraDto dto = new AvaliacaoCompraDto(1L, "Ótimo produto!", true);
        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when().post("/compras/1/avaliacoes-compra")
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON);

        given()
                .contentType(ContentType.JSON)
                .queryParam("id", 1L)
                .queryParam("id", 1L)
                .when().delete("/compras/1/avaliacoes-compra")
                .then()
                .statusCode(204);
    }

}
