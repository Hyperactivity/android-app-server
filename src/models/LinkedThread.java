package models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-16
 * Time: 13:10
 */
@Entity
public class LinkedThread implements Serializable {
    static final long serialVersionUID = 4L;

    private int id;

    public LinkedThread(int id, String headLine, Category parentPrivateCategory, Account account) {
        this.id = id;
        this.headLine = headLine;
        this.parentPrivateCategory = parentPrivateCategory;
        this.account = account;
    }

    @Deprecated
    public LinkedThread() {
    }

    @javax.persistence.Column(name = "id")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String headLine;

    @javax.persistence.Column(name = "headLine")
    @Basic
    public String getHeadLine() {
        return headLine;
    }

    public void setHeadLine(String headLine) {
        this.headLine = headLine;
    }

    private Category parentPrivateCategory;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "parentPrivateCategoryId", referencedColumnName = "id", nullable = false)
    public Category getParentPrivateCategory() {
        return parentPrivateCategory;
    }

    public void setParentPrivateCategory(Category parentPrivateCategory) {
        this.parentPrivateCategory = parentPrivateCategory;
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
