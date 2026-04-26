# Personal Finance API

REST API for managing personal finances — users, houses, categories and transactions.

## Tech Stack

- Java 26 + Spring Boot
- MySQL
- Flyway
- Docker

## Getting Started

```bash
cp .env.example .env
docker compose up --build
```

App available at: `http://localhost:8080`

---

## API Reference

### Users `/api/user`

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/user` | Get all users |
| `GET` | `/api/user/{id}` | Get user by ID |
| `GET` | `/api/user/name/{name}` | Get user by name |
| `GET` | `/api/user/surname/{surname}` | Get user by surname |
| `GET` | `/api/user/house/{houseId}` | Get users assigned to a house |
| `POST` | `/api/user` | Create user |
| `POST` | `/api/user/assign` | Assign user to a house |
| `POST` | `/api/user/remove` | Remove user from a house |
| `PATCH` | `/api/user/{id}` | Update user |
| `DELETE` | `/api/user/{id}` | Delete user |

<details>
<summary>Request examples</summary>

**POST** `/api/user`
```json
{
  "name": "Jan",
  "surname": "Kowalski"
}
```

**PATCH** `/api/user/{id}`
```json
{
  "name": "Jan",
  "surname": "Nowak",
  "houseId": 1
}
```

**POST** `/api/user/assign` and `/api/user/remove`
```json
{
  "userId": 1,
  "houseId": 2,
  "userToModifyId": 3
}
```
</details>

---

### Houses `/api/house`

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/house/{id}` | Get house by ID |
| `GET` | `/api/house/street/{street}` | Get houses by street |
| `GET` | `/api/house/streetAndNumber/{street}/{number}` | Get house by street and number |
| `GET` | `/api/house/owner/{ownerId}` | Get house by owner |
| `POST` | `/api/house` | Create house |
| `DELETE` | `/api/house/{id}` | Delete house |

<details>
<summary>Request examples</summary>

**POST** `/api/house`
```json
{
  "street": "Oak Street",
  "number": "5B",
  "owner_id": 1
}
```
</details>

---

### Transactions `/api/transactions`

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/transactions` | Get all transactions |
| `GET` | `/api/transactions/{id}` | Get transaction by ID |
| `GET` | `/api/transactions/search` | Search transactions (filters) |
| `GET` | `/api/transactions/user/{userId}/summary` | Get user summary |
| `GET` | `/api/transactions/house/{houseId}/summary` | Get house summary |
| `POST` | `/api/transactions` | Create transaction |
| `PATCH` | `/api/transactions/{id}` | Update transaction |
| `DELETE` | `/api/transactions/{id}` | Delete transaction |

#### GET `/api/transactions/search` — query params

| Parameter | Required | Description |
|-----------|----------|-------------|
| `userId` | ✅ | User ID |
| `minAmount` | ❌ | Minimum amount |
| `maxAmount` | ❌ | Maximum amount |
| `startDate` | ❌ | Start date (YYYY-MM-DD) |
| `endDate` | ❌ | End date (YYYY-MM-DD) |
| `type` | ❌ | `INCOME` or `EXPENSE` |
| `categoryId` | ❌ | Category ID |

<details>
<summary>Request and response examples</summary>

**POST** `/api/transactions`
```json
{
  "amount": 100.50,
  "date": "2026-04-26",
  "transactionType": "INCOME",
  "categoryId": 1,
  "userId": 2
}
```

**PATCH** `/api/transactions/{id}`
```json
{
  "transactionType": "EXPENSE",
  "date": "2026-04-26",
  "amount": 200.00,
  "categoryId": 1,
  "userId": 2
}
```

**GET** `/api/transactions/user/{userId}/summary` — response
```json
{
  "totalIncome": 5000,
  "totalOutcome": 2000,
  "balance": 3000
}
```
</details>

---

### Categories `/api/category`

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/category` | Get all categories |
| `GET` | `/api/category/{id}` | Get category by ID |
| `POST` | `/api/category/name/{name}` | Create category |
| `DELETE` | `/api/category/{id}` | Delete category |

---

## Status Codes

| Code | Meaning |
|------|---------|
| `200 OK` | Success |
| `201 Created` | Resource created |
| `204 No Content` | Resource deleted |
| `400 Bad Request` | Invalid input |
| `404 Not Found` | Resource not found |