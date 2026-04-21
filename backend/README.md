# LMS Backend

Spring Boot API for the Learning Management System.

## Demo accounts

- `student@lms.com` / `password`
- `instructor@lms.com` / `password`
- `admin@lms.com` / `password`

## Run

Install Maven, then run:

```powershell
cd backend
mvn spring-boot:run
```

The API starts on `http://localhost:8080`.

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
