package Client;

import java.time.LocalDateTime;
import java.util.Date;

public class user {

    private String UserName;
    private String FirstName;
    private String LastName;
    private String Email;
    private String PhoneNumber;
    private String Password;
    private String Country;
    private String BirthDate;
    private LocalDateTime InComeDate;
    private LocalDateTime LastChangeDate;
    private String Bio;
    private String Location;
    private String WebAddress;
    private String JWT;


    public user(String userName, String firstName, String lastName, String password, String country, String birthDate, String PhoneOrEmail, String StringPhoneOrEmail) {
        UserName = userName;
        FirstName = firstName;
        LastName = lastName;
        Country = country;
        Password = password;
        BirthDate = birthDate;
        InComeDate = LocalDateTime.now();
        LastChangeDate = InComeDate;
        if (PhoneOrEmail.equals("1")) setEmail(StringPhoneOrEmail);
        if (PhoneOrEmail.equals("2")) setPhoneNumber(StringPhoneOrEmail);
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
