# Microservicio Loans

microservicio para los prestamos del sistema

## Descripción

Loans es el microservicio que se usara para estar comprobando
si los libros estan disponibles o no.
Este conecta con el microservicio de book mediante una conexion 
de RestTemplate, ya que FeignClient ya esta deprecated
y actualmente para esto se usa la version mas nueva en spring boot


## Requisitos

- Crear un @Bean para checar el estatus del libro
- Marcar el @Bean con Scope de tipo prototype para la creacion 
de una nueva instancia cada que se llame
- Inyectar un servicio de book en loans con Feign para el estatus de este... esto se hizo con RestTempalte debido a que Feign esta deprecated

## Tecnologías
- Spring Boot
- Spring Data JPA
- Spring Web
- H2 Database (para almacenamiento en memoria o persistente)
- Maven
