import { NavLink, Outlet } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

export default function Layout() {
  const { user, logout } = useAuth();

  return (
    <div className="app-shell">
      <header className="topbar">
        <NavLink className="brand" to="/">
          SkillSpring LMS
        </NavLink>
        <nav className="nav-links">
          <NavLink to="/">Home</NavLink>
          <NavLink to="/courses">Courses</NavLink>
          <NavLink to="/dashboard">Dashboard</NavLink>
          {!user ? <NavLink to="/login">Login</NavLink> : null}
          {!user ? <NavLink to="/signup">Signup</NavLink> : null}
          {user ? <span className="user-badge">{user.name} · {user.role}</span> : null}
          {user ? (
            <button className="ghost-button" onClick={logout} type="button">
              Logout
            </button>
          ) : null}
        </nav>
      </header>
      <main>
        <Outlet />
      </main>
    </div>
  );
}
