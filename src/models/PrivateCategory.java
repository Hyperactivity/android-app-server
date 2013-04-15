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
@javax.persistence.IdClass(models.PrivateCategoriesPK.class)
@Entity
public class PrivateCategory implements Serializable {
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

    private int privateCategoryId;

    @javax.persistence.Column(name = "privateCategoryId")
    @Id
    public int getPrivateCategoryId() {
        return privateCategoryId;
    }

    public void setPrivateCategoryId(int privateCategoryId) {
        this.privateCategoryId = privateCategoryId;
    }

    private int categoryColorCode;

    @javax.persistence.Column(name = "categoryColorCode")
    @Basic
    public int getCategoryColorCode() {
        return categoryColorCode;
    }

    public void setCategoryColorCode(int categoryColorCode) {
        this.categoryColorCode = categoryColorCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrivateCategory that = (PrivateCategory) o;

        if (categoryColorCode != that.categoryColorCode) return false;
        if (privateCategoryId != that.privateCategoryId) return false;
        if (privateCategoryParentId != that.privateCategoryParentId) return false;
        if (userId != that.userId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + privateCategoryParentId;
        result = 31 * result + privateCategoryId;
        result = 31 * result + categoryColorCode;
        return result;
    }
}
