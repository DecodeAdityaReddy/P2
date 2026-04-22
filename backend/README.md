# LMS Backend

Spring Boot API for the Learning Management System.

## Demo accounts

- `student@lms.com` / `password`
- `instructor@lms.com` / `password`
- `admin@lms.com` / `password`

## Storage

The backend now uses Spring Data JPA.

- Default local database: `H2` file database
- Production-ready option: `MySQL` via environment variables

To use MySQL, set:

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver`
- `SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.MySQLDialect`

## Run

Install Maven, then run:

```powershell
cd backend
mvn spring-boot:run
```

The API starts on `http://localhost:8080`.

You can also open the H2 console at `http://localhost:8080/h2-console`.

## Available endpoints

- `POST /api/auth/signup`
- `POST /api/auth/login`
- `GET /api/courses`
- `POST /api/courses`
- `POST /api/enrollments/course/{courseId}`
- `GET /api/enrollments/me`
- `PUT /api/enrollments/course/{courseId}/progress`
- `GET /api/payments`
- `GET /api/announcements`
