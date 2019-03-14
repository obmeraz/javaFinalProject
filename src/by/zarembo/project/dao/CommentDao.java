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

public interface CommentDao extends Dao<Comment> {
    String COMMENT_ID = "comment_id";
    String COMMENT_CONTENT = "comment_content";
    String LIFEHACK_ID = "lifehack_id";
    String POST_DATE = "post_date";
    String USER_ID = "user_id";
    String COMMENT_LIKES_AMOUNT = "comment_likes_amount";


    List<Comment> findCommentsToLifeHack(long id) throws DaoException;

    void incrementCommentLikes(long id) throws DaoException;

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
