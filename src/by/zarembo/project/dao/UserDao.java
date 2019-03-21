package by.zarembo.project.dao;

import by.zarembo.project.entity.Entity;
import by.zarembo.project.entity.LifeHack;
import by.zarembo.project.entity.User;
import by.zarembo.project.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * The interface User dao.
 */
public interface UserDao extends Dao<User> {
    /**
     * The constant USER_ID.
     */
    String USER_ID = "user_id";
    /**
     * The constant FIRSTNAME.
     */
    String FIRSTNAME = "firstname";
    /**
     * The constant LASTNAME.
     */
    String LASTNAME = "lastname";
    /**
     * The constant NICKNAME.
     */
    String NICKNAME = "nickname";
    /**
     * The constant EMAIL.
     */
    String EMAIL = "email";
    /**
     * The constant PASSWORD.
     */
    String PASSWORD = "password_hash";
    /**
     * The constant ROLE.
     */
    String ROLE = "role_id";

    /**
     * Find user likes life hacks list.
     *
     * @param cursor the cursor
     * @param id     the id
     * @return the list
     * @throws DaoException the dao exception
     */
    List<LifeHack> findUserLikesLifeHacks(int cursor, long id) throws DaoException;

    /**
     * Find user by email and password optional.
     *
     * @param email    the email
     * @param password the password
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<User> findUserByEmailAndPassword(String email, String password) throws DaoException;

    /**
     * Find user by password optional.
     *
     * @param password the password
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<User> findUserByPassword(String password) throws DaoException;

    /**
     * Check email boolean.
     *
     * @param email the email
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean checkEmail(String email) throws DaoException;

    /**
     * Check user name boolean.
     *
     * @param userName the user name
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean checkUserName(String userName) throws DaoException;

    /**
     * Activate user account.
     *
     * @param id the id
     * @throws DaoException the dao exception
     */
    void activateUserAccount(long id) throws DaoException;

    /**
     * Find user likes life hacks count int.
     *
     * @param userId the user id
     * @return the int
     * @throws DaoException the dao exception
     */
    int findUserLikesLifeHacksCount(long userId) throws DaoException;

    /**
     * Take account activate information string.
     *
     * @param id the id
     * @return the string
     * @throws DaoException the dao exception
     */
    String takeAccountActivateInformation(long id) throws DaoException;

    @Override
    default User buildEntity(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getLong(USER_ID));
        user.setFirstName(resultSet.getString(FIRSTNAME));
        user.setLastName(resultSet.getString(LASTNAME));
        user.setNickName(resultSet.getString(NICKNAME));
        user.setEmail(resultSet.getString(EMAIL));
        user.setPassword(resultSet.getString(PASSWORD));
        user.setRole((resultSet.getInt(ROLE)));
        return user;
    }

    @Override
    default void setPreparedStatement(PreparedStatement preparedStatement, Entity entity) throws SQLException {
        User user = (User) entity;
        preparedStatement.setString(1, user.getFirstName());
        preparedStatement.setString(2, user.getLastName());
        preparedStatement.setString(3, user.getNickName());
        preparedStatement.setString(4, user.getEmail());
        preparedStatement.setString(5, user.getPassword());
        preparedStatement.setInt(6, user.getRoleId());
    }
}
