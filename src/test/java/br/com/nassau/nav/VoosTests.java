package br.com.nassau.nav;

import br.com.nassau.nav.domain.entities.BuscarVoo;
import br.com.nassau.nav.domain.entities.Voo;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WireMockTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VoosTests {

    @LocalServerPort
    private int porta;

    private static WireMockServer wireMockServer;

    @BeforeAll
    static void setupAll(){
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        configureFor("127.0.0.1", wireMockServer.port());
    }

    @BeforeEach
    public void setup(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = porta;
    }

    @AfterEach
    public void clean(){
        RestAssured.reset();
    }

    @AfterAll
    static void setupFimAll(){
        wireMockServer.stop();
    }


    @Test
    public void deveRetornarListaDeVoos(){

        stubFor(WireMock.get("/latam/listar-todos")
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                     [
                                       {
                                         "numero":"LT-9874",
                                         "origem":"REC",
                                         "destino":"SPO",
                                         "dataHora":"2023-09-08T00:39:01.153158",
                                         "valor":100
                                       }
                                     ]
                                                    """)));

        BuscarVoo buscarVoo = BuscarVoo.builder()
                .origem("REC")
                .destino("SPO")
                .data(LocalDate.now())
                .build();

        Voo[] valorEsperado = {};

        Voo[] valorAtual =  given()
                                .header("Content-Type", "application/json")
                                .body(buscarVoo)
                            .when()
                                .post("/voo/listar-todos")
                            .then()
                                .extract().as(Voo[].class);

        assertEquals("LATAM", valorAtual[0].getCiaAerea().getNome());
        assertEquals(new BigDecimal(100), valorAtual[0].getValorCiaArea());

        //assertArrayEquals(valorEsperado, );
    }
}
