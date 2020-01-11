package org.manalith.db.datatype;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;
import org.manalith.resource.ArticleFormat;

/**
 * @see org.manalith.resource.ArticleFormat
 */
public class ArticleFormatType implements UserType {

	@Override
	public int[] sqlTypes() {
		return new int[]{Types.VARCHAR};
	}

	@Override
	public Class<String> returnedClass() {
		return String.class;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		return (x == y)
				|| (x != null
				&& y != null
				&& (x.equals(y)));
	}

	@Override
	public int hashCode(Object arg0) throws HibernateException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] arg1, SharedSessionContractImplementor sharedSessionContractImplementor, Object arg2)
			throws HibernateException, SQLException {
		ArticleFormat format = null;
		try {
			format = new ArticleFormat(rs.getString(arg1[0]));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return format;
	}

	@Override
	public void nullSafeSet(PreparedStatement pstmt, Object format, int index, SharedSessionContractImplementor sharedSessionContractImplementor)
			throws HibernateException, SQLException {
		pstmt.setString(index, format.toString());

	}

	@Override
	public Object deepCopy(Object arg0) throws HibernateException {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public boolean isMutable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Serializable disassemble(Object arg0) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object assemble(Serializable arg0, Object arg1)
			throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object replace(Object original, Object target, Object owner)
			throws HibernateException {
		// TODO Auto-generated method stub
		return original;
	}
}
