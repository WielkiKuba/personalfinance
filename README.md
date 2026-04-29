# Personal Finance API Documentation

RESTful API for managing personal finances, including users, shared houses, transactions, and invitations.

## Base URL
`/api`

---

## 1. Transactions API
**Endpoint:** `/api/transactions`

| Method | Path | Description | Request Body / Params | Response |
|---|---|---|---|---|
| `POST` | `/` | Create a new transaction | `CreateTransactionRequest` | `201 Created` (`TransactionDataResponse`) |
| `GET` | `/` | Get all transactions | — | `200 OK` (`List<TransactionDataResponse>`) |
| `GET` | `/{id}` | Get transaction by ID | — | `200 OK` (`TransactionDataResponse`) |
| `GET` | `/summary/house/{houseId}` | Get transactions for a house | — | `200 OK` (`List<TransactionDataResponse>`) |
| `GET` | `/summary/user/{userId}` | Get transaction summary for a user | — | `200 OK` (`UserTransactionSummary`) |
| `GET` | `/search` | Search transactions by filters | `userId` (req), `minAmount`, `maxAmount`, `startDate`, `endDate`, `type`, `transactionCategory` | `200 OK` (`List<TransactionDataResponse>`) |
| `PATCH` | `/{id}` | Modify existing transaction | `UpdateTransactionRequest` | `200 OK` (`TransactionDataResponse`) |
| `DELETE` | `/{id}` | Delete a transaction | — | `204 No Content` |

---

## 2. House API
**Endpoint:** `/api/house`

| Method | Path | Description | Request Body / Params | Response |
|---|---|---|---|---|
| `POST` | `/` | Create a new house | `CreateHouseRequest` | `201 Created` (`HouseDataResponse`) |
| `GET` | `/{id}` | Get house by ID | — | `200 OK` (`HouseDataResponse`) |
| `GET` | `/street/{street}` | Get houses by street name | — | `200 OK` (`List<HouseDataResponse>`) |
| `GET` | `/streetAndNumber/{street}/{number}` | Get house by street and number | — | `200 OK` (`HouseDataResponse`) |
| `GET` | `/owner/{ownerId}` | Get house by owner ID | — | `200 OK` (`HouseDataResponse`) |
| `DELETE` | `/{houseId}` | Delete a house | `X-Session-User-Id` (Header) | `204 No Content` |

---

## 3. Invite API
**Endpoint:** `/api/invite`

| Method | Path | Description | Request Body / Params | Response |
|---|---|---|---|---|
| `POST` | `/` | Create a new invite | `CreateInviteRequest` | `201 Created` (`InviteDataResponse`) |
| `POST` | `/accept/{inviteId}` | Accept an invite | `X-Session-User-Id` (Header) | `200 OK` (`UserDataResponse`) |
| `GET` | `/{id}` | Get invite by ID | — | `200 OK` (`InviteDataResponse`) |
| `GET` | `/sender/{id}` | Get invites by sender ID | — | `200 OK` (`List<InviteDataResponse>`) |
| `GET` | `/recipient/{id}` | Get invites by recipient ID | — | `200 OK` (`List<InviteDataResponse>`) |
| `GET` | `/{senderId}/{recipientId}` | Get invite by sender and recipient | — | `200 OK` (`InviteDataResponse`) |
| `DELETE` | `/{inviteId}` | Delete an invite | — | `204 No Content` |

---

## 4. User API
**Endpoint:** `/api/user`

| Method | Path | Description | Request Body / Params | Response |
|---|---|---|---|---|
| `POST` | `/` | Create a new user | `CreateUserRequest` | `201 Created` (`UserDataResponse`) |
| `POST` | `/assign` | Assign user to a house | `HouseOperationRequest` | `200 OK` (`UserDataResponse`) |
| `POST` | `/remove` | Remove user from a house | `HouseOperationRequest` | `200 OK` (`UserDataResponse`) |
| `GET` | `/` | Get all users | — | `200 OK` (`List<UserDataResponse>`) |
| `GET` | `/{id}` | Get user by ID | — | `200 OK` (`UserDataResponse`) |
| `GET` | `/house/{houseId}` | Get all users in a house | — | `200 OK` (`List<UserDataResponse>`) |
| `GET` | `/name/{name}` | Get user by name | — | `200 OK` (`UserDataResponse`) |
| `GET` | `/surname/{surname}` | Get user by surname | — | `200 OK` (`UserDataResponse`) |
| `PATCH` | `/` | Modify user details | `UpdateUserRequest`, `X-Session-User-Id` (Header) | `200 OK` (`UserDataResponse`) |
| `DELETE` | `/{id}` | Delete a user | — | `204 No Content` |

---

## DTO Reference

### Request DTOs

#### `CreateTransactionRequest`
| Field | Type | Required |
|---|---|---|
| `userId` | `Long` | yes |
| `amount` | `BigDecimal` | yes |
| `date` | `LocalDate` | yes |
| `transactionType` | `TransactionType` | yes |
| `transactionCategory` | `TransactionCategory` | yes |

#### `UpdateTransactionRequest`
| Field | Type | Required |
|---|---|---|
| `userId` | `Long` | no |
| `amount` | `BigDecimal` | no |
| `date` | `LocalDate` | no |
| `transactionType` | `TransactionType` | no |
| `transactionCategory` | `TransactionCategory` | no |

#### `CreateHouseRequest`
| Field | Type | Required |
|---|---|---|
| `street` | `String` | yes |
| `number` | `String` | yes |
| `owner_id` | `Long` | yes |

#### `CreateInviteRequest`
| Field | Type | Required |
|---|---|---|
| `senderId` | `Long` | yes |
| `recipientId` | `Long` | yes |

#### `CreateUserRequest`
| Field | Type | Required |
|---|---|---|
| `name` | `String` | yes |
| `surname` | `String` | yes |

#### `UpdateUserRequest`
| Field | Type | Required |
|---|---|---|
| `name` | `String` | no |
| `surname` | `String` | no |
| `houseId` | `Long` | no |

#### `HouseOperationRequest`
| Field | Type | Required |
|---|---|---|
| `userId` | `Long` | yes |
| `houseId` | `Long` | yes |
| `userToModifyId` | `Long` | yes |

---

### Response DTOs

#### `UserDataResponse`
| Field | Type |
|---|---|
| `id` | `Long` |
| `name` | `String` |
| `surname` | `String` |
| `house` | `House` |

#### `HouseDataResponse`
| Field | Type |
|---|---|
| `id` | `Long` |
| `street` | `String` |
| `number` | `String` |
| `owner` | `UserDataResponse` |

#### `TransactionDataResponse`
| Field | Type |
|---|---|
| `id` | `Long` |
| `amount` | `BigDecimal` |
| `localDate` | `LocalDate` |
| `transactionType` | `TransactionType` |
| `transactionCategory` | `TransactionCategory` |
| `userDataResponse` | `UserDataResponse` |

#### `InviteDataResponse`
| Field | Type |
|---|---|
| `id` | `Long` |
| `sender` | `UserDataResponse` |
| `recipient` | `UserDataResponse` |

#### `UserTransactionSummary`
| Field | Type |
|---|---|
| *(see service implementation)* | — |