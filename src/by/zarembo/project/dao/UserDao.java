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

public interface UserDao extends Dao<User> {
    String USER_ID = "user_id";
    String FIRSTNAME = "firstname";
    String LASTNAME = "lastname";
    String NICKNAME = "nickname";
    String EMAIL = "email";
    String PASSWORD = "password_hash";
    String ROLE = "role_id";

    List<LifeHack> findUserLikesLifeHacks(int cursor, long id) throws DaoException;

    Optional<User> findUserByEmailAndPassword(String email, String password) throws DaoException;

    Optional<User> findUserByPassword(String password) throws DaoException;

    boolean checkEmail(String email) throws DaoException;

    boolean checkUserName(String userName) throws DaoException;

    void activateUserAccount(long id) throws DaoException;

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
