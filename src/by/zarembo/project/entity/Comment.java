package by.zarembo.project.entity;

import java.util.Objects;

/**
 * The type Comment.
 */
public class Comment extends Entity {
    private long commentId;
    private long lifeHackId;
    private long userId;
    private long postDate;
    private int likesAmount;
    private String content;
    private User user;


    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets life hack id.
     *
     * @return the life hack id
     */
    public long getLifeHackId() {
        return lifeHackId;
    }

    /**
     * Sets life hack id.
     *
     * @param lifeHackId the life hack id
     */
    public void setLifeHackId(long lifeHackId) {
        this.lifeHackId = lifeHackId;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Gets comment id.
     *
     * @return the comment id
     */
    public long getCommentId() {
        return commentId;
    }

    /**
     * Sets comment id.
     *
     * @param commentId the comment id
     */
    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    /**
     * Gets content.
     *
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets content.
     *
     * @param content the content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gets post date.
     *
     * @return the post date
     */
    public long getPostDate() {
        return postDate;
    }

    /**
     * Sets post date.
     *
     * @param postDate the post date
     */
    public void setPostDate(long postDate) {
        this.postDate = postDate;
    }

    /**
     * Gets likes amount.
     *
     * @return the likes amount
     */
    public int getLikesAmount() {
        return likesAmount;
    }

    /**
     * Sets likes amount.
     *
     * @param likesAmount the likes amount
     */
    public void setLikesAmount(int likesAmount) {
        this.likesAmount = likesAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return commentId == comment.commentId &&
                lifeHackId == comment.lifeHackId &&
                userId == comment.userId &&
                postDate == comment.postDate &&
                likesAmount == comment.likesAmount &&
                Objects.equals(content, comment.content) &&
                Objects.equals(user, comment.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentId, lifeHackId, userId, postDate, likesAmount, content, user);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", content='" + content + '\'' +
                ", postDate='" + postDate + '\'' +
                ", likesAmount=" + likesAmount +
                '}';
    }
}
