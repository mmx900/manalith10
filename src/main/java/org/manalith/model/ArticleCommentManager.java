/*
 * Created on 2005. 9. 25
 */
package org.manalith.model;

import javax.servlet.ServletException;

import org.manalith.model.dao.ArticleCommentDAO;
import org.manalith.resource.ArticleComment;


public class ArticleCommentManager {
	private ArticleCommentManager(){}
	public static ArticleCommentManager instance(){
		return new ArticleCommentManager();
	}
	
	public void createComment(ArticleComment comment) throws Exception {
		ArticleCommentDAO.instance().createComment(comment);
	}
	
	public void destroyComment(ArticleComment comment) throws Exception {
		ArticleCommentDAO manager = ArticleCommentDAO.instance();
		
		if(manager.isValidPassword(comment.getId(),comment.getPassword()))
			manager.destroyComment(comment.getArticleId(),comment.getId());
		else
			throw new ServletException("비밀번호가 다릅니다.");
	}
}
