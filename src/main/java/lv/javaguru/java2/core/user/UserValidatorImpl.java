package lv.javaguru.java2.core.user;


import lv.javaguru.java2.core.EmptyObjectNameException;
import lv.javaguru.java2.core.ObjectExistException;
import lv.javaguru.java2.database.user.UserDAO;
import lv.javaguru.java2.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidatorImpl implements UserValidator {

    @Autowired
    private UserDAO userDAO;

    public void validateLogin(User user) throws Exception {

        if (user.getLogin() == null || user.getLogin().length() < 1)
            throw new EmptyObjectNameException("user login");

        for (User userFromDB : userDAO.getAll())
            if (user.getId() != userFromDB.getId()
                    && user.getLogin().equals(userFromDB.getLogin()))
                throw new ObjectExistException("user with such login");

    }

    public void validateFirstAndLastNames(User user) throws Exception {

        if (user.getFirstName() == null || user.getFirstName().length() < 1)
            throw new EmptyObjectNameException("user first");

        if (user.getLastName() == null || user.getLastName().length() < 1)
            throw new EmptyObjectNameException("user last");

        for (User userFromDB : userDAO.getAll())
            if (user.getId() != userFromDB.getId()
                    && user.getFirstName().equals(userFromDB.getFirstName())
                    && user.getLastName().equals(userFromDB.getLastName()))
                throw new ObjectExistException("user with such first and last name");
    }

    public void validatePhone(User user) throws Exception {
        String ePattern = "^[0-9 +#-]+$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(user.getPhone());
        if (user.getPhone().length() != 0 && !m.matches())
            throw new InvalidPhoneException();

        if (!user.getPhone().isEmpty())
            for (User userFromDB : userDAO.getAll())
                if (user.getId() != userFromDB.getId()
                        && user.getPhone().equals(userFromDB.getPhone()))
                    throw new ObjectExistException("user with such phone");
    }

    public void validateEmail(User user) throws Exception {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(user.getEmail());
        if (user.getEmail().length() !=0 && !m.matches())
            throw new InvalidEmailException();

        if (!user.getEmail().isEmpty())
            for (User userFromDB : userDAO.getAll())
                if (user.getId() != userFromDB.getId()
                        && user.getEmail().equals(userFromDB.getEmail()))
                    throw new ObjectExistException("user with such email");
    }

}
