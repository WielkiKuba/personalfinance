# Personal Finance API Documentation

RESTful API for managing personal finances, including users, shared houses, transactions, and invitations.

## Base URL
`/api`

---

## 1. Transactions API
**Endpoint:** `/api/transactions`

| Method | Path | Description | Request Body / Params | Response |
|---|---|---|---|---|
| `POST` | `/` | Create a new transaction | `CreateTransactionRequest` | `201 Created` (`Transaction`) |
| `GET` | `/` | Get all transactions | - | `200 OK` (`List<Transaction>`) |
| `GET` | `/{id}` | Get transaction by ID | - | `200 OK` (`Transaction`) |
| `GET` | `/summary/house/{houseId}` | Get transaction summary for a house | - | `200 OK` (`List<Transaction>`) |
| `GET` | `/summary/user/{userId}` | Get transaction summary for a user | - | `200 OK` (`UserTransactionSummary`) |
| `GET` | `/search` | Search transactions via filters | `userId` (req), `minAmount`, `maxAmount`, `startDate`, `endDate`, `type`, `transactionCategory` | `200 OK` (`List<Transaction>`) |
| `PATCH`| `/{id}` | Modify existing transaction | `UpdateTransactionRequest` | `200 OK` (`Transaction`) |
| `DELETE`| `/{id}` | Delete a transaction | - | `204 No Content` |

---

## 2. House API
**Endpoint:** `/api/house`

| Method | Path | Description | Request Body / Params | Response |
|---|---|---|---|---|
| `POST` | `/` | Create a new house | `CreateHouseRequest` | `201 Created` (`House`) |
| `GET` | `/{id}` | Get house by ID | - | `200 OK` (`House`) |
| `GET` | `/street/{street}` | Get houses by street name | - | `200 OK` (`List<House>`) |
| `GET` | `/streetAndNumber/{street}/{number}`| Get house by street and number | - | `200 OK` (`House`) |
| `GET` | `/owner/{ownerId}` | Get house by owner ID | - | `200 OK` (`House`) |
| `DELETE`| `/{houseId}` | Delete a house | `X-Session-User-Id` (Header) | `204 No Content` |

---

## 3. Invite API
**Endpoint:** `/api/invite`

| Method | Path | Description | Request Body / Params | Response |
|---|---|---|---|---|
| `POST` | `/` | Create a new invite | `CreateInviteRequest` | `201 Created` (`Invite`) |
| `POST` | `/accept/{inviteId}` | Accept an invite | `Long sessionUserId` (Body) | `200 OK` (`UserDataResponse`) |
| `GET` | `/{id}` | Get invite by ID | - | `200 OK` (`Invite`) |
| `GET` | `/sender/{id}` | Get invites by sender ID | - | `200 OK` (`List<Invite>`) |
| `GET` | `/recipient/{id}` | Get invites by recipient ID | - | `200 OK` (`List<Invite>`) |
| `GET` | `/{senderId}/{recipientId}` | Get invite by sender and recipient | - | `200 OK` (`Invite`) |

---

## 4. User API
**Endpoint:** `/api/user`

| Method | Path | Description | Request Body / Params | Response |
|---|---|---|---|---|
| `POST` | `/` | Create a new user | `CreateUserRequest` | `201 Created` (`UserDataResponse`) |
| `POST` | `/assign` | Assign user to a house | `HouseOperationRequest` | `200 OK` (`UserDataResponse`) |
| `POST` | `/remove` | Remove user from a house | `HouseOperationRequest` | `200 OK` (`UserDataResponse`) |
| `GET` | `/` | Get all users | - | `200 OK` (`List<UserDataResponse>`) |
| `GET` | `/{id}` | Get user by ID | - | `200 OK` (`UserDataResponse`) |
| `GET` | `/house/{houseId}` | Get all users assigned to a house | - | `200 OK` (`List<UserDataResponse>`) |
| `GET` | `/name/{name}` | Get user by name | - | `200 OK` (`UserDataResponse`) |
| `GET` | `/surname/{surname}` | Get user by surname | - | `200 OK` (`UserDataResponse`) |
| `PATCH`| `/{id}` | Modify user details | `UpdateUserRequest` | `200 OK` (`UserDataResponse`) |
| `DELETE`| `/{id}` | Delete a user | - | `204 No Content` |

## Data Transfer Objects (DTOs) Reference

### `CreateTransactionRequest`
- `userId`: `Long`
- `amount`: `BigDecimal`
- `date`: `LocalDate`
- `transactionType`: `TransactionType`
- `transactionCategory`: `TransactionCategory`

### `HouseOperationRequest`
- `userId`: `Long`
- `houseId`: `Long`
- `userToModifyId`: `Long`

*(Note: Data structures reflect payload expectations mapped directly from API controller signatures).*