package org.example;


import Constantes.AtributosJson;
import Constantes.EndPoint;
import Constantes.Respostas;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import static io.restassured.RestAssured.*;

import java.util.HashMap;

import static org.hamcrest.Matchers.*;


public class AppTest {

    static final HashMap<String,String> header = new HashMap<String, String>();

    @BeforeClass
    public static void  carregarConfig(){
        baseURI = "https://api.thecatapi.com/v1";
        header.put("x-api-key","live_ygqwm5wuF77BVitM88wETwSSVKrG2c5gPiEKd03V3TJHzxHKDJvCYh6vSEJkqtm1");
    }

    @Test
    public void deveRetornarImagemEspecifica(){
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


    @Test
    public void deveRetornar10ImagensQuandoRequisitarParametroLimit(){

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

    @Test
    public void deveRealizarUmVotoValidoEmUmaImagem(){
        Response votoCriado = gerarVoto();
        Assert.assertEquals(votoCriado.jsonPath().getString(AtributosJson.menssage),Respostas.SUCCESS);
        Assert.assertEquals(votoCriado.jsonPath().getString(AtributosJson.imageId),Respostas.codigo);
        Assert.assertEquals(votoCriado.jsonPath().getInt(AtributosJson.value),1);
        Assert.assertEquals(votoCriado.jsonPath().getString(AtributosJson.contryCode),Respostas.BR);
    }
    @Test
    public void excluirUmVotoRealizado(){
        Response votoCriado = gerarVoto();

        String id = votoCriado.jsonPath().getString(Respostas.id);

        given()
                .headers(AppTest.header)//token
        .when()
                .delete(baseURI+EndPoint.votes+"/" +id)
        .then()
                .log().all()
                .assertThat()
                .body(AtributosJson.menssage,is(Respostas.SUCCESS))
        ;
    }
    private Response gerarVoto(){

        Response response =
                given()
                    .headers(AppTest.header)
                    .contentType(ContentType.JSON)
                    .body(gerarCorpoVoto())
                .when()
                    .post(baseURI+EndPoint.votes)
                .then()
                    .extract().response();
        return response;
    }
    public String gerarCorpoVoto(){
        JSONObject corpoVoto = new JSONObject();
        corpoVoto.put(AtributosJson.imageId, Respostas.codigo);
        corpoVoto.put(AtributosJson.value,1);

        return corpoVoto.toString();
    }

}
