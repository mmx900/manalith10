/*
 * Created on 2005. 9. 25
 */
package org.manalith.model;

import org.manalith.model.dao.ArticleDAO;
import org.manalith.model.dao.FileDAO;
import org.manalith.resource.Article;


public class ArticleManager {
	public static final String ARTICLE_CREATE_MODE = "create";
	public static final String ARTICLE_UPDATE_MODE = "update";
	
	private ArticleManager(){};
	public static ArticleManager instance(){
		return new ArticleManager();
	}

	/**
	 * 새로운 게시물을 생성한다.
	 */
	public void createArticle(Article article, int[] files) throws Exception{
		ArticleDAO.instance().createArticle(article);
		FileDAO.instance().setConnectedFiles(
				files,
				ArticleDAO.instance().getMatchedArticleId(article));
	}
	

	/**
	 * 기존의 게시물과 관련 파일들을 일괄 삭제한다.
	 */
	public void destroyArticle(String blogOwnerId, int articleId) throws Exception {
		FileDAO.instance().deleteFiles(articleId);
		ArticleDAO.instance().destroyArticle(
				blogOwnerId,articleId);
	}
	
	/**
	 * 기존 게시물을 변경한다.
	 */
	public void updateArticle(Article article, int[] files) throws Exception {
		ArticleDAO.instance().updateArticle(article);
		
		FileDAO.instance().setConnectedFiles(
				files,
				article.getId());
	}
}
