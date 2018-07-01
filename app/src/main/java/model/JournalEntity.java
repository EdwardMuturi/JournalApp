package model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class JournalEntity {
    @Id
    long id;

    String message;
    String createdBy;
    String createdAt;
    String title;

    @Override
    public String toString() {
        return "JournalEntity{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public JournalEntity() { //Default Constructor

    }

    public JournalEntity(String message, String createdBy, String createdAt, String title) {

        this.message = message;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.title = title;
    }
}
