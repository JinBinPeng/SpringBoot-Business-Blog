## SpringBoot-Business-Blog

### 技术栈(版本均目前最新)
SpringBoot2.0+Maven+Elasticsearch+Mongodb+Spring Security+Thmeleaf+JPA+Mysql

### 系统安装及运行
建议都用最新版

1.安装MySQL，在yml配置文件中配置自己的mysql信息，建好数据库并启动

2.安装Elasticsearch

修改目录config下的elasticsearch.yml配置文件

把`cluster.name: my-application`注释打开

`network.host`改为`localhost`，也就是`network.host: localhost`

3.安装Mongodb 

4.模块说明

blog ： 博客主系统

 file-server ：用于博客系统图片上传。存于Mongodb ，yml没有写配置是因为都采用默认
 
如果不涉及博客图片上传、则可以不启用。头像上传有bug，前端同学可以帮忙弄下

启动后访问http://localhost:8080 即可





