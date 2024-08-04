public class Note {
    private int noteId;
    private int userId;
    private String title;
    private String content;
    private String color;
    private java.sql.Timestamp createdAt;

    public Note(int noteId, int userId, String title, String content, String color, java.sql.Timestamp createdAt) {
        this.noteId = noteId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.color = color;
        this.createdAt = createdAt;
    }

    // Getters and setters
}