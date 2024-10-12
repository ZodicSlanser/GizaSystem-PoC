## **Bookstore Rate Limiting and Logging System**

### **Table of Contents**
1. [Overview](#overview)
2. [Features](#features)
3. [Technologies Used](#technologies-used)
4. [Setup and Installation](#setup-and-installation)
5. [How to Use](#how-to-use)
6. [Rate Limiting](#rate-limiting)
7. [Logging](#logging)


---

### **Overview**

This project is a Bookstore management system that enforces rate-limiting, role-based permissions, and custom logging. It’s built using **Java 17** and **Spring Boot 3**, with a focus on handling user requests efficiently and securely.

### **Features**

- **Role-based Permissions**: Admins, Free Users, Premium Users, and Store Owners have distinct access levels and operations they can perform.
- **Rate Limiting**: Users have request limits based on their roles (e.g., Free Users get 10 requests per minute, Premium Users get 100 requests per minute).
- **JWT Authentication**: Secure token-based authentication with **Spring Security**.
- **Custom Logging**: Logs all requests and abnormal behavior (e.g., exceeded rate limits or multiple failed login attempts).

### **Technologies Used**
- **Spring Boot 3.x**
    - Spring Security
    - Spring Data JPA
    - Spring Data Redis
- **Java 17**
- **PostgreSQL**
- **Redis** with Lettuce
- **JWT** for authentication
- **FileIO** for custom logging

### **Setup and Installation**

To run the project locally, follow these steps:

1. **Clone the repository:**

   ```bash
   git clone https://github.com/ZodicSlanser/GizaSystem-PoC.git
   cd PoC
   ```

2. **Install dependencies:**

   Make sure you have **Maven** and **Java 17** installed. Then run:

   ```bash
   mvn clean install
   ```

3. **Configure PostgreSQL and Redis**:

   Update your `application.properties` file with the appropriate configurations:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/bookstore_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   
   spring.redis.host=localhost
   spring.redis.port=6379
   ```

4. **Run the application:**

   ```bash
   mvn spring-boot:run
   ```

### **How to Use**

1. **Authentication**:
    - **Register** using the `/user/register` endpoint.
    - **Login** using the `/auth/login` endpoint to get a JWT token.
    - Use the JWT token for all subsequent requests by adding it to the `Authorization` header as `Bearer <token>`.

2. **Bookstore Operations**:
    - Users can view books based on their role (e.g., Free/Premium books).
    - Store Owners can add/edit books in their bookstore.

### **Rate Limiting**

Rate-limiting is implemented using Redis and applied as follows:
- **Free Users**: Max 5 requests per minute.
- **Premium Users**: Max 15 requests per minute.
- **Admin**: Unlimited access.

If a user exceeds the allowed rate limit, the system logs the incident and returns a rate-limit error.

### **Logging**

The system uses custom logging to keep track of:
1. **All Requests**: Logged in `logs/requests.log`.
2. **Abnormal Behaviors**: Logged in `logs/abnormal_behavior.log`. These include:
    - Exceeding rate limits.
    - Multiple login attempts from the same IP address.

