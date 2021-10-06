## 学习社区

## 资料
[Spring 文档](https://spring.io/guides)
[Spring Web文档](https://spring.io/guides/gs/serving-web-content/)
[Github OAuth Doc](https://docs.github.com/en/developers/apps/building-oauth-apps/authorizing-oauth-apps)
[Spring boot Mybatis](http://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)
[bootstrap](https://v3.bootcss.com/css/#grid)
[lombok](https://projectlombok.org/)
[generator](http://mybatis.org/generator/index.html)
[editor](http://editor.md.ipandao.com/)
## 工具
[Git](https://git-scm.com/download)
[visual-paradigm](https://www.visual-paradigm.com/cn/)

## 命令
mvn flyway:migrate
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate

## SQL
<!--
CREATE TABLE question
(
  id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(50),
  description TEXT,
  gmt_create BIGINT,
  gmt_modified BIGINT,
  creator INT,
  comment_count INT DEFAULT 0,
  view_count INT DEFAULT 0,
  like_count INT DEFAULT 0,
  tag VARCHAR(256)
);

CREATE TABLE comment
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    parent_id BIGINT NOT NULL,
    type INT NOT NULL,
    commentator INT NOT NULL,
    gmt_create BIGINT NOT NULL,
    gmt_modified BIGINT NOT NULL,
    like_count BIGINT DEFAULT 0
);
COMMENT ON COLUMN comment.parent_id IS '父类id';
COMMENT ON COLUMN comment.type IS '父类类型';
COMMENT ON COLUMN comment.commentator IS '评论人ID';
COMMENT ON COLUMN comment.gmt_create IS '创建时间';
COMMENT ON COLUMN comment.gmt_modified IS '更新时间';
COMMENT ON COLUMN comment.like_count IS '点赞数';
-->

