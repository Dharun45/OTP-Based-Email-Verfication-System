# OTP-Based-Email-Verfication-System
This is an OTP-based Email Verification System which contains backend flow where a user registers, the server generates a random 6-digit code, emails it to them, temporarily stores it with an expiry time, and verifies it when the user submits it back. It combines Spring Boot + Spring Data JPA (MySQL) + Spring Mail + REST APIs.

## 📌 Overview
 
This is an **OTP-based Email Verification System** — a backend flow where a user registers, the server generates a random 6-digit code, emails it to them, temporarily stores it with an expiry time, and verifies it when the user submits it back. It combines **Spring Boot + Spring Data JPA (MySQL) + Spring Mail + REST APIs**.
 
## ⚙️ How It Works
 
1. User registers with basic details (name, email, password)
2. Spring Boot generates a random 6-digit OTP
3. OTP is sent to the registered email address
4. OTP and its 5-minute expiry time are stored in MySQL
5. User submits the OTP for verification
6. Backend validates the OTP against the stored value and checks expiry
7. On success, the user's verification status is updated to `verified`
## 🛠️ Tech Stack
 
| Category | Technology |
|---|---|
| Language | Java 17+ is Needed |
| Framework | Spring Boot |
| Data Layer | Spring Data JPA |
| Database | MySQL |
| Email | Spring Boot Starter Mail (JavaMailSender, Gmail SMTP) |
| API Testing | Postman |
| IDE | IntelliJ IDEA |
| Build Tool | Maven |
 
## 🏗️ Architecture
 
The project follows a standard **four-layer architecture**:
 
```
Controller → Service → Repository → Entity
                ↓
          EmailService / OtpGenerator
```
 
- **Entity Layer** — `User` (id, name, email, password, role, isVerified, otp, otpExpiryTime)
- **Repository Layer** — `UserRepository` (Spring Data JPA)
- **DTO Layer** — `RegisterRequest`, `VerifyOtpRequest`
- **Service Layer** — `UserService`, `OtpGenerator`, `EmailService`
- **Controller Layer** — `UserController` (REST endpoints)
## 📂 Project Structure
 
```
src/main/java/com/dharun/otp_verification/
├── controller/
│   └── UserController.java
├── dto/
│   ├── RegisterRequest.java
│   └── VerifyOtpRequest.java
├── entity/
│   └── User.java
├── repository/
│   └── UserRepository.java
├── service/
│   ├── EmailService.java
│   ├── OtpGenerator.java
│   └── UserService.java
└── OtpVerificationApplication.java
```
 
## 🔌 API Endpoints
   Use Postman Desktop App 
1. Go to https://www.postman.com/downloads/
2. Download for Windows
3.Install and sign in with your account
### 1. Register User
```
POST /users/register
Content-Type: application/json
```
**Request Body:**
```json
{
  "name": "Dharun",
  "email": "receivermailid@gmail.com",
  "password": "test123"
}
```
**Response:**
```
"OTP sent to your email"
```
 
### 2. Verify OTP
```
POST /users/verify-otp
Content-Type: application/json
```
**Request Body:**
```json
{
  "email": "dharun@example.com",
  "otp": "123456"
}
```
**Response:**
```
"Email verified successfully"
```
 
### 3. Resend OTP
```
POST /users/resend-otp?email=dharun@example.com
```
**Response:**
```
"New OTP sent to your email"
```
 
## 🗄️ Database Schema
 
**Table: `users`**
 
| Column | Type | Description |
|---|---|---|
| id | BIGINT (PK) | Auto-generated user ID |
| name | VARCHAR | User's full name |
| email | VARCHAR | User's email address |
| password | VARCHAR | User's password (plain text — see Future Improvements) |
| role | VARCHAR | User role (default: USER) |
| is_verified | BIT | Verification status |
| otp | VARCHAR | Current OTP (nulled after verification) |
| otp_expiry_time | DATETIME | OTP expiry timestamp (nulled after verification) |
 
## 🚀 Getting Started
 
### Prerequisites
- Java 17+
- MySQL 8.0+
- Maven
- Gmail account with an **App Password** generated (for SMTP)
### Setup Steps
 
1. **Clone the repository**
```bash
   git clone <repo-url>
   cd OTP_Verification
```
 
2. **Create the MySQL database**
```sql
   CREATE DATABASE otp_verification_db;
```
 
3. **Configure `application.properties`**
```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/otp_verification_db
   spring.datasource.username=root
   spring.datasource.password=YOUR_MYSQL_PASSWORD
 
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
 
   spring.mail.host=smtp.gmail.com
   spring.mail.port=587
   spring.mail.username=YOUR_GMAIL_ADDRESS
   spring.mail.password=YOUR_GMAIL_APP_PASSWORD
   spring.mail.properties.mail.smtp.auth=true
   spring.mail.properties.mail.smtp.starttls.enable=true
```
 
4. **Run the application**
```bash
   mvn spring-boot:run
```
```bash
   run OtpVerifcationApp.java
```
   The app starts on `http://localhost:8080`
 
5. **Test with Postman**
   - Send `POST /users/register` with a valid email
   - Check inbox for OTP
   - Send `POST /users/verify-otp` with the received OTP
## Sample Test Flow
 
```
1. POST /users/register  → "OTP sent to your email"
2. Check Gmail inbox      → OTP: 688224
3. POST /users/verify-otp → "Email verified successfully"
4. SELECT * FROM users;   → is_verified = 1, otp = NULL
```
## Testimonal
<img width="1639" height="566" alt="image" src="https://github.com/user-attachments/assets/c57060d3-8a44-4423-9d84-eb9fcb5f57ac" />

<img width="1612" height="881" alt="image" src="https://github.com/user-attachments/assets/30ac85be-cb89-45a2-a413-8ee92e195554" />

