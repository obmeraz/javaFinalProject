package by.zarembo.project.service;

import by.zarembo.project.command.CommandType;
import by.zarembo.project.command.SortType;
import by.zarembo.project.dao.impl.CommentDaoImpl;
import by.zarembo.project.entity.Comment;
import by.zarembo.project.entity.LifeHack;
import by.zarembo.project.entity.User;
import by.zarembo.project.exception.DaoException;
import by.zarembo.project.exception.ServiceException;

import java.util.*;

public class CommentService {

    public void addNewComment(Comment comment) throws ServiceException {
        CommentDaoImpl commentDao = CommentDaoImpl.getInstance();
        try {
            commentDao.create(comment);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Comment buildComment(String commentContent, User user, long lifeHackId) {
        Comment comment = new Comment();
        comment.setLifeHackId(lifeHackId);
        comment.setUser(user);
        comment.setUserId(user.getUserId());
        comment.setContent(commentContent);
        comment.setLikesAmount(0);
        return comment;
    }

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

    public List<Comment> takeCommentsToLifeHackByLikesAmount(long id) throws ServiceException {
        CommentDaoImpl commentDao = CommentDaoImpl.getInstance();
        List<Comment> comments;
        try {
            comments = commentDao.findCommentsToLifeHackByLikesAmount(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return comments;
    }

    public void likeComment(long id) throws ServiceException {
        CommentDaoImpl commentDao = CommentDaoImpl.getInstance();
        try {
            commentDao.incrementCommentLikes(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void deleteComment(long id) throws ServiceException {
        CommentDaoImpl commentDao = CommentDaoImpl.getInstance();
        try {
            commentDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean checkSortTypeEnum(String sortType) {
        return Arrays.stream(SortType.values())
                .anyMatch(type -> type.toString().equals(sortType.toUpperCase()));
    }

    public void insertUserLikeComment(long commentId, long userId) throws ServiceException {
        CommentDaoImpl commentDao = CommentDaoImpl.getInstance();
        try {
            commentDao.insertUserLikeComment(commentId, userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void deleteUserLikeComment(long commentId, long userId) throws ServiceException {
        CommentDaoImpl commentDao = CommentDaoImpl.getInstance();
        try {
            commentDao.deleteUserLikeComment(commentId, userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }

    public void removeLikeComment(long commentId) throws ServiceException {
        CommentDaoImpl commentDao = CommentDaoImpl.getInstance();
        try {
            commentDao.decrementCommentLikes(commentId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

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

    public HashMap<LifeHack, Integer> countLifeHackComments(List<LifeHack> lifeHacks) throws ServiceException {
        HashMap<LifeHack, Integer> commentCountMap = new HashMap<>();
        for (LifeHack lifehack :
                lifeHacks) {
            List<Comment> comments = takeCommentsToLifeHack(lifehack.getLifehackId());
            commentCountMap.put(lifehack, comments.size());
        }
        return commentCountMap;
    }

    public List<Comment> takeSortComments(SortType sortType, long lifeHackId) throws ServiceException {
        List<Comment> comments = null;
        switch (sortType) {
            case SORT_BY_LIKES:
                comments = takeCommentsToLifeHackByLikesAmount(lifeHackId);
                break;
            case SORT_BY_DATE:
                comments = takeCommentsToLifeHack(lifeHackId);
                break;
        }
        return comments;
    }


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
