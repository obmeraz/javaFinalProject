package by.zarembo.project.dao;

import by.zarembo.project.entity.Entity;
import by.zarembo.project.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Dao<T extends Entity> {
    int RECORDS_PER_PAGE = 10;

    List<T> findAll() throws DaoException;

    void create(T entity) throws DaoException;

    void update(T entity) throws DaoException;

    void delete(long id) throws DaoException;

    Optional<T> findById(long id) throws DaoException;

    default Entity buildEntity(ResultSet resultSet) throws SQLException, DaoException {
        return null;
    }

    default void setPreparedStatement(PreparedStatement preparedStatement, Entity entity) throws SQLException {

    }
}

