# service--cotacao-dolar

API para proporcionar uma interface para obtenção de cotações do dólar comercial em datas específicas.

## Documentação
- [Arquitetura](docs/architecture.md)
- [Especificações](docs/specifications.md)
- [Guia de Instalação](docs/installation_guide.md)

## Funcionalidades

1. **API de Cotação do Dólar:** Permite ao usuário buscar a cotação do dólar comercial de uma data específica informada ou listar as que estão armazenadas na base de dados.

2. **Gravação em Banco de Dados MongoDB:** Armazena detalhes de cada requisição e da cotação correspondente.

3. **Consulta em Cache com Redis:** Antes de buscar no banco de dados ou na API externa, o sistema verifica no cache Redis.

## Tecnologias Utilizadas

- Docker
- Java 17
- MongoDB
- Redis

## Documentação
A aplicação está documentada via swagger, podendo ser acessada em:

#### Remotamente
A api está publicada em:
- https://service--cotacao-dolar.herokuapp.com/swagger-ui/index.html


#### Local
- http://localhost:8080/swagger-ui.html
