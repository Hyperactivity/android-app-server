package models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-22
 * Time: 11:05
 */
@Entity
public class Category implements Serializable {
    static final long serialVersionUID = 2L;
    private int id;
    private String headLine;
    private int colorCode;
    private Category parentCategory;
    private List<Category> childCategories;
    private List<Thread> threads;

    public Category(String headLine, int colorCode, Category parentCategory) {
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

    @javax.persistence.Column(name = "headLine")
    @Basic
    public String getHeadLine() {
        return headLine;
    }

    public void setHeadLine(String headLine) {
        this.headLine = headLine;
    }

    @javax.persistence.Column(name = "colorCode")
    @Basic
    public int getColorCode() {
        return colorCode;
    }

    public void setColorCode(int colorCode) {
        this.colorCode = colorCode;
    }

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "parentCategoryId")
    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    @OneToMany(mappedBy = "parentCategory", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    public List<Category> getChildCategories() {
        return childCategories;
    }

    public void setChildCategories(List<Category> childCategories) {
        this.childCategories = childCategories;
    }

    @OneToMany(mappedBy = "parentCategory", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    public List<Thread> getThreads() {
        return threads;
    }

    public void setThreads(List<Thread> replies) {
        this.threads = replies;
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
