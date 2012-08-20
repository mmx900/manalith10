/*
 * Created on 2005. 4. 20
 */
package org.manalith.model.dao;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.manalith.db.HibernateUtil;
import org.manalith.resource.VisitorLog;

/**
 * @author setzer
 */
public class VisitorLogDAO {
    private static VisitorLogDAO manager;
    private static Logger logger = Logger.getLogger(VisitorLogDAO.class);
    
    private VisitorLogDAO(){
    }
    
    public static VisitorLogDAO instance(){
        if(manager == null){
            manager = new VisitorLogDAO();
        }
        return manager;
    }
    
    public void addLog(VisitorLog vLog){
        Session session = HibernateUtil.currentSession();
        Transaction tx = null;
        
        try{
	        tx = session.beginTransaction();
	        session.save(vLog);
	        tx.commit();
        }catch(HibernateException e){
            if(tx != null) tx.rollback();
            logger.error(e);
        }finally{
            HibernateUtil.closeSession();
        }
    }
}
