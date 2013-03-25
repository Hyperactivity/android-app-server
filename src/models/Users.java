package models;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-03-25
 * Time: 14:42
 */
@Entity
public class Users {
    private int userId;

    @javax.persistence.Column(name = "userId")
    @Id
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private String profileDescription;

    @javax.persistence.Column(name = "profileDescription")
    @Basic
    public String getProfileDescription() {
        return profileDescription;
    }

    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
    }

    private Date birthDate;

    @javax.persistence.Column(name = "birthDate")
    @Basic
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    private int limitPerDay;

    @javax.persistence.Column(name = "limitPerDay")
    @Basic
    public int getLimitPerDay() {
        return limitPerDay;
    }

    public void setLimitPerDay(int limitPerDay) {
        this.limitPerDay = limitPerDay;
    }

    private boolean useDefaulyColors;

    @javax.persistence.Column(name = "useDefaulyColors")
    @Basic
    public boolean isUseDefaulyColors() {
        return useDefaulyColors;
    }

    public void setUseDefaulyColors(boolean useDefaulyColors) {
        this.useDefaulyColors = useDefaulyColors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Users users = (Users) o;

        if (limitPerDay != users.limitPerDay) return false;
        if (useDefaulyColors != users.useDefaulyColors) return false;
        if (userId != users.userId) return false;
        if (birthDate != null ? !birthDate.equals(users.birthDate) : users.birthDate != null) return false;
        if (profileDescription != null ? !profileDescription.equals(users.profileDescription) : users.profileDescription != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (profileDescription != null ? profileDescription.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + limitPerDay;
        result = 31 * result + (useDefaulyColors ? 1 : 0);
        return result;
    }
}
