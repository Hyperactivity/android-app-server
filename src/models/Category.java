package models;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-16
 * Time: 13:10
 */
@Entity
public class Category {
    private int id;

    public Category(int id, String headLine, int colorCode, Category parentCategory) {
        this.id = id;
        this.headLine = headLine;
        this.colorCode = colorCode;
        this.parentCategory = parentCategory;
    }

    @Deprecated
    public Category() {
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

    private int colorCode;

    @javax.persistence.Column(name = "colorCode")
    @Basic
    public int getColorCode() {
        return colorCode;
    }

    public void setColorCode(int colorCode) {
        this.colorCode = colorCode;
    }

    private Category parentCategory;

    @ManyToOne
    @JoinColumn(name = "parentCategoryId", referencedColumnName = "id", nullable = false)
    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (colorCode != category.colorCode) return false;
        if (id != category.id) return false;
        if (headLine != null ? !headLine.equals(category.headLine) : category.headLine != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (headLine != null ? headLine.hashCode() : 0);
        result = 31 * result + colorCode;
        return result;
    }
}
