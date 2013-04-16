package models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-03-25
 * Time: 14:42
 */
@Entity
public class Account implements Serializable {
    private int userId;

    @Column(name = "accountId")
    @Id
    @GeneratedValue
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    //Check if
    public Account(String username, int facebookId, String profileDescription, Date birthDate, int limitPerDay, boolean useDefaultColors, boolean showBirthDate) {
        this.username = username;
        this.facebookId = facebookId;
        this.profileDescription = profileDescription;
        this.birthDate = birthDate;
        this.limitPerDay = limitPerDay;
        this.useDefaultColors = useDefaultColors;
        this.showBirthDate = showBirthDate;
    }

    public Account() {
    }


    private String profileDescription;

    @Column(name = "profileDescription")
    @Basic
    public String getProfileDescription() {
        return profileDescription;
    }

    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
    }

    private Date birthDate;

    @Column(name = "birthDate")
    @Basic
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    private int limitPerDay;

    @Column(name = "limitPerDay")
    @Basic
    public int getLimitPerDay() {
        return limitPerDay;
    }

    public void setLimitPerDay(int limitPerDay) {
        this.limitPerDay = limitPerDay;
    }

    private boolean useDefaultColors;

    @Column(name = "useDefaultColors")
    @Basic
    public boolean isUseDefaultColors() {
        return useDefaultColors;
    }

    public void setUseDefaultColors(boolean useDefaultColors) {
        this.useDefaultColors = useDefaultColors;
    }

    private int facebookId;

    @Column(name = "facebookId")
    @Basic
    public int getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(int facebookId) {
        this.facebookId = facebookId;
    }

    private String username;

    @Column(name = "username")
    @Basic
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private boolean showBirthDate;

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

        if (facebookId != account.facebookId) return false;
        if (limitPerDay != account.limitPerDay) return false;
        if (showBirthDate != account.showBirthDate) return false;
        if (useDefaultColors != account.useDefaultColors) return false;
        if (userId != account.userId) return false;
        if (birthDate != null ? !birthDate.equals(account.birthDate) : account.birthDate != null) return false;
        if (profileDescription != null ? !profileDescription.equals(account.profileDescription) : account.profileDescription != null)
            return false;
        if (username != null ? !username.equals(account.username) : account.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (profileDescription != null ? profileDescription.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + limitPerDay;
        result = 31 * result + (useDefaultColors ? 1 : 0);
        result = 31 * result + facebookId;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (showBirthDate ? 1 : 0);
        return result;
    }
}
