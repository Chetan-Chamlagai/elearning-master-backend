require("dotenv").config();

const express = require("express");
const cors = require("cors");
const helmet = require("helmet");
const morgan = require("morgan");
const path = require("path");

const connectDB = require("./config/db");

const app = express();

// --------------------------------------------------
// Database
// --------------------------------------------------

connectDB();

// --------------------------------------------------
// Middleware
// --------------------------------------------------

app.use(helmet());
app.use(cors());

app.use(express.json());
app.use(express.urlencoded({ extended: true }));

app.use(morgan("dev"));

// --------------------------------------------------
// Static uploaded files
// --------------------------------------------------

app.use(
    "/uploads",
    express.static(path.join(__dirname, "uploads"))
);

// --------------------------------------------------
// Health check
// --------------------------------------------------

app.get("/", (req, res) => {
    res.json({
        success: true,
        message: "E-Learning API is running",
        version: "1.0.0"
    });
});

app.get("/api/health", (req, res) => {
    res.json({
        success: true,
        message: "API healthy"
    });
});

// --------------------------------------------------
// Routes
// --------------------------------------------------

app.use("/api/auth", require("./routes/authRoutes"));
app.use("/api/users", require("./routes/userRoutes"));
app.use("/api/courses", require("./routes/courseRoutes"));
app.use("/api/lessons", require("./routes/lessonRoutes"));
app.use("/api/quizzes", require("./routes/quizRoutes"));
app.use("/api/enrollments", require("./routes/enrollmentRoutes"));

// --------------------------------------------------
// 404
// --------------------------------------------------

app.use((req, res) => {
    res.status(404).json({
        success: false,
        message: "Route not found"
    });
});

// --------------------------------------------------
// Error handler
// --------------------------------------------------

app.use((err, req, res, next) => {
    console.error(err);

    res.status(err.status || 500).json({
        success: false,
        message: err.message || "Internal server error"
    });
});

// --------------------------------------------------
// Start server
// --------------------------------------------------

const PORT = process.env.PORT || 5000;

app.listen(PORT, () => {
    console.log(`Server running on http://localhost:${PORT}`);
});
