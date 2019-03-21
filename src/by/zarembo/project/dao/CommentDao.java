package by.zarembo.project.dao;

import by.zarembo.project.dao.impl.UserDaoImpl;
import by.zarembo.project.entity.Comment;
import by.zarembo.project.entity.Entity;
import by.zarembo.project.entity.User;
import by.zarembo.project.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * The interface Comment dao.
 */
public interface CommentDao extends Dao<Comment> {
    /**
     * The constant COMMENT_ID.
     */
    String COMMENT_ID = "comment_id";
    /**
     * The constant COMMENT_CONTENT.
     */
    String COMMENT_CONTENT = "comment_content";
    /**
     * The constant LIFEHACK_ID.
     */
    String LIFEHACK_ID = "lifehack_id";
    /**
     * The constant POST_DATE.
     */
    String POST_DATE = "post_date";
    /**
     * The constant USER_ID.
     */
    String USER_ID = "user_id";
    /**
     * The constant COMMENT_LIKES_AMOUNT.
     */
    String COMMENT_LIKES_AMOUNT = "comment_likes_amount";


    /**
     * Find comments to lifehack list.
     *
     * @param id the id
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Comment> findCommentsToLifeHack(long id) throws DaoException;

    /**
     * Insert user like comment.
     *
     * @param commentId the comment id
     * @param userId    the user id
     * @throws DaoException the dao exception
     */
    void insertUserLikeComment(long commentId, long userId) throws DaoException;

    /**
     * Delete user like comment.
     *
     * @param lifeHackId the life hack id
     * @param userId     the user id
     * @throws DaoException the dao exception
     */
    void deleteUserLikeComment(long lifeHackId, long userId) throws DaoException;

    /**
     * Is user liked comment already int.
     *
     * @param lifeHackId the life hack id
     * @param userId     the user id
     * @return the int
     * @throws DaoException the dao exception
     */
    int isUserLikedCommentAlready(long lifeHackId, long userId) throws DaoException;

    /**
     * Decrement comment likes amount.
     *
     * @param lifeHackId the life hack id
     * @throws DaoException the dao exception
     */
    void decrementCommentLikes(long lifeHackId) throws DaoException;

    /**
     * Increment comment likes amount.
     *
     * @param id the id
     * @throws DaoException the dao exception
     */
    void incrementCommentLikes(long id) throws DaoException;

    /**
     * Find comments to lifehack sort by likes amount list.
     *
     * @param id the id
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Comment> findCommentsToLifeHackSortByLikesAmount(long id) throws DaoException;

    @Override
    default Comment buildEntity(ResultSet resultSet) throws SQLException {
        Comment comment = new Comment();
        UserDao userDao = UserDaoImpl.getInstance();
        User user = userDao.buildEntity(resultSet);
        comment.setUser(user);
        comment.setCommentId(resultSet.getInt(COMMENT_ID));
        comment.setContent(resultSet.getString(COMMENT_CONTENT));
        comment.setLifeHackId(resultSet.getInt(LIFEHACK_ID));
        Timestamp timestamp = resultSet.getTimestamp(POST_DATE);
        comment.setPostDate(timestamp.getTime());
        comment.setUserId(resultSet.getLong(USER_ID));
        comment.setLikesAmount(resultSet.getInt(COMMENT_LIKES_AMOUNT));
        return comment;
    }

    @Override
    default void setPreparedStatement(PreparedStatement preparedStatement, Entity entity) throws SQLException {
        Comment comment = (Comment) entity;
        preparedStatement.setLong(1, comment.getUserId());
        preparedStatement.setLong(2, comment.getLifeHackId());
        preparedStatement.setString(3, comment.getContent());
        preparedStatement.setLong(4, comment.getLikesAmount());
    }
}
