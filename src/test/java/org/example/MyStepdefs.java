package org.example;

import Constantes.AtributosJson;
import Constantes.EndPoint;
import Constantes.Respostas;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Assert;



import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;


public class MyStepdefs  {
    private Response response;
    public static String baseURI;
    private static final HashMap<String,String> header = new HashMap<String, String>();
    @Before
    public static void  carregarConfig(){
        baseURI = "https://api.thecatapi.com/v1";

        header.put("x-api-key","live_ygqwm5wuF77BVitM88wETwSSVKrG2c5gPiEKd03V3TJHzxHKDJvCYh6vSEJkqtm1");
    }
    @Dado("que tenho o link do ApiCat")
    public void queTenhoOLinkDoApiCat() {
        given()
                .when()
                .get(baseURI+EndPoint.images+ Respostas.idImagem)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body(AtributosJson.url, is(Respostas.enderecoComIdImagem))
        ;

    }

    @Quando("digito o link de uma imagem existente com o comando GET")
    public Response digitoOLinkDeUmaImagemExistenteComOComandoGET() {
        response =
               given()
               .when()
                       .get("https://api.thecatapi.com/v1/images/9ab")
               .then().extract().response();

        return response;
    }

    @Entao("terminal retorna validacoes de status e endereco da Api")
    public void terminalRetornaValidacoesDeStatusEEnderecoDaApi() {
        Response retornaImagem = imagemEspecifica();
        Assert.assertEquals(retornaImagem.jsonPath().getString("url"),"https://cdn2.thecatapi.com/images/9ab.jpg");
        Assert.assertEquals(retornaImagem.jsonPath().getString("id"),"9ab");

    }
    private Response imagemEspecifica(){
        Response response=

                given()
                        .when()
                        .get(baseURI+EndPoint.images+ Respostas.idImagem)
                        .then()
                        .extract().response();
        return response;
    }
    @Dado("que desejo ver {int} imagens do ApiCat")
    public void queDesejoVerImagensDoApiCat(int arg0) {
        given()
                .param(AtributosJson.limit,10)
                .when()
                .get(baseURI+EndPoint.imagesSeach+Respostas.limiteDeImagens)
                .then()
                .log().all()
                .assertThat()
                .body(Respostas.size,is(10))
        ;
    }
    public Response retornarImagemLimitadaADez(){
        response = given()
                .param(AtributosJson.limit,10)
            .when()
                .get(baseURI+EndPoint.imagesSeach+Respostas.limiteDeImagens)
            .then().extract().response();
        return response;
    }
    @Quando("solicito no campo search a busca de imagem limitada a {int}")
    public void solicitoNoCampoSearchABuscaDeImagemLimitadaA(int arg0) {

        response= given()
                .param(AtributosJson.limit,10)
            .when()
                .get(baseURI+EndPoint.imagesSeach+Respostas.limiteDeImagens)
            .then()
                .extract().response();
    }
    @Entao("o sistema me retorna apenas {int} imagens")
    public void oSistemaMeRetornaApenasImagens(int arg0) {
        Response dezImagens = retornarImagemLimitadaADez();
        Assert.assertEquals(dezImagens.statusCode(),200);
    }
    @Dado("que desejo realizar um voto em uma imagem do ApiCat")
    public void queDesejoRealizarUmVotoEmUmaImagemDoApiCat() {
        given()
                .headers(MyStepdefs.header)
                .contentType(ContentType.JSON)
                .body(gerarCorpoVoto())
        .when()
                .post(baseURI+EndPoint.votes)
        .then()
                .log().all()
                .assertThat()
                .body(AtributosJson.menssage,is(Respostas.SUCCESS));
    }

    @Quando("realizo o voto")
    public Response realizoOVoto() {
        response =
                given()
                        .headers(MyStepdefs.header)
                        .contentType(ContentType.JSON)
                        .body((gerarCorpoVoto()))
                .when()
                        .post(baseURI+EndPoint.votes)
                .then()
                        .extract().response();
        return response;
    }

        public String gerarCorpoVoto(){
            JSONObject voto = new JSONObject();
            voto.put(AtributosJson.imageId, Respostas.codigo);
            voto.put(AtributosJson.value,1);

            return voto.toString();
        }

    @Entao("sistema me retorna mensagem SUCCESS")
    public void sistemaMeRetornaMensagemSUCCESS() {
        Response votoCriado = realizoOVoto();
        Assert.assertEquals(votoCriado.jsonPath().getString(AtributosJson.menssage),Respostas.SUCCESS);
    }
    @Dado("que desejo excluir um voto realizado")
    public void queDesejoExcluirUmVotoRealizado() {
        Response votoCriado = realizoOVoto();
        String id = votoCriado.jsonPath().getString(Respostas.id);

    }

    @Quando("excluo o voto")
    public void excluoOVoto() {
        Response votoCriado = realizoOVoto();

        String id = votoCriado.jsonPath().getString(Respostas.id);

        given()
                .headers(MyStepdefs.header)
        .when()
                .delete(baseURI+EndPoint.votes+"/" +id)
        .then()
                .log().all()
                .assertThat()
                .body(AtributosJson.menssage,is(Respostas.SUCCESS))
        ;
    }

}


