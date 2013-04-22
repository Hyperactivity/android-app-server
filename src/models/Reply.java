package models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-16
 * Time: 13:14
 */
@Entity
public class Reply implements Serializable {

    public Reply(Thread parentThread, Account account, String text, Timestamp currentTime) {
        setParentThread(parentThread);
        setAccount(account);
        setText(text);
        setTime(currentTime);
    }

    @Deprecated
    public Reply() {
    }

    private int id;

    @javax.persistence.Column(name = "id")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private Timestamp time;

    @javax.persistence.Column(name = "time")
    @Basic
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    private String text;

    @javax.persistence.Column(name = "text")
    @Basic
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private Thread parentThread;

    @ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name = "threadId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
        public Thread getParentThread() {
        return parentThread;
    }

    @Deprecated
    public void setParentThread(Thread parentThread) {
        this.parentThread = parentThread;
    }



    private Account account;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "accountId", referencedColumnName = "id", nullable = false)
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reply reply = (Reply) o;

        if (id != reply.id) return false;
        if (text != null ? !text.equals(reply.text) : reply.text != null) return false;
        if (time != null ? !time.equals(reply.time) : reply.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    private List<ThumbsUp> thumbsUp;

    @OneToMany(mappedBy = "reply")
    public List<ThumbsUp> getThumbsUp() {
        return thumbsUp;
    }

    public void setThumbsUp(List<ThumbsUp> thumbsUp) {
        this.thumbsUp = thumbsUp;
    }
}
