import { useState } from "react";
import { useNavigate } from "react-router-dom";
import CourseCard from "../components/CourseCard";
import { useAuth } from "../context/AuthContext";
import { useLms } from "../context/LmsContext";

export default function CoursesPage() {
  const navigate = useNavigate();
  const { user } = useAuth();
  const { courses, enrollments, enrollInCourse } = useLms();
  const [query, setQuery] = useState("");
  const [message, setMessage] = useState("");

  const filteredCourses = courses.filter((course) => {
    const normalizedQuery = query.toLowerCase();
    return (
      course.title.toLowerCase().includes(normalizedQuery) ||
      course.category.toLowerCase().includes(normalizedQuery) ||
      course.instructor.toLowerCase().includes(normalizedQuery) ||
      course.level.toLowerCase().includes(normalizedQuery)
    );
  });

  const handleEnroll = (courseId) => {
    if (!user) {
      navigate("/login", { state: { from: "/courses" } });
      return;
    }

    const result = enrollInCourse(courseId, user);
    setMessage(result.message);
  };

  return (
    <section className="page">
      <div className="section-heading">
        <div>
          <p className="eyebrow">Course catalog</p>
          <h1>Discover the right course for your next step.</h1>
        </div>
        <input
          className="search-input"
          type="search"
          placeholder="Search by title, category, level, or instructor"
          value={query}
          onChange={(event) => setQuery(event.target.value)}
        />
      </div>
      {message ? <p className="inline-message">{message}</p> : null}
      <div className="course-grid">
        {filteredCourses.map((course) => (
          <CourseCard
            key={course.id}
            course={course}
            isEnrolled={enrollments.some(
              (item) => item.courseId === course.id && item.email === user?.email
            )}
            onEnroll={() => handleEnroll(course.id)}
          />
        ))}
      </div>
    </section>
  );
}
