//package br.unitins;
//
//import br.unitins.topicos1.api.dto.request.PreCompraDto;
//import io.quarkus.test.junit.QuarkusTest;
//import io.restassured.http.ContentType;
//import org.junit.jupiter.api.Test;
//
//import java.util.Arrays;
//
//import static io.restassured.RestAssured.given;
//
//@QuarkusTest
//public class PrePreCompraResourceTest {
//
//    @Test
//    public void testCriarCompra() {
//        PreCompraDto dto = new PreCompraDto(1L, 2L, Arrays.asList(4L, 5L), "CARTAO_CREDITO");
//
//        given()
//                .contentType(ContentType.JSON)
//                .body(dto)
//                .when().post("/compras")
//                .then()
//                .statusCode(201);
//    }
//
//
//    @Test
//    public void testBuscarCompras() {
//        Long idPerfil = 1L;
//
//        PreCompraDto dto = new PreCompraDto(idPerfil, 2L, Arrays.asList(4L, 5L), "CARTAO_CREDITO");
//
//        given()
//                .contentType(ContentType.JSON)
//                .body(dto)
//                .when().post("/compras")
//                .then()
//                .statusCode(201);
//
//
//        given()
//                .contentType(ContentType.JSON)
//                .queryParam("idPerfil", idPerfil)
//                .when().get("/compras")
//                .then()
//                .statusCode(200);
//    }
//
//    @Test
//    public void testBuscarCompraPorId() {
//        Long idCompra = 1L;
//
//        Long idPerfil = 1L;
//
//        PreCompraDto dto = new PreCompraDto(idPerfil, 2L, Arrays.asList(4L, 5L), "CARTAO_CREDITO");
//
//        given()
//                .contentType(ContentType.JSON)
//                .body(dto)
//                .when().post("/compras")
//                .then()
//                .statusCode(201);
//
//        given()
//                .pathParam("id", idCompra)
//                .when().get("/compras/{id}")
//                .then()
//                .statusCode(200)
//                .contentType(ContentType.JSON);
//    }
//
//    @Test
//    public void testCriarCompraMesmoPerfil() {
//        PreCompraDto dto = new PreCompraDto(1L, 1L, Arrays.asList(4L, 5L), "CARTAO_CREDITO");
//
//        given()
//                .contentType(ContentType.JSON)
//                .body(dto)
//                .when().post("/compras")
//                .then()
//                .statusCode(400)
//                .contentType(ContentType.JSON);
//    }
//
//    @Test
//    public void testCriarCompraAnuncioInativo() {
//        PreCompraDto dto = new PreCompraDto(1L, 4L, Arrays.asList(8L, 9L), "CARTAO_CREDITO");
//
//        given()
//                .contentType(ContentType.JSON)
//                .body(dto)
//                .when().post("/compras")
//                .then()
//                .statusCode(400)
//                .contentType(ContentType.JSON);
//    }
//}
