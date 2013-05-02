package models;

import javax.persistence.*;
import java.awt.*;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-22
 * Time: 11:05
 */
@Entity
public class Category {
    private int id;
    private String headLine;
    private int colorCode;
    private Category parentCategory;
    private transient Collection<Category> childCategories;
    private transient Collection<Thread> threads;

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
    @GeneratedValue
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

    @ManyToOne(optional = true)
    @JoinColumn(name = "parentCategoryId")
    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.REMOVE)
    public Collection<Category> getChildCategories() {
        return childCategories;
    }

    public void setChildCategories(Collection<Category> childCategories) {
        this.childCategories = childCategories;
    }

    @OneToMany(mappedBy = "parentCategory")
    public Collection<Thread> getThreads() {
        return threads;
    }

    public void setThreads(Collection<Thread> threads) {
        this.threads = threads;
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

    /**
     * The object implements the writeExternal method to save its contents
     * by calling the methods of DataOutput for its primitive values or
     * calling the writeObject method of ObjectOutput for objects, strings,
     * and arrays.
     *
     * @param out the stream to write the object to
     * @throws java.io.IOException Includes any I/O exceptions that may occur
     * @serialData Overriding methods should use this tag to describe
     * the data layout of this Externalizable object.
     * List the sequence of element types and, if possible,
     * relate the element to a public/protected field and/or
     * method of this Externalizable class.
     */
//    @Override
//    public void writeExternal(ObjectOutput out) throws IOException {
//        out.writeInt(id);
//        out.writeObject(headLine);
//        out.writeInt(colorCode);
//        out.writeObject(parentCategory);
//    }
//
//    /**
//     * The object implements the readExternal method to restore its
//     * contents by calling the methods of DataInput for primitive
//     * types and readObject for objects, strings and arrays.  The
//     * readExternal method must read the values in the same sequence
//     * and with the same types as were written by writeExternal.
//     *
//     * @param in the stream to read data from in order to restore the object
//     * @throws java.io.IOException    if I/O errors occur
//     * @throws ClassNotFoundException If the class for an object being
//     *                                restored cannot be found.
//     */
//    @Override
//    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
//    }
}
