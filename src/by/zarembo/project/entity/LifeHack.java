package by.zarembo.project.entity;

import java.util.Arrays;

public class LifeHack extends Entity {
    private long lifehackId;
    private long userId;
    private long publicationDate;
    private int likesAmount;
    private byte[] imageBytes;
    private String name;
    private String content;
    private String excerpt;
    private CategoryType category;
    private String image;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getLifehackId() {
        return lifehackId;
    }

    public void setLifehackId(long lifehackId) {
        this.lifehackId = lifehackId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(long publicationDate) {
        this.publicationDate = publicationDate;
    }

    public int getLikesAmount() {
        return likesAmount;
    }

    public void setLikesAmount(int likesAmount) {
        this.likesAmount = likesAmount;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public CategoryType getCategory() {
        return category;
    }

    public void setCategory(CategoryType category) {
        this.category = category;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    @Override
    public String toString() {
        return "LifeHack{" +
                "lifehackId=" + lifehackId +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", publicationDate='" + publicationDate + '\'' +
                ", category=" + category +
                ", likesAmount=" + likesAmount +
                ", imageBytes=" + Arrays.toString(imageBytes) +
                ", image='" + image + '\'' +
                '}';
    }
}
