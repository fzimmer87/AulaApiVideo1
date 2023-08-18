# language: pt
  Funcionalidade: Automatizar ApiCat com RestAssured

  @CT001
  Cenario: Deve retornar imagem específica
    Dado que tenho o link do ApiCat
    Quando digito o link de uma imagem existente com o comando GET
    Entao terminal retorna validacoes de status e endereco da Api
