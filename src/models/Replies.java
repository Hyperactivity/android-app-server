package models;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-03-25
 * Time: 14:42
 */
@Entity
public class Replies {
    private int threadId;

    @javax.persistence.Column(name = "threadId")
    @Id
    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    private int replyId;

    @javax.persistence.Column(name = "replyId")
    @Basic
    public int getReplyId() {
        return replyId;
    }

    public void setReplyId(int replyId) {
        this.replyId = replyId;
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

    private int time;

    @javax.persistence.Column(name = "time")
    @Basic
    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    private int relevance;

    @javax.persistence.Column(name = "relevance")
    @Basic
    public int getRelevance() {
        return relevance;
    }

    public void setRelevance(int relevance) {
        this.relevance = relevance;
    }

    private String threadText;

    @javax.persistence.Column(name = "threadText")
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

        Replies replies = (Replies) o;

        if (relevance != replies.relevance) return false;
        if (replyId != replies.replyId) return false;
        if (threadId != replies.threadId) return false;
        if (time != replies.time) return false;
        if (userId != replies.userId) return false;
        if (threadText != null ? !threadText.equals(replies.threadText) : replies.threadText != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = threadId;
        result = 31 * result + replyId;
        result = 31 * result + userId;
        result = 31 * result + time;
        result = 31 * result + relevance;
        result = 31 * result + (threadText != null ? threadText.hashCode() : 0);
        return result;
    }
}
