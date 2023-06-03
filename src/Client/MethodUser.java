package Client;

import java.time.LocalDateTime;
import java.util.Date;

public class MethodUser {
    public void SetPhoneNumber(user user, String StringPhoneOrEmail) {
        user.setPhoneNumber(StringPhoneOrEmail);
    }

    public void SetEmail(user user, String StringPhoneOrEmail) {
        user.setEmail(StringPhoneOrEmail);
    }

    public void SetDate(user user, LocalDateTime Date) {
        user.setLastChangeDate(Date);
    }


    public void SetBio(user user, String Bio) {
        user.setBio(Bio);
    }

    public void SetLocation(user user, String Location) {
        user.setLocation(Location);
    }

    public void SetWebAddress(user user, String WebAddress) {
        user.setWebAddress(WebAddress);
    }


    public void SetDescription(user user, String Bio) {
        SetBio(user, Bio);
    }

    public void SetDescription(user user, String Bio, String Location) {
        SetBio(user, Bio);
        SetLocation(user, Location);
    }

    public void SetDescription(user user, String Bio, String Location, String WebAddress) {
        SetBio(user, Bio);
        SetLocation(user, Location);
        SetWebAddress(user, WebAddress);
    }


}
