package br.com.nassau.nav;

import br.com.nassau.nav.domain.entities.CiaArea;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CiaAreaTests {

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
    public void deveRetornarCiaAraQuandoCiaAreaCadastrada(){
        when()
                .get("/cia-area/{id}", "e5c1be28-a5d7-48d5-a7e9-c9ed3a0e4217")
        .then()
                .assertThat()
                    .statusCode(HttpStatus.OK.value())
                    .body("nome", equalTo("GOL"));
    }

    @Test
    public void deveRetornarNotFoundQuandoCiaAreaNaoCadastrada(){
        when()
                .get("/cia-area/{id}", "83c753c2-22e0-4a01-a6bf-b4e4df3a74e3")
        .then()
                .assertThat()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("message", equalTo("Cia area não encontrada!"));
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
                                    .endpointListaVoos("http://127.0.0.1:8080/latam/listar-todos")
                                    .build()};

        CiaArea[] valorAtual =  when()
                                    .get("/cia-area/buscar-nome/{nome}", "LATAM")
                                .then()
                                    .assertThat().statusCode(HttpStatus.OK.value())
                                    .extract().as(CiaArea[].class);

        assertArrayEquals(valorEsperado, valorAtual);
    }

    /*
     * Given que nao existem cias areas com o nome TAP
     * when um vendedor consultar por nome
     * then deve retornar uma lista vazia
     */
    @Test
    public void deveRetornarListaVaziaQuandoCiaAreaNaoCadastrada(){
        when()
                .get("/cia-area/buscar-nome/{nome}", "TAP")
        .then()
                .assertThat()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("message", equalTo("Cia area não encontrada!"));
    }

    /**
     * GIVEN que existem cia-areas cadastradas
     * WHEN buscar todos as cia-areas
     * Then retornar as cias-areas cadastradas
     */
    @Test
    public void deveRetornaCiaAreasCadastradas(){
        CiaArea azul = CiaArea.builder()
                            .id(UUID.fromString("0f8c2d20-6d56-4a4e-968e-41052143777f"))
                            .nome("AZUL")
                            .endpointListaVoos("http://127.0.0.1:8080/azul/listar-todos")
                        .build();

        CiaArea latam = CiaArea.builder()
                .id(UUID.fromString("5f97340c-73e5-449f-b4cd-7be459535be4"))
                .nome("LATAM")
                .endpointListaVoos("http://127.0.0.1:8080/latam/listar-todos")
                .build();

        CiaArea gol = CiaArea.builder()
                .id(UUID.fromString("e5c1be28-a5d7-48d5-a7e9-c9ed3a0e4217"))
                .nome("GOL")
                .endpointListaVoos("http://127.0.0.1:8080/gol/listar-todos")
                .build();


        CiaArea[] valorEsperado = Arrays.array(azul, latam, gol);

        CiaArea[] valorAtual = when()
                                    .get("cia-area/listar-todos")
                            .then()
                                    .assertThat()
                                        .statusCode(HttpStatus.OK.value())
                                    .extract().as(CiaArea[].class);

        Assertions.assertEquals("AZUL", valorAtual[0].getNome());
        Assertions.assertEquals("LATAM", valorAtual[1].getNome());
        Assertions.assertEquals("GOL", valorAtual[2].getNome());
    }

    @Test
    public void deveSalvarCiaArea(){

        Response response = given()
                .header("Content-Type", "application/json")
                .body("""
                        {
                            "nome":"American Airlines",
                            "endpointListaVoos": "http://127.0.0.1:8080/american-airlines/listar-todos"
                        }
                        """)
                .when()
                    .post("/cia-area/")
                .then()
                    .assertThat()
                .statusCode(HttpStatus.CREATED.value()).extract().response();

        String locationEsperado = "http://localhost:" + porta + "/cia-area/";

        String location = response.header("Location");
        String locationAtual = location.substring(0, location.lastIndexOf("/") + 1);

        Assertions.assertEquals(locationEsperado, locationAtual);

    }
}
