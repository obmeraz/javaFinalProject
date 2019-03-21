package by.zarembo.project.dao.impl;

import by.zarembo.project.dao.CommentDao;
import by.zarembo.project.entity.Comment;
import by.zarembo.project.exception.DaoException;
import by.zarembo.project.pool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type Comment dao.
 */
public class CommentDaoImpl implements CommentDao {
    private final static CommentDaoImpl instance = new CommentDaoImpl();

    private static final String USER_LIKE_COMMENTS_COUNT = "user_like_comment_count";

    private static final String SQL_SELECT_ALL_COMMENTS = "SELECT comment_id,post_date,comments.user_id,lifehack_id," +
            "comment_content,comment_likes_amount, u.user_id,role_id,firstname,lastname,nickname," +
            "email,password_hash FROM comments join users u on comments.user_id = u.user_id ORDER BY post_date DESC;";

    private static final String SQL_INSERT_NEW_COMMENT = "INSERT INTO comments (user_id," +
            "lifehack_id,comment_content,comment_likes_amount) VALUES (?,?,?,?);";

    private static final String SQL_UPDATE_COMMENT = "UPDATE comments SET post_date=?,user_id=?," +
            "lifehack_id=?,comment_content=?,comment_likes_amount=? WHERE comment_id=?;";

    private static final String SQL_DELETE_COMMENT = "DELETE FROM comments WHERE comment_id=?";

    private static final String SQL_SELECT_COMMENT_BY_ID = "SELECT comment_id,post_date,comments.user_id,lifehack_id," +
            "comment_content,comment_likes_amount,u.user_id,role_id,firstname,lastname,nickname," +
            "email,password_hash FROM comments join users u on comments.user_id = u.user_id where comment_id=?;";

    private static final String SQL_SELECT_ALL_COMMENTS_TO_LIFEHACK = "SELECT comment_id,post_date,comments.user_id,lifehack_id," +
            "comment_content,comment_likes_amount, u.user_id,role_id,firstname,lastname,nickname," +
            "email,password_hash FROM comments join users u on comments.user_id = u.user_id WHERE lifehack_id=? ORDER BY post_date DESC;";

    private static final String SQL_SELECT_ALL_COMMENTS_TO_LIFEHACK_BY_LIKES_AMOUNT = "SELECT comment_id,post_date,comments.user_id,lifehack_id," +
            "comment_content,comment_likes_amount, u.user_id,role_id,firstname,lastname,nickname," +
            "email,password_hash FROM comments join users u on comments.user_id = u.user_id WHERE lifehack_id=? ORDER BY comment_likes_amount DESC;";

    private static final String SQL_LIKE_COMMENT_BY_ID = "UPDATE comments SET comment_likes_amount=comment_likes_amount+1 " +
            "WHERE comment_id=?";

    private static final String SQL_REMOVE_LIKE_COMMENT_BY_ID = "UPDATE comments SET comment_likes_amount=comment_likes_amount-1 " +
            "WHERE comment_id=?";

    private static final String SQL_INSERT_USER_LIKE_COMMENT = "INSERT INTO user_like_comments(comment_id,user_id) VALUES (?,?)";

    private static final String SQL_DELETE_USER_LIKE_COMMENT = "DELETE from user_like_comments where user_id=? and comment_id=?";

    private static final String SQL_SELECT_USER_LIKES_COMMENTS = "SELECT count(user_id) as user_like_comment_count FROM user_like_comments WHERE user_id=? and comment_id=?;";

    private CommentDaoImpl() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static CommentDaoImpl getInstance() {

        return instance;
    }

    @Override
    public List<Comment> findAll() throws DaoException {
        List<Comment> comments = new ArrayList<>();
        Connection connection = ConnectionPool.getInstance().takeConnection();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_COMMENTS)) {
            while (resultSet.next()) {
                comments.add(buildEntity(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException("Can't select all comments", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return comments;
    }

    @Override
    public void create(Comment entity) throws DaoException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_NEW_COMMENT)) {
            setPreparedStatement(preparedStatement, entity);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("SQL exception, can't create comment", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public void update(Comment entity) throws DaoException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_COMMENT)) {
            setPreparedStatement(preparedStatement, entity);
            preparedStatement.setLong(6, entity.getCommentId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("SQL exception, can't create comment", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public void delete(long id) throws DaoException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_COMMENT)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("SQL exception, can't delete comment", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public Optional<Comment> findById(long id) throws DaoException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        Comment comment = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_COMMENT_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                comment = buildEntity(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find comment by id", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return Optional.ofNullable(comment);
    }

    @Override
    public List<Comment> findCommentsToLifeHack(long id) throws DaoException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        List<Comment> comments = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_COMMENTS_TO_LIFEHACK)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                comments.add(buildEntity(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find comments to lifehack", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return comments;
    }

    @Override
    public List<Comment> findCommentsToLifeHackSortByLikesAmount(long id) throws DaoException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        List<Comment> comments = new ArrayList<>();
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(SQL_SELECT_ALL_COMMENTS_TO_LIFEHACK_BY_LIKES_AMOUNT)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                comments.add(buildEntity(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException("Can't select comments to lifehack sort by likes", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return comments;
    }

    @Override
    public void incrementCommentLikes(long id) throws DaoException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_LIKE_COMMENT_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("SQL exception, can't increment comments likes", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public void insertUserLikeComment(long commentId, long userId) throws DaoException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_USER_LIKE_COMMENT)) {
            preparedStatement.setLong(1, commentId);
            preparedStatement.setLong(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("SQL exception, insert into users like comment", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public void deleteUserLikeComment(long lifeHackId, long userId) throws DaoException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER_LIKE_COMMENT)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, lifeHackId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("SQL exception, delete user like comment", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public int isUserLikedCommentAlready(long lifeHackId, long userId) throws DaoException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        int count;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_LIKES_COMMENTS)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, lifeHackId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            count = resultSet.getInt(USER_LIKE_COMMENTS_COUNT);
        } catch (SQLException e) {
            throw new DaoException("Can't check is user liked comment already", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return count;
    }

    @Override
    public void decrementCommentLikes(long lifeHackId) throws DaoException {
        Connection connection = ConnectionPool.getInstance().takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_REMOVE_LIKE_COMMENT_BY_ID)) {
            preparedStatement.setLong(1, lifeHackId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("SQL exception, decrement comments likes", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }
}
