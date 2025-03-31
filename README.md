# Spring Boot - API Credit Card

Esta es una aplicación Java / Maven / Spring Boot (version 1.0.0) para almacenamiento y consulta de números de tarjetas de crédito, usando para los datos sensibles Cifrado de Datos en Reposo, Tokenización y Enmascaramiento. Adicional usa Spring Security para la autenticación, autorización y accesos a los endPoints.

### Ejecutar la aplicacion localmente

* Clona el repositorio
* Asegurate de usar JDK 17 o superior y Maven 3.x
* Hay algunas maneras de ejecutar la aplicacion Spring Boot localmente una manera es ejecutar el metodo `main` de com.bci.ApiCreditcardMsApplication desde un IDE.
* Alternativamente puedes usar [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) de esta manera

```
mvn spring-boot:run
```

* Tambien puedes ejecutarlo con el comando java directamente
```
java -jar target/api-creditcard-ms-1.0.0-SNAPSHOT.jar
```

Una vez que la aplicacion se ejecuta visualizaras algo asi:

```
2025-03-30T17:36:44.247-05:00 INFO [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path '/api/v1'
2025-03-30T17:36:44.756-05:00 INFO [           main] com.bci.ApiCreditcardMsApplication       : Started ApiCreditcardMsApplication in 2.276 seconds (process running for 2.587)
```

### Creación de las tablas en Base H2

Los scripts de creacion de las tablas se pueden visualizar en la consola

```
Hibernate: create table credit_card (id bigint generated by default as identity, encrypted_card varchar(255), token varchar(255), primary key (id))
Hibernate: create table role (id bigint generated by default as identity, name varchar(255), primary key (id))
Hibernate: create table user_roles (role_id bigint not null, user_id bigint not null, primary key (role_id, user_id))
Hibernate: create table users (id bigint generated by default as identity, password varchar(255), username varchar(255), primary key (id))
Hibernate: alter table if exists user_roles add constraint FKrhfovtciq1l558cw6udg0h0d3 foreign key (role_id) references role
Hibernate: alter table if exists user_roles add constraint FKhfh9dx7w3ubf1co1vdev94g3f foreign key (user_id) references users
```

### Pruebas Unitarias con JUnit y Mockito

El Test de Pruebas Unitarias esta localizado en com.bci.ApiCreditcardMsApplicationTest

![Screenshot 2025-03-30 at 5 56 49 PM](https://github.com/user-attachments/assets/4b29b315-c5d1-4a9b-9b3c-c64a97e552ff)


### Registro de usuario con rol ADMIN - Response: HTTP 201 (Created)

```
curl -X 'POST' \
  'http://localhost:8080/api/v1/auth/register' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "username": "admin",
  "password": "admin123",
  "role": "ADMIN"
}'
```

### Registro de usuario con rol USER - Response: HTTP 201 (Created)

```
curl -X 'POST' \
  'http://localhost:8080/api/v1/auth/register' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "username": "xtrivino",
  "password": "xtrivino123",
  "role": "USER"
}'
```

### Login de usuario con rol ADMIN - Response: HTTP 200 (OK)

```
curl -X 'POST' \
  'http://localhost:8080/api/v1/auth/login' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "username": "admin",
  "password": "admin123"
}'
```

### Login de usuario con rol USER - Response: HTTP 200 (OK)

```
curl -X 'POST' \
  'http://localhost:8080/api/v1/auth/login' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "username": "xtrivino",
  "password": "xtrivino123"
}'
```

### Almacenar Tarjeta de Credito con Token de ADMIN - Response: HTTP 201 (Created)

```
curl -X 'POST' \
  'http://localhost:8080/api/v1/creditcard/store' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc0MzQyMTI1NCwiZXhwIjoxNzQzNTA3NjU0fQ.INQrjChKCHv7KOoXM6J7nVe_RFG5CpuUCH4GMJrFGOU' \
  -H 'Content-Type: application/json' \
  -d '{
  "cardNumber": "4111111111111111"
}'
```

### Almacenar Tarjeta de Crédito con Token de USER - Response: HTTP 403 (Forbidden)

```
curl -X 'POST' \
  'http://localhost:8080/api/v1/creditcard/store' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ4dHJpdmlubyIsImlhdCI6MTc0MzQyMTMxNCwiZXhwIjoxNzQzNTA3NzE0fQ.11jJyK-ZWAPbU4uLd-lcTC8SOIKKsA9UjySpBChLF8U' \
  -H 'Content-Type: application/json' \
  -d '{
  "cardNumber": "4111111111111111"
}'
```

### Consulta de tarjeta de Crédito con Token ADMIN - Response: HTTP 403 (Forbidden)

```
curl -X 'GET' \
  'http://localhost:8080/api/v1/creditcard/get/ae33ddd658ab4ce2' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc0MzM4OTU5NCwiZXhwIjoxNzQzNDc1OTk0fQ.Rz3qM_aJpp0JdUrJS_fQYmuedhRv4ubDzEuB_Z8KqIk'
```

### Consulta de tarjeta de Crédito con Token USER - Response: HTTP 200 (OK)

```
curl -X 'GET' \
  'http://localhost:8080/api/v1/creditcard/get/ae33ddd658ab4ce2' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ4dHJpdmlubyIsImlhdCI6MTc0MzM4OTgyOSwiZXhwIjoxNzQzNDc2MjI5fQ.XXI81fZigEmbpkMDlssiAY6MoO8fkKIj9-wwOsbQZOg'
```

### Para visualizar Swagger 2 API docs

Ejecutar en el navegador http://localhost:8080/api/v1/swagger-ui/index.html

![Screenshot 2025-03-30 at 10 07 23 PM](https://github.com/user-attachments/assets/c5015c98-c486-4ce8-a8c4-4cd3fc36be15)


### Para visualizar la base H2 en memoria

Ejecutar en el navegador http://localhost:8080/api/v1/h2-console/, usuario default es 'sa' con password en blanco.

![Screenshot 2025-03-30 at 10 08 36 PM](https://github.com/user-attachments/assets/5fc0c900-1e3f-477d-bd7f-a4c064843e41)


### Preguntas y comentarios: fxtrivino@gmail.com
