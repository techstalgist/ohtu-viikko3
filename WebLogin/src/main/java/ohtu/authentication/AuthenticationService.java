package ohtu.authentication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ohtu.data_access.UserDao;
import ohtu.domain.User;
import ohtu.util.CreationStatus;

public class AuthenticationService {

    private UserDao userDao;

    public AuthenticationService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean logIn(String username, String password) {
        for (User user : userDao.listAll()) {
            if (user.getUsername().equals(username)
                    && user.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }

    public CreationStatus createUser(String username, String password, String passwordConfirmation) {
        CreationStatus status = new CreationStatus();
        
        if (userDao.findByName(username) != null) {
            status.addError("username is already taken");
        }
        
        validateUsername(username, status);       
       
        validatePasswordAndConfirmation(password, passwordConfirmation, status);

        if (status.isOk()) {
            userDao.add(new User(username, password));
        }
        
        return status;
    }
    
    private void validateUsername(String username, CreationStatus status) {
        if (username.length()<3 ) {
            status.addError("username should have at least 3 characters");
        }
        
        Pattern p = Pattern.compile("[a-z]*");
        Matcher m = p.matcher(username);
        if (!m.matches()) {
            status.addError("username can only contain English alphabets.");
        }
    }

    private void validatePasswordAndConfirmation(String password, String confirmation, CreationStatus status) {
        if (password.length() < 8) { 
            status.addError("password should have at least 8 characters");
        }
        
        if (!password.equals(confirmation)) {
            status.addError("password and password confirmation do not match");
        }
        
        boolean foundDigitOrSpecialChar = false;
        for (char c : password.toCharArray()) {
            if (!Character.isLetter(c)) {
                foundDigitOrSpecialChar = true;
            }
        }
        
        if (!foundDigitOrSpecialChar) {
            status.addError("password must contain at least one digit or special character");
        }
    }
    
}
