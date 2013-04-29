package models;

import javax.persistence.*;
import java.io.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-16
 * Time: 13:10
 */
@Entity
public class PrivateCategory implements Externalizable {
    static final long serialVersionUID = 6L;
    private int id;
    private int colorCode;
    private Category parentPrivateCategory;
    private Account account;
    private String headLine;
    private List<Note> notes;
    private List<LinkedThread> linkedThreads;

    public PrivateCategory(String headLine, int colorCode, Category parentPrivateCategory, Account account) {
        this.headLine = headLine;
        this.colorCode = colorCode;
        this.parentPrivateCategory = parentPrivateCategory;
        this.account = account;
    }

    public PrivateCategory() {
    }

    @Column(name = "id")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "colorCode")
    @Basic
    public int getColorCode() {
        return colorCode;
    }

    public void setColorCode(int colorCode) {
        this.colorCode = colorCode;
    }

    @ManyToOne
    @JoinColumn(name = "parentPrivateCategoryId", referencedColumnName = "id", nullable = false)
    public Category getParentPrivateCategory() {
        return parentPrivateCategory;
    }

    public void setParentPrivateCategory(Category parentPrivateCategory) {
        this.parentPrivateCategory = parentPrivateCategory;
    }

    @ManyToOne
    @JoinColumn(name = "accountId", referencedColumnName = "id", nullable = false)
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Column(name = "headLine")
    @Basic
    public String getHeadLine() {
        return headLine;
    }

    public void setHeadLine(String headLine) {
        this.headLine = headLine;
    }

    @OneToMany(mappedBy = "parentPrivateCategory", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> replies) {
        this.notes = replies;
    }

    @OneToMany(mappedBy = "parentPrivateCategory", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    public List<LinkedThread> getLinkedThreads() {
        return linkedThreads;
    }

    public void setLinkedThreads(List<LinkedThread> linkedThreads) {
        this.linkedThreads = linkedThreads;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrivateCategory that = (PrivateCategory) o;

        if (colorCode != that.colorCode) return false;
        if (id != that.id) return false;
        if (headLine != null ? !headLine.equals(that.headLine) : that.headLine != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + colorCode;
        result = 31 * result + (headLine != null ? headLine.hashCode() : 0);
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
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(id);
        out.writeInt(colorCode);
        out.writeObject(parentPrivateCategory);
        out.writeObject(account);
        out.writeUTF(headLine);
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
