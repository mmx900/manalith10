/*
 * Created on 2005. 5. 6
 */
package org.manalith.db.datatype;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;
import org.postgresql.util.PGobject;


/**
 * PostgreSQL의 Inet형 데이터타입을 매핑하기 위해 만들어진 클래스입니다.
 * pg74 jdbc 드라이버까지는 Inet형 데이터를 삽입할때 자바의 문자열 형태로도 가능했지만,
 * postgresql8.0 드라이버에서는 반드시 PGobject로 저장해야 하기 때문입니다.
 * http://www.hibernate.org/76.html의 내용을 참고했습니다.
 * TODO 몇가지 함수가 미완 상태입니다.
 * @author setzer
 */
public class InetType implements UserType {
	
	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#sqlTypes()
	 */
	public int[] sqlTypes() {
		return new int[]{Types.OTHER};
	}
	
	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#returnedClass()
	 */
	public Class returnedClass() {
		return String.class;
	}
	
	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#equals(java.lang.Object, java.lang.Object)
	 */
	public boolean equals(Object x, Object y) throws HibernateException {
		return (x == y)
		|| (x != null
				&& y != null
				&& (x.equals(y)));
	}
	
	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#hashCode(java.lang.Object)
	 */
	public int hashCode(Object arg0) throws HibernateException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#nullSafeGet(java.sql.ResultSet, java.lang.String[], java.lang.Object)
	 */
	public Object nullSafeGet(ResultSet rs, String[] arg1, Object arg2)
	throws HibernateException, SQLException {
		return rs.getString(arg1[0]);
	}
	
	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#nullSafeSet(java.sql.PreparedStatement, java.lang.Object, int)
	 */
	public void nullSafeSet(PreparedStatement pstmt, Object obj, int index)
	throws HibernateException, SQLException {
		PGobject pobj = new PGobject();
		pobj.setType("inet");
		pobj.setValue((String) obj);
		pstmt.setObject(index,pobj);
		
	}
	
	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#deepCopy(java.lang.Object)
	 */
	public Object deepCopy(Object obj) throws HibernateException {
		if (obj == null) return null;
		return new String((String) obj);
	}
	
	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#isMutable()
	 */
	public boolean isMutable() {
		return false;
	}
	
	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#disassemble(java.lang.Object)
	 */
	public Serializable disassemble(Object arg0) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#assemble(java.io.Serializable, java.lang.Object)
	 */
	public Object assemble(Serializable arg0, Object arg1)
	throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#replace(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	public Object replace(Object arg0, Object arg1, Object arg2)
	throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
