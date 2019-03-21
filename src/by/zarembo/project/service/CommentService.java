package by.zarembo.project.service;

import by.zarembo.project.command.SortType;
import by.zarembo.project.dao.impl.CommentDaoImpl;
import by.zarembo.project.entity.Comment;
import by.zarembo.project.entity.LifeHack;
import by.zarembo.project.entity.User;
import by.zarembo.project.exception.DaoException;
import by.zarembo.project.exception.ServiceException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * The type Comment service.
 */
public class CommentService {

    /**
     * Add new comment.
     *
     * @param comment the comment
     * @throws ServiceException the service exception
     */
    public void addNewComment(Comment comment) throws ServiceException {
        CommentDaoImpl commentDao = CommentDaoImpl.getInstance();
        try {
            commentDao.create(comment);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Build comment comment.
     *
     * @param commentContent the comment content
     * @param user           the user
     * @param lifeHackId     the life hack id
     * @return the comment
     */
    public Comment buildComment(String commentContent, User user, long lifeHackId) {
        Comment comment = new Comment();
        comment.setLifeHackId(lifeHackId);
        comment.setUser(user);
        comment.setUserId(user.getUserId());
        comment.setContent(commentContent);
        comment.setLikesAmount(0);
        return comment;
    }

    /**
     * Take comments to lifehack list.
     *
     * @param id the id
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Comment> takeCommentsToLifeHack(long id) throws ServiceException {
        CommentDaoImpl commentDao = CommentDaoImpl.getInstance();
        List<Comment> comments;
        try {
            comments = commentDao.findCommentsToLifeHack(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return comments;
    }

    private List<Comment> takeCommentsToLifeHackSortByLikesAmount(long id) throws ServiceException {
        CommentDaoImpl commentDao = CommentDaoImpl.getInstance();
        List<Comment> comments;
        try {
            comments = commentDao.findCommentsToLifeHackSortByLikesAmount(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return comments;
    }

    /**
     * Like comment.
     *
     * @param id the id
     * @throws ServiceException the service exception
     */
    public void likeComment(long id) throws ServiceException {
        CommentDaoImpl commentDao = CommentDaoImpl.getInstance();
        try {
            commentDao.incrementCommentLikes(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Delete comment.
     *
     * @param id the id
     * @throws ServiceException the service exception
     */
    public void deleteComment(long id) throws ServiceException {
        CommentDaoImpl commentDao = CommentDaoImpl.getInstance();
        try {
            commentDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Check sort type enum boolean.
     *
     * @param sortType the sort type
     * @return the boolean
     */
    public boolean checkSortTypeEnum(String sortType) {
        return Arrays.stream(SortType.values())
                .anyMatch(type -> type.toString().equals(sortType.toUpperCase()));
    }

    /**
     * Insert user like comment.
     *
     * @param commentId the comment id
     * @param userId    the user id
     * @throws ServiceException the service exception
     */
    public void insertUserLikeComment(long commentId, long userId) throws ServiceException {
        CommentDaoImpl commentDao = CommentDaoImpl.getInstance();
        try {
            commentDao.insertUserLikeComment(commentId, userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Delete user like comment.
     *
     * @param commentId the comment id
     * @param userId    the user id
     * @throws ServiceException the service exception
     */
    public void deleteUserLikeComment(long commentId, long userId) throws ServiceException {
        CommentDaoImpl commentDao = CommentDaoImpl.getInstance();
        try {
            commentDao.deleteUserLikeComment(commentId, userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }

    /**
     * Remove like comment.
     *
     * @param commentId the comment id
     * @throws ServiceException the service exception
     */
    public void removeLikeComment(long commentId) throws ServiceException {
        CommentDaoImpl commentDao = CommentDaoImpl.getInstance();
        try {
            commentDao.decrementCommentLikes(commentId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Is comment liked already boolean.
     *
     * @param commentId the comment id
     * @param userId    the user id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    public boolean isCommentLikedAlready(long commentId, long userId) throws ServiceException {
        CommentDaoImpl commentDao = CommentDaoImpl.getInstance();
        boolean isLiked = false;
        int count;
        try {
            count = commentDao.isUserLikedCommentAlready(commentId, userId);
            if (count > 0) {
                isLiked = true;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return isLiked;
    }

    /**
     * Take comment by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws ServiceException the service exception
     */
    public Optional<Comment> takeCommentById(long id) throws ServiceException {
        Optional<Comment> commentOptional;
        CommentDaoImpl commentDao = CommentDaoImpl.getInstance();
        try {
            commentOptional = commentDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return commentOptional;
    }

    /**
     * Count life hack comments hash map.
     *
     * @param lifeHacks the life hacks
     * @return the hash map
     * @throws ServiceException the service exception
     */
    public HashMap<LifeHack, Integer> countLifeHackComments(List<LifeHack> lifeHacks) throws ServiceException {
        HashMap<LifeHack, Integer> commentCountMap = new HashMap<>();
        for (LifeHack lifehack :
                lifeHacks) {
            List<Comment> comments = takeCommentsToLifeHack(lifehack.getLifehackId());
            commentCountMap.put(lifehack, comments.size());
        }
        return commentCountMap;
    }

    /**
     * Take sort comments list.
     *
     * @param sortType   the sort type
     * @param lifeHackId the life hack id
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Comment> takeSortComments(SortType sortType, long lifeHackId) throws ServiceException {
        List<Comment> comments = null;
        switch (sortType) {
            case SORT_BY_LIKES:
                comments = takeCommentsToLifeHackSortByLikesAmount(lifeHackId);
                break;
            case SORT_BY_DATE:
                comments = takeCommentsToLifeHack(lifeHackId);
                break;
        }
        return comments;
    }


    /**
     * Mark is liked comments hash map.
     *
     * @param comments the comments
     * @param userId   the user id
     * @return the hash map
     * @throws ServiceException the service exception
     */
    public HashMap<Comment, Boolean> markIsLikedComments(List<Comment> comments, long userId) throws ServiceException {
        HashMap<Comment, Boolean> commentsLikesMap = new HashMap<>();
        for (Comment comment : comments) {
            if (isCommentLikedAlready(comment.getCommentId(), userId)) {
                commentsLikesMap.put(comment, true);
            } else {
                commentsLikesMap.put(comment, false);
            }
        }
        return commentsLikesMap;
    }
}
