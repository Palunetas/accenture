# Proyecto Libreria

Con base a los objetivos se hizo este proyecto

## Descripción

El proyecto se creo utilizando Multimodulo
donde se distribuye la infraestructura de la logica de negocios.
Se le agrego Eureka y ApiGateway para complementar el proyecto, se agregaron
Test Cases para los microservicios de User y Books, se hicieron
las configuraciones correspondientes de Eureka y ApiGateway 
para todos los microservicios.

Los Microservicios Book y Usuario contienen script de sql
para agregar datos a las bases de datos y lograra las pruebas unitarias

El proyecto es ejecutable y funcional

## Tecnologías

- Spring Boot
- Spring Cloud
- Eureka
- ApiGateway
- MultiModule
- Api RESTFul
- Spring Data JPA
- Spring Web
- H2 Database (para almacenamiento en memoria o persistente)
- Maven


## Configuración

se ejecuta primero Eureka luego ApiGateway
posterior se pueden ejecutar la logica de negocios
en cualquier orden
