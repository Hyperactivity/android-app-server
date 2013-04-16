package models;

import assistant.GeneralResponse;

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
public class Category extends GeneralResponse implements Serializable {
    private int parentCategoryId;

    @javax.persistence.Column(name = "parentCategoryId")
    @Basic
    public int getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(int parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    private int categoryId;

    @javax.persistence.Column(name = "id")
    @Id
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    private String categoryName;

    @javax.persistence.Column(name = "headLine")
    @Basic
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    private int categoryColorCode;

    @javax.persistence.Column(name = "colorCode")
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

        Category that = (Category) o;

        if (categoryColorCode != that.categoryColorCode) return false;
        if (categoryId != that.categoryId) return false;
        if (parentCategoryId != that.parentCategoryId) return false;
        if (categoryName != null ? !categoryName.equals(that.categoryName) : that.categoryName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = parentCategoryId;
        result = 31 * result + categoryId;
        result = 31 * result + (categoryName != null ? categoryName.hashCode() : 0);
        result = 31 * result + categoryColorCode;
        return result;
    }
}
