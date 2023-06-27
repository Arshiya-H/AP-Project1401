package Client;

import java.time.LocalDateTime;
import java.util.Date;

public class MethodUser {
    public void SetPhoneNumber(User User, String StringPhoneOrEmail) {
        User.setPhoneNumber(StringPhoneOrEmail);
    }

    public void SetEmail(User User, String StringPhoneOrEmail) {
        User.setEmail(StringPhoneOrEmail);
    }

    public void SetDate(User User, LocalDateTime Date) {
        User.setLastChangeDate(Date);
    }


    public void SetBio(User User, String Bio) {
        User.setBio(Bio);
    }

    public void SetLocation(User User, String Location) {
        User.setLocation(Location);
    }

    public void SetWebAddress(User User, String WebAddress) {
        User.setWebAddress(WebAddress);
    }


    public void SetDescription(User User, String Bio) {
        SetBio(User, Bio);
    }

    public void SetDescription(User User, String Bio, String Location) {
        SetBio(User, Bio);
        SetLocation(User, Location);
    }

    public void SetDescription(User User, String Bio, String Location, String WebAddress) {
        SetBio(User, Bio);
        SetLocation(User, Location);
        SetWebAddress(User, WebAddress);
    }


}
