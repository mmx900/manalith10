/*
 * Created on 2005. 3. 22
 */
package org.manalith.resource;

import java.util.Date;


/**
 * @author setzer
 * @see http://www.sixapart.com/pronet/docs/trackback_spec
 */
public class ArticleTrackback {
    private String blogOwnerId;
    private int articleId;
    private int id;
    //The title of entry. Optional.
    private String title;
    //The excerpt of entry. Optional.
    private String excerpt;
    //The permalink for the entry. Required.
    private String url;
    //The name of weblog to which the entry was posted. Optional.
    private String blog_name;
    private String inetAddress;
    private Date date;
    /**
     * @return Returns the articleId.
     */
    public int getArticleId() {
        return articleId;
    }
    /**
     * @param articleId The articleId to set.
     */
    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }
    /**
     * @return Returns the blog_name.
     */
    public String getBlog_name() {
        return blog_name;
    }
    /**
     * @param blog_name The blog_name to set.
     */
    public void setBlog_name(String blog_name) {
        this.blog_name = blog_name;
    }
    /**
     * @return Returns the blogOwnerId.
     */
    public String getBlogOwnerId() {
        return blogOwnerId;
    }
    /**
     * @param blogOwnerId The blogOwnerId to set.
     */
    public void setBlogOwnerId(String blogOwnerId) {
        this.blogOwnerId = blogOwnerId;
    }
    /**
     * @return Returns the excerpt.
     */
    public String getExcerpt() {
        return excerpt;
    }
    /**
     * @param excerpt The excerpt to set.
     */
    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }
    /**
     * @return Returns the title.
     */
    public String getTitle() {
        return title;
    }
    /**
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * @return Returns the url.
     */
    public String getUrl() {
        return url;
    }
    /**
     * @param url The url to set.
     */
    public void setUrl(String url) {
        this.url = url;
    }
    /**
     * @return Returns the date.
     */
    public Date getDate() {
        return date;
    }
    /**
     * @param date The date to set.
     */
    public void setDate(Date date) {
        this.date = date;
    }
    /**
     * @return Returns the inetAddress.
     */
    public String getInetAddress() {
        return inetAddress;
    }
    /**
     * @param inetAddress The inetAddress to set.
     */
    public void setInetAddress(String inetAddress) {
        this.inetAddress = inetAddress;
    }
    /**
     * @return Returns the id.
     */
    public int getId() {
        return id;
    }
    /**
     * @param id The id to set.
     */
    public void setId(int id) {
        this.id = id;
    }
}
