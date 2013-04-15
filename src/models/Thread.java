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
public class Thread implements Serializable {
    private int parentCategoryId;

    @javax.persistence.Column(name = "parentCategoryId")
    @Basic
    public int getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(int parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
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

    private int threadId;

    @javax.persistence.Column(name = "threadId")
    @Id
    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    private String threadName;

    @javax.persistence.Column(name = "threadName")
    @Basic
    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
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

        java.lang.Thread thread = (java.lang.Thread) o;
//
//        if (parentCategoryId != thread.parentCategoryId) return false;
//        if (threadId != thread.threadId) return false;
//        if (userId != thread.userId) return false;
//        if (threadName != null ? !threadName.equals(thread.threadName) : thread.threadName != null) return false;
//        if (threadText != null ? !threadText.equals(thread.threadText) : thread.threadText != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = parentCategoryId;
        result = 31 * result + userId;
        result = 31 * result + threadId;
        result = 31 * result + (threadName != null ? threadName.hashCode() : 0);
        result = 31 * result + (threadText != null ? threadText.hashCode() : 0);
        return result;
    }
}
