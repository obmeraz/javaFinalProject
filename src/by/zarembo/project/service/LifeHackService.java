package by.zarembo.project.service;

import by.zarembo.project.dao.impl.LifeHackDaoImpl;
import by.zarembo.project.dao.impl.UserDaoImpl;
import by.zarembo.project.entity.LifeHack;
import by.zarembo.project.entity.CategoryType;
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

public class LifeHackService {
    private static Logger logger = LogManager.getLogger();
    private static final int RECORDS_PER_PAGE = 10;

    public void addNewLifeHack(LifeHack lifeHack) throws ServiceException {
        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        try {
            lifeHackDao.create(lifeHack);
        } catch (DaoException e) {
            throw new ServiceException("Can't create lifehack", e);
        }
    }

    public void deleteLifeHack(long lifeHackId) throws ServiceException {
        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        try {
            lifeHackDao.delete(lifeHackId);
        } catch (DaoException e) {
            throw new ServiceException("Can't delete lifehack", e);
        }
    }

    public List<LifeHack> takeLifeHacksList() throws ServiceException {
        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        List<LifeHack> lifeHacks;
        try {
            lifeHacks = lifeHackDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Can't take all lifehacks", e);
        }
        return lifeHacks;
    }

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

    public int takeLifeHacksUserLikesCount(long userId) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        int count;
        try {
            count = userDao.findUserLikesLifeHacksCount(userId);
        } catch (DaoException e) {
            throw new ServiceException("Can't take all lifehacks", e);
        }
        return count;
    }

    public int takeLifeHacksCategoryCount(CategoryType categoryType) throws ServiceException {
        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        List<LifeHack> lifeHacks;
        int count;
        try {
            count = lifeHackDao.findCategoriesLifeHacksCount(categoryType);
        } catch (DaoException e) {
            throw new ServiceException("Can't take all lifehacks", e);
        }
        return count;
    }


    public Map<CategoryType, Integer> takeCategories() throws ServiceException {
        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        Map<CategoryType, Integer> categoryMap;
        try {
            categoryMap = lifeHackDao.findLifeHacksCategories();
        } catch (DaoException e) {
            throw new ServiceException("can't take categories", e);
        }
        return categoryMap;
    }

    public Optional<LifeHack> takeLifeHackById(long lifeHackId) throws ServiceException {
        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        Optional<LifeHack> lifeHackOptional;
        try {
            lifeHackOptional = lifeHackDao.findById(lifeHackId);
        } catch (DaoException e) {
            throw new ServiceException("can't take lifehack by id", e);
        }
        return lifeHackOptional;
    }

    public void likeLifeHack(long lifeHackId) throws ServiceException {
        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        try {
            lifeHackDao.likeLifeHack(lifeHackId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void removeLikeLifHack(long lifeHackId) throws ServiceException {
        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        try {
            lifeHackDao.decrementLifeHackLike(lifeHackId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void insertUserLikeLifeHack(long lifeHackId, long userId) throws ServiceException {
        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        try {
            lifeHackDao.insertUserLikeLifeHack(lifeHackId, userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void deleteUserLikeLifeHack(long lifeHackId, long userId) throws ServiceException {
        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        try {
            lifeHackDao.deleteUserLikeLifeHack(lifeHackId, userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }

    public boolean isLikedAlready(long lifeHackId, long userId) throws ServiceException {
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

    public void editLifeHackName(String newName, LifeHack lifeHack) throws ServiceException {
        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        lifeHack.setName(newName);
        try {
            lifeHackDao.update(lifeHack);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }

    public void editLifeHackContent(String newContent, LifeHack lifeHack) throws ServiceException {
        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        lifeHack.setContent(newContent);
        try {
            lifeHackDao.update(lifeHack);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

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

    public void editLifeHackExcerpt(String newExcerpt, LifeHack lifeHack) throws ServiceException {
        LifeHackDaoImpl lifeHackDao = LifeHackDaoImpl.getInstance();
        lifeHack.setExcerpt(newExcerpt);
        try {
            lifeHackDao.update(lifeHack);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }

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

    public int getNumberOfPages(String type, String category, User user) throws ServiceException {
        List<LifeHack> lifeHackList = takeLifeHacksList();
        int rows = 0;
        if ("category".equals(type)) {
            if (category != null) {
                CategoryType categoryType = CategoryType.valueOf(category.toUpperCase());
                rows = takeLifeHacksCategoryCount(categoryType);
            }
        } else if ("user_like".equals(type)) {
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
