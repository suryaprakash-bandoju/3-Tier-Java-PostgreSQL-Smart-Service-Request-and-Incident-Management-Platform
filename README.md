```markdown
# Smart Service Request & Incident Management Platform

## Overview
A medium-complexity, 3-tier enterprise web application designed to manage internal IT service requests and incidents. It features Role-Based Access Control (RBAC), a relational data model, and a responsive Bootstrap UI. 

This project is specifically architected to expose real-world DevOps deployment concerns, including manual database provisioning, environment configuration, and native build tool lifecycles.

## Architecture & Tech Stack
- **Presentation Tier:** Thymeleaf, HTML5, CSS3, Bootstrap 5, JavaScript
- **Application Tier:** Java 17, Spring Boot 3, Spring MVC, Spring Security, Spring Data JPA
- **Data Tier:** PostgreSQL
- **Build Tool:** Apache Maven
- **Testing:** JUnit 5, Mockito

---

## Prerequisites (Ubuntu VM)
Ensure the following packages are installed on your Ubuntu target environment before proceeding.

```bash
# Update package index
sudo apt update

# Install Java 17 JDK
sudo apt install openjdk-17-jdk -y
java -version

# Install Apache Maven
sudo apt install maven -y
mvn -version

# Install PostgreSQL
sudo apt install postgresql postgresql-contrib -y
sudo systemctl start postgresql
sudo systemctl enable postgresql
```

---

## 1. Database Setup (CRITICAL DEVOPS STEP)
Unlike simple applications that auto-generate schemas, this application requires the database, user, and schema to be provisioned manually before startup.

**Step 1: Create the Database and User**
Run the following commands as the `postgres` system user:
```bash
sudo -u postgres psql -c "CREATE DATABASE servicedesk_db;"
sudo -u postgres psql -c "CREATE USER devops_user WITH ENCRYPTED PASSWORD 'SecureDevOps123!';"
sudo -u postgres psql -c "GRANT ALL PRIVILEGES ON DATABASE servicedesk_db TO devops_user;"
sudo -u postgres psql -c "ALTER DATABASE servicedesk_db OWNER TO devops_user;"
```

**Step 2: Initialize Schema and Seed Data**
Execute the provided SQL script to create tables and seed initial users/categories:
```bash
# Ensure you are in the root directory of this repository
sudo -u postgres psql -d servicedesk_db -f database/schema.sql
```
*Note: The schema includes 3 seeded users. Their passwords are BCrypt encoded in the database for the plaintext password: `password123`.*

---

## 2. Build & Test Lifecycle
Navigate to the root directory of this repository and execute the Maven build lifecycle.

**Clean previous build artifacts:**
```bash
mvn clean
```

**Compile the source code:**
```bash
mvn compile
```

**Run Unit Tests:**
```bash
mvn test
```
*Verify that all tests pass in the console output before proceeding.*

**Package the application into an executable JAR:**
```bash
mvn package -DskipTests
```
*The compiled artifact will be generated at: `target/service-request-management-1.0.0.jar`*

---

## 3. Run / Deploy
Start the application using the Java runtime. 

**Foreground execution (for testing/verification):**
```bash
java -jar target/service-request-management-1.0.0.jar
```

**Background execution (production-like):**
```bash
nohup java -jar target/service-request-management-1.0.0.jar > app.log 2>&1 &
echo $! > app.pid
```
*To stop the background process later: `kill $(cat app.pid)`*

---

## 4. Verification & Access
- The application binds to **Port 8080**.
- Open your browser and navigate to: `http://<YOUR_VM_IP>:8080`
- You will be redirected to the login page.

### Login Credentials
Use the following seeded accounts to test Role-Based Access Control. 
*(Note: The password for ALL accounts is `password123`)*

| Username | Role | Access Level |
| :--- | :--- | :--- |
| `user1` | **USER** | Can create requests, view own requests, add comments. |
| `agent1` | **AGENT** | Can view all requests, update status, add resolution notes. |
| `manager1`| **MANAGER**| Full access. Can view operational dashboard, assign agents, manage priorities. |

**Verification Checklist:**
1. Log in as `user1` and create a new "High Priority" request.
2. Log out, then log in as `manager1`.
3. Navigate to the **Manager Portal** (`/manager/dashboard`).
4. Verify the new request appears in the operational dashboard.
5. Change the status of the request to `IN_PROGRESS`.
6. Log out, log back in as `user1`, and verify the status updated and you can add a comment.

---

## Troubleshooting
- **Port 8080 already in use:** Check running processes with `sudo lsof -i :8080` and kill the conflicting process, or change `server.port` in `src/main/resources/application.properties`.
- **Database Connection Refused:** Ensure PostgreSQL is running (`sudo systemctl status postgresql`) and that the `servicedesk_db` database exists.
- **Access Denied for devops_user:** Ensure you ran the `GRANT ALL PRIVILEGES` and `ALTER DATABASE ... OWNER TO` commands in Step 1.
```