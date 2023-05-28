# expatrio-assessment

This service consists of the following modules:
1. authentication
2. common
3. dao
4. protocol
5. server
6. user-management

### Authentication
This module houses all the logic for user authentication, login, JWT token generation and validation.

### Common
All the data objects and domain models are defined here along with custom exceptions.

### DAO
This is the Data Access Object layer. All the queries using jooq and flyway migration lie in this module.

### Protocol
REST API contracts designed using OpenAPI specs and the controllers are defined in this module.

### Server
This is where the Main Class by the name of ExpatrioApp.kt lies along with the application.yml file.

### User Management
The main business logic for customer creation, deletion and updation lies in this module.
