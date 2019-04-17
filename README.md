# Monese Project

## Scope

Design and implement a RESTful API (including data model and the backing implementation)
for money transfers between accounts.

## Implementation
The code was implemented using Java 8 with Micronaut Framework and Gradle.
The H2 in-memory database is populated using Flyway.
The tests were written using Mockito, JUnit and the Micronaut integration.

## How to run
Application
```
./gradlew run
```
Tests
```
./gradlew test
```

## APIs
```
GET > /accounts?showBalance=[true|false]
```
Retrieve all accounts from database.
Optional parameter *showBalance* shows the balance based on the previously deposited values.

```
GET > /accounts/{accountNumber}
```
Retrieve all informations from one account, filtering by account number.

```
POST > /operations/transfer
```
Make the transfer between accounts.
Body:
```
{
	"amount": 50,
	"source": {
		"number": "1968974456"
	},
	"destination": {
		"number": "2654987415"
	}
}
```

## Possible improvements
- Add transaction id in the header of the transactions for a better troubleshooting
- Validate transaction using destination's suid and name
- Use a temporary structure to improve balance calculation (such as a database view, recording the balance of the account at the end of previous month)
- Test the other APIs that weren't part of the scope