# Projeto de delivery

## Rodando o projeto localmente

### Pré-Requisito

* Java 8 <http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html>
* MySql <https://dev.mysql.com/doc/mysql-installation-excerpt/5.7/en/>
* Maven <https://maven.apache.org/install.html>

### Configurando o projeto

Para rodar o projeto localmente, será necessário passar os dados de usuário e senha referentes ao seu banco de dados MySql local. Abra o arquivo `src/main/resources/application-dev.properties` e passe estes dados nos atributos `spring.datasource.username` e `spring.datasource.password`:

```
spring.datasource.username=<usuario mysql>
spring.datasource.password=<senha mysql>
```

### Rodando o projeto

Para rodar o projeto pelo terminal, basta rodar o comando `mvn spring-boot:run` na pasta raiz do projeto.

Se estiver usando alguma IDE, você também pode subir o projeto executando a classe `com.zxventures.zedelivery.ZedeliveryApplication`.

## Deploy

O deploy deste sistema é feito automaticamente pelo servidor de integração contínua GoCD que se encontra no endereço <http://18.191.241.253:8153/go/pipelines>

## Produção

O projeto está rodando em produção no endereço <http://18.191.138.197:8080>
