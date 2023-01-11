## 论坛

## 资料
[Spring 文档](https://spring.io/guides)  
[Bootstrap](https://v3.bootcss.com/getting-started/)  
[GitHub OAuth](https://v3.bootcss.com/getting-started/)  
[Thymeleaf](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#setting-attribute-values)  
[SpringMVC](https://docs.spring.io/spring-framework/docs/5.0.3.RELEASE/spring-framework-reference/web.html#mvc-handlermapping-interceptor)  
[jQuery API 文档](https://api.jquery.com/)

## 工具
Git  
Lombok

## 脚本
```bash
mvn flyway:migrate
```
也可以手动执行resources/db/migration下的sql文件  
```bash
mvn '-Dmybatis.generator.overwrite=true' mybatis-generator:generate
```