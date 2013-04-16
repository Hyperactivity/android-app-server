package models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-03-25
 * Time: 14:42
 */
@Entity
public class Thread implements Serializable {




    private int parentCategoryId;

    @Column(name = "parentCategoryId")
    @Basic
    public int getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(int parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
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

    private String threadName;

    @Column(name = "headLine")
    @Basic
    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    private List<Reply> replies;

    @OneToMany(mappedBy = "parentThread")
    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
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

    private String threadText;

    @Column(name = "text")
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

        Thread thread = (Thread) o;

        if (id != thread.id) return false;
        if (parentCategoryId != thread.parentCategoryId) return false;
        if (userId != thread.userId) return false;
        if (threadName != null ? !threadName.equals(thread.threadName) : thread.threadName != null) return false;
        if (threadText != null ? !threadText.equals(thread.threadText) : thread.threadText != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = parentCategoryId;
        result = 31 * result + userId;
        result = 31 * result + id;
        result = 31 * result + (threadName != null ? threadName.hashCode() : 0);
        result = 31 * result + (threadText != null ? threadText.hashCode() : 0);
        return result;
    }
}
