import { formatPrice } from "../utils/format";

export default function CourseCard({ course, isEnrolled, onEnroll }) {
  return (
    <article className="course-card">
      <span className="pill">{course.category}</span>
      <h3>{course.title}</h3>
      <p>{course.description}</p>
      <p>
        {course.level} level · {course.lessons} lessons · {course.duration}
      </p>
      <div className="course-meta">
        <span>{course.instructor}</span>
        <strong>{formatPrice(course.price)}</strong>
      </div>
      <div className="course-meta">
        <span>Rating {course.rating}/5</span>
        <span>{isEnrolled ? "Purchased" : "Available"}</span>
      </div>
      <button
        className={isEnrolled ? "secondary-button" : "primary-button"}
        onClick={onEnroll}
        type="button"
      >
        {isEnrolled ? "Enrolled" : "Buy and Enroll"}
      </button>
    </article>
  );
}
