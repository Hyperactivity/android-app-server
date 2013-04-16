package models;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.*;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-03-25
 * Time: 14:42
 */
@Entity
public class Reply implements Serializable {



    public Reply(Thread parentThread, int userId, String text, Timestamp currentTime) {
        setParentThread(parentThread);
        setUserId(userId);
        setText(text);
        setTime(currentTime);
    }

    @Deprecated
    public Reply() {
    }

    private int userId;

    @Column(name = "accountId")
    @Basic
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private Timestamp time;

    @Column(name = "time")
    @Basic
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    private String text;

    @Column(name = "text")
    @Basic
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private Thread parentThread;

    @ManyToOne
    @JoinColumn(name = "threadId", referencedColumnName = "id", nullable = false)
    public Thread getParentThread() {
        return parentThread;
    }

    public void setParentThread(Thread parentThread) {
        this.parentThread = parentThread;
    }

    private int id;

    @Column(name = "id")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reply reply = (Reply) o;

        if (id != reply.id) return false;
        if (userId != reply.userId) return false;
        if (text != null ? !text.equals(reply.text) : reply.text != null) return false;
        if (time != null ? !time.equals(reply.time) : reply.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }
}
