# language: pt
  Funcionalidade: Automatizar ApiCat com RestAssured

  @CT001
  Cenario: Deve retornar imagem específica
    Dado que tenho o link do ApiCat
    Quando digito o link de uma imagem existente com o comando GET
    Entao terminal retorna validacoes de status e endereco da Api

  @CT002
  Cenario: Deve retornar 10 imagens
    Dado que desejo ver 10 imagens do ApiCat
    Quando solicito no campo search a busca de imagem limitada a 10
    Entao o sistema me retorna apenas 10 imagens

  @CT003
   Cenario: Realizar um voto
    Dado que desejo realizar um voto em uma imagem do ApiCat
    Quando realizo o voto
    Entao sistema me retorna mensagem SUCCESS

  @CT004
   Cenario: Excluir voto realizado
    Dado que desejo excluir um voto realizado
    Quando excluo o voto
    Entao sistema me retorna mensagem SUCCESS

