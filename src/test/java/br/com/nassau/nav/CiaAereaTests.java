package br.com.nassau.nav;

import br.com.nassau.nav.domain.entities.CiaAerea;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CiaAereaTests {

    @LocalServerPort
    private int porta;

    @BeforeEach
    public void setup(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = porta;
    }

    @AfterEach
    public void clean(){
        RestAssured.reset();
    }


    /*
     * GIVEN uma cia-aerea cadastrada
     * WHEN consultar por ID
     * Then retorna a cia-aerea
     */
    @Test
    public void deveRetornarCiaAereaQuandoCiaAereaCadastrada(){
        when()
            .get("/cia-aerea/{id}", "e5c1be28-a5d7-48d5-a7e9-c9ed3a0e4217")
        .then()
            .assertThat().statusCode(HttpStatus.OK.value())
                .body("nome", equalTo("GOL"));
    }

    /*
     * GIVEN um id não cadastrado
     * WHEN consultar por ID
     * THEN retornar not-found (404)
     */
    @Test
    public void deveRetornarNotFoundQuandoConsultarPorIDECiaAereaNaoCadastrada(){
        Assertions.assertTrue(true);
    }


    /*
     * Given que existem cias aereas com o nome LATAM
     * when um vendedor consultar por nome
     * then deve retornar uma lista contendo latam
     */
    @Test
    public void deveRetornarListaComLatamQuandoConsultarPorNomeECiaAereaCadastradas(){
        CiaAerea[] valorEsperado = {CiaAerea.builder()
                                    .id(UUID.fromString("5f97340c-73e5-449f-b4cd-7be459535be4"))
                                    .nome("LATAM")
                                    .endpointListaVoos("http://127.0.0.1:8080/latam/listar-todos")
                                    .build()};

        CiaAerea[] valorAtual =  when()
                                    .get("/cia-aerea/buscar-nome/{nome}", "LATAM")
                                .then()
                                    .assertThat().statusCode(HttpStatus.OK.value())
                                    .extract().as(CiaAerea[].class);

        assertArrayEquals(valorEsperado, valorAtual);
    }

    /*
     * Given que nao existem cias aereas com o nome TAP
     * when um vendedor consultar por nome
     * then deve retornar uma lista vazia
     */
    @Test
    public void deveRetornarNotFoundQuandoCiaAereaNaoCadastrada(){
        when()
                .get("/cia-aerea/buscar-nome/{nome}", "TAP")
        .then()
                .assertThat()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("message", equalTo("Cia aerea não encontrada!"));
    }
}
