package by.zarembo.project.dao;

import by.zarembo.project.entity.Entity;
import by.zarembo.project.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * The interface Dao.
 *
 * @param <T> the type parameter
 */
public interface Dao<T extends Entity> {
    /**
     * The constant RECORDS_PER_PAGE.
     */
    int RECORDS_PER_PAGE = 10;

    /**
     * Find all list.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    List<T> findAll() throws DaoException;

    /**
     * Create.
     *
     * @param entity the entity
     * @throws DaoException the dao exception
     */
    void create(T entity) throws DaoException;

    /**
     * Update.
     *
     * @param entity the entity
     * @throws DaoException the dao exception
     */
    void update(T entity) throws DaoException;

    /**
     * Delete.
     *
     * @param id the id
     * @throws DaoException the dao exception
     */
    void delete(long id) throws DaoException;

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<T> findById(long id) throws DaoException;

    /**
     * Build entity entity.
     *
     * @param resultSet the result set
     * @return the entity
     * @throws SQLException the sql exception
     * @throws DaoException the dao exception
     */
    default Entity buildEntity(ResultSet resultSet) throws SQLException, DaoException {
        return null;
    }

    /**
     * Sets prepared statement.
     *
     * @param preparedStatement the prepared statement
     * @param entity            the entity
     * @throws SQLException the sql exception
     */
    default void setPreparedStatement(PreparedStatement preparedStatement, Entity entity) throws SQLException {

    }
}

