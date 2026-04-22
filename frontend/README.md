# LMS Frontend

React frontend for a Full-Stack Learning Management System.

## Pages included

- Home page
- Login page
- Signup page
- Course catalog with search
- Protected dashboard

## Run locally

1. `npm install`
2. `npm run dev`

## Demo login emails

- `student@lms.com`
- `instructor@lms.com`
- `admin@lms.com`

The frontend now connects to the Spring Boot backend through `/api` when the backend is running.
If the API is unavailable, it falls back to browser-stored demo data so the UI still works locally.
