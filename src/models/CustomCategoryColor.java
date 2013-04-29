package models;

import javax.persistence.*;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-16
 * Time: 13:10
 */
@javax.persistence.IdClass(models.CustomCategoryColorPK.class)
@Entity
public class CustomCategoryColor implements Externalizable {
    static final long serialVersionUID = 3L;
    private int accountId;
    private int categoryId;
    private int colorCode;
    private Account account;
    private Category category;

    public CustomCategoryColor(int accountId, int categoryId, int colorCode) {
        setAccountId(accountId);
        setCategoryId(categoryId);
        setColorCode(colorCode);
    }

    @Deprecated
    public CustomCategoryColor() {
    }

    @javax.persistence.Column(name = "accountId")
    @Id
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @javax.persistence.Column(name = "categoryId")
    @Id
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @javax.persistence.Column(name = "colorCode")
    @Basic
    public int getColorCode() {
        return colorCode;
    }

    public void setColorCode(int colorCode) {
        this.colorCode = colorCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomCategoryColor that = (CustomCategoryColor) o;

        if (accountId != that.accountId) return false;
        if (categoryId != that.categoryId) return false;
        if (colorCode != that.colorCode) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = accountId;
        result = 31 * result + categoryId;
        result = 31 * result + colorCode;
        return result;
    }

     @ManyToOne
     @JoinColumn(name = "accountId", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
     public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @OneToOne
    @JoinColumn(name = "categoryId", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(colorCode);
        out.writeObject(account);
        out.writeObject(category);
    }

    /**
     * The object implements the readExternal method to restore its
     * contents by calling the methods of DataInput for primitive
     * types and readObject for objects, strings and arrays.  The
     * readExternal method must read the values in the same sequence
     * and with the same types as were written by writeExternal.
     *
     * @param in the stream to read data from in order to restore the object
     * @throws java.io.IOException    if I/O errors occur
     * @throws ClassNotFoundException If the class for an object being
     *                                restored cannot be found.
     */
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
