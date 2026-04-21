import { useState } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

export default function LoginPage() {
  const navigate = useNavigate();
  const location = useLocation();
  const { login } = useAuth();
  const [error, setError] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [form, setForm] = useState({
    email: "student@lms.com",
    password: ""
  });

  const redirectTo = location.state?.from || "/dashboard";

  const handleSubmit = async (event) => {
    event.preventDefault();

    if (form.password.length < 4) {
      setError("Use any password with at least 4 characters.");
      return;
    }

    setIsSubmitting(true);
    setError("");
    await login(form);
    setIsSubmitting(false);
    navigate(redirectTo, { replace: true });
  };

  return (
    <section className="page auth-page">
      <form className="auth-card" onSubmit={handleSubmit}>
        <h1>Login</h1>
        <p>Use demo emails like `student@lms.com`, `instructor@lms.com`, or `admin@lms.com`.</p>
        {error ? <p className="form-error">{error}</p> : null}
        <label>
          Email
          <input
            required
            type="email"
            value={form.email}
            onChange={(event) =>
              setForm((current) => ({ ...current, email: event.target.value }))
            }
          />
        </label>
        <label>
          Password
          <input
            required
            type="password"
            value={form.password}
            onChange={(event) =>
              setForm((current) => ({
                ...current,
                password: event.target.value
              }))
            }
          />
        </label>
        <button className="primary-button" type="submit">
          {isSubmitting ? "Signing In..." : "Sign In"}
        </button>
        <p>
          New here? <Link to="/signup">Create an account</Link>
        </p>
      </form>
    </section>
  );
}
