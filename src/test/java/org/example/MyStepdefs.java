package org.example;

import Constantes.AtributosJson;
import Constantes.EndPoint;
import Constantes.Respostas;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.baseURI;


public class MyStepdefs  {
    private static ResponseOptions<Response> response;
    @Dado("que tenho o link do ApiCat")
    public void queTenhoOLinkDoApiCat() {
        baseURI = "https://api.thecatapi.com/v1";
    }

    @Quando("digito o link de uma imagem existente com o comando GET")
    public void digitoOLinkDeUmaImagemExistenteComOComandoGET() {
                get(baseURI+ EndPoint.images+ Respostas.idImagem);
    }

//    @Entao("terminal retorna validacoes de status e endereco da Api")
//    public void terminalRetornaValidacoesDeStatusEEnderecoDaApi() {
//        .then()
//                .log().all()
//                .statusCode(200)
//                .body(AtributosJson.url, is(Respostas.enderecoComIdImagem))
//        ;
//    }
}
