package org.manalith.model.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.internal.SessionImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.manalith.db.HibernateUtil;
import org.manalith.resource.Article;
import org.manalith.resource.ArticleFormat;
import org.manalith.resource.User;
import org.manalith.resource.calendar.BlogCalendar;
import org.manalith.resource.calendar.Day;
import org.manalith.resource.calendar.Month;
import org.manalith.resource.calendar.Year;

/**
 * @author setzer
 */
public class ArticleDAO {
	private static ArticleDAO manager = null;
	private static Logger logger = LoggerFactory.getLogger(ArticleDAO.class);

	private ArticleDAO() {
	}

	public static ArticleDAO instance() {
		if (manager == null) {
			manager = new ArticleDAO();
		}
		return manager;
	}

	/**
	 * 아티클을 생성합니다.
	 *
	 * @param article 생성할 아티클
	 */
	public void createArticle(Article article) {
		Session session = HibernateUtil.currentSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();

			int num = 1;
			Object max = session.createQuery(
					"select max(a.num) from Article a where a.blogOwnerId = :id")
					.setParameter("id", article.getBlogOwnerId())
					.uniqueResult();

			if (max != null) num = num + ((Integer) max).intValue();

			article.setNum(num);

			session.save(article);
			tx.commit();

			increaseArticleCount(article.getBlogOwnerId());
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			logger.error(e.getMessage(), e);
		} finally {
			HibernateUtil.closeSession();
		}
	}

	/**
	 * Article이 작성된 날들에 대한 정보를 가진 BlogCalendar를 가져옵니다.
	 */
	public BlogCalendar getArticleCalendar(String blogOwnerId) {
		Session session = HibernateUtil.currentSession();

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT EXTRACT(YEAR FROM date) as year, EXTRACT(MONTH FROM date) as month, EXTRACT(DAY FROM date) as day ");
		sb.append("FROM manalith_blog_article ");
		sb.append("WHERE blogOwnerId=? ");
		sb.append("GROUP BY date ");
		sb.append("ORDER BY date ASC");

		BlogCalendar calendar = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = ((SessionImpl) session).connection().prepareStatement(sb.toString());
			pstmt.setString(1, blogOwnerId);
			rs = pstmt.executeQuery();

			if (rs != null) calendar = new BlogCalendar();

			Year year = null;
			Month month = null;
			Day day = null;
			while (rs.next()) {
				int curYear = rs.getInt("year");
				int curMonth = rs.getInt("month");
				int curDay = rs.getInt("day");

				if (year == null) {
					year = new Year(curYear);
					calendar.add(year);
				} else if (year.toInt() != curYear) {
					year = new Year(curYear);
					calendar.add(year);
				}

				if (month == null) {
					month = new Month(curMonth);
					year.add(month);
				} else if (month.toInt() != curMonth) {
					month = new Month(curMonth);
					year.add(month);
				}

				if (day == null) {
					day = new Day(curDay);
					month.add(day);
				} else if (day.toInt() != curDay) {
					day = new Day(curDay);
					month.add(day);
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
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
			HibernateUtil.closeSession();
		}
		return calendar;
	}

	//FIXME : getArticle(String, int)와 getArticle(int)의 차이가 없음
	public Article getArticle(String blogOwnerId, int articleId) {
		return getArticle(articleId);
	}

	//TODO : trackback send과 병행 테스트, 이전
	//FIXME : 테스트
	public Article getArticle(int articleId) {
		/*
		 Article article = null;
		 
		 Session session = HibernateUtil.currentSession();
		 
		 try{
		 article = (Article) session.load(Article.class, new Long(articleId));
		 if(article != null){
		 article.setComments(ArticleCommentDAO.instance().getComments(articleId));
		 article.setTrackbacks(TrackbackDAO.instance().getTrackbacks(articleId));
		 article.setFiles(FileDAO.instance().getConnectedFiles(article.getId()));
		 }
		 }catch(HibernateException e){
		 logger.error(e.getMessage(), e);
		 }finally{
		 HibernateUtil.closeSession();
		 }
		 
		 return article;
		 */
		Article article = null;

		Session session = HibernateUtil.currentSession();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT num, blogOwnerId, title, author, category, date, contents, format, totalCommentCount, totalTrackbackCount ");
		sb.append("FROM manalith_blog_article ");
		sb.append("WHERE id = ?");

		try {
			pstmt = ((SessionImpl) session).connection().prepareStatement(sb.toString());

			pstmt.setInt(1, articleId);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				article = new Article();
				article.setId(articleId);
				article.setNum(rs.getInt("num"));
				article.setBlogOwnerId(rs.getString("blogOwnerId"));
				article.setTitle(rs.getString("title"));
				article.setAuthor(new User(rs.getString("author")));
				article.setCategory(rs.getString("category"));
				article.setDate(new Date(rs.getTimestamp("date").getTime()));
				article.setContents(rs.getString("contents"));
				article.setFormat(new ArticleFormat(rs.getString("format")));
				article.setTotalCommentCount(rs.getInt("totalCommentCount"));
				article.setTotalTrackbackCount(rs.getInt("totalTrackbackCount"));
				article.setComments(ArticleCommentDAO.instance().getComments(articleId));
				article.setTrackbacks(TrackbackDAO.instance().getTrackbacks(articleId));
				article.setFiles(FileDAO.instance().getConnectedFiles(article.getId()));
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
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
			session.close();
		}

		return article;
	}

	/**
	 * 주어진 블로그의 모든 아티클을 가져옵니다.
	 *
	 * @param blogOwnerId 블로그의 ID
	 * @return 모든 아티클의 목록
	 */
	public List<Article> getArticles(String blogOwnerId) {
		List<Article> articles = null;
		Session session = HibernateUtil.currentSession();

		try {
			articles = session.createQuery(
					"from Article a where a.blogOwnerId = :id order by a.id desc", Article.class)
					.setParameter("id", blogOwnerId)
					.list();

			int articleId = 0;
			Article article = null;
			for (int i = 0; i < articles.size(); i++) {
				article = (Article) articles.get(i);
				articleId = article.getId();
				article.setComments(ArticleCommentDAO.instance().getComments(articleId));
				article.setTrackbacks(TrackbackDAO.instance().getTrackbacks(articleId));
				article.setFiles(FileDAO.instance().getConnectedFiles(article.getId()));
				articles.set(i, article);
			}
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
		} finally {
			HibernateUtil.closeSession();
		}

		return articles;
	}

	/**
	 * 주어진 블로그의 특정 카테고리에 속하는 모든 아티클을 가져옵니다.
	 *
	 * @param blogOwnerId 블로그의 ID
	 * @param category    가져올 아티클의 카테고리
	 * @return 구해진 아티클의 목록
	 */
	public List<Article> getArticles(String blogOwnerId, String category) {
		Article article = null;
		List<Article> articles = null;
		int articleId = 0;
		Session session = HibernateUtil.currentSession();

		try {
			articles = session.createQuery(
					"from Article a where a.blogOwnerId = :id and a.category = :category order by a.id desc", Article.class)
					.setParameter("id", blogOwnerId)
					.setParameter("category", category)
					.list();

			for (int i = 0; i < articles.size(); i++) {
				article = (Article) articles.get(i);
				articleId = article.getId();
				article.setComments(ArticleCommentDAO.instance().getComments(articleId));
				article.setTrackbacks(TrackbackDAO.instance().getTrackbacks(articleId));
				article.setFiles(FileDAO.instance().getConnectedFiles(article.getId()));
				articles.set(i, article);
			}
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
		} finally {
			HibernateUtil.closeSession();
		}

		return articles;
	}

	public List<Article> getArticles(String blogOwnerId, int page, int totalArticleSize, int pageSize) {
		List<Article> articles = null;
		Session session = HibernateUtil.currentSession();

		try {
			int previous = pageSize * (page - 1);

			articles = session.createQuery(
					"from Article a where a.blogOwnerId = :id order by a.id desc", Article.class)
					.setParameter("id", blogOwnerId)
					.setMaxResults(pageSize)
					.setFirstResult(previous)
					.list();

			Article article = null;
			int articleId = 0;
			for (int i = 0; i < articles.size(); i++) {
				article = (Article) articles.get(i);
				articleId = article.getId();
				article.setComments(ArticleCommentDAO.instance().getComments(articleId));
				article.setTrackbacks(TrackbackDAO.instance().getTrackbacks(articleId));
				article.setFiles(FileDAO.instance().getConnectedFiles(article.getId()));
				articles.set(i, article);
			}
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
		} finally {
			HibernateUtil.closeSession();
		}

		return articles;
	}

	public List<Article> getArticles(String blogOwnerId, Article article) {
		List<Article> articles = null;
		Session session = HibernateUtil.currentSession();

		try {
			articles = session.createQuery(
					"from Article a where a.blogOwnerId = :blogOwnerId and a.id = :id order by a.id desc", Article.class)
					.setParameter("blogOwnerId", blogOwnerId)
					.setParameter("id", article.getId())
					.list();

			int articleId = 0;
			for (int i = 0; i < articles.size(); i++) {
				article = (Article) articles.get(i);
				articleId = article.getId();
				article.setComments(ArticleCommentDAO.instance().getComments(articleId));
				article.setTrackbacks(TrackbackDAO.instance().getTrackbacks(articleId));
				article.setFiles(FileDAO.instance().getConnectedFiles(article.getId()));
				articles.set(i, article);
			}
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
		} finally {
			HibernateUtil.closeSession();
		}

		return articles;
	}

	/**
	 * 특정 블로그에서 주어진 날짜에 작성된 모든 아티클을 가져옵니다.
	 *
	 * @param blogOwnerId 블로그의 ID
	 * @param date        가져올 아티클이 작성된 날짜
	 * @return 구해진 아티클의 목록
	 */
	public List<Article> getArticles(String blogOwnerId, java.util.Date date) {
		List<Article> articles = null;
		Session session = HibernateUtil.currentSession();

		try {
			Calendar cl = Calendar.getInstance();
			cl.setTime(date);
			cl.add(Calendar.DATE, 1);

			articles = session.createQuery(
					"from Article a where a.blogOwnerId = :id and a.date > :start and a.date < :end order by a.id desc", Article.class)
					.setParameter("id", blogOwnerId)
					.setParameter("start", new Date(date.getTime()))
					.setParameter("end", new Date(cl.getTime().getTime()))
					.list();

			Article article = null;
			int articleId = 0;

			for (int i = 0; i < articles.size(); i++) {
				article = (Article) articles.get(i);
				articleId = article.getId();
				article.setComments(ArticleCommentDAO.instance().getComments(articleId));
				article.setTrackbacks(TrackbackDAO.instance().getTrackbacks(articleId));
				article.setFiles(FileDAO.instance().getConnectedFiles(article.getId()));
				articles.set(i, article);
			}
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
		} finally {
			HibernateUtil.closeSession();
		}

		return articles;
	}

	/**
	 * 특정 블로그에서 최근에 작성된 것 부터 주어진 갯수만큼 아티클을 가져옵니다.
	 *
	 * @param blogOwnerId 블로그의 ID
	 * @param limitation  가져 올 갯수
	 * @return 구해진 아티클의 목록
	 */
	public List<Article> getRecentArticles(String blogOwnerId, int limitation) {
		List<Article> articles = null;
		Session session = HibernateUtil.currentSession();

		try {
			articles = session.createQuery(
					"from Article a where a.blogOwnerId = :id order by a.id desc", Article.class)
					.setParameter("id", blogOwnerId)
					.setMaxResults(limitation)
					.list();

			Article article = null;
			int articleId = 0;
			for (int i = 0; i < articles.size(); i++) {
				article = (Article) articles.get(i);
				articleId = article.getId();
				article.setComments(ArticleCommentDAO.instance().getComments(articleId));
				article.setTrackbacks(TrackbackDAO.instance().getTrackbacks(articleId));
				article.setFiles(FileDAO.instance().getConnectedFiles(article.getId()));
				articles.set(i, article);
			}
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
		} finally {
			HibernateUtil.closeSession();
		}

		return articles;
	}

	/**
	 * 주어진 블로그에 주어진 저자가 작성한 모든 아티클을 가져옵니다.
	 *
	 * @param blogOwnerId 아티클을 가져 올 블로그의 ID
	 * @param author      아티클을 가져 올 저자의 ID
	 * @return 구해진 아티클의 목록
	 */
	public List<Article> getArticles(String blogOwnerId, User author) {
		List<Article> articles = null;
		Session session = HibernateUtil.currentSession();

		try {
			articles = session.createQuery(
					"from Article a where a.blogOwnerId = :id and a.author = :author order by a.id desc", Article.class)
					.setParameter("id", blogOwnerId)
					.setParameter("author", author.getId())
					.list();

			Article article = null;
			int articleId = 0;
			for (int i = 0; i < articles.size(); i++) {
				article = (Article) articles.get(i);
				articleId = article.getId();
				article.setComments(ArticleCommentDAO.instance().getComments(articleId));
				article.setTrackbacks(TrackbackDAO.instance().getTrackbacks(articleId));
				article.setFiles(FileDAO.instance().getConnectedFiles(article.getId()));
				articles.set(i, article);
			}
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
		} finally {
			HibernateUtil.closeSession();
		}

		return articles;
	}

	/**
	 * 주어진 아티클의 정보로 아티클의 ID를 구해옵니다.
	 *
	 * @param article ID를 구해올 아티클과 일치하는 내용을 가진 아티클
	 * @return 구해진 아티클의 ID
	 */
	//FIXME : 잘못된 쿼리 및 동작
	public int getMatchedArticleId(Article article) {
		int articleId = 0;
		/*
		 ArticleCommentDAO commentManager = ArticleCommentDAO.instance();
		 
		 PreparedStatement pstmt = null;
		 ResultSet rs = null;
		 StringBuffer sb = new StringBuffer();
		 
		 sb.append("SELECT id ");
		 sb.append("FROM manalith_blog_article ");
		 sb.append("WHERE blogOwnerId=? AND title=? AND author=? AND category=? AND date=? AND contents=? AND format=? ");
		 sb.append("ORDER BY id DESC");
		 
		 try{
		 pstmt = conn.prepareStatement(sb.toString());
		 pstmt.setString(1,article.getBlogOwnerId());
		 pstmt.setString(2,article.getTitle());
		 pstmt.setString(3,article.getAuthor().toString());
		 pstmt.setString(4,article.getCategory());
		 pstmt.setTimestamp(5,new Timestamp(article.getDate().getTime()));
		 pstmt.setString(6,article.getContents());
		 pstmt.setString(7,article.getFormat().toString());
		 rs = pstmt.executeQuery();
		 
		 if(rs.next())
		 articleId = rs.getInt("id");
		 
		 }catch(SQLException e){
		 logger.error(e.toString());
		 }catch(Exception e){
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
		 */

		return articleId;

	}

	/**
	 * 아티클을 업데이트합니다.
	 *
	 * @param article 업데이트할 ID와 내용을 담고 있는 아티클
	 */
	public void updateArticle(Article article) {
		Session session = HibernateUtil.currentSession();

		try {
			session.createQuery(
					"update Article set title=:title, category=:category, date=:date, contents=:contents, format=:format where id = :id")
					.setParameter("title", article.getTitle())
					.setParameter("category", article.getCategory())
					.setParameter("date", new Timestamp(article.getDate().getTime()))
					.setParameter("contents", article.getContents())
					.setParameter("format", article.getFormat().toString())
					.setParameter("id", article.getId())
					.executeUpdate();

		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
		} finally {
			HibernateUtil.closeSession();
		}
	}

	/**
	 * 특정 아티클을 삭제합니다.
	 *
	 * @param blogOwnerId 삭제할 대상 블로그의 ID
	 * @param articleId   아티클의 고유 ID
	 */
	//FIXME : COMMENT, TRACKBACK 등의 동시 제거
	public void destroyArticle(String blogOwnerId, int articleId) {
		Session session = HibernateUtil.currentSession();

		try {
			session.createQuery(
					"delete Article where id = :id")
					.setParameter("id", articleId)
					.executeUpdate();

			decreaseArticleCount(blogOwnerId);
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
		} finally {
			HibernateUtil.closeSession();
		}
	}

	/**
	 * 해당 블로그에 존재하는 모든 아티클을 삭제합니다.
	 *
	 * @param blogOwnerId 삭제할 대상 블로그의 ID
	 */
	//FIXME : COMMENT, TRACKBACK 등의 동시 제거
	public void destroyAllArticles(String blogOwnerId) {
		Session session = HibernateUtil.currentSession();

		try {
			session.createQuery(
					"delete Article where blogOwnerId = :id")
					.setParameter("id", blogOwnerId)
					.executeUpdate();

			restoreArticleCount(blogOwnerId);
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
		} finally {
			HibernateUtil.closeSession();
		}
	}

	/**
	 * 전체 아티클의 갯수를 가산합니다.
	 *
	 * @param blogOwnerId 갯수를 변경할 블로그의 ID
	 * @throws HibernateException
	 */
	private void increaseArticleCount(String blogOwnerId) throws HibernateException {
		Session session = HibernateUtil.currentSession();

		try {
			session.createQuery(
					"update Blog set totalArticleCount = totalArticleCount + 1 where owner = :owner")
					.setParameter("owner", blogOwnerId)
					.executeUpdate();

		} catch (HibernateException e) {
			throw e;
		} finally {
			HibernateUtil.closeSession();
		}
	}

	/**
	 * 전체 아티클의 갯수를 감산합니다.
	 *
	 * @param blogOwnerId 갯수를 변경할 블로그의 ID
	 * @throws HibernateException
	 */
	private void decreaseArticleCount(String blogOwnerId) throws HibernateException {
		Session session = HibernateUtil.currentSession();

		try {
			session.createQuery(
					"update Blog set totalArticleCount = totalArticleCount - 1 where owner = :owner")
					.setParameter("owner", blogOwnerId)
					.executeUpdate();

		} catch (HibernateException e) {
			throw e;
		} finally {
			HibernateUtil.closeSession();
		}
	}

	/**
	 * 전체 아티클 갯수를 초기화합니다.
	 *
	 * @param blogOwnerId 초기화할 블로그의 ID
	 * @throws HibernateException
	 */
	private void restoreArticleCount(String blogOwnerId) throws HibernateException {
		Session session = HibernateUtil.currentSession();

		try {
			session.createQuery(
					"update Blog set totalArticleCount = 0 where owner = :owner")
					.setParameter("owner", blogOwnerId)
					.executeUpdate();

		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
		} finally {
			HibernateUtil.closeSession();
		}
	}

	/**
	 * 현재 존재하는 아티클들로부터 존재하는 카테고리들을 구합니다.
	 *
	 * @param blogOwnerId 카테고리를 구해 올 블로그의 ID
	 * @return 구해진 카테고리들의 문자열 집합
	 */
	public List<String> getCategories(String blogOwnerId) {
		List<String> categories = null;
		Session session = HibernateUtil.currentSession();

		try {
			categories = session.createQuery(
					"select a.category from Article a where a.blogOwnerId = :id group by a.category order by a.category asc", String.class)
					.setParameter("id", blogOwnerId)
					.list();

		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
		} finally {
			HibernateUtil.closeSession();
		}

		return categories;
	}
}
