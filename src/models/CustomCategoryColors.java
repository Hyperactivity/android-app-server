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
@javax.persistence.IdClass(models.CustomCategoryColorsPK.class)
@Entity
public class CustomCategoryColors {
    private int userId;

    @javax.persistence.Column(name = "userId")
    @Id
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private int categoryId;

    @javax.persistence.Column(name = "categoryId")
    @Id
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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

        CustomCategoryColors that = (CustomCategoryColors) o;

        if (categoryColorCode != that.categoryColorCode) return false;
        if (categoryId != that.categoryId) return false;
        if (userId != that.userId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + categoryId;
        result = 31 * result + categoryColorCode;
        return result;
    }
}
