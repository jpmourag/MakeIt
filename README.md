# MakeIt
Foi utilizado o seguinte Java tanto no front quanto no backend/api [Java 17 Zulu](https://www.azul.com/downloads-new/?version=java-17-lts&os=windows&architecture=x86-64-bit&package=jdk#zulu)

O frontend foi feito no [Apache NetBeans 17](https://netbeans.apache.org/download/nb17/)

O backend/api foi feito no [IntelliJ IDEA Community Edition v2022.3.2](https://www.jetbrains.com/idea/download/#section=windows)

Para o server do frontend foi utilizado [Apache Tomcat 8.5](https://tomcat.apache.org/download-80.cgi)

O backend foi feito com SpringBoot então utiliza o próprio Spring
> O back e o front utilizaram **Lombok** então ele precisa estar ativo

</br>

# Execução
## No frontend

O front end possui duas variáveis de ambiente, o host e a porta da chamada da api.

```sh
TODO_SERVER_HOST
TODO_SERVER_PORT
```
por padrão ele vai chamar a porta 3333 e o host 127.0.0.1 também conhecido como localhost

Para inicar deve-ser apertar o icone do martelo com vassoura, clean and build e então apertar run na aplicação, quando perdir o server, é melhor colocar o [Apache Tomcat 8.5](https://tomcat.apache.org/download-80.cgi)

A outra opção é chamar executar o seguinte comando no terminar na raiz do projeto
```
mvn clean compile
```
e então pegar o arquivo .war para fazer o deploy em algum server como o [Apache Tomcat 8.5](https://tomcat.apache.org/download-80.cgi)

## No backend
Primeiramente é necessário ter o banco de dados rodando, no caso a aplicação usa o Postgres. Então eu recomendo usar o docker tendo em vista que o projeto já vem com um docker-compose pronto pra executar e rodar o Postgres
Depois de configurar e instânciar o Postgres, é necessário adicionar os seguinte parâmetros no arquivo **application.properties** dentro da parta **resources** da api
```yaml
server.port= # porta do server, recomendo usar 3333, pois o frontend vai chamar 3333 por padrão, mas pode usar env aqui também

spring.datasource.url= #link de conexão com postgres exemplo: jdbc:postgresql://localhost:5432/postgres

spring.datasource.username= # nome do user do Postgres

spring.datasource.password=postgres= # senha do user do Postgres

spring.datasource.driver-class-name=org.postgresql.Driver
#driver do porgress

jwt.secret= # private key da auth jwt


######
# o comando spring.jpa.hibernate.ddl-auto=create pode ser legal de usar na primeira vez que for rodar, pois ele criar todas as tabelas do banco de dados, mas não esquecer de tirar logo em seguida
######
```
Assim como o frontend pd executar o backend de duas formas, primeiramente abrindo ele no [IntelliJ](https://www.jetbrains.com/idea/download/#section=windows) e buildando ele por lá, ou pd apenas executar o seguinte comando no terminal
```sh
mvn spring-boot:run
```
também é possível gerar o arquivo jar e execular ele pelo java no terminal
> Lembre-se o **Lombok** precisa estar ativo


# Funcionalidade do projeto
> Autentificação JWT

> LazyLoad Pagination

> Pesquisa com Lazyload Pagination

> Filtro por tarefa concluída ou não concluída, ou todas sendo tanto em geral, todas as tarefas, como sendo específico, dentro de uma pasta.

> Pastas, onde cada pasta pode ter várias tarefas

> Toast notify

> E todas as funcionalidades básicas de CRUD para todas as entidades, como criar, ler, atualizar e deletar.