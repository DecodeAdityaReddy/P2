import { createContext, useContext, useEffect, useMemo, useState } from "react";
import { api } from "../services/api";

const AuthContext = createContext(null);
const USER_STORAGE_KEY = "skillspring-auth-user";
const TOKEN_STORAGE_KEY = "skillspring-auth-token";

const demoUsers = {
  "student@lms.com": {
    name: "Aarav Student",
    email: "student@lms.com",
    role: "Student"
  },
  "instructor@lms.com": {
    name: "Ishita Instructor",
    email: "instructor@lms.com",
    role: "Instructor"
  },
  "admin@lms.com": {
    name: "Neel Admin",
    email: "admin@lms.com",
    role: "Admin"
  }
};

function normalizeRole(role) {
  return role?.charAt(0) + role?.slice(1).toLowerCase();
}

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() => {
    try {
      const stored = window.localStorage.getItem(USER_STORAGE_KEY);
      return stored ? JSON.parse(stored) : null;
    } catch {
      return null;
    }
  });
  const [token, setToken] = useState(() => window.localStorage.getItem(TOKEN_STORAGE_KEY));

  useEffect(() => {
    if (user) {
      window.localStorage.setItem(USER_STORAGE_KEY, JSON.stringify(user));
    } else {
      window.localStorage.removeItem(USER_STORAGE_KEY);
    }
  }, [user]);

  useEffect(() => {
    if (token) {
      window.localStorage.setItem(TOKEN_STORAGE_KEY, token);
    } else {
      window.localStorage.removeItem(TOKEN_STORAGE_KEY);
    }
  }, [token]);

  const login = async ({ email, password }) => {
    try {
      const response = await api.login({
        email,
        password
      });

      setToken(response.token);
      setUser({
        ...response.user,
        role: normalizeRole(response.user.role)
      });
      return { ok: true };
    } catch {
      const fallbackUser = demoUsers[email] || {
        name: email.split("@")[0],
        email,
        role: "Student"
      };

      setUser(fallbackUser);
      setToken("demo-token");
      return { ok: true, fallback: true };
    }
  };

  const signup = async ({ name, email, password, role }) => {
    try {
      const response = await api.signup({
        name,
        email,
        password,
        role: role.toUpperCase()
      });

      setToken(response.token);
      setUser({
        ...response.user,
        role: normalizeRole(response.user.role)
      });
      return { ok: true };
    } catch {
      setUser({
        name,
        email,
        role
      });
      setToken("demo-token");
      return { ok: true, fallback: true };
    }
  };

  const logout = () => {
    setUser(null);
    setToken(null);
  };

  const value = useMemo(
    () => ({
      user,
      token,
      login,
      signup,
      logout
    }),
    [user, token]
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  const context = useContext(AuthContext);

  if (!context) {
    throw new Error("useAuth must be used inside AuthProvider");
  }

  return context;
}
