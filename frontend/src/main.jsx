import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter } from "react-router-dom";
import App from "./App";
import { AuthProvider } from "./context/AuthContext";
import { LmsProvider } from "./context/LmsContext";
import "./styles.css";

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <BrowserRouter>
      <AuthProvider>
        <LmsProvider>
          <App />
        </LmsProvider>
      </AuthProvider>
    </BrowserRouter>
  </React.StrictMode>
);
