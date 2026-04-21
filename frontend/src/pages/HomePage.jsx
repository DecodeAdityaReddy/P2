import { Link } from "react-router-dom";
import { useLms } from "../context/LmsContext";

const highlights = [
  "JWT-based authentication and protected routes",
  "Role-based panels for admins, instructors, and students",
  "Course browsing, search, and secure checkout flow"
];

export default function HomePage() {
  const { courses, enrollments, payments, serverConnected } = useLms();

  return (
    <section className="page">
      <div className="hero">
        <div>
          <p className="eyebrow">Modern online learning platform</p>
          <h1>Learn, manage courses, and grow with one LMS dashboard.</h1>
          <p className="hero-copy">
            A responsive frontend for your Learning Management System with
            authentication, course discovery, dashboards, and payment-ready UX.
          </p>
          <div className="hero-actions">
            <Link className="primary-button" to="/courses">
              Explore Courses
            </Link>
            <Link className="secondary-button" to="/signup">
              Create Account
            </Link>
          </div>
        </div>
        <div className="hero-panel">
          <h2>Frontend features</h2>
          <ul className="feature-list">
            {highlights.map((item) => (
              <li key={item}>{item}</li>
            ))}
          </ul>
        </div>
      </div>
      <div className="stats-strip">
        <article className="stat-card">
          <h2>{courses.length}</h2>
          <p>Published courses</p>
        </article>
        <article className="stat-card">
          <h2>{enrollments.length}</h2>
          <p>Total enrollments</p>
        </article>
        <article className="stat-card">
          <h2>{payments.length}</h2>
          <p>Successful payments</p>
        </article>
      </div>
      <p className="server-status">
        Backend status: {serverConnected ? "Connected to Spring API" : "Running in browser demo mode"}
      </p>
    </section>
  );
}
