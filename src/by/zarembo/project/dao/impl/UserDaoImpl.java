package by.zarembo.project.dao.impl;

import by.zarembo.project.dao.Dao;
import by.zarembo.project.dao.UserDao;
import by.zarembo.project.entity.LifeHack;
import by.zarembo.project.entity.User;
import by.zarembo.project.exception.DaoException;
import by.zarembo.project.pool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class UserDaoImpl implements Dao<User>, UserDao {
    private static UserDaoImpl instance = new UserDaoImpl();

    private static final String SQL_SELECT_ALL_USERS = "SELECT user_id,firstname,lastname," +
            "nickname,email,password_hash,role_id FROM users;";

    private static final String SQL_INSERT_NEW_USER = "INSERT INTO users (firstname,lastname," +
            "nickname,email,password_hash,role_id) " +
            "VALUES(?,?,?,?,?,?);";

    private static final String SQL_UPDATE_USER = "UPDATE users SET firstname=?,lastname=?," +
            "nickname=?,email=?,password_hash=?,role_id=?\n" +
            "WHERE user_id=?;";

    private static final String SQL_DELETE_USER = "DELETE FROM users WHERE user_id=?;";

    private static final String SQL_FIND_USER_BY_ID = "SELECT user_id,firstname,lastname," +
            "nickname,email,password_hash,role_id FROM users WHERE user_id=?;";

    private static final String SQL_CHECK_EMAIL = "SELECT email from users WHERE email=?";

    private static final String SQL_CHECK_USER_NAME = "SELECT nickname from users WHERE nickname=?";


    private static final String SQL_SELECT_ALL_USER_LIKES_LIFEHACKS = "SELECT lifehacks.lifehack_id,author_user_id,lifehack_name," +
            "excerpt,lifehack_content,publication_date,category_id,lifehack_likes_amount,picture,u.user_id,role_id,firstname,lastname,nickname,email,password_hash FROM lifehacks " +
            " JOIN users u on lifehacks.author_user_id = u.user_id JOIN user_like_lifehacks ull on lifehacks.lifehack_id = ull.lifehack_id WHERE ull.user_id=? LIMIT ?,?; ";

    private static final String SQL_SELECT_USER_LIFEHACK_LIKES_COUNT = "select count(*) as user_lifehack_count from user_like_lifehacks where user_id=?;";

    private static final String SQL_SELECT_USER_BY_EMAIL_AND_PASSWORD = "SELECT user_id,firstname,lastname," +
            "nickname,email,password_hash,role_id FROM users WHERE email=? AND password_hash=?;";

    private static final String SQL_SELECT_USER_BY_PASSWORD = "SELECT user_id,firstname,lastname," +
            "nickname,email,password_hash,role_id FROM users WHERE password_hash=?;";

    private static final String SQL_UPDATE_ACTIVATED_FIELD = "UPDATE users SET activated='Yes' where user_id=?;";

    private static final String SQL_SELECT_ACTIVATED_FIELD = "SELECT activated FROM users WHERE user_id=?";

    private UserDaoImpl() {

    }

    public static UserDaoImpl getInstance() {
        return instance;
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> users = new ArrayList<>();
        Connection connection = ConnectionPool.getInstance().takeConnection();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USERS)) {
            while (resultSet.next()) {
                users.add(buildEntity(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException("SQL exception, can't find all users", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return users;
    }

    @Override
    public void create(User entity) throws DaoException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_NEW_USER)) {
            setPreparedStatement(preparedStatement, entity);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("SQL exception, affected rows 0");
            }
        } catch (SQLException e) {
            throw new DaoException("SQL exception, can't create user", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public void update(User entity) throws DaoException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER)) {
            setPreparedStatement(preparedStatement, entity);
            preparedStatement.setLong(7, entity.getUserId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("SQL exception, affected rows 0");
            }
        } catch (SQLException e) {
            throw new DaoException("SQL exception, can't update user", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public void delete(long id) throws DaoException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER)) {
            preparedStatement.setLong(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("SQL exception, affected rows 0");
            }
        } catch (SQLException e) {
            throw new DaoException("SQL exception, can't delete user", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public Optional<User> findById(long id) throws DaoException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = buildEntity(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("SQL exception, can't find all users", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<LifeHack> findUserLikesLifeHacks(int start, long userId) throws DaoException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        List<LifeHack> lifeHacks = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_USER_LIKES_LIFEHACKS)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setInt(2, start);
            preparedStatement.setInt(3, RECORDS_PER_PAGE);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
                lifeHacks.add(lifeHackDao.buildEntity(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException("SQL exception, can't find lifehack", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return lifeHacks;
    }

    public int findUserLikesLifeHacksCount(long userId) throws DaoException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        int count = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_LIFEHACK_LIKES_COUNT)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("user_lifehack_count");
            }
        } catch (SQLException e) {
            throw new DaoException("SQL exception, can't find lifehack", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return count;
    }

    @Override
    public boolean checkEmail(String email) throws DaoException {
        boolean isExistsAlready = false;
        Connection connection = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHECK_EMAIL)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                isExistsAlready = true;
            }
        } catch (SQLException e) {
            throw new DaoException("SQL exception, can't find email", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return isExistsAlready;
    }

    @Override
    public boolean checkUserName(String userName) throws DaoException {
        boolean isExistsAlready = false;
        Connection connection = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHECK_USER_NAME)) {
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                isExistsAlready = true;
            }
        } catch (SQLException e) {
            throw new DaoException("SQL exception, can't find username", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return isExistsAlready;
    }

    @Override
    public Optional<User> findUserByEmailAndPassword(String email, String password) throws DaoException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_BY_EMAIL_AND_PASSWORD)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = buildEntity(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("SQL exception, can't find all users", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findUserByPassword(String password) throws DaoException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_BY_PASSWORD)) {
            preparedStatement.setString(1, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = buildEntity(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("SQL exception, can't find all users", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public void activateUserAccount(long id) throws DaoException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ACTIVATED_FIELD)) {
            preparedStatement.setLong(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("SQL exception, affected rows 0");
            }
        } catch (SQLException e) {
            throw new DaoException("SQL exception, can't update user", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public String takeAccountActivateInformation(long id) throws DaoException {
        String result;
        Connection connection = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ACTIVATED_FIELD)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = resultSet.getString("activated");
        } catch (SQLException e) {
            throw new DaoException("SQL exception, can't update user", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return result;
    }
}
