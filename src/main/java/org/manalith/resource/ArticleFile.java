/*
 * Created on 2005. 3. 29
 */
package org.manalith.resource;

/**
 * @author setzer
 */
public class ArticleFile {
    private int id;
    private int articleId;
    private String blogOwnerId;
    private String path;
    private String name;
    private String savedName;
    private long size;

    public ArticleFile() {

    }

    public ArticleFile(int id) {
        this.id = id;
    }

    public ArticleFile(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

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
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Returns the path.
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path The path to set.
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return Returns the size.
     */
    public long getSize() {
        return size;
    }

    /**
     * @param size The size to set.
     */
    public void setSize(long size) {
        this.size = size;
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

    /**
     * @return Returns the savedName.
     */
    public String getSavedName() {
        return savedName;
    }

    /**
     * @param savedName The savedName to set.
     */
    public void setSavedName(String savedName) {
        this.savedName = savedName;
    }
}
