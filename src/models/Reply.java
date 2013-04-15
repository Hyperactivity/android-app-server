package models;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-03-25
 * Time: 14:42
 */
@Entity
public class Reply implements Serializable {
    private int threadId;

    @Column(name = "threadId")
    @Id
    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    private int replyId;

    @Column(name = "replyId")
    @Basic
    public int getReplyId() {
        return replyId;
    }

    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }

    private int userId;

    @Column(name = "userId")
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

    private String threadText;

    @Column(name = "threadText")
    @Basic
    public String getThreadText() {
        return threadText;
    }

    public void setThreadText(String threadText) {
        this.threadText = threadText;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reply reply = (Reply) o;

        if (replyId != reply.replyId) return false;
        if (threadId != reply.threadId) return false;
        if (userId != reply.userId) return false;
        if (threadText != null ? !threadText.equals(reply.threadText) : reply.threadText != null) return false;
        if (time != null ? !time.equals(reply.time) : reply.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = threadId;
        result = 31 * result + replyId;
        result = 31 * result + userId;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (threadText != null ? threadText.hashCode() : 0);
        return result;
    }
}
