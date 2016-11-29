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
	    password: mysql
    
	hibernate:
	    persistenceUnitName: seed
    	packagesToScan: com.qhkj.seed
    	
qiniu:
  accessKey: IZV4vlVHlVOS6lpaOD0XPL4ez5s69tforscCA_GO
  secretKey: rKN1i8N4ilLVxvZGpxLCt7JhDMA-P7W27x3xUAkW
  bucketName: seed
  bucketHost: og0d6ah43.bkt.clouddn.com    	
```

将所有"seed"改为自己的