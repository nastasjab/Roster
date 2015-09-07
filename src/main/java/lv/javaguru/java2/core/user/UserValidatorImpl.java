package lv.javaguru.java2.core.user;


import lv.javaguru.java2.core.ObjectExistException;
import lv.javaguru.java2.database.user.UserDAO;
import lv.javaguru.java2.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidatorImpl implements UserValidator {

    @Autowired
    private UserDAO userDAO;

    public void validateLogin(String userName, boolean add) throws Exception {

        if (userName == null)
            throw new Exception("Login cannot be empty");

        if (add)
            for (User user : userDAO.getAll())
                if (userName.equals(user.getLogin()))
                    throw new ObjectExistException("user with such login");

    }

    public void validateFirstAndLastNames(String firtsName, String lastName, boolean add) throws Exception {

        if (firtsName == null || firtsName.length() < 1)
            throw new Exception("First name cannot be empty");

        if (lastName == null || lastName.length() < 1)
            throw new Exception("Last name cannot be empty");

        if (add)
            for (User user : userDAO.getAll())
                if (firtsName.equals(user.getFirstName()) && lastName.equals(user.getLastName()))
                    throw new ObjectExistException("user with such first and last name");
    }

    /*
    private boolean isValidPhone(String phone) {
        String ePattern = "^[0-9 +#-]+$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(phone);
        return m.matches();
    }

    private boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
    */
}
