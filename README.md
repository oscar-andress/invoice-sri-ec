# 📑 Invoice-Sri-Ec Microservice
![CI](https://github.com/oscar-andress/invoice-sri-ec/actions/workflows/ci.yml/badge.svg)
![CD](https://github.com/oscar-andress/invoice-sri-ec/actions/workflows/cd.yml/badge.svg)

Invoice Microservice microservice built with Spring Boot, designed as part of a microservices architecture and ready for CI/CD.

This service is responsible for issuing invoices in accordance with the rules imposed by SRI-Ecuador.

## 📌 Key Features

* Invoice issue

---

## 🔗 Available Endpoints

### 🔸 Invoice Issue

```http
POST /api/v1/invoice/issue
```

**Request example:**

```json
{
  "buyerIdentification": "0101010101",
  "buyerName": "Juan Perez",
  "buyerIdentificationType": "05",
  "buyerAddress": "Av. Amazonas y Naciones Unidas",
  "details": [
    {
      "description": "Software development service",
      "quantity": 1,
      "unitPrice": 100.00,
      "discount": 0.00,
      "taxCode": 2,
      "taxPercentageCode": 2,
      "taxPercentage": 12.00
    },
    {
      "description": "Monthly technical support",
      "quantity": 2,
      "unitPrice": 50.00,
      "discount": 0.00,
      "taxCode": 2,
      "taxPercentageCode": 2,
      "taxPercentage": 12.00
    }
  ]
}
```

**Response example:**

```json
{
  "idInvoice": 8,
  "accessKey": "0701202601100112233400110010010000000102170631811",
  "sequential": "000000010",
  "status": "ISSUED",
  "issueDate": "2026-01-07",
  "totalAmount": 224
}
```
---
## 🧪 Testing

Includes **unit and integration tests** for:

* Services

---

## 👤 Author

**Oscar Vega**
Backend Developer – Spring Boot | Microservices 
