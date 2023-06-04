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
    private LocalDateTime LastChangeDate;
    private String Bio;
    private String Location;
    private String WebAddress;
    private String JWT;


    public User(String userName, String firstName, String lastName, String password, String country, String birthDate, String PhoneOrEmail, String phoneNumber, String email) {
        UserName = userName;
        FirstName = firstName;
        LastName = lastName;
        Country = country;
        Password = password;
        BirthDate = birthDate;
        InComeDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        LastChangeDate = LocalDateTime.now();
        if (PhoneOrEmail.equals("1") || PhoneOrEmail.equals("3")) setEmail(email);
        if (PhoneOrEmail.equals("2") || PhoneOrEmail.equals("3")) setPhoneNumber(phoneNumber);
    }


    public void setJWT(String JWT) {
        this.JWT = JWT;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public void setLastChangeDate(LocalDateTime lastChangeDate) {
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

}
