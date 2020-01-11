package org.manalith.model.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.manalith.db.HibernateUtil;
import org.manalith.resource.VisitorLog;

public class VisitorLogDAO {
    private static VisitorLogDAO manager;
    private static Logger logger = LoggerFactory.getLogger(VisitorLogDAO.class);

    private VisitorLogDAO() {
    }

    public static VisitorLogDAO instance() {
        if (manager == null) {
            manager = new VisitorLogDAO();
        }
        return manager;
    }

    public void addLog(VisitorLog vLog) {
        Session session = HibernateUtil.currentSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.save(vLog);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            logger.error(e.getMessage(), e);
        } finally {
            HibernateUtil.closeSession();
        }
    }
}
