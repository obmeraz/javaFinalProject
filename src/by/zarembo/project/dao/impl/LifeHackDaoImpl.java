package by.zarembo.project.dao.impl;

import by.zarembo.project.dao.LifeHackDao;
import by.zarembo.project.entity.CategoryType;
import by.zarembo.project.entity.LifeHack;
import by.zarembo.project.exception.DaoException;
import by.zarembo.project.pool.ConnectionPool;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

/**
 * The type Life hack dao.
 */
public class LifeHackDaoImpl implements LifeHackDao {
    private static LifeHackDaoImpl instance = new LifeHackDaoImpl();

    private static final String USER_LIKES_COUNT = "user_like_count";
    private static final String COLUMN_COUNT_LIFEHACKS = "count_lifehacks";
    private static final String COLUMN_CATEGORY_NAME = "category_name";
    private static final String COLUMN_LIFEHACK_COUNT = "lifehack_count";

    private static final String SQL_SELECT_ALL_LIFEHACKS = "SELECT  u.user_id,role_id,firstname,lastname,nickname,email,password_hash, lifehack_id,author_user_id,lifehack_name," +
            "publication_date,lifehack_content,excerpt,category_id,lifehack_likes_amount,picture FROM lifehacks JOIN users u on lifehacks.author_user_id = u.user_id;";

    private static final String SQL_INSERT_NEW_LIFEHACK = "INSERT INTO lifehacks " +
            "(author_user_id,lifehack_name,lifehack_content,excerpt,category_id,lifehack_likes_amount,picture) " +
            "values(?,?,?,?,?,?,?);";

    private static final String SQL_UPDATE_LIFEHACK = "UPDATE lifehacks SET author_user_id=?," +
            "lifehack_name=?,lifehack_content=?,excerpt=?,category_id=?,lifehack_likes_amount=?\n" +
            "WHERE lifehack_id=?;";

    private static final String SQL_DELETE_LIFEHACK_BY_ID = "DELETE FROM lifehacks WHERE lifehack_id=?;";

    private static final String SQL_SELECT_LIFEHACK_BY_ID = "SELECT u.user_id,role_id,firstname,lastname," +
            "nickname,email,password_hash, lifehack_id,author_user_id,lifehack_name,publication_date,lifehack_content," +
            "excerpt,category_id,lifehack_likes_amount,picture FROM lifehacks JOIN " +
            "users u on lifehacks.author_user_id = u.user_id WHERE lifehack_id=?;";

    private static final String SQL_SELECT_LIFEHACKS_BY_CATEGORY = "SELECT u.user_id,role_id,firstname,lastname," +
            "nickname,email,password_hash, lifehack_id,author_user_id,lifehack_name,publication_date,lifehack_content," +
            "excerpt,category_id,lifehack_likes_amount,picture FROM lifehacks JOIN users u on " +
            "lifehacks.author_user_id = u.user_id WHERE category_id=? LIMIT ?,?";

    private static final String SQL_SELECT_COUNT_LIFEHACKS_CATEGORY = "select count(*) as lifehack_count from lifehacks join categories on lifehacks.category_id=categories.category_id where lifehacks.category_id=?";

    private static final String SQL_SELECT_ALL_CATEGORIES = "SELECT count(lifehack_id) as count_lifehacks,categories.category_id,category_name FROM lifehacks RIGHT JOIN categories ON lifehacks.category_id=categories.category_id\n" +
            "GROUP BY category_id ORDER BY count_lifehacks DESC;";

    private static final String SQL_SELECT_POPULAR_LIFEHACKS = "SELECT u.user_id,role_id,firstname,lastname,nickname,email,password_hash, lifehack_id,author_user_id,lifehack_name,publication_date,lifehack_content,excerpt,category_id,lifehack_likes_amount,picture FROM lifehacks JOIN users u on lifehacks.author_user_id = u.user_id ORDER BY lifehack_likes_amount DESC LIMIT ?,?;";

    private static final String SQL_SELECT_RECENT_LIFEHACKS = "SELECT u.user_id,role_id,firstname,lastname,nickname,email,password_hash, lifehack_id,author_user_id,lifehack_name,publication_date,lifehack_content,excerpt,category_id,lifehack_likes_amount,picture FROM lifehacks JOIN users u on lifehacks.author_user_id = u.user_id ORDER BY publication_date DESC LIMIT ?,?;";

    private static final String SQL_INCREMENT_LIFEHACK_LIKES = "UPDATE lifehacks SET lifehack_likes_amount=lifehack_likes_amount+1" +
            " WHERE lifehack_id=?";

    private static final String SQL_DECREMENT_LIFEHACK_LIKES = "UPDATE lifehacks SET lifehack_likes_amount=lifehack_likes_amount-1" +
            " WHERE lifehack_id=?";

    private static final String SQL_INSERT_USER_LIKE_LIFEHACK = "INSERT INTO user_like_lifehacks (user_id, lifehack_id) VALUES (?,?);";

    private static final String SQL_DELETE_USER_LIKE_LIFEHACK = "DELETE FROM user_like_lifehacks WHERE user_id=? AND lifehack_id=?;";

    private static final String SQL_SELECT_USER_LIKES_LIFEHACKS = "SELECT count(user_id) as user_like_count FROM user_like_lifehacks WHERE user_id=? and lifehack_id=?;";

    private LifeHackDaoImpl() {

    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static LifeHackDaoImpl getInstance() {
        return instance;
    }

    @Override
    public List<LifeHack> findAll() throws DaoException {
        List<LifeHack> lifeHacks = new ArrayList<>();
        Connection connection = ConnectionPool.getInstance().takeConnection();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_LIFEHACKS)) {
            while (resultSet.next()) {
                lifeHacks.add(buildEntity(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException("SQL exception, can't find all lifehacks", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return lifeHacks;
    }

    @Override
    public void create(LifeHack entity) throws DaoException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_NEW_LIFEHACK)) {
            setPreparedStatement(preparedStatement, entity);
            InputStream targetStream = new ByteArrayInputStream(entity.getImageBytes());
            preparedStatement.setBlob(7, targetStream);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("SQL exception, affected rows 0");
            }
        } catch (SQLException e) {
            throw new DaoException("SQL exception, can't create lifehack", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public void update(LifeHack entity) throws DaoException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_LIFEHACK)) {
            setPreparedStatement(preparedStatement, entity);
            preparedStatement.setLong(7, entity.getLifehackId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("SQL exception, affected rows 0");
            }
        } catch (SQLException e) {
            throw new DaoException("SQL exception, can't update lifehack", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public void delete(long id) throws DaoException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_LIFEHACK_BY_ID)) {
            preparedStatement.setLong(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("SQL exception, affected rows 0");
            }
        } catch (SQLException e) {
            throw new DaoException("SQL exception, can't delete lifehack", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public Optional<LifeHack> findById(long id) throws DaoException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        LifeHack lifeHack = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_LIFEHACK_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                lifeHack = buildEntity(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("SQL exception, can't find lifehack", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return Optional.ofNullable(lifeHack);
    }

    @Override
    public List<LifeHack> findPopularLifeHacks(int currentPage) throws DaoException {
        return findLifeHacks(currentPage, SQL_SELECT_POPULAR_LIFEHACKS);
    }

    @Override
    public List<LifeHack> findRecentLifeHacks(int currentPage) throws DaoException {
        return findLifeHacks(currentPage, SQL_SELECT_RECENT_LIFEHACKS);
    }

    private List<LifeHack> findLifeHacks(int currentPage, String sqlQuery) throws DaoException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        List<LifeHack> lifeHacks = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, currentPage);
            preparedStatement.setInt(2, RECORDS_PER_PAGE);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                lifeHacks.add(buildEntity(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException("SQL exception, can't find lifehacks");
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return lifeHacks;
    }

    @Override
    public List<LifeHack> findLifeHacksByCategory(int currentPage, CategoryType category) throws DaoException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        List<LifeHack> lifeHacks = new ArrayList<>();
        int categoryId = category.ordinal();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_LIFEHACKS_BY_CATEGORY)) {
            preparedStatement.setInt(1, categoryId);
            preparedStatement.setInt(2, currentPage);
            preparedStatement.setInt(3, RECORDS_PER_PAGE);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                lifeHacks.add(buildEntity(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException("SQL exception, can't find lifehacks by category", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return lifeHacks;
    }

    @Override
    public Map<CategoryType, Integer> findLifeHacksCategories() throws DaoException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        Map<CategoryType, Integer> categoryMap = new EnumMap<>(CategoryType.class);
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_CATEGORIES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CategoryType category = CategoryType.valueOf(resultSet.getString(COLUMN_CATEGORY_NAME).toUpperCase());
                Integer count = resultSet.getInt(COLUMN_COUNT_LIFEHACKS);
                categoryMap.put(category, count);
            }
        } catch (SQLException e) {
            throw new DaoException("SQL exception, can't find lifehacks categories", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return categoryMap;
    }

    @Override
    public void likeLifeHack(long lifeHackId) throws DaoException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INCREMENT_LIFEHACK_LIKES)) {
            preparedStatement.setLong(1, lifeHackId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("SQL exception, can't like lifehack", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public void decrementLifeHackLike(long lifeHackId) throws DaoException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DECREMENT_LIFEHACK_LIKES)) {
            preparedStatement.setLong(1, lifeHackId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("SQL exception, can't remove like lifehack", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public void insertUserLikeLifeHack(long lifeHackId, long userId) throws DaoException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_USER_LIKE_LIFEHACK)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, lifeHackId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("SQL exception, insert into users like lifehack", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public void deleteUserLikeLifeHack(long lifeHackId, long userId) throws DaoException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER_LIKE_LIFEHACK)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, lifeHackId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("SQL exception, delete users like lifehack", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public int isLikedLifeHackAlready(long lifeHackId, long userId) throws DaoException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        int count;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_LIKES_LIFEHACKS)) {
            preparedStatement.setLong(2, lifeHackId);
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            count = resultSet.getInt(USER_LIKES_COUNT);
        } catch (SQLException e) {
            throw new DaoException("can't check user like lifehack", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return count;
    }

    @Override
    public int findCategoriesLifeHacksCount(CategoryType categoryType) throws DaoException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        int count = 0;
        int categoryId = categoryType.ordinal();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_COUNT_LIFEHACKS_CATEGORY)) {
            preparedStatement.setInt(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(COLUMN_LIFEHACK_COUNT);
            }
        } catch (SQLException e) {
            throw new DaoException("SQL exception, can't find categries lifehacks count", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return count;
    }
}

