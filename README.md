# Full-Stack Learning Management System

This project includes:

- `frontend`: React + Vite LMS client
- `backend`: Spring Boot + Spring Security + Spring Data JPA API
- `tools`: local portable Maven used in this workspace

## Run the project

### Backend

```powershell
.\start-backend.ps1
```

Backend URL:

- `http://localhost:8080`

### Frontend

```powershell
.\start-frontend.ps1
```

Frontend URL:

- `http://localhost:5173`

### Start both

```powershell
.\start-project.ps1
```

## Deployment

This repo is now prepared for public deployment:

- Frontend: deploy `frontend` to Vercel
- Backend: deploy `backend` to Render using `render.yaml`
- Database: use MySQL and set the backend environment variables from [backend/.env.example](C:\Users\yelat\Documents\Codex\2026-04-21-projects-full-stack-learning-management-system\backend\.env.example)

Frontend deployment env:

- `VITE_API_URL=https://your-backend-domain.onrender.com/api`

Backend deployment env:

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver`
- `SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.MySQLDialect`
- `APP_CORS_ALLOWED_ORIGINS=https://your-frontend-domain.vercel.app`
- `APP_JWT_SECRET=your-long-random-secret`

## Demo accounts

- `student@lms.com` / `password`
- `instructor@lms.com` / `password`
- `admin@lms.com` / `password`

## Current status

- JWT authentication
- Role-based dashboard flows
- Course management APIs
- Enrollment and progress tracking
- JPA-backed persistence
- H2 local database support
- MySQL-ready datasource configuration
