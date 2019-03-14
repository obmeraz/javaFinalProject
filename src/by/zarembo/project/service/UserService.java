package by.zarembo.project.service;

import by.zarembo.project.dao.impl.UserDaoImpl;
import by.zarembo.project.entity.RoleType;
import by.zarembo.project.entity.User;
import by.zarembo.project.exception.DaoException;
import by.zarembo.project.exception.ServiceException;
import by.zarembo.project.util.EmailType;
import by.zarembo.project.util.EmailSender;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;
import java.util.Optional;

public class UserService {
    private static final String ACTIVATED = "Yes";

    public void signUpUser(User user) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            userDao.create(user);
        } catch (DaoException e) {
            throw new ServiceException("Can't create user", e);
        }
    }

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

    public Optional<User> takeUserByEmailAndPassword(String email, String password) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        Optional<User> userOptional;
        try {
            userOptional = userDao.findUserByEmailAndPassword(email, password);
        } catch (DaoException e) {
            throw new ServiceException("Can't create user", e);
        }
        return userOptional;
    }

    public Optional<User> takeUserByPassword(String password) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        Optional<User> userOptional;
        try {
            userOptional = userDao.findUserByPassword(password);
        } catch (DaoException e) {
            throw new ServiceException("Can't find user", e);
        }
        return userOptional;
    }

    public boolean checkIsEmailAlreadyExists(String email) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            return userDao.checkEmail(email);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean checkIsUserNameAlreadyExists(String userName) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            return userDao.checkUserName(userName);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Optional<User> takeUserById(long id) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        Optional<User> userOptional;
        try {
            userOptional = userDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException("Can't find user", e);
        }
        return userOptional;
    }

    public void giveUserAdminRights(User user) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        user.setRoleEnum(RoleType.ADMIN);
        try {
            userDao.update(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }

    public void removeAdminRights(User user) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        user.setRoleEnum(RoleType.USER);
        try {
            userDao.update(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }

    public void activateUserAccount(long id) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            userDao.activateUserAccount(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

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

    public List<User> takeAllUsers() throws ServiceException {
        List<User> users;
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            users = userDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Can'd final all users", e);
        }
        return users;
    }

    public void deleteUserById(long id) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            userDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

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

    /*public List<LifeHack> takeUserLikeLifeHacks(long userId) throws ServiceException {
        List<LifeHack> lifeHacks;
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            lifeHacks = userDao.findUserLikesLifeHacks(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return lifeHacks;
    }*/
}
