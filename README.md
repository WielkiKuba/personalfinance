# 📘 Personal Finance API

REST API do zarządzania użytkownikami, domami, kategoriami i transakcjami finansowymi.

---

# 🚀 Base URL
/api

---

# 🧑 USER API

## ➕ Create user
POST /api/user

### Body:
```json
{
  "name": "Jan",
  "surname": "Kowalski"
}
```

---

## ✏️ Update user
PATCH /api/user/{id}

### Body:
```json
{
  "name": "Jan",
  "surname": "Nowak",
  "houseId": 1
}
```

---

## 🏠 Assign user to house
POST /api/user/assign

### Body:
```json
{
  "userId": 1,
  "houseId": 2,
  "userToModifyId": 3
}
```

---

## 🏠 Remove user from house
POST /api/user/remove

### Body:
```json
{
  "userId": 1,
  "houseId": 2,
  "userToModifyId": 3
}
```

---

## 📄 Get all users
GET /api/user

---

## 🔍 Get user by id
GET /api/user/{id}

---

## 🔍 Get user by name
GET /api/user/name/{name}

---

## 🔍 Get user by surname
GET /api/user/surname/{surname}

---

## ❌ Delete user
DELETE /api/user/{id}

---

# 🏡 HOUSE API

## ➕ Create house
POST /api/house

### Body:
```json
{
  "street": "Dębowa",
  "number": "5B",
  "owner_id": 1
}
```

---

## 🔍 Get house by id
GET /api/house/{id}

---

## 🔍 Get house by street
GET /api/house/street/{street}

---

## 🔍 Get house by street and number
GET /api/house/streetAndNumber/{street}/{number}

---

## 🔍 Get house by owner
GET /api/house/owner/{ownerId}

---

## ❌ Delete house
DELETE /api/house/{id}

---

# 💰 TRANSACTION API

## ➕ Create transaction
POST /api/transactions

### Body:
```json
{
  "amount": 100.50,
  "date": "2026-04-26",
  "transactionType": "INCOME",
  "categoryId": 1,
  "userId": 2
}
```

---

## ✏️ Update transaction
PATCH /api/transactions/{id}

### Body:
```json
{
  "transactionType": "EXPENSE",
  "date": "2026-04-26",
  "categoryId": 1,
  "userId": 2,
  "amount": 200.00
}
```

---

## 🔎 Search transactions
GET /api/transactions/search

Query params:
- userId (required)
- minAmount
- maxAmount
- startDate (YYYY-MM-DD)
- endDate (YYYY-MM-DD)
- type (INCOME / EXPENSE)
- categoryId

---

## 📄 Get all transactions
GET /api/transactions

---

## 🔍 Get transaction by id
GET /api/transactions/{id}

---

## 🧾 Summary by house
GET /api/transactions/house/{houseId}/summary

---

## 👤 Summary by user
GET /api/transactions/user/{userId}/summary

### Response:
```json
{
  "totalIncome": 5000,
  "totalOutcome": 2000,
  "balance": 3000
}
```

---

## ❌ Delete transaction
DELETE /api/transactions/{id}

---

# 🏷 CATEGORY API

## 📄 Get all categories
GET /api/category

---

## 🔍 Get category by id
GET /api/category/{id}

---

## ➕ Create category
POST /api/category/name/{name}

---

## ❌ Delete category
DELETE /api/category/{id}

---

# 📦 DTO STRUCTURE

## CreateUserRequest
```json
{
  "name": "string",
  "surname": "string"
}
```

## UpdateUserRequest
```json
{
  "name": "string",
  "surname": "string",
  "houseId": "number"
}
```

## HouseOperationRequest
```json
{
  "userId": "number",
  "userToModifyId": "number",
  "houseId": "number"
}
```

## CreateHouseRequest
```json
{
  "street": "string",
  "number": "string",
  "owner_id": "number"
}
```

## CreateTransactionRequest
```json
{
  "amount": "number",
  "date": "YYYY-MM-DD",
  "transactionType": "INCOME | EXPENSE",
  "categoryId": "number",
  "userId": "number"
}
```

## UpdateTransactionRequest
```json
{
  "transactionType": "INCOME | EXPENSE",
  "date": "YYYY-MM-DD",
  "categoryId": "number",
  "userId": "number",
  "amount": "number"
}
```

---

# 🧠 ENUMS

TransactionType:
- INCOME
- EXPENSE

---

# ⚠️ STATUS CODES

- 200 OK
- 201 CREATED
- 204 NO CONTENT
- 400 BAD REQUEST
- 404 NOT FOUND
```
