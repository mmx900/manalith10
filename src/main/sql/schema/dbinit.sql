CREATE TABLE IF NOT EXISTS manalith_maingate_rss_source(
	id SERIAL PRIMARY KEY NOT NULL,
	title TEXT NOT NULL,
	description TEXT NOT NULL,
	webUrl TEXT NOT NULL,
	rssUrl TEXT NOT NULL,
	category TEXT NOT NULL,
	lastUpdate TIMESTAMP NULL,
	date TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS manalith_maingate_rss_item(
	id SERIAL PRIMARY KEY NOT NULL,
	source INT NOT NULL REFERENCES manalith_maingate_rss_source(id),
	rssVersion TEXT NULL,
	rssCharset TEXT NULL,
	channelTitle TEXT NULL,
	channelDescription TEXT NULL,
	channelLanguage TEXT NULL,
	channelCopyright TEXT NULL,
	channelGenerator TEXT NULL,
	channelPubDate TIMESTAMP NULL,
	channelLink TEXT NULL,
	itemTitle TEXT NULL,
	itemDescription TEXT NULL,
	itemCategory TEXT NULL,
	itemPubDate TIMESTAMP NULL,
	itemLink TEXT NULL
);

CREATE TABLE IF NOT EXISTS manalith_member(
	idx SERIAL NOT NULL,
	id VARCHAR(12) PRIMARY KEY NOT NULL,
	password TEXT NOT NULL,
	name TEXT NOT NULL,
	email TEXT NOT NULL,
	date TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS manalith_blog(
	idx SERIAL NOT NULL,
	title TEXT NOT NULL,
	description TEXT NOT NULL,
	template TEXT DEFAULT 'default' NOT NULL,
	owner VARCHAR(12) NOT NULL PRIMARY KEY REFERENCES manalith_member(id),
	created TIMESTAMP NOT NULL DEFAULT now(),
	totalArticleCount INT NOT NULL DEFAULT 0,
	pageSize INT NOT NULL DEFAULT 10,
	url TEXT NOT NULL,
	allowRSS BOOL NOT NULL DEFAULT TRUE,
	titleImage TEXT NULL,
	backgroundImage TEXT NULL
);

CREATE TABLE IF NOT EXISTS manalith_blog_article(
	id SERIAL NOT NULL PRIMARY KEY,
	num INT NOT NULL,
	blogOwnerId VARCHAR(12) NOT NULL REFERENCES manalith_blog(owner),
	title TEXT NOT NULL,
	author VARCHAR(12) NOT NULL REFERENCES manalith_member(id),
	category TEXT NOT NULL,
	date TIMESTAMP NOT NULL DEFAULT now(),
	contents TEXT NOT NULL,
	format VARCHAR(8) check(format in ('text','html')) NOT NULL,
	totalCommentCount int NOT NULL DEFAULT 0,
	totalTrackbackCount int NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS manalith_blog_article_comment(
	id SERIAL NOT NULL PRIMARY KEY,
	blogOwnerId TEXT NOT NULL REFERENCES manalith_member(id),
	articleId INT NOT NULL REFERENCES manalith_blog_article(id),
	name TEXT NOT NULL,
	password TEXT NOT NULL,
	date TIMESTAMP NOT NULL DEFAULT now(),
	homepage TEXT NULL,
	contents TEXT NOT NULL,
	inetAddress inet NOT NULL
);

CREATE TABLE IF NOT EXISTS manalith_blog_author(
	id SERIAL NOT NULL PRIMARY KEY,
	blogOwnerId VARCHAR(12) NOT NULL REFERENCES manalith_blog(owner),
	userId VARCHAR(12) NOT NULL REFERENCES manalith_member(id),
	date TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS manalith_blog_file(
	id SERIAL NOT NULL PRIMARY KEY,
	blogOwnerId VARCHAR(12) NOT NULL REFERENCES manalith_member(id),
	articleId INT NOT NULL DEFAULT 0,
	name TEXT NOT NULL,
	size BIGINT NOT NULL,
	date TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS manalith_blog_article_trackback(
	id SERIAL NOT NULL PRIMARY KEY,
	blogOwnerId VARCHAR(12) NOT NULL REFERENCES manalith_member(id),
	articleId INT NOT NULL REFERENCES manalith_blog_article(id),
	title TEXT NULL,
	excerpt TEXT NULL,
	url TEXT NOT NULL,
	blog_name TEXT NULL,
	date TIMESTAMP NOT NULL DEFAULT NOW(),
	inetAddress INET NOT NULL
);

CREATE TABLE IF NOT EXISTS manalith_blog_bookmark(
	id SERIAL NOT NULL PRIMARY KEY,
	blogOwnerId VARCHAR(12) NOT NULL REFERENCES manalith_member(id),
	category TEXT NOT NULL,
	title TEXT NOT NULL,
	url TEXT NOT NULL,
	rssUrl TEXT NULL,
	imageUrl TEXT NULL,
	description TEXT NULL,
	date TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS manalith_blog_log(
	id SERIAL NOT NULL PRIMARY KEY,
	blogOwnerId VARCHAR(12) NOT NULL REFERENCES manalith_member(id),
	ip INET NULL,
	referer TEXT NULL DEFAULT 'Manalith-Unknown',
	agent TEXT NULL DEFAULT 'Manalith-Unknown',
	date TIMESTAMP NOT NULL DEFAULT NOW()
);
