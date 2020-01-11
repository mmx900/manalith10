/*
 * Created on 2005. 3. 22
 */
package org.manalith.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.manalith.db.HibernateUtil;
import org.manalith.model.dao.entity.UserEntity;
import org.manalith.resource.User;


/**
 * @author setzer
 */
public class UserDAO {
    private static UserDAO manager = null;
    private static Logger logger = LoggerFactory.getLogger(UserDAO.class);

    private UserDAO() {
    }

    public static UserDAO instance() {
        if (manager == null) {
            manager = new UserDAO();
        }
        return manager;
    }

    public User getUser(String id) {
        User user = null;
        Session session = HibernateUtil.currentSession();

        try {
            UserEntity entity = (UserEntity) session.get(UserEntity.class, id);
            user = new ModelMapper().map(entity, User.class);
        } catch (HibernateException e) {
            logger.error(e.getMessage(), e);
        } finally {
            HibernateUtil.closeSession();
        }

        return user;
    }

    public List<User> getUsers() {
        List<User> userList = new ArrayList<>();
        Session session = HibernateUtil.currentSession();
        ModelMapper mapper = new ModelMapper();

        try {
            List<UserEntity> entities = (List<UserEntity>) session.createCriteria(UserEntity.class).list();
            userList = entities.stream().map(e -> mapper.map(e, User.class)).collect(Collectors.toList());
        } catch (HibernateException e) {
            logger.error(e.getMessage(), e);
        } finally {
            HibernateUtil.closeSession();
        }

        return userList;
    }

    public List<User> getMatchedUsersByName(String name) {
        List<User> userList = new ArrayList<>();
        Session session = HibernateUtil.currentSession();
        ModelMapper mapper = new ModelMapper();

        try {
            List<UserEntity> entities = (List<UserEntity>) session
                    .createQuery("select u from UserEntity u where u.name like ?")
                    .setString(0, "%" + name + "%")
                    .list();
            userList = entities.stream().map(e -> mapper.map(e, User.class)).collect(Collectors.toList());
        } catch (HibernateException e) {
            logger.error(e.getMessage(), e);
        } finally {
            HibernateUtil.closeSession();
        }

        return userList;
    }

    public List<User> getMatchedUsersById(String id) {
        List<User> userList = new ArrayList<>();
        Session session = HibernateUtil.currentSession();
        ModelMapper mapper = new ModelMapper();

        try {
            List<UserEntity> entities = (List<UserEntity>) session
                    .createQuery("select u from UserEntity u where u.id like ?")
                    .setString(0, "%" + id + "%")
                    .list();
            userList = entities.stream().map(e -> mapper.map(e, User.class)).collect(Collectors.toList());
        } catch (HibernateException e) {
            logger.error(e.getMessage(), e);
        } finally {
            HibernateUtil.closeSession();
        }

        return userList;
    }

    public List<User> getMatchedUsersById(String id, String exceptionId) {
        List<User> userList = new ArrayList<>();
        Session session = HibernateUtil.currentSession();
        ModelMapper mapper = new ModelMapper();

        try {
            List<UserEntity> entities = (List<UserEntity>) session
                    .createQuery("select u from UserEntity u where u.id like ? and not u.id=?")
                    .setString(0, "%" + id + "%")
                    .setString(1, exceptionId)
                    .list();
            userList = entities.stream().map(e -> mapper.map(e, User.class)).collect(Collectors.toList());
        } catch (HibernateException e) {
            logger.error(e.getMessage(), e);
        } finally {
            HibernateUtil.closeSession();
        }

        return userList;
    }

    public List<User> getMatchedUsersByEmail(String email) {
        List<User> userList = new ArrayList<>();
        Session session = HibernateUtil.currentSession();
        ModelMapper mapper = new ModelMapper();

        try {
            List<UserEntity> entities = (List<UserEntity>) session
                    .createQuery("select u from UserEntity u where u.email like ?")
                    .setString(0, "%" + email + "%")
                    .list();
            userList = entities.stream().map(e -> mapper.map(e, User.class)).collect(Collectors.toList());
        } catch (HibernateException e) {
            logger.error(e.getMessage(), e);
        } finally {
            HibernateUtil.closeSession();
        }

        return userList;
    }

    public void addUser(User user) {
        Session session = HibernateUtil.currentSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            UserEntity entity = new UserEntity();
            new ModelMapper().map(user, entity);
            session.save(entity);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            logger.error(e.getMessage(), e);
        } finally {
            HibernateUtil.closeSession();
        }
    }

    public void updateUser(User user) {
        Session session = HibernateUtil.currentSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.createQuery("update UserEntity u set u.name = :name, u.password = :password, u.email = :email where u.id = :id")
                    .setParameter("name", user.getName())
                    .setParameter("password", user.getPassword())
                    .setParameter("email", user.getEmail())
                    .setParameter("id", user.getId())
                    .executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            logger.error(e.getMessage(), e);
        } finally {
            HibernateUtil.closeSession();
        }
    }

    public boolean validUser(User user) {
        Session session = HibernateUtil.currentSession();
        boolean result = false;

        try {
            result = session
                    .createQuery("select u from UserEntity u where u.id=? and u.password=?")
                    .setParameter(0, user.getId())
                    .setParameter(1, user.getPassword())
                    .uniqueResult() != null;
        } catch (HibernateException e) {
            logger.error(e.getMessage(), e);
        } finally {
            HibernateUtil.closeSession();
        }

        return result;
    }

    public boolean existUser(String id) {
        Session session = HibernateUtil.currentSession();
        boolean result = false;

        try {
            result = session
                    .createQuery("select u from UserEntity u where u.id=?")
                    .setParameter(0, id)
                    .uniqueResult() != null;
        } catch (HibernateException e) {
            logger.error(e.getMessage(), e);
        } finally {
            HibernateUtil.closeSession();
        }

        return result;
    }

    //FIXME : user를 제거하기 위해서, user의 id를 foreign key로 사용하는 테이블들의 row를 먼저 삭제해야 함.
    public void removeUser(String id) {
        Session session = HibernateUtil.currentSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.delete(new UserEntity(id));
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            logger.error(e.getMessage(), e);
        } finally {
            HibernateUtil.closeSession();
        }
    }
}
