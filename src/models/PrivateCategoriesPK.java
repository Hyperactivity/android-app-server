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
public class PrivateCategoriesPK implements Serializable {
    private int userId;

    @Id
    @Column(name = "userId")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private int privateCategoryId;

    @Id
    @Column(name = "privateCategoryId")
    public int getPrivateCategoryId() {
        return privateCategoryId;
    }

    public void setPrivateCategoryId(int privateCategoryId) {
        this.privateCategoryId = privateCategoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrivateCategoriesPK that = (PrivateCategoriesPK) o;

        if (privateCategoryId != that.privateCategoryId) return false;
        if (userId != that.userId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + privateCategoryId;
        return result;
    }
}
