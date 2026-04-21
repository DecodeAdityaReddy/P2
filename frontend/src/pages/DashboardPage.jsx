import { useAuth } from "../context/AuthContext";
import { useLms } from "../context/LmsContext";
import { formatPrice } from "../utils/format";

export default function DashboardPage() {
  const { user } = useAuth();
  const {
    courses,
    enrollments,
    payments,
    progress,
    announcements,
    serverConnected,
    updateProgress,
    addCourse
  } = useLms();

  const userEnrollments = enrollments.filter((item) => item.email === user.email);
  const purchasedCourses = userEnrollments
    .map((item) => {
      const course = courses.find((entry) => entry.id === item.courseId);
      return course ? { ...course, status: item.status } : null;
    })
    .filter(Boolean);
  const instructorCourses = courses.filter((item) => item.instructor === user.name);
  const revenue = payments.reduce((sum, item) => sum + item.amount, 0);

  const handleAddCourse = async (event) => {
    event.preventDefault();
    const formData = new FormData(event.currentTarget);

    await addCourse(
      {
        title: formData.get("title"),
        category: formData.get("category"),
        level: formData.get("level"),
        duration: formData.get("duration"),
        lessons: Number(formData.get("lessons")),
        price: Number(formData.get("price")),
        description: formData.get("description")
      },
      user
    );

    event.currentTarget.reset();
  };

  return (
    <section className="page">
      <div className="dashboard-banner">
        <p className="eyebrow">{user.role} access</p>
        <h1>{user.role} Dashboard</h1>
        <p>
          Welcome back, {user.name}. Your dashboard updates as you enroll,
          create courses, and manage activity across the LMS.
        </p>
        <p className="server-status">
          {serverConnected ? "Spring API is live." : "Using local browser storage until backend starts."}
        </p>
      </div>

      {user.role === "Student" ? (
        <>
          <div className="stats-grid">
            <article className="stat-card">
              <h2>{purchasedCourses.length}</h2>
              <p>Enrolled courses</p>
            </article>
            <article className="stat-card">
              <h2>
                {purchasedCourses.length
                  ? Math.round(
                      purchasedCourses.reduce(
                        (sum, course) => sum + (progress[`${user.email}-${course.id}`] || 0),
                        0
                      ) / purchasedCourses.length
                    )
                  : 0}
                %
              </h2>
              <p>Average completion</p>
            </article>
            <article className="stat-card">
              <h2>{announcements.length}</h2>
              <p>Latest announcements</p>
            </article>
          </div>
          <div className="dashboard-section">
            <h2>Your learning</h2>
            {purchasedCourses.length ? (
              <div className="dashboard-list">
                {purchasedCourses.map((course) => {
                  const progressKey = `${user.email}-${course.id}`;
                  return (
                    <article className="detail-card" key={course.id}>
                      <div className="detail-row">
                        <div>
                          <h3>{course.title}</h3>
                          <p>{course.description}</p>
                        </div>
                        <strong>{progress[progressKey] || 0}% complete</strong>
                      </div>
                      <input
                        type="range"
                        min="0"
                        max="100"
                        value={progress[progressKey] || 0}
                        onChange={(event) =>
                          updateProgress(course.id, user, event.target.value)
                        }
                      />
                    </article>
                  );
                })}
              </div>
            ) : (
              <article className="detail-card">
                <p>No enrollments yet. Buy a course from the catalog to start learning.</p>
              </article>
            )}
          </div>
        </>
      ) : null}

      {user.role === "Instructor" ? (
        <>
          <div className="stats-grid">
            <article className="stat-card">
              <h2>{instructorCourses.length}</h2>
              <p>Courses created</p>
            </article>
            <article className="stat-card">
              <h2>
                {
                  enrollments.filter((item) =>
                    instructorCourses.some((course) => course.id === item.courseId)
                  ).length
                }
              </h2>
              <p>Total learners</p>
            </article>
            <article className="stat-card">
              <h2>{formatPrice(revenue)}</h2>
              <p>Platform revenue snapshot</p>
            </article>
          </div>
          <div className="dashboard-columns">
            <form className="detail-card course-form" onSubmit={handleAddCourse}>
              <h2>Create a course</h2>
              <input name="title" placeholder="Course title" required />
              <input name="category" placeholder="Category" required />
              <input name="level" placeholder="Level" required />
              <input name="duration" placeholder="Duration" required />
              <input min="1" name="lessons" placeholder="Lessons" required type="number" />
              <input min="0" name="price" placeholder="Price" required type="number" />
              <textarea name="description" placeholder="Description" required rows="4" />
              <button className="primary-button" type="submit">
                Publish Course
              </button>
            </form>
            <div className="dashboard-section">
              <h2>Your published courses</h2>
              <div className="dashboard-list">
                {instructorCourses.length ? (
                  instructorCourses.map((course) => (
                    <article className="detail-card" key={course.id}>
                      <div className="detail-row">
                        <h3>{course.title}</h3>
                        <strong>{formatPrice(course.price)}</strong>
                      </div>
                      <p>{course.description}</p>
                    </article>
                  ))
                ) : (
                  <article className="detail-card">
                    <p>Create your first course using the form.</p>
                  </article>
                )}
              </div>
            </div>
          </div>
        </>
      ) : null}

      {user.role === "Admin" ? (
        <>
          <div className="stats-grid">
            <article className="stat-card">
              <h2>{courses.length}</h2>
              <p>Total courses</p>
            </article>
            <article className="stat-card">
              <h2>{enrollments.length}</h2>
              <p>Total enrollments</p>
            </article>
            <article className="stat-card">
              <h2>{formatPrice(revenue)}</h2>
              <p>Total revenue</p>
            </article>
          </div>
          <div className="dashboard-columns">
            <div className="dashboard-section">
              <h2>Recent payments</h2>
              <div className="dashboard-list">
                {payments.length ? (
                  payments
                    .slice()
                    .reverse()
                    .map((payment) => (
                      <article className="detail-card" key={payment.id}>
                        <div className="detail-row">
                          <h3>{payment.id}</h3>
                          <strong>{formatPrice(payment.amount)}</strong>
                        </div>
                        <p>{payment.email}</p>
                      </article>
                    ))
                ) : (
                  <article className="detail-card">
                    <p>No payment activity yet.</p>
                  </article>
                )}
              </div>
            </div>
            <div className="dashboard-section">
              <h2>Announcements</h2>
              <div className="dashboard-list">
                {announcements.map((announcement) => (
                  <article className="detail-card" key={announcement.id}>
                    <div className="detail-row">
                      <h3>{announcement.title}</h3>
                      <span className="pill">{announcement.audience}</span>
                    </div>
                  </article>
                ))}
              </div>
            </div>
          </div>
        </>
      ) : null}
    </section>
  );
}
