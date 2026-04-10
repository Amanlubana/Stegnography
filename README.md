# 🔐 Digital Steganography Web Application

A full-stack secure web application that allows users to **hide and extract secret messages inside images** using **AES encryption** and **LSB (Least Significant Bit) steganography**.

---

## 🚀 Features

### 🔑 Authentication System
- User Registration & Login
- Secure Password Handling
- Forgot Password with OTP verification

### 🖼 Steganography
- Embed secret messages inside PNG images
- Extract hidden messages from images
- Uses **LSB (Least Significant Bit)** technique

### 🔐 Security
- AES Encryption for message security
- Password-protected embedding & extraction
- Input validation (PNG-only upload)

### 🎨 User Interface
- Modern responsive UI (Thymeleaf + CSS)
- Dark/Light mode toggle 🌙☀
- Smooth animations & transitions
- Image preview before upload
- Toast notifications for feedback

### 📥 File Handling
- Download embedded images
- Real-time image preview before processing

---

## 🛠 Tech Stack

### Backend:
- Java 24
- Spring Boot 3
- Spring Security
- Spring MVC

### Frontend:
- Thymeleaf
- HTML5 + CSS3
- JavaScript (UI enhancements)

### Database:
- MySQL

### Security & Processing:
- AES Encryption
- LSB Image Steganography

---

# 📁Project Structure
src

└── main

    ├── java
    │   └── com.example.demo
    │       ├── DemoApplication.java
    │
    │       ├── config
    │       │   ├── SecurityConfig.java
    │       │   └── AppConfig.java
    │
    │       ├── controller
    │       │   ├── StegoController.java
    │       │   ├── ViewController.java
    │       │   └── AuthController.java
    │
    │       ├── service
    │       │   ├── StegoService.java
    │       │   ├── CryptoService.java
    │       │   └── UserService.java
    │
    │       ├── repository
    │       │   └── UserRepository.java
    │
    │       ├── entity
    │       │   └── User.java
    │
    │       ├── dto
    │       │   ├── LoginRequest.java
    │       │   ├── SignupRequest.java
    │       │   └── StegoRequest.java
    │
    │       ├── exception
    │       │   ├── GlobalExceptionHandler.java
    │       │   └── CustomException.java
    │
    │       └── util
    │           └── FileUtil.java
    │
    └── resources
        ├── templates
        │   ├── index.html
        │   ├── login.html
        │   ├── register.html
        │   ├── dashboard.html
        │   ├── embed.html
        │   ├── extract.html
        │   ├── forgot-password.html
        │   └── verify-otp.html
        │
        ├── static
        │   ├── css
        │   │   └── style.css
        │   │
        │   └── js
        │       └── script.js
        │
        └── application.properties

## ⚙️ How It Works

### 🔐 Embedding Process:
1. User uploads a PNG image
2. Message is encrypted using AES
3. Encrypted message is hidden inside image pixels using LSB
4. New image is generated and downloaded

### 🔍 Extraction Process:
1. User uploads stego image
2. Hidden data is extracted from LSB bits
3. AES decryption is applied using password
4. Original message is displayed

---

## 📸 Screenshots
Login Page ->
<img width="1920" height="1080" alt="Screenshot (40)" src="https://github.com/user-attachments/assets/a6ee7f7f-5eef-4f42-9d85-24cdbbfeb9df" />
Register Page ->
<img width="1920" height="1080" alt="Screenshot (41)" src="https://github.com/user-attachments/assets/3d33f022-c603-42a7-be4c-0a5d402850cc" />
Forgot Password Page ->
<img width="1920" height="1080" alt="Screenshot (42)" src="https://github.com/user-attachments/assets/0519743b-ccc8-42b0-8861-d389d83a8304" />
Reset Password Page ->
<img width="1920" height="1080" alt="Screenshot (43)" src="https://github.com/user-attachments/assets/8d6505c4-cd52-490c-871f-26047336f0ff" />
Dashboard ->
<img width="1920" height="1080" alt="Screenshot (44)" src="https://github.com/user-attachments/assets/4184cd4b-87d7-4505-946a-029f1f9c91aa" />
Embed Page ->
<img width="1920" height="1080" alt="Screenshot (45)" src="https://github.com/user-attachments/assets/97cfd954-77ac-410e-879f-18a91323f502" />
Extract Page ->
<img width="1920" height="1080" alt="Screenshot (46)" src="https://github.com/user-attachments/assets/a4eba76d-7376-405b-b24f-3e988c53e4a7" />

---
# Author

Amandeep Singh  
Java Full Stack Developer  
