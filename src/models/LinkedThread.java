package models;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-16
 * Time: 13:10
 */
@Entity
public class LinkedThread{
    private int id;
    private String headLine;
    private PrivateCategory parentPrivateCategory;
    private Account account;
    private Thread thread;

    public LinkedThread(String headLine, PrivateCategory parentPrivateCategory, Account account, Thread thread) {
        setHeadLine(headLine);
        setParentPrivateCategory(parentPrivateCategory);
        setAccount(account);
        setThread(thread);
    }

    @Deprecated
    public LinkedThread() {
    }

    @Column(name = "id")
    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "headLine")
    @Basic
    public String getHeadLine() {
        return headLine;
    }

    public void setHeadLine(String headLine) {
        this.headLine = headLine;
    }

    @ManyToOne
    @JoinColumn(name = "parentPrivateCategoryId", referencedColumnName = "id", nullable = false)
    public PrivateCategory getParentPrivateCategory() {
        return parentPrivateCategory;
    }

    public void setParentPrivateCategory(PrivateCategory parentPrivateCategory) {
        this.parentPrivateCategory = parentPrivateCategory;
    }

    @ManyToOne
    @JoinColumn(name = "accountId", referencedColumnName = "id", nullable = false)
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @ManyToOne
    @JoinColumn(name = "threadId", referencedColumnName = "id", nullable = false)
    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LinkedThread that = (LinkedThread) o;

        if (id != that.id) return false;
        if (headLine != null ? !headLine.equals(that.headLine) : that.headLine != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (headLine != null ? headLine.hashCode() : 0);
        return result;
    }
}
