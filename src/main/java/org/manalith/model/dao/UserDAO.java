/*
 * Created on 2005. 3. 22
 */
package org.manalith.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.manalith.db.ConnectionFactory;
import org.manalith.resource.User;


/**
 * @author setzer
 */
public class UserDAO {
    private Connection conn;
    private static UserDAO manager = null;
    private static Logger logger = LoggerFactory.getLogger(UserDAO.class);
    
    private UserDAO(){
        try {
            conn = ConnectionFactory.getConnection();
        } catch (SQLException ex) {
            logger.error("SQLException: " + ex.getMessage()); 
            logger.error("SQLState: " + ex.getSQLState()); 
            logger.error("VendorError: " + ex.getErrorCode());
            logger.error("detail :");
            logger.error(ex.getMessage(), ex);
        }
    }
    
    public static UserDAO instance(){
        if(manager == null){
            manager = new UserDAO();
        }
        return manager;
    }
    
    public User getUser(String id){
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT id,password,name,email FROM manalith_member ");
        sb.append("WHERE id=?");
        
        try{
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1,id);
            rs = pstmt.executeQuery();
            
            if(rs.next()){
                user = new User();
                user.setId(rs.getString("id"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
            }
        }catch(SQLException e){
            logger.error(e.toString());
        }finally {
            if (rs != null) {
                try {
                    rs.close(); 
                } catch (SQLException e) {
                    logger.error(e.toString());
                }
                rs = null; 
            }
            if (pstmt != null) {
                try {
                  pstmt.close(); 
                } catch (SQLException e) {
        	        logger.error(e.toString());
                }
                pstmt = null; 
            } 
        }
        return user;
    }

    public List<User> getUsers(){
        User user = null;
        List<User> userList = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT id,name,email FROM manalith_member ORDER BY id DESC");
        
        try{
            pstmt = conn.prepareStatement(sb.toString());
            
            logger.warn("다음 쿼리로 유저 정보를 가져옵니다 :");
            logger.warn(pstmt.toString());
            
            rs = pstmt.executeQuery();
            
            userList = new ArrayList<User>();
            while(rs.next()){
                user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                userList.add(user);
            }
        }catch(SQLException e){
            logger.error(e.toString());
        }finally {
            if (rs != null) {
                try {
                    rs.close(); 
                } catch (SQLException e) {
                    logger.error(e.toString());
                }
                rs = null; 
            }
            if (pstmt != null) {
                try {
                  pstmt.close(); 
                } catch (SQLException e) {
        	        logger.error(e.toString());
                }
                pstmt = null; 
            } 
        }
        return userList;
    }
    
    public List<User> getMatchedUsersByName(String name){
        User user = null;
        List<User> userList = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT id,name,email FROM manalith_member ");
        sb.append("WHERE name LIKE '?'");
        sb.append("ORDER BY id DESC");
        
        try{
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1,"%" + name + "%");
            
            rs = pstmt.executeQuery();
            
            userList = new ArrayList<User>();
            while(rs.next()){
                user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                userList.add(user);
            }
        }catch(SQLException e){
            logger.error(e.toString());
        }finally {
            if (rs != null) {
                try {
                    rs.close(); 
                } catch (SQLException e) {
                    logger.error(e.toString());
                }
                rs = null; 
            }
            if (pstmt != null) {
                try {
                  pstmt.close(); 
                } catch (SQLException e) {
        	        logger.error(e.toString());
                }
                pstmt = null; 
            } 
        }
        return userList;
    }

    public List<User> getMatchedUsersById(String id){
        User user = null;
        List<User> userList = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT id,name,email FROM manalith_member ");
        sb.append("WHERE id LIKE ? ");
        sb.append("ORDER BY id DESC");
        
        try{
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1,"%" + id + "%");
            
            rs = pstmt.executeQuery();
            
            userList = new ArrayList<User>();
            while(rs.next()){
                user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                userList.add(user);
            }
        }catch(SQLException e){
            logger.error(e.toString());
        }finally {
            if (rs != null) {
                try {
                    rs.close(); 
                } catch (SQLException e) {
                    logger.error(e.toString());
                }
                rs = null; 
            }
            if (pstmt != null) {
                try {
                  pstmt.close(); 
                } catch (SQLException e) {
        	        logger.error(e.toString());
                }
                pstmt = null; 
            } 
        }
        return userList;
    }
    public List<User> getMatchedUsersById(String id, String exceptionId){
        User user = null;
        List<User> userList = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT id,name,email FROM manalith_member ");
        sb.append("WHERE id LIKE ? AND NOT id=?");
        sb.append("ORDER BY id DESC");
        
        try{
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1,"%" + id + "%");
            pstmt.setString(2,exceptionId);
            
            rs = pstmt.executeQuery();
            
            userList = new ArrayList<User>();
            while(rs.next()){
                user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                userList.add(user);
            }
        }catch(SQLException e){
            logger.error(e.toString());
        }finally {
            if (rs != null) {
                try {
                    rs.close(); 
                } catch (SQLException e) {
                    logger.error(e.toString());
                }
                rs = null; 
            }
            if (pstmt != null) {
                try {
                  pstmt.close(); 
                } catch (SQLException e) {
        	        logger.error(e.toString());
                }
                pstmt = null; 
            } 
        }
        return userList;
    }
    
    public List<User> getMatchedUsersByEmail(String email){
        User user = null;
        List<User> userList = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT id,name,email FROM manalith_member ");
        sb.append("WHERE email LIKE ? ");
        sb.append("ORDER BY id DESC");
        
        try{
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1,"%" + email + "%");
            
            rs = pstmt.executeQuery();
            
            userList = new ArrayList<User>();
            while(rs.next()){
                user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                userList.add(user);
            }
        }catch(SQLException e){
            logger.error(e.toString());
        }finally {
            if (rs != null) {
                try {
                    rs.close(); 
                } catch (SQLException e) {
                    logger.error(e.toString());
                }
                rs = null; 
            }
            if (pstmt != null) {
                try {
                  pstmt.close(); 
                } catch (SQLException e) {
        	        logger.error(e.toString());
                }
                pstmt = null; 
            } 
        }
        return userList;
    }
    
    public void addUser(User user){
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO manalith_member(id,name,password,email) ");
        sb.append("VALUES(?,?,?,?)");
        
        try{
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1,user.getId());
            pstmt.setString(2,user.getName());
            pstmt.setString(3,user.getPassword());
            pstmt.setString(4,user.getEmail());
            
            pstmt.executeUpdate();
        }catch(SQLException e){
            logger.error(e.toString());
        }finally {
            if (pstmt != null) {
                try {
                  pstmt.close(); 
                } catch (SQLException e) {
        	        logger.error(e.toString());
                }
                pstmt = null; 
            } 
        }
    }

    public void updateUser(User user){
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE manalith_member ");
        sb.append("SET name=?,password=?,email=? ");
        sb.append("WHERE id=?");
        
        try{
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1,user.getName());
            pstmt.setString(2,user.getPassword());
            pstmt.setString(3,user.getEmail());
            pstmt.setString(4,user.getId());
            
            pstmt.executeUpdate();
        }catch(SQLException e){
            logger.error(e.toString());
        }finally {
            if (pstmt != null) {
                try {
                  pstmt.close(); 
                } catch (SQLException e) {
        	        logger.error(e.toString());
                }
                pstmt = null; 
            } 
        }
    }
    
    public boolean validUser(User user){
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean result = false;
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT id,password,name,email FROM manalith_member ");
        sb.append("WHERE id=? AND password=?");
        
        try{
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1,user.getId());
            pstmt.setString(2,user.getPassword());
            rs = pstmt.executeQuery();
            
            if(rs.next()){
                result = true;
            }
        }catch(SQLException e){
            logger.error(e.toString());
        }finally {
            if (rs != null) {
                try {
                    rs.close(); 
                } catch (SQLException e) {
                    logger.error(e.toString());
                }
                rs = null; 
            }
            if (pstmt != null) {
                try {
                  pstmt.close(); 
                } catch (SQLException e) {
        	        logger.error(e.toString());
                }
                pstmt = null; 
            } 
        }
        return result;
    }
    
    public boolean existUser(String id){
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean result = false;
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT id,password,name,email FROM manalith_member ");
        sb.append("WHERE id=?");
        
        try{
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1,id);
            rs = pstmt.executeQuery();
            
            if(rs.next()){
                result = true;
            }
        }catch(SQLException e){
            logger.error(e.toString());
        }finally {
            if (rs != null) {
                try {
                    rs.close(); 
                } catch (SQLException e) {
                    logger.error(e.toString());
                }
                rs = null; 
            }
            if (pstmt != null) {
                try {
                  pstmt.close(); 
                } catch (SQLException e) {
        	        logger.error(e.toString());
                }
                pstmt = null; 
            } 
        }
        return result;
    }
    
    //FIXME : user를 제거하기 위해서, user의 id를 foreign key로 사용하는 테이블들의 row를 먼저 삭제해야 함.
    public void removeUser(String id){
        PreparedStatement pstmt = null;
        StringBuffer sb = new StringBuffer();
        sb.append("DELETE FROM manalith_member ");
        sb.append("WHERE id=?");
        
        try{
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1,id);
            
            pstmt.executeUpdate();
        }catch(SQLException e){
            logger.error(e.toString());
        }finally {
            if (pstmt != null) {
                try {
                  pstmt.close(); 
                } catch (SQLException e) {
        	        logger.error(e.toString());
                }
                pstmt = null; 
            } 
        }
    }
}
