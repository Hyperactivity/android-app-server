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
@javax.persistence.IdClass(models.LinkedThreadsPK.class)
@Entity
public class LinkedThreads {
    private int userId;

    @javax.persistence.Column(name = "userId")
    @Id
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

    private int linkedThreadId;

    @javax.persistence.Column(name = "linkedThreadId")
    @Id
    public int getLinkedThreadId() {
        return linkedThreadId;
    }

    public void setLinkedThreadId(int linkedThreadId) {
        this.linkedThreadId = linkedThreadId;
    }

    private String linkedThreadName;

    @javax.persistence.Column(name = "linkedThreadName")
    @Basic
    public String getLinkedThreadName() {
        return linkedThreadName;
    }

    public void setLinkedThreadName(String linkedThreadName) {
        this.linkedThreadName = linkedThreadName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LinkedThreads that = (LinkedThreads) o;

        if (linkedThreadId != that.linkedThreadId) return false;
        if (privateCategoryParentId != that.privateCategoryParentId) return false;
        if (userId != that.userId) return false;
        if (linkedThreadName != null ? !linkedThreadName.equals(that.linkedThreadName) : that.linkedThreadName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + privateCategoryParentId;
        result = 31 * result + linkedThreadId;
        result = 31 * result + (linkedThreadName != null ? linkedThreadName.hashCode() : 0);
        return result;
    }
}
