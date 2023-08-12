package br.com.nassau.nav;

import br.com.nassau.nav.domain.entities.CiaArea;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NavApplicationTests {

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
     * Given que nao existem cias areas com o nome TAP
     * when um vendedor consultar por nome
     * then deve retornar uma lista vazia
     */
    @Test
    public void deveRetornarListaVaziaQuandoCiaAreaNaoCadastrada(){
        CiaArea[] valorEsperado = {};

        CiaArea[] valorAtual =  when()
                .get("/cia-area/buscar-nome/{nome}", "TAP")
                .then()
                .extract().as(CiaArea[].class);

        assertArrayEquals(valorEsperado, valorAtual);
    }


    /*
     * Given que existem cias areas com o nome LATAM
     * when um vendedor consultar por nome
     * then deve retornar uma lista contendo latam
     */
    @Test
    public void deveRetornarListaComLatamQuandoCiaAreaCadastradas(){
        CiaArea[] valorEsperado = {CiaArea.builder()
                                    .id(UUID.fromString("5f97340c-73e5-449f-b4cd-7be459535be4"))
                                    .nome("LATAM")
                                    .endpointListaVoos("http://127.0.0.1/latam/listar-todos")
                                    .build()};



        CiaArea[] valorAtual =  when()
                                    .get("/cia-area/buscar-nome/{nome}", "LATAM")
                                .then()
                                    .extract().as(CiaArea[].class);

        assertArrayEquals(valorEsperado, valorAtual);
    }
}
