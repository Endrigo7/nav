package br.com.nassau.nav;

import br.com.nassau.nav.domain.entities.CiaArea;
import br.com.nassau.nav.domain.entities.Voo;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.restassured.RestAssured;
import org.apache.http.impl.conn.Wire;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.*;

@WireMockTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VooTests {

    @LocalServerPort
    private int porta;

    private static WireMockServer wireMockServer;

    @BeforeAll
    static void setupAll(){
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        configureFor("127.0.0.1", wireMockServer.port());
        System.out.println("#### Porta onde está rodando o servidor de mock -> " + wireMockServer.port());
    }

    @BeforeEach
    public void setup(){
        baseURI = "http://localhost";
        port = porta;
    }

    @AfterEach
    public void clean(){
        RestAssured.reset();
    }

    @AfterAll
    static void stop(){
        wireMockServer.stop();
    }

    @Test
    public void deveRetornarOsVoosDisponiveisQuandoExistirem(){

        stubFor(WireMock.get("/azul/listar-todos")
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                [
                                    {
                                        "numero": "AZ-1234",
                                        "origem": "REC",
                                        "destino": "GRU",
                                        "dataHora": "2023-09-08T20:48:07",
                                        "valor": 100
                                    },
                                    {
                                        "numero": "AZ-8895",
                                        "origem": "REC",
                                        "destino": "GRU",
                                        "dataHora": "2023-09-08T20:48:07",
                                        "valor": 250
                                    }
                                ]
                                """)));

        stubFor(WireMock.get("/latam/listar-todos")
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                [
                                    {
                                        "numero": "LT-0001",
                                        "origem": "REC",
                                        "destino": "GRU",
                                        "dataHora": "2023-09-08T20:48:07",
                                        "valor": 400
                                    }
                                ]
                                """)));

        stubFor(WireMock.get("/gol/listar-todos")
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                [
                                    {
                                        "numero": "GO-9921",
                                        "origem": "REC",
                                        "destino": "GRU",
                                        "dataHora": "2023-09-08T20:48:07",
                                        "valor": 200
                                    }
                                ]
                                """)));

        CiaArea azul = CiaArea.builder()
                .id(UUID.fromString("0f8c2d20-6d56-4a4e-968e-41052143777f"))
                .nome("AZUL")
                .endpointListaVoos( "http://127.0.0.1:8080/azul/listar-todos")
                .build();

        CiaArea latam = CiaArea.builder()
                .id(UUID.fromString("5f97340c-73e5-449f-b4cd-7be459535be4"))
                .nome("LATAM")
                .endpointListaVoos( "http://127.0.0.1:8080/latam/listar-todos")
                .build();

        CiaArea gol = CiaArea.builder()
                .id(UUID.fromString("e5c1be28-a5d7-48d5-a7e9-c9ed3a0e4217"))
                .nome("GOL")
                .endpointListaVoos( "http://127.0.0.1:8080/gol/listar-todos")
                .build();

        Voo voo1 = Voo.builder()
                .ciaAerea(azul)
                .numero("AZ-1234")
                .origem("REC")
                .destino("GRU")
                .dataHora(LocalDateTime.of(2023, 9, 8, 20, 48, 7))
                .valorCiaArea(new BigDecimal(100))
                .valorNav(new BigDecimal(120).setScale(2, RoundingMode.HALF_EVEN))
                .build();

        Voo voo2 = Voo.builder()
                .ciaAerea(azul)
                .numero("AZ-8895")
                .origem("REC")
                .destino("GRU")
                .dataHora(LocalDateTime.of(2023, 9, 8, 20, 48, 7))
                .valorCiaArea(new BigDecimal(250))
                .valorNav(new BigDecimal(300).setScale(2, RoundingMode.HALF_EVEN))
                .build();

        Voo voo3 = Voo.builder()
                .ciaAerea(latam)
                .numero("LT-0001")
                .origem("REC")
                .destino("GRU")
                .dataHora(LocalDateTime.of(2023, 9, 8, 20, 48, 7))
                .valorCiaArea(new BigDecimal(400))
                .valorNav(new BigDecimal(480).setScale(2, RoundingMode.HALF_EVEN))
                .build();

        Voo voo4 = Voo.builder()
                .ciaAerea(gol)
                .numero("GO-9921")
                .origem("REC")
                .destino("GRU")
                .dataHora(LocalDateTime.of(2023, 9, 8, 20, 48, 7))
                .valorCiaArea(new BigDecimal(200))
                .valorNav(new BigDecimal(240).setScale(2, RoundingMode.HALF_EVEN))
                .build();

        Voo[] valorEsperado = Arrays.array(voo1, voo4, voo2, voo3);

        Voo[] valorAtual =  given()
                                .header("Content-Type", "application/json")
                                .body("""
                                    {
                                        "origem":"rec",
                                        "destino":"con",
                                        "data":"2023-11-09"
                                    }
                                    """)
                            .when()
                                .post("voo/listar-todos")
                            .then()
                                .extract().as(Voo[].class);

        Assertions.assertArrayEquals(valorEsperado, valorAtual);
    }

}


