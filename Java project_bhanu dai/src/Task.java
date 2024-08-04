public class Task {
    private int taskId;
    private int userId;
    private String title;
    private String description;
    private java.sql.Date dueDate;
    private java.sql.Timestamp createdAt;

    public Task(int taskId, int userId, String title, String description, java.sql.Date dueDate, java.sql.Timestamp createdAt) {
        this.taskId = taskId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
    }

    // Getters and setters
}