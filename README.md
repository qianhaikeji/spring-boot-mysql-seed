# spring-boot-mysql-seed
a spring boot seed base mysql framework



### 环境变量配置

修改application.yml

```yaml
spring:  
	dataSource:
    	driverClassName: com.mysql.cj.jdbc.Driver
	    url: jdbc:mysql://127.0.0.1/seed?serverTimezone=UTC
    	username: root
	    password: xx
    
	hibernate:
	    persistenceUnitName: seed
    	packagesToScan: com.qhkj.seed
    	
qiniu:
  bucketName: seed  	
```

将所有"seed"改为自己的