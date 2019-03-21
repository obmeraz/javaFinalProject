package by.zarembo.project.service;

import by.zarembo.project.dao.impl.LifeHackDaoImpl;
import by.zarembo.project.dao.impl.UserDaoImpl;
import by.zarembo.project.entity.CategoryType;
import by.zarembo.project.entity.LifeHack;
import by.zarembo.project.entity.User;
import by.zarembo.project.exception.DaoException;
import by.zarembo.project.exception.ServiceException;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The type Life hack service.
 */
public class LifeHackService {
    private static final String LIFEHACK_CATEGORY = "category";
    private static final String TYPE_USER_LIKE = "user_like";
    private static final int RECORDS_PER_PAGE = 10;
    private static Logger logger = LogManager.getLogger();


    /**
     * Add new lifehack.
     *
     * @param lifeHack the lifehack
     * @throws ServiceException the service exception
     */
    public void addNewLifeHack(LifeHack lifeHack) throws ServiceException {
        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        try {
            lifeHackDao.create(lifeHack);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Delete lifehack.
     *
     * @param lifeHackId the lifehack id
     * @throws ServiceException the service exception
     */
    public void deleteLifeHack(long lifeHackId) throws ServiceException {
        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        try {
            lifeHackDao.delete(lifeHackId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Take all lifehacks list list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<LifeHack> takeAllLifeHacksList() throws ServiceException {
        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        List<LifeHack> lifeHacks;
        try {
            lifeHacks = lifeHackDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return lifeHacks;
    }

    /**
     * Take lifehacks by type list.
     *
     * @param type        the type
     * @param category    the category
     * @param currentPage the current page
     * @param user        the user
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<LifeHack> takeLifeHacksByType(String type, String category, int currentPage, User user) throws ServiceException {
        List<LifeHack> lifeHacks = null;
        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        LifeHackSelection selection = LifeHackSelection.valueOf(type.toUpperCase());
        int cursor = currentPage * RECORDS_PER_PAGE - RECORDS_PER_PAGE;
        try {
            switch (selection) {
                case POPULAR:
                    lifeHacks = lifeHackDao.findPopularLifeHacks(cursor);
                    break;
                case RECENT:
                    lifeHacks = lifeHackDao.findRecentLifeHacks(cursor);
                    break;
                case CATEGORY:
                    if (category != null) {
                        CategoryType categoryType = CategoryType.valueOf(category.toUpperCase());
                        lifeHacks = lifeHackDao.findLifeHacksByCategory(cursor, categoryType);
                    }
                    break;
                case USER_LIKE:
                    lifeHacks = userDao.findUserLikesLifeHacks(cursor, user.getUserId());
                    break;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return lifeHacks;
    }

    /**
     * Take lifehacks user likes count int.
     *
     * @param userId the user id
     * @return the int
     * @throws ServiceException the service exception
     */
    private int takeLifeHacksUserLikesCount(long userId) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        int count;
        try {
            count = userDao.findUserLikesLifeHacksCount(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return count;
    }

    /**
     * Take lifehacks category count int.
     *
     * @param categoryType the category type
     * @return the int
     * @throws ServiceException the service exception
     */
    private int takeLifeHacksCategoryCount(CategoryType categoryType) throws ServiceException {
        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        List<LifeHack> lifeHacks;
        int count;
        try {
            count = lifeHackDao.findCategoriesLifeHacksCount(categoryType);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return count;
    }


    /**
     * Take all categories  with count lifehacks map.
     *
     * @return the map
     * @throws ServiceException the service exception
     */
    public Map<CategoryType, Integer> takeCategories() throws ServiceException {
        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        Map<CategoryType, Integer> categoryMap;
        try {
            categoryMap = lifeHackDao.findLifeHacksCategories();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return categoryMap;
    }

    /**
     * Take lifehack by id optional.
     *
     * @param lifeHackId the lifehack id
     * @return the optional
     * @throws ServiceException the service exception
     */
    public Optional<LifeHack> takeLifeHackById(long lifeHackId) throws ServiceException {
        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        Optional<LifeHack> lifeHackOptional;
        try {
            lifeHackOptional = lifeHackDao.findById(lifeHackId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return lifeHackOptional;
    }

    /**
     * Like lifehack.
     *
     * @param lifeHackId the lifehack id
     * @throws ServiceException the service exception
     */
    public void likeLifeHack(long lifeHackId) throws ServiceException {
        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        try {
            lifeHackDao.likeLifeHack(lifeHackId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Remove like from lifehack.
     *
     * @param lifeHackId the lifehack id
     * @throws ServiceException the service exception
     */
    public void removeLikeLifeHack(long lifeHackId) throws ServiceException {
        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        try {
            lifeHackDao.decrementLifeHackLike(lifeHackId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Insert user like lifehack.
     *
     * @param lifeHackId the lifehack id
     * @param userId     the user id
     * @throws ServiceException the service exception
     */
    public void insertUserLikeLifeHack(long lifeHackId, long userId) throws ServiceException {
        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        try {
            lifeHackDao.insertUserLikeLifeHack(lifeHackId, userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Delete user like lifehack.
     *
     * @param lifeHackId the lifehack id
     * @param userId     the user id
     * @throws ServiceException the service exception
     */
    public void deleteUserLikeLifeHack(long lifeHackId, long userId) throws ServiceException {
        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        try {
            lifeHackDao.deleteUserLikeLifeHack(lifeHackId, userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }

    /**
     * Is lifehack liked already boolean.
     *
     * @param lifeHackId the lifehack id
     * @param userId     the user id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    public boolean isLifeHackLikedAlready(long lifeHackId, long userId) throws ServiceException {
        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        boolean isLiked = false;
        int count;
        try {
            count = lifeHackDao.isLikedLifeHackAlready(lifeHackId, userId);
            if (count > 0) {
                isLiked = true;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return isLiked;
    }

    /**
     * Edit lifehack name.
     *
     * @param newName  the new name
     * @param lifeHack the life hack
     * @throws ServiceException the service exception
     */
    public void editLifeHackName(String newName, LifeHack lifeHack) throws ServiceException {
        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        lifeHack.setName(newName);
        try {
            lifeHackDao.update(lifeHack);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }

    /**
     * Edit lifehack content.
     *
     * @param newContent the new content
     * @param lifeHack   the life hack
     * @throws ServiceException the service exception
     */
    public void editLifeHackContent(String newContent, LifeHack lifeHack) throws ServiceException {
        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        lifeHack.setContent(newContent);
        try {
            lifeHackDao.update(lifeHack);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Edit lifehack category.
     *
     * @param newCategory the new category
     * @param lifeHack    the life hack
     * @throws ServiceException the service exception
     */
    public void editLifeHackCategory(String newCategory, LifeHack lifeHack) throws ServiceException {
        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        CategoryType categoryType = CategoryType.valueOf(newCategory.toUpperCase());
        lifeHack.setCategory(categoryType);
        try {
            lifeHackDao.update(lifeHack);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Edit lifehack excerpt.
     *
     * @param newExcerpt the new excerpt
     * @param lifeHack   the life hack
     * @throws ServiceException the service exception
     */
    public void editLifeHackExcerpt(String newExcerpt, LifeHack lifeHack) throws ServiceException {
        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        lifeHack.setExcerpt(newExcerpt);
        try {
            lifeHackDao.update(lifeHack);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }

    /**
     * Build entity lifehack lifehack.
     *
     * @param user     the user
     * @param category the category
     * @param name     the name
     * @param content  the content
     * @param excerpt  the excerpt
     * @param filePart the file part
     * @return the life hack
     */
    public LifeHack buildLifeHack(User user, String category, String name,
                                  String content, String excerpt, Part filePart) {
        LifeHack lifeHack = new LifeHack();
        CategoryType categoryType = CategoryType.valueOf(category.toUpperCase());
        lifeHack.setName(name);
        lifeHack.setExcerpt(excerpt);
        lifeHack.setUserId(user.getUserId());
        lifeHack.setContent(content);
        lifeHack.setCategory(categoryType);
        lifeHack.setImageBytes(covertImageToByteArray(filePart));
        return lifeHack;
    }

    private byte[] covertImageToByteArray(Part filePart) {
        InputStream inputStream = null;
        byte[] bytes = null;
        try {
            inputStream = filePart.getInputStream();
            bytes = IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            logger.error("While covering img to byte error", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("While closing inputstream", e);
                }
            }
        }
        return bytes;
    }

    /**
     * Gets number of pages.
     *
     * @param type     the type
     * @param category the category
     * @param user     the user
     * @return the number of pages
     * @throws ServiceException the service exception
     */
    public int getNumberOfPages(String type, String category, User user) throws ServiceException {
        List<LifeHack> lifeHackList = takeAllLifeHacksList();
        int rows = 0;
        if (LIFEHACK_CATEGORY.equals(type)) {
            if (category != null) {
                CategoryType categoryType = CategoryType.valueOf(category.toUpperCase());
                rows = takeLifeHacksCategoryCount(categoryType);
            }
        } else if (TYPE_USER_LIKE.equals(type)) {
            rows = takeLifeHacksUserLikesCount(user.getUserId());
        } else {
            rows = lifeHackList.size();
        }
        int numberOfPages = rows / RECORDS_PER_PAGE;
        if (numberOfPages % RECORDS_PER_PAGE > 0) {
            numberOfPages++;
        }
        return numberOfPages;
    }
}
