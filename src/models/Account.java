package models;

import org.hibernate.id.IncrementGenerator;

import javax.persistence.*;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.sql.Date;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-16
 * Time: 13:10
 */
@Entity
public class Account{
    private int id;
    private String profileDescription;
    private Date birthDate;
    private int limitPerDay;
    private boolean useDefaultColors;
    private int facebookId;
    private String username;
    private boolean showBirthDate;

    public Account(String username, int facebookId, String profileDescription, Date birthDate, int limitPerDay, boolean useDefaultColors, boolean showBirthDate) {
        this.username = username;
        this.facebookId = facebookId;
        this.profileDescription = profileDescription;
        this.birthDate = birthDate;
        this.limitPerDay = limitPerDay;
        this.useDefaultColors = useDefaultColors;
        this.showBirthDate = showBirthDate;
    }

    @Deprecated
    public Account() {
    }

    @Column(name = "id")
    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "profileDescription")
    @Basic
    public String getProfileDescription() {
        return profileDescription;
    }

    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
    }

    @Column(name = "birthDate")
    @Basic
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Column(name = "limitPerDay")
    @Basic
    public int getLimitPerDay() {
        return limitPerDay;
    }

    public void setLimitPerDay(int limitPerDay) {
        this.limitPerDay = limitPerDay;
    }

    @Column(name = "useDefaultColors")
    @Basic
    public boolean isUseDefaultColors() {
        return useDefaultColors;
    }

    public void setUseDefaultColors(boolean useDefaultColors) {
        this.useDefaultColors = useDefaultColors;
    }

    @Column(name = "facebookId")
    @Basic
    public int getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(int facebookId) {
        this.facebookId = facebookId;
    }

    @Column(name = "username")
    @Basic
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "showBirthDate")
    @Basic
    public boolean isShowBirthDate() {
        return showBirthDate;
    }

    public void setShowBirthDate(boolean showBirthDate) {
        this.showBirthDate = showBirthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (id != account.id) return false;
        if (facebookId != account.facebookId) return false;
        if (limitPerDay != account.limitPerDay) return false;
        if (showBirthDate != account.showBirthDate) return false;
        if (useDefaultColors != account.useDefaultColors) return false;
        if (birthDate != null ? !birthDate.equals(account.birthDate) : account.birthDate != null) return false;
        if (profileDescription != null ? !profileDescription.equals(account.profileDescription) : account.profileDescription != null)
            return false;
        if (username != null ? !username.equals(account.username) : account.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (profileDescription != null ? profileDescription.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + limitPerDay;
        result = 31 * result + (useDefaultColors ? 1 : 0);
        result = 31 * result + facebookId;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (showBirthDate ? 1 : 0);
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
//        out.writeObject(profileDescription);
//        out.writeObject(birthDate);
//        out.writeInt(limitPerDay);
//        out.writeBoolean(useDefaultColors);
//        out.writeInt(facebookId);
//        out.writeObject(username);
//        out.writeBoolean(showBirthDate);
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
//        //To change body of implemented methods use File | Settings | File Templates.
//    }
}
