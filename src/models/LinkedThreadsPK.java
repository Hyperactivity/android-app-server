package models;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-03-25
 * Time: 14:42
 */
public class LinkedThreadsPK implements Serializable {
    private int userId;

    @Id
    @Column(name = "userId")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private int linkedThreadId;

    @Id
    @Column(name = "linkedThreadId")
    public int getLinkedThreadId() {
        return linkedThreadId;
    }

    public void setLinkedThreadId(int linkedThreadId) {
        this.linkedThreadId = linkedThreadId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LinkedThreadsPK that = (LinkedThreadsPK) o;

        if (linkedThreadId != that.linkedThreadId) return false;
        if (userId != that.userId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + linkedThreadId;
        return result;
    }
}
