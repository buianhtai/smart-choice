# Components structures
1. Common
>Structure 
```sh
└── com
    └── nab
        ├── constant : All constants values that can be used in the services
        ├── domain : Object that can be used in the services 
        │   ├── external : 
        │   └── product
        │       ├── model : Product model and Product AggreeGate
        │       └── repository
        ├── enums: Common Enumerate
        ├── event: DDD Events & base handler used in `publiser` and subsciber
        ├── exceptions: Common exception
        ├── filter: Centralize Filter request can be use
        ├── http:
        ├── mapper: Base mapper 
        └── shared : Common class can be shared 
```
- Mock-services
>Mock partner service such as *lazada, shopee, tiki*  
- Services  
1. Event Service
>Handle Events from Kafka.In this example every event will be store in `MongoDB` for late usage.Each `event` will have `event-handler` class accordingly.Below is event class diagram.  

>Event class diagram

![Event Handler diagram](assets/images/Event_Handler.png)
Event service used Spring Cloud stream to simplyfiez the process
```yml
  spring:
  application.name: event-service
  kafka:
    consumer:
      properties:
        spring.json.trusted.packages: "*"
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
  cloud:
    function.definition: consumeEvent
    stream:
      bindings:
        consumeEvent-in-0.destination: ${app.events.topic}
        consumeEvent-in-0.group: eventGroup
```
2.Product Composite service

![Product Composite diagram](assets/images/Product_Diagram.png)



- Spring Cloud  
--- Gateway  
>To comunicate the services and hide the outside.The configuration as below
```yml
spring.cloud.gateway.routes:
  - id: product-composite
    uri: http://product-composite
    predicates:
      - Path=/api/v1/products/**

  - id: oauth-server
    uri: http://${app.auth-server}
    predicates:
      - Path=/oauth/**
```

--- Authorization-Server
>OAuth 2.0 Authorization Server.
# Software usage
- Spring Boot
- Spring Reactive Webflux
>Spring Framework uses Project Reactor as the base implementation of its reactive support, and also comes with a new web framework, Spring WebFlux, which supports the development of reactive, that is, non-blocking, HTTP clients and services.
- Spring Cloud Stream Kafka Binder
>Spring Cloud Stream comes with opinionated conventions on how to handle messaging
- Docker
>Deploying Our Microservices Using Docker

# Running the microservices

To run the server, do:

```bash
./run.sh
```
or run step by step commands
```
./gradlew build
docker-compose build
docker-compose up
```
> It will take a long time as downloading docker images file 
> **zookeeper,kafka,mongdodb,openjdk:12.0.2**
> Running unit tests on product-composite-service 
> Create microservices images .. Below is some logs from the command (run.sh)
````sh
Status: Downloaded newer image for confluentinc/cp-zookeeper:latest
Status: Downloaded newer image for mongo:3.6.9
Creating mongodb     ... done
Creating tiki        ... done
Creating zookeeper   ... done
Creating shopee      ... done
Creating gateway     ... done
Creating auth-server ... done
Creating lazada      ... done
Creating kafka       ... done
Creating event-service     ... done
Creating product-composite ... done
Attaching to tiki, lazada, mongodb, shopee, auth-server, gateway, zookeeper, kafka, product-composite, event-service
event-service        |  value.deserializer = class org.springframework.kafka.support.serializer.JsonDeserializer
event-service        | 
event-service        | 2021-01-25 15:09:50.887  INFO 1 --- [pool-3-thread-1] o.a.kafka.common.utils.AppInfoParser     : Kafka version: 2.6.0
event-service        | 2021-01-25 15:09:50.887  INFO 1 --- [pool-3-thread-1] o.a.kafka.common.utils.AppInfoParser     : Kafka commitId: 62abe01bee039651
event-service        | 2021-01-25 15:09:50.887  INFO 1 --- [pool-3-thread-1] o.a.kafka.common.utils.AppInfoParser     : Kafka startTimeMs: 1611587390887
event-service        | 2021-01-25 15:09:50.898  INFO 1 --- [pool-3-thread-1] org.apache.kafka.clients.Metadata        : [Consumer clientId=consumer-eventGroup-3, groupId=eventGroup] Cluster ID: WM2Fm9RZSx-OkgCInbcWTw
event-service        | 2021-01-25 15:09:50.922  INFO 1 --- [pool-3-thread-1] o.a.k.c.c.internals.AbstractCoordinator  : [Consumer clientId=consumer-eventGroup-3, groupId=eventGroup] Discovered group coordinator kafka:29092 (id: 2147483646 rack: null)
event-service        | 2021-01-25 15:09:50.934  INFO 1 --- [pool-3-thread-1] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-eventGroup-3, groupId=eventGroup] Found no committed offset for partition event-0
mongodb              | 2021-01-25T15:10:04.898+0000 I NETWORK  [listener] connection accepted from 127.0.0.1:60810 #5 (3 connections now open)
mongodb              | 2021-01-25T15:10:04.899+0000 I NETWORK  [conn5] received client metadata from 127.0.0.1:60810 conn5: { application: { name: "MongoDB Shell" }, driver: { name: "MongoDB Internal Client", version: "3.6.9" }, os: { type: "Linux", name: "PRETTY_NAME="Debian GNU/Linux 9 (stretch)"", architecture: "x86_64", version: "Kernel 4.19.121-linuxkit" } }
mongodb              | 2021-01-25T15:10:04.904+0000 I NETWORK  [conn5] end connection 127.0.0.1:60810 (2 connections now open)


````
# How to used

1. First get accessToken to perform the `Search` API

Once it is up, this request asks for a token with the "product:read" scope:

```bash
curl -k https://writer:secret@localhost:8443/oauth/token -d grant_type=password -d username=customer -d password=password -s | jq .
```

Which will respond with something like:

```sh
{
  "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJjdXN0b21lciIsImV4cCI6MjIxMTIxODA2MSwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImp0aSI6IldkLzVjTFo0NlFyUUtDUDhoQzl1T1BSbXV5OD0iLCJjbGllbnRfaWQiOiJ3cml0ZXIiLCJzY29wZSI6WyJwcm9kdWN0OnJlYWQiLCJwcm9kdWN0OndyaXRlIl19.GxuVD0ZRL15n9lyZKRIf-npfR-c2JVYaOJ6BaEKF4h3Nmr2aZJAgnPbYCHzoelWNCcfS4T0Z54i_VPPRHtRDrMEaIdIOi9Bv4sLbYbpEHtcom4XRg_0XHqwlLqbxa4-sjX_eXntFHLL46K05jttifEpJrWmG5Fk5ZKIMKPZmvXbLC-T1wsYSbBKCMmpUv2fUCsy_yTGnmFrwGeeGC_5MuniZm3dfjRvvz19doe2XYjkf5jV3KU_4jnacNE-8N-L40gWKlXwUEakAIFdeVAFWp58KJ2soekdebTQeO1GDzb0rahtw53H2Ib_Znd2Z36Tujz-E_uhd2ztwFNo3JqJUiQ",
  "token_type": "bearer",
  "expires_in": 599999999,
  "scope": "product:read product:write",
  "jti": "Wd/5cLZ46QrQKCP8hC9uOPRmuy8="
}
```

User noscope will get error 403 forbitden

```bash
curl -k https://noscopes:secret@localhost:8443/oauth/token -d grant_type=password -d username=customer -d password=password -s | jq .
```
2. Call the search to get product API
> The endpoint https://localhost:8443/api/v1/products used gateway to `hide` the services behind the outside.
> The gateway call the `product-composite-service` which comunicate with 3 external services(mock-services ) (tiki,lazada,shopee) to get the products information. Each external service has difference `structure` response so the system has to `convert` to `standard` response.

- Sample curl
```bash
curl --location --request GET 'https://localhost:8443/api/v1/products?query=iphone%20XS%20MAX&sort=ASC' \
--header 'X-Request-ID: 84f240d1-cb45-410a-9661-b3d0f6dcab5d' \
--header 'Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJjdXN0b21lciIsImV4cCI6MjIxMTIxODU1NSwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImp0aSI6InZFSGlhUVRDbXRocWg3dHRubTE3d1MrS3Rpbz0iLCJjbGllbnRfaWQiOiJ3cml0ZXIiLCJzY29wZSI6WyJwcm9kdWN0OnJlYWQiLCJwcm9kdWN0OndyaXRlIl19.RMhYgu17NSf7V4xAfyRYATzzTx_9C6licrsGb6TW2it7d162hPOGoY9KVea8GyK8-aNqPLBQGd-RO-5Xk6msFgpFmr-PF94F68z15lXLyGmx56njNgy2WgZroN6VAjfdX8DA7iR_w1Wh-bMyqmvDGe2gZK7vud6vUddtDCbzZNZQf0J5xdXzQ0U-rlUUolDQKZvr5NTp5Y20n41fgpVjD1JrJbNYbYm1cPhe5e2_lEy5Iy6lUi79XlIgqHBSb_ZUbNOf-PrtX7c_-8LWvTKkXRNzjDlxnfjZZwq1EB8Nniteu5F9UKD0E5d4sohCko_eALzkHGe87Q8Q-LgLgNgbdQ'
```

- Sample Response
```json
{
    "data": {
        "products": [
            {
                "code": "867863548187882",
                "name": "iphone XS MAX",
                "description": "Small Wooden Bag",
                "price": 24.22,
                "image": "http://lorempixel.com/g/1600/1200/abstract/",
                "discountRate": 74.7112948124,
                "promotion": "StellarPrice567686",
                "category": "21F57C00",
                "brand": "Cty TNHH Cường",
                "source": "SHOPEE"
            },
            {
                "code": "442200404330468",
                "name": "iphone XS MAX",
                "description": "Incredible Steel Wallet",
                "price": 50.15,
                "image": "http://lorempixel.com/g/1366/768/technics/",
                "discountRate": 26.0570119919,
                "promotion": "GreatPromotion056006",
                "category": "29FCB5BB",
                "brand": "Công ty Ngô",
                "source": "TIKI"
            },
            {
                "code": "864731274217665",
                "name": "iphone XS MAX",
                "description": "Heavy Duty Wool Table",
                "price": 51.22,
                "image": "http://lorempixel.com/1920/1200/city/",
                "discountRate": 52.7330135874,
                "promotion": "KillerCode520332",
                "category": "4CB0DC50",
                "brand": "Cty Trinh",
                "source": "LAZADA"
            }
        ]
    },
    "meta": {
        "code": "200"
    }
}
```
![Logs from the microservices](assets/images/Logs.png)



3. Query the user search history
```sh
   docker-compose exec mongodb mongo event-db --quiet --eval "db.events.find()"      
```
Sample Result
```sh
{ "_id" : ObjectId("600e9823ed6a8753c9c0526f"), "event" : { "userName" : "customer", "query" : "iphone XS MAX", "_class" : "com.nab.event.SearchEvent" }, "_class" : "com.nab.smartchoice.persistence.EventEntity" }
{ "_id" : ObjectId("600e9827ed6a8753c9c05270"), "event" : { "userName" : "customer", "query" : "iphone XS MAX", "_class" : "com.nab.event.SearchEvent" }, "_class" : "com.nab.smartchoice.persistence.EventEntity" }
{ "_id" : ObjectId("600e9829ed6a8753c9c05271"), "event" : { "userName" : "customer", "query" : "iphone XS MAX", "_class" : "com.nab.event.SearchEvent" }, "_class" : "com.nab.smartchoice.persistence.EventEntity" }
```

## TODO
- Apply *Kubernetes & Istio* as alternative deployment
- Apply Grafana, Prometheus and Kiali ... for tracing and monitoring to easilier debug and scale.
- Apply Grpc for internal call to get highly performant and scalable.
- Convert from Spring boot to Quarkus to enhance the performace 
(CPU & Memory usage).
- Do more testing & integration test on mock-service ,product-composite.
- Store search result in `external system` such as DB/Redis cached 
for better perfomance and reduce cost.