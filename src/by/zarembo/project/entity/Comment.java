package by.zarembo.project.entity;

public class Comment extends Entity {
    private long commentId;
    private long lifeHackId;
    private long userId;
    private long postDate;
    private int likesAmount;
    private String content;
    private User user;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getLifeHackId() {
        return lifeHackId;
    }

    public void setLifeHackId(long lifeHackId) {
        this.lifeHackId = lifeHackId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getPostDate() {
        return postDate;
    }

    public void setPostDate(long postDate) {
        this.postDate = postDate;
    }

    public int getLikesAmount() {
        return likesAmount;
    }

    public void setLikesAmount(int likesAmount) {
        this.likesAmount = likesAmount;
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
