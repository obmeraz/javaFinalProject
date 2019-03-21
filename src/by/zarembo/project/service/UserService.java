package by.zarembo.project.service;

import by.zarembo.project.dao.impl.UserDaoImpl;
import by.zarembo.project.entity.RoleType;
import by.zarembo.project.entity.User;
import by.zarembo.project.exception.DaoException;
import by.zarembo.project.exception.ServiceException;
import by.zarembo.project.util.EmailSender;
import by.zarembo.project.util.EmailType;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;
import java.util.Optional;

/**
 * The type User service.
 */
public class UserService {
    private static final String ACTIVATED = "Yes";

    /**
     * Sign up user.
     *
     * @param user the user
     * @throws ServiceException the service exception
     */
    public void signUpUser(User user) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            userDao.create(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Build entity user user.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @param nickname  the nickname
     * @param email     the email
     * @param password  the password
     * @return the user
     */
    public User buildUser(String firstName, String lastName, String nickname, String email,
                          String password) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setNickName(nickname);
        user.setEmail(email);
        String passwordHash = DigestUtils.sha256Hex(password);
        user.setPassword(passwordHash);
        user.setRoleEnum(RoleType.USER);
        return user;
    }

    /**
     * Take user by email and password optional.
     *
     * @param email    the email
     * @param password the password
     * @return the optional
     * @throws ServiceException the service exception
     */
    public Optional<User> takeUserByEmailAndPassword(String email, String password) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        Optional<User> userOptional;
        try {
            userOptional = userDao.findUserByEmailAndPassword(email, password);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return userOptional;
    }

    /**
     * Take user by password optional.
     *
     * @param password the password
     * @return the optional
     * @throws ServiceException the service exception
     */
    public Optional<User> takeUserByPassword(String password) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        Optional<User> userOptional;
        try {
            userOptional = userDao.findUserByPassword(password);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return userOptional;
    }

    /**
     * Check is email already exists in database boolean.
     *
     * @param email the email
     * @return the boolean
     * @throws ServiceException the service exception
     */
    public boolean checkIsEmailAlreadyExists(String email) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            return userDao.checkEmail(email);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Check is user name already exists boolean.
     *
     * @param userName the user name
     * @return the boolean
     * @throws ServiceException the service exception
     */
    public boolean checkIsUserNameAlreadyExists(String userName) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            return userDao.checkUserName(userName);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Take user by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws ServiceException the service exception
     */
    public Optional<User> takeUserById(long id) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        Optional<User> userOptional;
        try {
            userOptional = userDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException("Can't find lifehack by id", e);
        }
        return userOptional;
    }

    /**
     * Give user admin rights.
     *
     * @param user the user
     * @throws ServiceException the service exception
     */
    public void giveUserAdminRights(User user) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        user.setRoleEnum(RoleType.ADMIN);
        try {
            userDao.update(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }

    /**
     * Remove admin rights from user.
     *
     * @param user the user
     * @throws ServiceException the service exception
     */
    public void removeAdminRights(User user) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        user.setRoleEnum(RoleType.USER);
        try {
            userDao.update(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }

    /**
     * Activate user account.
     *
     * @param id the id
     * @throws ServiceException the service exception
     */
    public void activateUserAccount(long id) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            userDao.activateUserAccount(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Is account activated boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    public boolean isAccountActivated(long id) throws ServiceException {
        boolean isActivated = false;
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            String activated = userDao.takeAccountActivateInformation(id);
            if (ACTIVATED.equals(activated)) {
                isActivated = true;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return isActivated;
    }

    /**
     * Take all users list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<User> takeAllUsers() throws ServiceException {
        List<User> users;
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            users = userDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Can't final all users", e);
        }
        return users;
    }

    /**
     * Delete user by id.
     *
     * @param id the id
     * @throws ServiceException the service exception
     */
    public void deleteUserById(long id) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            userDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Change password boolean.
     *
     * @param oldPassword the old password
     * @param newPassword the new password
     * @param user        the user
     * @return the boolean
     * @throws ServiceException the service exception
     */
    public boolean changePassword(String oldPassword, String newPassword, User user) throws ServiceException {
        if (!user.getPassword().equals(DigestUtils.sha256Hex(oldPassword))) {
            return false;
        }
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        String newPasswordHash = DigestUtils.sha256Hex(newPassword);
        user.setPassword(newPasswordHash);
        try {
            userDao.update(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return true;
    }

    /**
     * Change email.
     *
     * @param newEmail the new email
     * @param user     the user
     * @throws ServiceException the service exception
     */
    public void changeEmail(String newEmail, User user) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        user.setEmail(newEmail);
        try {
            userDao.update(user);
            EmailSender emailSender = new EmailSender();
            emailSender.sendEmail(user, EmailType.CHANGE_EMAIL);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
