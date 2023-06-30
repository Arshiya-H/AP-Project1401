package Client;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User {

    private String UserName;
    private String FirstName;
    private String LastName;
    private String Email;
    private String PhoneNumber;
    private String Password;
    private String Country;
    private String BirthDate;
    private String InComeDate;
    private String LastChangeDate;
    private String Bio;
    private String Location;
    private String WebAddress;
    private String JWT;
    private byte[] avatar;
    private byte[] header;

    public User(String userName, String firstName, String lastName, String email, String phoneNumber, String password, String country
            , String birthDate, String inComeDate, String lastChangeDate, byte[] avatar, byte[] header, String bio, String location
            , String webAddress, String JWT) {
        UserName = userName;
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        PhoneNumber = phoneNumber;
        Password = password;
        Country = country;
        BirthDate = birthDate;
        InComeDate = inComeDate;
        LastChangeDate = lastChangeDate;
        this.avatar = avatar;
        this.header = header;
        Bio = bio;
        Location = location;
        WebAddress = webAddress;
        this.JWT = JWT;
    }

    public User() {
    }

    public String getUserName() {
        return UserName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setJWT(String JWT) {
        this.JWT = JWT;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public void setLastChangeDate(String lastChangeDate) {
        LastChangeDate = lastChangeDate;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public void setWebAddress(String webAddress) {
        WebAddress = webAddress;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }

    public void setInComeDate(String inComeDate) {
        InComeDate = inComeDate;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public void setHeader(byte[] header) {
        this.header = header;
    }
}
