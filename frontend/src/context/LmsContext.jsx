import { createContext, useContext, useEffect, useMemo, useState } from "react";
import { useAuth } from "./AuthContext";
import { courses as seedCourses } from "../data/courses";
import { api } from "../services/api";

const LmsContext = createContext(null);
const STORAGE_KEY = "skillspring-lms-data";

const defaultState = {
  courses: seedCourses,
  enrollments: [],
  payments: [],
  progress: {},
  announcements: [
    {
      id: 1,
      title: "New Java backend track available",
      audience: "All users"
    },
    {
      id: 2,
      title: "Weekend live mentor session on Spring Security",
      audience: "Students"
    }
  ]
};

function loadInitialState() {
  try {
    const stored = window.localStorage.getItem(STORAGE_KEY);
    return stored ? JSON.parse(stored) : defaultState;
  } catch {
    return defaultState;
  }
}

export function LmsProvider({ children }) {
  const { user, token } = useAuth();
  const [state, setState] = useState(loadInitialState);
  const [serverConnected, setServerConnected] = useState(false);

  useEffect(() => {
    window.localStorage.setItem(STORAGE_KEY, JSON.stringify(state));
  }, [state]);

  useEffect(() => {
    let cancelled = false;

    async function hydrate() {
      try {
        const [courses, announcements] = await Promise.all([
          api.getCourses(),
          api.getAnnouncements()
        ]);

        if (cancelled) {
          return;
        }

        setServerConnected(true);
        setState((current) => ({
          ...current,
          courses,
          announcements
        }));

        if (user && token) {
          const enrollments = await api.getMyEnrollments();
          const payments =
            user.role === "Admin" || user.role === "Instructor" ? await api.getPayments() : [];

          if (cancelled) {
            return;
          }

          const progress = Object.fromEntries(
            enrollments.map((item) => [`${item.email}-${item.courseId}`, item.progress || 0])
          );

          setState((current) => ({
            ...current,
            enrollments,
            payments,
            progress
          }));
        }
      } catch {
        if (!cancelled) {
          setServerConnected(false);
        }
      }
    }

    hydrate();

    return () => {
      cancelled = true;
    };
  }, [user, token]);

  const enrollInCourse = async (courseId, userProfile) => {
    if (!userProfile) {
      return { ok: false, message: "Please login first." };
    }

    try {
      const response = await api.enrollInCourse(courseId);

      setState((current) => ({
        ...current,
        enrollments: [...current.enrollments, response.enrollment],
        payments: [...current.payments, response.payment],
        progress: {
          ...current.progress,
          [`${userProfile.email}-${courseId}`]: 0
        }
      }));

      setServerConnected(true);
      return { ok: true, message: "Enrollment successful." };
    } catch {
      const alreadyEnrolled = state.enrollments.some(
        (item) => item.courseId === courseId && item.email === userProfile.email
      );

      if (alreadyEnrolled) {
        return { ok: false, message: "You are already enrolled in this course." };
      }

      const course = state.courses.find((item) => item.id === courseId);

      setState((current) => ({
        ...current,
        enrollments: [
          ...current.enrollments,
          {
            id: Date.now(),
            courseId,
            email: userProfile.email,
            userName: userProfile.name,
            purchasedAt: new Date().toISOString(),
            status: "Active",
            progress: 0
          }
        ],
        payments: [
          ...current.payments,
          {
            id: `PAY-${Date.now()}`,
            courseId,
            email: userProfile.email,
            amount: course.price,
            provider: "Razorpay Demo",
            status: "Paid",
            paidAt: new Date().toISOString()
          }
        ],
        progress: {
          ...current.progress,
          [`${userProfile.email}-${courseId}`]: 0
        }
      }));

      return { ok: true, message: "Enrollment successful." };
    }
  };

  const updateProgress = async (courseId, userProfile, nextValue) => {
    if (!userProfile) {
      return;
    }

    const safeValue = Math.max(0, Math.min(100, Number(nextValue) || 0));

    try {
      await api.updateProgress(courseId, safeValue);
      setServerConnected(true);
    } catch {
      setServerConnected(false);
    }

    setState((current) => ({
      ...current,
      progress: {
        ...current.progress,
        [`${userProfile.email}-${courseId}`]: safeValue
      },
      enrollments: current.enrollments.map((item) =>
        item.courseId === courseId && item.email === userProfile.email
          ? { ...item, progress: safeValue }
          : item
      )
    }));
  };

  const addCourse = async (payload, userProfile) => {
    try {
      const course = await api.createCourse(payload);
      setState((current) => ({
        ...current,
        courses: [course, ...current.courses]
      }));
      setServerConnected(true);
      return { ok: true };
    } catch {
      setState((current) => ({
        ...current,
        courses: [
          {
            id: Date.now(),
            ...payload,
            instructor: userProfile?.name || payload.instructor,
            rating: 5
          },
          ...current.courses
        ]
      }));
      return { ok: true, fallback: true };
    }
  };

  const value = useMemo(
    () => ({
      ...state,
      serverConnected,
      enrollInCourse,
      updateProgress,
      addCourse
    }),
    [state, serverConnected]
  );

  return <LmsContext.Provider value={value}>{children}</LmsContext.Provider>;
}

export function useLms() {
  const context = useContext(LmsContext);

  if (!context) {
    throw new Error("useLms must be used inside LmsProvider");
  }

  return context;
}
