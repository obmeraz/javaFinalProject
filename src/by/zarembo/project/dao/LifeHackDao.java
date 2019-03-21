package by.zarembo.project.dao;

import by.zarembo.project.dao.impl.UserDaoImpl;
import by.zarembo.project.entity.CategoryType;
import by.zarembo.project.entity.Entity;
import by.zarembo.project.entity.LifeHack;
import by.zarembo.project.entity.User;
import by.zarembo.project.exception.DaoException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface Lifehack dao.
 */
public interface LifeHackDao extends Dao<LifeHack> {
    /**
     * The constant LIFEHACK_ID.
     */
    String LIFEHACK_ID = "lifehack_id";
    /**
     * The constant LIFEHACK_LIKES_AMOUNT.
     */
    String LIFEHACK_LIKES_AMOUNT = "lifehack_likes_amount";
    /**
     * The constant CATEGORY_ID.
     */
    String CATEGORY_ID = "category_id";
    /**
     * The constant AUTHOR_USER_ID.
     */
    String AUTHOR_USER_ID = "author_user_id";
    /**
     * The constant LIFEHACK_NAME.
     */
    String LIFEHACK_NAME = "lifehack_name";
    /**
     * The constant LIFEHACK_CONTENT.
     */
    String LIFEHACK_CONTENT = "lifehack_content";
    /**
     * The constant LIFEHACK_EXCERPT.
     */
    String LIFEHACK_EXCERPT = "excerpt";
    /**
     * The constant PUBLICATION_DATE.
     */
    String PUBLICATION_DATE = "publication_date";
    /**
     * The constant PICTURE.
     */
    String PICTURE = "picture";

    /**
     * Find popular lifehacks list.
     *
     * @param cursor the cursor
     * @return the list
     * @throws DaoException the dao exception
     */
    List<LifeHack> findPopularLifeHacks(int cursor) throws DaoException;

    /**
     * Find recent lifehacks list.
     *
     * @param cursor the cursor
     * @return the list
     * @throws DaoException the dao exception
     */
    List<LifeHack> findRecentLifeHacks(int cursor) throws DaoException;

    /**
     * Find lifehacks by category list.
     *
     * @param cursor   the cursor
     * @param category the category
     * @return the list
     * @throws DaoException the dao exception
     */
    List<LifeHack> findLifeHacksByCategory(int cursor, CategoryType category) throws DaoException;

    /**
     * Like lifehack.
     *
     * @param lifeHackId the life hack id
     * @throws DaoException the dao exception
     */
    void likeLifeHack(long lifeHackId) throws DaoException;

    /**
     * Decrement lifehack like.
     *
     * @param lifeHackId the life hack id
     * @throws DaoException the dao exception
     */
    void decrementLifeHackLike(long lifeHackId) throws DaoException;

    /**
     * Insert user likelife hack.
     *
     * @param lifeHackId the life hack id
     * @param userId     the user id
     * @throws DaoException the dao exception
     */
    void insertUserLikeLifeHack(long lifeHackId, long userId) throws DaoException;

    /**
     * Delete user likelife hack.
     *
     * @param lifeHackId the life hack id
     * @param userId     the user id
     * @throws DaoException the dao exception
     */
    void deleteUserLikeLifeHack(long lifeHackId, long userId) throws DaoException;

    /**
     * Is liked lifehack already int.
     *
     * @param lifeHackId the life hack id
     * @param userId     the user id
     * @return the int
     * @throws DaoException the dao exception
     */
    int isLikedLifeHackAlready(long lifeHackId, long userId) throws DaoException;

    /**
     * Find categories lifehacks count int.
     *
     * @param categoryType the category type
     * @return the int
     * @throws DaoException the dao exception
     */
    int findCategoriesLifeHacksCount(CategoryType categoryType) throws DaoException;

    /**
     * Find lifehacks categories map.
     *
     * @return the map
     * @throws DaoException the dao exception
     */
    Map<CategoryType, Integer> findLifeHacksCategories() throws DaoException;


    @Override
    default LifeHack buildEntity(ResultSet resultSet) throws SQLException, DaoException {
        LifeHack lifeHack = new LifeHack();
        UserDao userDao = UserDaoImpl.getInstance();
        User user = userDao.buildEntity(resultSet);
        lifeHack.setUser(user);
        lifeHack.setLifehackId(resultSet.getInt(LIFEHACK_ID));
        lifeHack.setLikesAmount(resultSet.getInt(LIFEHACK_LIKES_AMOUNT));
        Optional<CategoryType> categoryTypeOptional = CategoryType.valueOf(resultSet.getInt(CATEGORY_ID));
        categoryTypeOptional.orElseThrow(DaoException::new);
        lifeHack.setCategory(categoryTypeOptional.get());
        lifeHack.setUserId(resultSet.getInt(AUTHOR_USER_ID));
        lifeHack.setContent(resultSet.getString(LIFEHACK_CONTENT));
        lifeHack.setName(resultSet.getString(LIFEHACK_NAME));
        lifeHack.setExcerpt(resultSet.getString(LIFEHACK_EXCERPT));
        Timestamp timestamp = resultSet.getTimestamp(PUBLICATION_DATE);
        lifeHack.setPublicationDate(timestamp.getTime());
        Blob blob = resultSet.getBlob(PICTURE);
        String img = covertBlobToString(blob);
        lifeHack.setImage(img);
        return lifeHack;
    }

    @Override
    default void setPreparedStatement(PreparedStatement preparedStatement, Entity entity) throws SQLException {
        LifeHack lifeHack = (LifeHack) entity;
        preparedStatement.setLong(1, lifeHack.getUserId());
        preparedStatement.setString(2, lifeHack.getName());
        preparedStatement.setString(3, lifeHack.getContent());
        preparedStatement.setString(4, lifeHack.getExcerpt());
        preparedStatement.setInt(5, lifeHack.getCategory().ordinal());
        preparedStatement.setInt(6, lifeHack.getLikesAmount());
    }

    /**
     * Covert blob to string string.
     *
     * @param blob the blob
     * @return the string
     * @throws SQLException the sql exception
     */
    default String covertBlobToString(Blob blob) throws SQLException {
        String img = null;
        if (blob != null) {
            InputStream inputStream = blob.getBinaryStream();
            byte[] bytes;
            try {
                bytes = IOUtils.toByteArray(inputStream);
                img = new String(Base64.encodeBase64(bytes), StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }
}
