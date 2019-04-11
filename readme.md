这里将SpringBoot中集成了shiro，实现了部分功能
# 权限控制
**wang.congjun.spring.shiro.config.ShiroConfig.getShiroFilterFactoryBean**  
在这个方法中添加了主要的权限，从数据苦中读取
# 登陆、权限校验
**wang.congjun.spring.shiro.realm.UserRealm**  
使用ConcurrentHashMap模拟数据库；将用户名密码、权限存放在其中

# 页面介绍
**index.html**
主页面

**login.html**
登陆页面

**noAuth.html**
无权访问页面

**add.html**
添加用户页面（模拟）

**update.html**
更新用户页面（模拟）

add.html和update.html需要登陆才能访问

add.html需要user:add权限才能访问

update.html需要user:edit权限才能访问



