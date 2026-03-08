
# Invoice System API

Simple REST API for creating invoices, making payments, and processing overdue invoices.

## Run locally

mvn spring-boot:run

## Build jar

mvn clean package

## Run with Docker

docker-compose up --build

API will run at:

http://localhost:8080

## Endpoints

POST /invoices  
GET /invoices  
POST /invoices/{id}/payments  
POST /invoices/process-overdue
