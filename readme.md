### 1.安装本地mysql
####下载mysql镜像
`docker pull mysql:5.7`
####运行容器
`docker run -itd --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=muyi123 mysql`


### 2.安装本地redis
####下载redis镜像
`docker pull redis:latest`
####运行容器
`docker run -itd --name redis -p 6379:6379 redis`


## 主要包含：
##### 1.多数据库配置
##### 2.redis配置及常用方法
##### 3.es配置及常用方法
##### 4.mybatis-plus配置及常用方法
##### 5.Exception全局处理
##### 6.spring-cloud全家桶(eureka、getaway、feign、ribbon、hystrix)
##### 

