const DEFAULT_PRODUCTION_API = "https://lms-backend-production-b938.up.railway.app/api";

const API_BASE = import.meta.env.VITE_API_URL ||
  (import.meta.env.PROD ? DEFAULT_PRODUCTION_API : "/api");

function readToken() {
  return window.localStorage.getItem("skillspring-auth-token");
}

async function request(path, options = {}) {
  const headers = {
    "Content-Type": "application/json",
    ...(options.headers || {})
  };

  const token = readToken();

  if (token) {
    headers.Authorization = `Bearer ${token}`;
  }

  const response = await fetch(`${API_BASE}${path}`, {
    ...options,
    headers
  });

  if (!response.ok) {
    const text = await response.text();
    throw new Error(text || "Request failed");
  }

  return response.json();
}

export const api = {
  login(payload) {
    return request("/auth/login", {
      method: "POST",
      body: JSON.stringify(payload)
    });
  },
  signup(payload) {
    return request("/auth/signup", {
      method: "POST",
      body: JSON.stringify(payload)
    });
  },
  getCourses() {
    return request("/courses");
  },
  createCourse(payload) {
    return request("/courses", {
      method: "POST",
      body: JSON.stringify(payload)
    });
  },
  getAnnouncements() {
    return request("/announcements");
  },
  getMyEnrollments() {
    return request("/enrollments/me");
  },
  enrollInCourse(courseId) {
    return request(`/enrollments/course/${courseId}`, {
      method: "POST"
    });
  },
  updateProgress(courseId, progress) {
    return request(`/enrollments/course/${courseId}/progress`, {
      method: "PUT",
      body: JSON.stringify({ progress })
    });
  },
  getPayments() {
    return request("/payments");
  }
};
