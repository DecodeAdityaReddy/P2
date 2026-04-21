import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

export default function SignupPage() {
  const navigate = useNavigate();
  const { signup } = useAuth();
  const [error, setError] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [form, setForm] = useState({
    name: "",
    email: "",
    password: "",
    role: "Student"
  });

  const handleSubmit = async (event) => {
    event.preventDefault();

    if (form.password.length < 4) {
      setError("Password should contain at least 4 characters.");
      return;
    }

    setIsSubmitting(true);
    setError("");
    await signup(form);
    setIsSubmitting(false);
    navigate("/dashboard", { replace: true });
  };

  return (
    <section className="page auth-page">
      <form className="auth-card" onSubmit={handleSubmit}>
        <h1>Create Account</h1>
        {error ? <p className="form-error">{error}</p> : null}
        <label>
          Full Name
          <input
            required
            type="text"
            value={form.name}
            onChange={(event) =>
              setForm((current) => ({ ...current, name: event.target.value }))
            }
          />
        </label>
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
          Role
          <select
            value={form.role}
            onChange={(event) =>
              setForm((current) => ({ ...current, role: event.target.value }))
            }
          >
            <option>Student</option>
            <option>Instructor</option>
            <option>Admin</option>
          </select>
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
          {isSubmitting ? "Registering..." : "Register"}
        </button>
        <p>
          Already have an account? <Link to="/login">Login</Link>
        </p>
      </form>
    </section>
  );
}
