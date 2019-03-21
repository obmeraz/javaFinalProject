package by.zarembo.project.entity;

import java.util.Arrays;
import java.util.Objects;

/**
 * The type Life hack.
 */
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
     * Gets lifehack id.
     *
     * @return the lifehack id
     */
    public long getLifehackId() {
        return lifehackId;
    }

    /**
     * Sets lifehack id.
     *
     * @param lifehackId the lifehack id
     */
    public void setLifehackId(long lifehackId) {
        this.lifehackId = lifehackId;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
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
     * Gets publication date.
     *
     * @return the publication date
     */
    public long getPublicationDate() {
        return publicationDate;
    }

    /**
     * Sets publication date.
     *
     * @param publicationDate the publication date
     */
    public void setPublicationDate(long publicationDate) {
        this.publicationDate = publicationDate;
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
     * Gets category.
     *
     * @return the category
     */
    public CategoryType getCategory() {
        return category;
    }

    /**
     * Sets category.
     *
     * @param category the category
     */
    public void setCategory(CategoryType category) {
        this.category = category;
    }

    /**
     * Get image bytes byte [ ].
     *
     * @return the byte [ ]
     */
    public byte[] getImageBytes() {
        return imageBytes;
    }

    /**
     * Sets image bytes.
     *
     * @param imageBytes the image bytes
     */
    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    /**
     * Gets image.
     *
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets image.
     *
     * @param image the image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Gets excerpt.
     *
     * @return the excerpt
     */
    public String getExcerpt() {
        return excerpt;
    }

    /**
     * Sets excerpt.
     *
     * @param excerpt the excerpt
     */
    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LifeHack lifeHack = (LifeHack) o;
        return lifehackId == lifeHack.lifehackId &&
                userId == lifeHack.userId &&
                publicationDate == lifeHack.publicationDate &&
                likesAmount == lifeHack.likesAmount &&
                Arrays.equals(imageBytes, lifeHack.imageBytes) &&
                Objects.equals(name, lifeHack.name) &&
                Objects.equals(content, lifeHack.content) &&
                Objects.equals(excerpt, lifeHack.excerpt) &&
                category == lifeHack.category &&
                Objects.equals(image, lifeHack.image) &&
                Objects.equals(user, lifeHack.user);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(lifehackId, userId, publicationDate, likesAmount, name, content, excerpt, category, image, user);
        result = 31 * result + Arrays.hashCode(imageBytes);
        return result;
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
