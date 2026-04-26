# Personal Finance API

REST API do zarządzania finansami osobistymi — użytkownicy, domy, kategorie i transakcje.

## Tech Stack

- Java 26 + Spring Boot
- MySQL
- Flyway
- Docker

## Uruchomienie

```bash
cp .env.example .env
docker compose up --build
```

Aplikacja dostępna pod: `http://localhost:8080`

---

## API Reference

### Users `/api/user`

| Method | Endpoint | Opis |
|--------|----------|------|
| `GET` | `/api/user` | Lista wszystkich użytkowników |
| `GET` | `/api/user/{id}` | Użytkownik po ID |
| `GET` | `/api/user/name/{name}` | Użytkownik po imieniu |
| `GET` | `/api/user/surname/{surname}` | Użytkownik po nazwisku |
| `GET` | `/api/user/house/{houseId}` | Użytkownicy przypisani do domu |
| `POST` | `/api/user` | Utwórz użytkownika |
| `POST` | `/api/user/assign` | Przypisz użytkownika do domu |
| `POST` | `/api/user/remove` | Usuń użytkownika z domu |
| `PATCH` | `/api/user/{id}` | Zaktualizuj użytkownika |
| `DELETE` | `/api/user/{id}` | Usuń użytkownika |

<details>
<summary>Przykłady requestów</summary>

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

**POST** `/api/user/assign` i `/api/user/remove`
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

| Method | Endpoint | Opis |
|--------|----------|------|
| `GET` | `/api/house/{id}` | Dom po ID |
| `GET` | `/api/house/street/{street}` | Domy po ulicy |
| `GET` | `/api/house/streetAndNumber/{street}/{number}` | Dom po ulicy i numerze |
| `GET` | `/api/house/owner/{ownerId}` | Dom po właścicielu |
| `POST` | `/api/house` | Utwórz dom |
| `DELETE` | `/api/house/{id}` | Usuń dom |

<details>
<summary>Przykłady requestów</summary>

**POST** `/api/house`
```json
{
  "street": "Dębowa",
  "number": "5B",
  "owner_id": 1
}
```
</details>

---

### Transactions `/api/transactions`

| Method | Endpoint | Opis |
|--------|----------|------|
| `GET` | `/api/transactions` | Lista wszystkich transakcji |
| `GET` | `/api/transactions/{id}` | Transakcja po ID |
| `GET` | `/api/transactions/search` | Wyszukaj transakcje (filtry) |
| `GET` | `/api/transactions/user/{userId}/summary` | Podsumowanie użytkownika |
| `GET` | `/api/transactions/house/{houseId}/summary` | Podsumowanie domu |
| `POST` | `/api/transactions` | Utwórz transakcję |
| `PATCH` | `/api/transactions/{id}` | Zaktualizuj transakcję |
| `DELETE` | `/api/transactions/{id}` | Usuń transakcję |

#### GET `/api/transactions/search` — parametry

| Parametr | Wymagany | Opis |
|----------|----------|------|
| `userId` | ✅ | ID użytkownika |
| `minAmount` | ❌ | Minimalna kwota |
| `maxAmount` | ❌ | Maksymalna kwota |
| `startDate` | ❌ | Data od (YYYY-MM-DD) |
| `endDate` | ❌ | Data do (YYYY-MM-DD) |
| `type` | ❌ | `INCOME` lub `EXPENSE` |
| `categoryId` | ❌ | ID kategorii |

<details>
<summary>Przykłady requestów i odpowiedzi</summary>

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

**GET** `/api/transactions/user/{userId}/summary` — odpowiedź
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

| Method | Endpoint | Opis |
|--------|----------|------|
| `GET` | `/api/category` | Lista wszystkich kategorii |
| `GET` | `/api/category/{id}` | Kategoria po ID |
| `POST` | `/api/category/name/{name}` | Utwórz kategorię |
| `DELETE` | `/api/category/{id}` | Usuń kategorię |

---

## Status Codes

| Kod | Znaczenie |
|-----|-----------|
| `200 OK` | Sukces |
| `201 Created` | Zasób utworzony |
| `204 No Content` | Zasób usunięty |
| `400 Bad Request` | Błędne dane wejściowe |
| `404 Not Found` | Zasób nie istnieje |