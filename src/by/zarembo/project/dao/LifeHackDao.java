package by.zarembo.project.dao;

import by.zarembo.project.dao.impl.UserDaoImpl;
import by.zarembo.project.entity.Entity;
import by.zarembo.project.entity.LifeHack;
import by.zarembo.project.entity.CategoryType;
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

public interface LifeHackDao extends Dao<LifeHack> {
    String LIFEHACK_ID = "lifehack_id";
    String LIFEHACK_LIKES_AMOUNT = "lifehack_likes_amount";
    String CATEGORY_ID = "category_id";
    String AUTHOR_USER_ID = "author_user_id";
    String LIFEHACK_NAME = "lifehack_name";
    String LIFEHACK_CONTENT = "lifehack_content";
    String LIFEHACK_EXCERPT = "excerpt";
    String PUBLICATION_DATE = "publication_date";
    String PICTURE = "picture";

    List<LifeHack> findPopularLifeHacks(int cursor) throws DaoException;

    List<LifeHack> findRecentLifeHacks(int cursor) throws DaoException;

    List<LifeHack> findLifeHacksByCategory(int cursor, CategoryType category) throws DaoException;

    void likeLifeHack(long lifeHackId) throws DaoException;

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
