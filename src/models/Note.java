package models;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-03-25
 * Time: 14:42
 */
@Entity
public class Note implements Serializable {
    private int noteId;

    @javax.persistence.Column(name = "id")
    @Id
    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    private int userId;

    @javax.persistence.Column(name = "userId")
    @Basic
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private int privateCategoryParentId;

    @javax.persistence.Column(name = "privateCategoryParentId")
    @Basic
    public int getPrivateCategoryParentId() {
        return privateCategoryParentId;
    }

    public void setPrivateCategoryParentId(int privateCategoryParentId) {
        this.privateCategoryParentId = privateCategoryParentId;
    }

    private String noteName;

    @javax.persistence.Column(name = "noteName")
    @Basic
    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    private String replyText;

    @javax.persistence.Column(name = "replyText")
    @Basic
    public String getReplyText() {
        return replyText;
    }

    public void setReplyText(String replyText) {
        this.replyText = replyText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        if (noteId != note.noteId) return false;
        if (privateCategoryParentId != note.privateCategoryParentId) return false;
        if (userId != note.userId) return false;
        if (noteName != null ? !noteName.equals(note.noteName) : note.noteName != null) return false;
        if (replyText != null ? !replyText.equals(note.replyText) : note.replyText != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = noteId;
        result = 31 * result + userId;
        result = 31 * result + privateCategoryParentId;
        result = 31 * result + (noteName != null ? noteName.hashCode() : 0);
        result = 31 * result + (replyText != null ? replyText.hashCode() : 0);
        return result;
    }
}
