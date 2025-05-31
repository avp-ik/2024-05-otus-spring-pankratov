# Document Maker Service
- Сервис генерации печатных форма в формате PDF и сохранения в файловое хранилище.

## Используемые технологии
### Общие для всех сервисов
- Java 17
- Spring Boot
- Spring MVC
- OpenAPI
- Actuator
### Auth-gateway-service
- Spring Security
- Spring JPA
- Feign
- Resilience4J Circuit Breaker
### Document-maker-service
- Spring Integration
### Document-printer-service
- Thymeleaf
- Flying Saucer
- Feign
### Document-store-service
- Minio

## Используемые компоненты
### play.min.io (Cloud Service)
- система хранения файлов, поддерживающая протокол S3
### PostgreSQL
- база данных с открытым исходным кодом
### Docker и Docker Compose
- инструменты контейнеризации приложений

## Диаграмма компонентов
```plantuml
@startuml

skinparam component {
  backgroundColor<<Plan>> Pink
}

"Client System" as cs
[auth-gateway-service] as ags

database "PostgreSql" {
  [db_auth] as auth
}

[document-maker-service] as dms
[document-printer-service] as dps
[sign-service] <<Plan>> as ss
[whatermark-service] <<Plan>> as ws
[document-store-service] as dss

cloud {
  [play.min.io] as minio
}

cs -right-> ags : POST /auth/sign-up\lPOST /auth/sign-in\l\lPOST /api/v1/docs\lGET /api/v1/docs/{id}
ags .right.> auth

ags -down-> dms : POST /api/v1/docs\lGET /api/v1/docs/{id}

dms -down-> dps : POST /api/v1/printedDocs\lGET /api/v1/printedDocs/{id}
dms -down-> ss : POST /api/v1/signedDocs\lGET /api/v1/signedDocs/{id}
dms -down-> ws : POST /api/v1/changedDocs\lGET /api/v1/changedDocs/{id}
dms -down-> dss : GET /api/v1/docs/{id}


dps --> dss : POST /api/v1/docs
ws --> dss : POST /api/v1/docs
ss --> dss : POST /api/v1/docs

dss --> minio : S3 ( based on REST API )

@enduml
```
