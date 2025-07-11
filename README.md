# **x-stream-scout** 
*A collection of event-driven microservices that stream, store, index, and search tweets using Kafka, Elasticsearch, and a relational database.*  

**Overview**  
**x-stream-scout** is a **distributed event-driven microservices project** designed to:  

- Stream tweets from Kafka (published by x-stream-simulator)  
- Store tweets in Elasticsearch for full-text search  
- Persist tweet metadata in PostgreSQL for analytics  
- Search & display tweets via a web-based UI  
- Ensure scalability & observability using Spring Boot, Kafka, Elasticsearch, Prometheus, Grafana, Zipkin, and Keycloak  

---

## **Architecture**  
The project consists of multiple microservices:  

| Microservice               | Description |
|---------------------------|-------------|
| **twitter-to-kafka-service** | Fetches tweets and publishes them to Kafka |
| **kafka-to-elastic-service** | Consumes Kafka messages and indexes them into Elasticsearch |
| **query-service** | Exposes APIs to search tweets from Elasticsearch |
| **analytics-service** | Processes tweets and stores analytical data in PostgreSQL |
| **kafka-streams-service** | Performs real-time tweet processing |
| **web-client** | Displays tweets via a frontend UI |
| **config-server** | Centralized configuration using Spring Cloud Config |
| **api-gateway** | Routes API calls using Spring Cloud Gateway |
| **discovery-service** | Manages service discovery (if needed) |
| **authz-server** | Authentication & authorization using Keycloak |
| **Monitoring & Logging** | Includes Prometheus, Grafana, Zipkin, Logstash, and Kibana |

---

## **Tech Stack**
- **Backend:** Spring Boot, Spring Cloud, Kafka, Elasticsearch, PostgreSQL  
- **Event Processing:** Kafka Streams  
- **Security:** Keycloak  
- **Monitoring & Logging:** Prometheus, Grafana, Zipkin, Logstash, Kibana  
- **Deployment:** Docker, Kubernetes  

---
