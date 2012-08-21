--0.1 Unknown
--0.3 20050421
/*
performance reason, table manalith_maingate is deprecated.

CREATE TABLE manalith_maingate(
	wideTitle TEXT NULL,
	url TEXT NOT NULL,
	allowRegister BOOL NOT NULL DEFAULT TRUE,
	allowRSS BOOL NOT NULL DEFAULT TRUE
);

--DROP TABLE manalith_maingate;
*/
/* manalith_maingate_rss_source */

INSERT INTO manalith_maingate_rss_source(title, description, webUrl, rssUrl, category)
	VALUES(?,?,?,?,?);

SELECT id, title, description, webUrl, rssUrl, category
	FROM manalith_maingate_rss_source;

SELECT title, description, webUrl, rssUrl, category
	FROM manalith_maingate_rss_source
	WHERE id = ?;

/* manalith_maingate_rss_item */

/* manalith_member */
	
INSERT INTO manalith_member(id,name,password,email)
	VALUES('mmx900','soyu','1234','nospam@localhost.localdomain');

SELECT id, password, name, email FROM manalith_member WHERE id='mmx900' ORDER BY id;
SELECT id, password, name, email FROM manalith_member WHERE id='mmx900' AND password='1234';
UPDATE manalith_member SET name='soyu-V', email='yespam@yourhost.space', password='4321' WHERE id='mmx900';
SELECT id, password, name, email FROM manalith_member WHERE id='mmx900';
DELETE FROM manalith_member WHERE id='mmx900';

/* manalith_blog */

INSERT INTO manalith_blog(title, description, template, owner, pageSize, url, allowRSS)
	VALUES('M9 Ultimate Blog','Ultimate! Last! Lost! Most! Amazing!','default','mmx900',10,'http://localhost:8080/blogHaja/blog/', TRUE);

SELECT title, description, template, created, totalArticleCount, pageSize, url, allowRSS, titleImage, backgroundImage
	FROM manalith_blog
	WHERE owner='mmx900';

UPDATE manalith_blog
	SET title='M9 Blog', description='my home', template='default2', pageSize=15, url='', allowRSS=FALSE
	WHERE owner='mmx900';

DELETE FROM manalith_blog WHERE owner='mmx900';

/* manalith_blog_article */

num = SELECT MAX(num) + 1 AS num FROM manalith_blog_article WHERE blogOwnerId='mmx900'
-- num ? 1 : num

INSERT INTO manalith_blog_article(blogOwnerid,num,title,author,category,date,contents,format)
	VALUES('mmx900',num,'First Blogggg Arrrticlee!','mmx900','my life',now(),'Hello, blahblahblah','html');
	UPDATE manalith_blog SET totalArticleCount = totalArticleCount + 1 WHERE owner='mmx900';

SELECT id, title, author, category, date, contents, format, totalCommentCount, totalTrackbackCount
	FROM manalith_blog_article
	WHERE blogOwnerId = 'mmx900';

SELECT title, author, category, date, contents, format, totalCommentCount, totalTrackbackCount
	FROM manalith_blog_article
	WHERE id = 3;

UPDATE manalith_blog_article
	SET title='Nay! Second!',category='yourlife',date=now(),contents='ByeBye, blah',format='text'
	WHERE id = 1;

SELECT category FROM manalith_blog_article WHERE blogOwnerId='mmx900' GROUP BY category;

SELECT EXTRACT(YEAR FROM date) as year, EXTRACT(MONTH FROM date) as month, EXTRACT(day FROM date) as day
	FROM manalith_blog_article
	GROUP BY date
	ORDER BY date asc;

DELETE FROM manalith_blog_article
	WHERE id = 3;
	UPDATE manalith_blog SET totalArticleCount = totalArticleCount - 1 WHERE owner='mmx900';

/* manalith_blog_article_comment */

INSERT INTO manalith_blog_article_comment(blogOwnerId,articleId,name,password,homepage,contents,inetAddress)
	VALUES('mmx900',1,'mmx900','1234','http://m9.pe.kr','--DROP TABLE manalith_blog_article','127.0.0.1');

INSERT INTO manalith_blog_article_comment(blogOwnerId,articleId,name,password,homepage,contents,inetAddress)
	VALUES('mmx900',1,'mmx900','1234',NULL,'blahblah','127.0.0.1');

SELECT id, name, date, homepage, contents
	FROM manalith_blog_article_comment
	WHERE articleId=1;

DELETE FROM manalith_blog_article_comment
	WHERE id=1;

/* manalith_blog_author */

INSERT INTO manalith_blog_author(blogOwnerId,userId)
	VALUES('mmx900','lunar');

SELECT a.userId,b.name,b.email FROM manalith_blog_author a
	INNER JOIN manalith_member b ON a.userId = b.id
	WHERE a.blogOwnerId='mmx900';

DELETE FROM manalith_blog_author
	WHERE blogOwnerId='mmx900' AND userId='lunar';

DELETE FROM manalith_blog_author
	WHERE blogOwnerId='mmx900';

/* manalith_blog_file */

SELECT NOW();
INSERT INTO manalith_blog_file(blogOwnerId, name, size)
	VALUES('mmx900','deleteMe.jpg',40011120003);

--GET UNCONNECTED FILES
SELECT id,blogOwnerId,name,size,date FROM manalith_blog_file WHERE articleId=0;

DELETE FROM manalith_blog_file WHERE articleId=0;

/* manalith_blog_article_trackback */

INSERT INTO manalith_blog_article_trackback(blogOwnerId, articleId, title, excerpt, url, blog_name, inetAddress)
	VALUES(?,?,?,?,?,?,?);

SELECT id, blogOwnerId, title, excerpt, url, blog_name, date, inetAddress
	FROM manalith_blog_article_trackback
	ORDER BY id DESC;

/* manalith_blog_bookmark */

INSERT INTO manalith_blog_bookmark(blogOwnerId,category,title,url,rssUrl,imageUrl,description)
	VALUES('mmx900','monolith','friends','http://m9.pe.kr',NULL,NULL,'manalith framework fansite');

SELECT id,category,title,url,rssUrl,imageUrl,description FROM manalith_blog_bookmark
	WHERE blogOwnerId = 'mmx900'
	ORDER BY category ASC;

SELECT rssUrl FROM manalith_blog_bookmark WHERE rssUrl != '' GROUP BY rssUrl

DELETE FROM manalith_blog_bookmark
	WHERE id=1;

/* manalith_blog_log */

INSERT INTO manalith_blog_log(blogOwnerId, ip, referer, agent)
	VALUES('mmx900','127.0.0.1',null,null);

SELECT blogOwnerId, ip, referer, agent, date FROM manalith_blog_log LIMIT 5 OFFSET 20000;
