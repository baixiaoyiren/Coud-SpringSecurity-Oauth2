



# SpringSecurity

## 1、学习目标

![](.\图片\Snipaste_2022-11-06_10-54-33.png)

## 2、SpringSecurity

### 2.1、SpringSecurity简介

#### 2.1.1、安全框架概述

​	什么是安全框架？解决系统安全问题的框架。如果没有安全框架，我们需要手动处理每个资源的访问控制，非常麻烦。使用安全框架，我们可以通过配置的方式实现对资源的访问限制。

#### 2.1.2、常用的安全框架

- Spring Security：Spring家族一员。是一个能够为基于Spring的企业应用系统提供声明式的安全访问控制解决方案的安全框架。它提供了一组可以在Spring应用上下文中配置的Bean，充分利用了`Spring IoC`，`DI（控制反转Inversion of Control,DI:Dependency Injection 依赖注入）`和`AOP（面向切面编程）`功能，为应用系统提供声明式的安全访问控制功能，减少了为企业系统安全控制编写大量重复代码的工作。
- Apache Shiro：一个功能强大且易于使用的Java安全框架,提供了认证,授权,加密,和会话管理。

#### 2.1.3、SpringSecurity简介

##### 概述

​	Spring Security是一个高度自定义的安全框架。利用 Spring IoC/DI和AOP功能，为系统提供了声明式安全访问控制功能，减少了为系统安全而编写大量重复代码的工作。使用 Spring Secruity 的原因有很多，但大部分都是发现了 javaEE的 Servlet 规范或 EJB 规范中的安全功能缺乏典型企业应用场景。同时认识到他们在 WAR 或 EAR 级别无法移植。因此如果你更换服务器环境，还有大量工作去重新配置你的应用程序。使用 Spring Security解决了这些问题，也为你提供许多其他有用的、可定制的安全功能。正如你可能知道的两个应用程序的两个主要区域是“认证”和“授权”（或者访问控制）。这两点也是 Spring Security 重要核心功能。

 	**“认证”，是建立一个他声明的主体的过程（一个“主体”一般是指用户，设备或一些可以在你的应用程序中执行动作的其他系统），通俗点说就是系统认为用户是否能登录。**

​	**“授权”指确定一个主体是否允许在你的应用程序执行一个动作的过程。通俗点讲就是系统判断用户是否有权限去做某些事情。**



**历史**

​	Spring Security 以“The Acegi Secutity System for Spring”的名字始于2003年年底。其前身为 acegi 项目。起因是 Spring 开发者邮件列表中一个问题，有人提问是否考虑提供一个基于 Spring 的安全实现。限制于时间问题，开发出了一个简单的安全实现，但是并没有深入研究。几周后，Spring 社区中其他成员同样询问了安全问题，代码提供给了这些人。2004 年 1 月份已经有 20 人左右使用这个项目。随着更多人的加入，在 2004 年 3 月左右在 sourceforge 中建立了一个项目。在最开始并没有认证模块，所有的认证功能都是依赖容器完成的，而 acegi 则注重授权。但是随着更多人的使用，基于容器的认证就显现出了不足。acegi 中也加入了认证功能。大约 1 年后 acegi 成为 Spring子项目。在 2006 年 5 月发布了 acegi 1.0.0 版本。2007 年底 acegi 更名为Spring Security。



#### 快速入门

1、新建maven项目

##### 导入依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.xxx</groupId>
    <artifactId>springsecuritydemo</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>springsecuritydemo</name>
    <description>springsecuritydemo</description>

    <properties>
        <java.version>1.8</java.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <spring-boot.version>2.2.5.RELEASE</spring-boot.version>
    </properties>

    <dependencies>
        <!--spring security 组件-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <!--web 组件-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!--<dependency>-->
        <!--    <groupId>org.springframework.boot</groupId>-->
        <!--    <artifactId>spring-boot-starter-thymeleaf</artifactId>-->
        <!--</dependency>-->

        <!-- test 组件-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.83</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <optional>true</optional>
        </dependency>
    </dependencies>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring-boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <resources>
            <resource>
                <directory>src/main/java</directory>
                <!--所在的目录-->
                <includes>
                    <!--包括目录下的.properties,.xml 文件都会被扫描到-->
                    <include>**/*</include>
                </includes>
                <filtering>false</filtering>
            </resource>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.*</include>
                </includes>
            </resource>
        </resources>

        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                    <encoding>UTF-8</encoding>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>2.3.7.RELEASE</version>
                <configuration>
                    <mainClass>com.xxx.springsecuritydemo.SpringsecuritydemoApplication</mainClass>
                </configuration>
                <executions>
                    <execution>
                        <id>repackage</id>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>

</project>

```

此时包结构如图

![](图片/Snipaste_2022-11-06_11-50-50.png)





controller层

```java
@Controller
public class LoginController {
    @RequestMapping("login")
    public String login(){
        System.out.println("执行登录");
        return "redirect:main.html";
    }
}
```



.properties

```properties
# 应用名称
spring.application.name=springsecuritydemo
# 应用服务 WEB 访问端口
server.port=8080

```



启动项目，控制台会打印随机密码

![](图片/控制台打印密码.png)



##### 访问页面localhost:8080/login.html

​	导入spring-boot-starter-security 启动器后，Spring Security 已经生效，默认拦截全部请求，如果用户没有登录，跳转到内置登录页面。

​	默认的 username 为 user，password 打印在控制台中。

​	在浏览器中输入账号和密码后会显示 login.html 页面内容。



login.html 页面

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<form action="/login" method="post">
    用户名：<input type="text" name="username" /><br/>
    密码：<input type="password" name="password" /><br/>
    <input type="submit" value="登录" />
</form>
</body>
</html>
```


登陆成功主页面

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
登陆成功！
</body>
</html>
```



#### 2.1.4、UserDetailsService详解

　当什么也没有配置的时候，账号和密码是由 Spring Security 定义生成的。而在实际项目中账号和密码都是从数据库中查询出来的。所以我们要通过自定义逻辑控制认证逻辑。如果需要自定义逻辑时，只需要实现 UserDetailsService 接口即可。接口定义如下：

![](图片/UserDetailsService.png)

#### 返回值

返回值 UserDetails 是一个接口，定义如下

![](图片/UserDetailsService.png)

要想返回 `UserDetails `的实例就只能返回接口的实现类。SpringSecurity 中提供了如下的实例。对于我们只需要使用里面的 `User `类即可。注意 User 的全限定路径是：

`org.springframework.security.core.userdetails.User`此处经常和系统中自己开发的 User 类弄混。



在 User 类中提供了很多方法和属性。

其中构造方法有两个，调用其中任何一个都可以实例化

`UserDetails `实现类 `User `类的实例。而三个参数的构造方法实际上也是调用 7 个参数的构造方法。

- `username`:用户名
- `password`:密码
- `authorities`：用户具有的权限。此处不允许为 null

​        此处的用户名应该是客户端传递过来的用户名。而密码应该是从数据库中查询出来的密码。Spring Security 会根据 User 中的 `password`(从数据库当中查出来的)和客户端传递过来的 `password `进行比较。如果相同则表示认证通过，如果不相同表示认证失败。

​      `authorities `里面的权限对于后面学习授权是很有必要的，包含的所有内容为此用户具有的权限，如有里面没有包含某个权限，而在做某个事情时必须包含某个权限则会出现 403。通常都是通过`AuthorityUtils.commaSeparatedStringToAuthorityList(“”)` 来创建`authorities` 集合对象的。参数是一个字符串，多个权限使用逗号分隔。



#### 方法参数

方法参数表示用户名。此值是客户端表单传递过来的数据。默认情况下必须叫 `username`，否则无法接收。



#### 异常

`UsernameNotFoundException `用户名没有发现异常。在`loadUserByUsername`中是需要通过自己的逻辑从数据库中取值的。如果通过用户名没有查询到对应的数据，应该抛出`UsernameNotFoundException`，系统就知道用户名没有查询到。



### PasswordEncoder 密码解析器详解

　Spring Security 要求容器中必须有`PasswordEncoder`实例。所以当自定义登录逻辑时要求必须给容器注入`PaswordEncoder`的bean对象。



#### 接口介绍

- `encode()`：把参数按照特定的解析规则进行解析。

- `matches()` ：验证从存储中获取的编码密码与编码后提交的原始密码是否匹配。如果密码匹配，则返回 true；如果不匹配，则返回 false。第一个参数表示需要被解析的密码。第二个参数表示存储的密码。

- `upgradeEncoding()`：如果解析的密码能够再次进行解析且达到更安全的结果则返回 true，否则返回 false。默认返回 false。


#### 内置解析器介绍

​	在 Spring Security 中内置了很多解析器。



#### BCryptPasswordEncoder 简介

​	BCryptPasswordEncoder 是 Spring Security 官方推荐的密码解析器，平时多使用这个解析器。

　	BCryptPasswordEncoder 是对 `bcrypt `强散列方法的具体实现。是基于Hash算法实现的单向加密。可以通过strength控制加密强度，默认 10。

#### 代码演示

​	新建测试方法BCryptPasswordEncoder 用法。

```java
package com.xxxx.springsecuritydemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class MyTest {

   @Test
   public void test(){
      //创建解析器
      PasswordEncoder pw = new BCryptPasswordEncoder();
      //对密码加密
      String encode = pw.encode("123");
      System.out.println(encode);

      //判断原字符和加密后内容是否匹配
      boolean matches = pw.matches("1234", encode);
      System.out.println("==================="+matches);
	}

}
```



### 快速入门自定义逻辑

在上一个简单项目的基础上进行改造

此时项目结构如下

![](图片/Snipaste_2022-11-06_12-23-54.png)



SecurityConfig配置密码加密

```java
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
```

UserDetailsImpl

在 Spring Security 中实现 UserDetailService 就表示为用户详情服务。在这个类中编写用户认证逻辑。

```java
/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-06-12:12
 * Description: UserDetailsService模拟从数据库当中查询出用户并认证
 */
//@Service
public class UserDetailsImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /*
            此处的逻辑是：
                1。查询数据库判断用户名是否存在，如果不存在则抛出UsernameNotFoundException异常
                2。把查询出来的密码进行解析，或者把密码放入构造方法当中
         */
        if (!"admin".equals(username)){
            throw new UsernameNotFoundException("用户名不存在！");
        }
        // 模拟数据库当中查询出来的密码
        String password = passwordEncoder.encode("123456");

        // 数据库当中查询出来的权限:admin mormal
        List<GrantedAuthority> permissions = AuthorityUtils.commaSeparatedStringToAuthorityList("admin,normal");

        // 构建springsecurity的user对象并返回
        return new User(username,password,permissions);
    }
}
```



启动项目，此时控制台没有再打印默认的密码了，因为我们已经实现了自定义的密码获取逻辑

![](图片/Snipaste_2022-11-06_12-29-28.png)



查看效果：输入的密码要和UserdetailsService中查询出来的密码一致，不一致则登陆失败

![](图片/Snipaste_2022-11-06_12-26-55.png)

登陆成功

![](图片/Snipaste_2022-11-06_12-30-53.png)



失败效果：如果登录的密码和我们数据库查询出来的密码不一致则提示登陆失败

![](图片/Snipaste_2022-11-06_12-31-57.png)







### 自定义登录页面

​	虽然 Spring Security 给我们提供了登录页面，但是对于实际项目中，大多喜欢使用自己的登录页面。所以 Spring Security 中不仅仅提供了登录页面，还支持用户自定义登录页面。实现过程也比较简单，只需要修改配置类即可。



#### 编写登录页面

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<form action="/login" method="post">
    用户名：<input type="text" name="username" /><br/>
    密码：<input type="password" name="password" /><br/>
    <input type="submit" value="登录" />
</form>
</body>
</html>

```
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<form action="/login" method="post">
    用户名：<input type="text" name="username" /><br/>
    密码：<input type="password" name="password" /><br/>
    <input type="submit" value="登录" />
</form>
</body>
</html>
```



#### 修改配置类

​	修改配置类中主要是设置哪个页面是登录页面。配置类需要继承WebSecurityConfigurerAdapte,并重写 configure （HttpSecurity http）方法，修改登录页面。

​	

```java
/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-06-12:10
 * Description:
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 表单提交
        http.formLogin()
            // 自定义登录页面
                .loginPage("/login.html");
        
    }
}
```

但是这样写的话所有的资源都可以访问了，也就是说，认证失效了

改进

```java
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login.html");
        // 所有的请求都必须被认证，必须登录之后才能访问
        http.authorizeRequests()
                .anyRequest()
                .authenticated();
    }
}
```

然而这样也会出现问题：我们尝试访问登录

localhost:8080/login

会跳转到我们自定义页面，但是会发生如下情况

![](图片/Snipaste_2022-11-06_12-48-49.png)



为什么会出现重定向过多呢？

因为我们访问main.html的时候，请求会被拦截，需要去认证，认证会跳转到我们的login.html页面，但是我们是所有的资源请求的需要认证，包括login.html页面，所有虽然访问main.html的时候会跳转到login.html页面进行认证，但是访问login.html又会跳转到login.html去认证，这样反复循环，就会出现重定向次数过多错误。

配置改进：login.html页面不需要认证

```java
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // login.html访问不需要认证
        http.authorizeRequests()
                        .antMatchers("/login.html").permitAll()
                        .and()
                .formLogin()
                .loginPage("/login.html");
        // 所有的请求都必须被认证，必须登录之后才能访问
        http.authorizeRequests()
                .anyRequest()
                .authenticated();
    }
}
```

这个时候出现了我们自定义的登录页面，但是我们在自定义的登录验证类当中打印信息![](图片/Snipaste_2022-11-06_14-15-54.png)

然后在地址栏访问我们的登录页面并输入用户名和密码admin 和 123456

![](图片/Snipaste_2022-11-06_14-17-28.png)

发现控制台并没有打印出任何信息，也就是说，我们登录的时候并没有走我们自定义的登录验证逻辑，这是为什么呢？因为我们的表单提交并没有指定提交的url，因此我们需要将表单提交的url指定到/login,并在配置类当中指定loginProcessingUrl，这两者要一一对应，不对应也登录不上。



![](图片/Snipaste_2022-11-06_14-23-10.png)



但是发现还是没有走我们自定义的校验逻辑，此时配置类还需要加上http.csrf().disable();

```java
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 表单提交
        http
                .formLogin()
                //当发现是/login的时候就认为是登录，必须和表单提交的地址是一致的
                .loginProcessingUrl("/login")
                .loginPage("/login.html");
        // 授权认证
        http.authorizeRequests()
                //login.html不需要被认证
                .antMatchers("/login.html").permitAll()
                // 所有的请求都必须被认证，必须登陆之后被访问
                .anyRequest()
                .authenticated();
        // 关闭跨站伪造保护，类似于关闭防火墙，不懂的自行百度
        http.csrf().disable();
    }
}
```



此时再次访问localhost:8080/login的时候会跳转到我们自定义的登录页面，然后输入用户名和密码。

验证结果：

![](图片/Snipaste_2022-11-06_14-27-40.png)



因为我们直接访问的是localhost:8080/login，这是我们controller定义好的跳转逻辑，会自动跳转到我们指定的页面，但是如果我们直接访问login.html页面，会发生什么？

访问localhost:8080/login.html

![](图片/Snipaste_2022-11-06_14-32-31.png)



![](图片/Snipaste_2022-11-06_14-33-02.png)

会发现直接跳转404找不到页面了，因为我们没有指定login.html登陆成功之后跳转的逻辑。因此需要在配置类当中加上跳转逻辑

```java
/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-06-12:10
 * Description:
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 表单提交
        http
                .formLogin()
                //当发现是/login的时候就认为是登录，必须和表单提交的地址是一致的
                .loginProcessingUrl("/login")
                .loginPage("/login.html")
                //登录成功之后跳转的页面
                .successForwardUrl("/main.html");


        // 授权认证
        http.authorizeRequests()
                //login.html不需要被认证
                .antMatchers("/login.html").permitAll()
                // 所有的请求都必须被认证，必须登陆之后被访问
                .anyRequest()
                .authenticated();
        // 关闭跨站伪造保护，类似于关闭防火墙，不懂的自行百度
        http.csrf().disable();
    }
}
```

虽然定义好了登录成功之后跳转的页面，但是我们发现，我们访问localhost:8080/login输入用户名密码校验成功之后，跳转的时候报错405：There was an unexpected error (type=Method Not Allowed, status=405).

方法不被允许，这是为什么呢？

因为我们登陆成功之后跳转的页面必须是post请求，但是我们在successForwardUrl("/main.html")配置直接跳/main.html是get请求。

因此我们需要在controller写一个post接口，让用户登录成功之后跳转到指定的接口就好了。

```java
/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-06-12:10
 * Description:
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 表单提交
        http
                .formLogin()
                //当发现是/login的时候就认为是登录，必须和表单提交的地址是一致的
                .loginProcessingUrl("/login")
                .loginPage("/login.html")
                // 登陆成功之后跳转的路径
                .successForwardUrl("/toMain");


        // 授权认证
        http.authorizeRequests()
                //login.html不需要被认证
                .antMatchers("/login.html").permitAll()
                // 所有的请求都必须被认证，必须登陆之后被访问
                .anyRequest()
                .authenticated();
        // 关闭跨站伪造保护，类似于关闭防火墙，不懂的自行百度
        http.csrf().disable();
    }
}
```



controller实现的/toMain接口

```java
@Controller
public class LoginController {
    // 因为我们直接访问的是localhost:8080/login.html页面，因此login方法可以去掉
    //@RequestMapping("login")
    //public String login(){
    //    System.out.println("执行登录");
    //    return "redirect:main.html";
    //}

    @PostMapping("toMain")
    public String toMain(){
        System.out.println("执行登录");
        return "redirect:main.html";
    }
}
```



再次访问localhost:8080/login.html，输入用户名和密码，发现已经登陆成功了，成功跳转到main.html页面。



问题：假如登陆失败呢？

![](图片/Snipaste_2022-11-06_14-44-59.png)

发现还是停留在登录页面。只不过url变成了http://localhost:8080/login.html?error

正常情况下，我们登陆失败，会跳转到单独的登陆失败页面.

表单处理中成功会跳转到一个地址，失败也可以跳转到一个地址。

自定义登录失败页面

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
操作失败，请重新登录 <a href= "/login.html">跳转</a>
</body>
</html>
```

然后在配置类上添加登陆失败跳转的url

```java
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 表单提交
        http
                .formLogin()
                //当发现是/login的时候就认为是登录，必须和表单提交的地址是一致的
                .loginProcessingUrl("/login")
                .loginPage("/login.html")
                // 登陆成功之后跳转的路径
                .successForwardUrl("/toMain")
                //登陆失败后跳转的请求
                .failureForwardUrl("/toError");


        // 授权认证
        http.authorizeRequests()
                //login.html不需要被认证
                .antMatchers("/login.html").permitAll()
                // 所有的请求都必须被认证，必须登陆之后被访问
                .anyRequest()
                .authenticated();
        // 关闭跨站伪造保护，类似于关闭防火墙，不懂的自行百度
        http.csrf().disable();
    }
}
```

然后也和登陆成功处理一样在controller新建一个登陆失败跳转到指定页面的接口

```java
@Controller
public class LoginController {
    //@RequestMapping("login")
    //public String login(){
    //    System.out.println("执行登录");
    //    return "redirect:main.html";
    //}

    @PostMapping("toMain")
    public String toMain(){
        System.out.println("执行登录");
        return "redirect:main.html";
    }

    @PostMapping("toError")
    public String toError(){
        System.out.println("登陆失败");
        return "redirect:error.html";
    }
}
```

但是发现没有跳转到指定的登陆失败页面，为什么呢？因为所有的除了被指定的资源可以访问之外，其他的资源都必须认证之后才能访问，因此无法访问到登录失败的页面。所以要指定登陆失败的页面不需要认证即可访问。

```java
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 表单提交
        http
                .formLogin()
                //当发现是/login的时候就认为是登录，必须和表单提交的地址是一致的
                .loginProcessingUrl("/login")
                .loginPage("/login.html")
                // 登陆成功之后跳转的路径
                .successForwardUrl("/toMain")
                //登陆失败后跳转的请求
                .failureForwardUrl("/toError");


        // 授权认证
        http.authorizeRequests()
                //login.html、/error.html不需要被认证就能访问
                .antMatchers("/login.html","/error.html").permitAll()
                // 所有的请求都必须被认证，必须登陆之后被访问
                .anyRequest()
                .authenticated();
        // 关闭跨站伪造保护，类似于关闭防火墙，不懂的自行百度
        http.csrf().disable();
    }
}
```

此时再次登陆失败，就会跳转到指定的登陆失败处理页面

![](图片/Snipaste_2022-11-06_14-54-58.png)





​	还有一点就是我们传入的用户名和密码必须是username和password这两个固定的参数名，类似于userName,pwd等等之类的传入之后也不会认证成功，这是为什么呢？

![](图片/Snipaste_2022-11-06_14-57-59.png)

这个过滤器会拦截我们的请求，验证我们的提交的是否为post请求和传入的参数是否正确username 和 password，当然我们也可以在配置类自定义传入的参数，然后我们就可以在表单上传入我们想要指定传入的参数了。

```java
@Override
protected void configure(HttpSecurity http) throws Exception {
    // 表单提交
    http
            .formLogin()
        // 指定userName 和password传入的参数名
            .usernameParameter("userName")
            .passwordParameter("pwd")
            //当发现是/login的时候就认为是登录，必须和表单提交的地址是一致的
            .loginProcessingUrl("/login")
            .loginPage("/login.html")
            // 登陆成功之后跳转的路径
            .successForwardUrl("/toMain")
            //登陆失败后跳转的请求
            .failureForwardUrl("/toError");


    // 授权认证
    http.authorizeRequests()
            //login.html、/error.html不需要被认证就能访问
            .antMatchers("/login.html","/error.html").permitAll()
            // 所有的请求都必须被认证，必须登陆之后被访问
            .anyRequest()
            .authenticated();
    // 关闭跨站伪造保护，类似于关闭防火墙，不懂的自行百度
    http.csrf().disable();
}
```



```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<form action="/login" method="post">
    用户名：<input type="text" name="userName" /><br/>
    密码：<input type="password" name="pwd" /><br/>
    <input type="submit" value="登录" />
</form>
</body>
</html>
```

此时访问localhost:8080/login.html



![](图片/Snipaste_2022-11-06_15-01-58.png)

输入正确的用户名和密码发现，可以登陆成功！



#### 自定义登录成功处理器

##### 源码分析

　	使用successForwardUrl()时表示成功后转发请求到地址。内部是通过 `successHandler()`方法进行控制成功后交给哪个类进行处理

　 	ForwardAuthenticationSuccessHandler内部就是最简单的请求转发。由于是请求转发，当遇到需要跳转到站外或在前后端分离的项目中就无法使用了。

​	当需要控制登录成功后去做一些事情时，可以进行自定义认证成功控制器。



##### 代码实现

**自定义类**

新建类 com.xxxx.handler.MyAuthenticationSuccessHandler

```java
/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-06-15:06
 * Description: 自定义认证成功跳转的url
 */
public class MyAuthenticationSucessHandler implements AuthenticationSuccessHandler {

    private final String url;

    public MyAuthenticationSucessHandler(String url) {
        this.url = url;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.sendRedirect(url);
    }
}
```



```java
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public MyAuthenticationSucessHandler sucessHandler(){
        return new MyAuthenticationSucessHandler("https://www.baidu.com");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 表单提交
        http
                .formLogin()
                .usernameParameter("userName")
                .passwordParameter("pwd")
                //当发现是/login的时候就认为是登录，必须和表单提交的地址是一致的
                .loginProcessingUrl("/login")
                .loginPage("/login.html")
                // 登陆成功之后跳转的路径
                //.successForwardUrl("/toMain")
                // 指定登陆成功之后跳转的url的拦截器,不能和successForwardUrl共存
                .successHandler(sucessHandler())
                //登陆失败后跳转的请求
                .failureForwardUrl("/toError");


        // 授权认证
        http.authorizeRequests()
                //login.html、/error.html不需要被认证就能访问
                .antMatchers("/login.html","/error.html").permitAll()
                // 所有的请求都必须被认证，必须登陆之后被访问
                .anyRequest()
                .authenticated();
        // 关闭跨站伪造保护，类似于关闭防火墙，不懂的自行百度
        http.csrf().disable();
    }
}
```

此时登陆成功之后就能跳转到指定的页面了。





登陆成功之后打印用户信息

```java
/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-06-15:06
 * Description: 自定义认证成功跳转的url
 */
public class MyAuthenticationSucessHandler implements AuthenticationSuccessHandler {

    private final String url;

    public MyAuthenticationSucessHandler(String url) {
        this.url = url;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        System.out.println(user.getUsername());
        // 会输出null,这是因为出于安全原因考虑的
        System.out.println(user.getPassword());
        System.out.println(user.getAuthorities());
        httpServletResponse.sendRedirect(url);
    }
}
```



登录成功之后，控制台的输出

![](图片/Snipaste_2022-11-06_15-20-05.png)



#### 自定义登录失败处理器

##### 源码分析

failureForwardUrl()内部调用的是` failureHandler()`方法

ForwardAuthenticationFailureHandler 中也是一个请求转发，并在request 作用域中设置 `SPRING_SECURITY_LAST_EXCEPTION `的 key，内容为异常对象。

##### 代码实现

新建 com.xxxx.handler.MyForwardAuthenticationFailureHandler 实现AuthenticationFailureHandler。在方法中添加重定向语句

```java
/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-06-15:21
 * Description:
 */
public class MyForwardAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final String url;

    public MyForwardAuthenticationFailureHandler(String url) {
        this.url = url;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.sendRedirect(url);
    }
}
```



安全配置类当中的配置

```java
/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-06-12:10
 * Description:
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public MyAuthenticationSucessHandler sucessHandler(){
        return new MyAuthenticationSucessHandler("http://www.baidu.com");
    }

    @Bean
    public MyForwardAuthenticationFailureHandler forwardAuthenticationFailureHandler(){
        return new MyForwardAuthenticationFailureHandler("/error.html");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 表单提交
        http
                .formLogin()
                .usernameParameter("userName")
                .passwordParameter("pwd")
                //当发现是/login的时候就认为是登录，必须和表单提交的地址是一致的
                .loginProcessingUrl("/login")
                .loginPage("/login.html")
                // 登陆成功之后跳转的路径
                //.successForwardUrl("/toMain")
                // 指定登陆成功之后跳转的url的拦截器,不能和successForwardUrl共存
                .successHandler(sucessHandler())
                //登陆失败后跳转的请求
                //.failureForwardUrl("/toError");
            // 指定登陆失败之后跳转的url的拦截器,不能和failureForwardUrl共存
                    .failureHandler(forwardAuthenticationFailureHandler());


        // 授权认证
        http.authorizeRequests()
                //login.html、/error.html不需要被认证就能访问
                .antMatchers("/login.html","/error.html").permitAll()
                // 所有的请求都必须被认证，必须登陆之后被访问
                .anyRequest()
                .authenticated();
        // 关闭跨站伪造保护，类似于关闭防火墙，不懂的自行百度
        http.csrf().disable();
    }
}
```

访问http://localhost:8080/login.html输入错误密码之后跳转的失败页面



![](图片/Snipaste_2022-11-06_15-25-22.png)



### 访问控制url匹配

在前面讲解了认证中所有常用配置，主要是对` http.formLogin()`进行操作。而在配置类中 `http.authorizeRequests()`主要是对url进行控制，也就是我们所说的授权（访问控制）。`http.authorizeRequests()`也支持连缀写法，总体公式为：

- url 匹配规则.权限控制方法

　    通过上面的公式可以有很多 url 匹配规则和很多权限控制方法。这些内容进行各种组合就形成了Spring Security中的授权。

　    在所有匹配规则中取所有规则的交集。配置顺序影响了之后授权效果，越是具体的应该放在前面，越是笼统的应该放到后面。

#### anyRequest()

​	在之前认证过程中我们就已经使用过 anyRequest()，表示匹配所有的请求。一般情况下此方法都会使用，设置全部内容都需要进行认证。

```
.anyRequest().authenticated();  
```

#### 常用的：antMatcher()

方法定义如下：

```
public C antMatchers(String... antPatterns) 
```

参数是不定向参数，每个参数是一个 ant 表达式，用于匹配 URL规则。

规则如下：

- `?`： 匹配一个字符
- `*`：匹配 0 个或多个字符
- `**` ：匹配 0 个或多个目录



在实际项目中经常需要放行所有静态资源，下面演示放行 js 文件夹下所有脚本文件。

```
.antMatchers("/js/**","/css/**").permitAll()  
```

还有一种配置方式是只要是.js 文件都放行，无论是在哪个目录

```
.antMatchers("/**/*.js").permitAll()
```

案例：

```java
// 授权认证
http.authorizeRequests()
        //login.html、/error.html不需要被认证就能访问
        .antMatchers("/login.html","/error.html").permitAll()
        // 放行静态资源
        .antMatchers("/img/**","/css/**").permitAll()
        // 所有的请求都必须被认证，必须登陆之后被访问
        .anyRequest()
        .authenticated();
// 关闭跨站伪造保护，类似于关闭防火墙，不懂的自行百度
http.csrf().disable();
```

此时就能够访问到了

![](图片/Snipaste_2022-11-06_15-39-28.png)

#### regexMatchers()

　	使用正则表达式进行匹配。和 `antMatchers()`主要的区别就是参数，`antMatchers()`参数是 ant 表达式，`regexMatchers()`参数是正则表达式。

```
.regexMatchers( ".+[.]js").permitAll()
```

##### 两个参数时使用方式

​	无论是` antMatchers()`还是` regexMatchers()`都具有两个参数的方法，其中第一个参数都是 `HttpMethod`，表示请求方式，当设置了`HttpMethod `后表示只有设定的特定的请求方式才执行对应的权限设置。





#### mvcMatchers()

　mvcMatchers()适用于配置了 servletPath 的情况。

​	`servletPath `就是所有的 URL 的统一前缀。在 SpringBoot 整合SpringMVC 的项目中可以在 application.properties 中添加下面内容设置 `ServletPath`

```
spring.mvc.servlet.path=/xxxx
```

​	在 Spring Security 的配置类中配置`.servletPath()`是 mvcMatchers()返回值特有的方法，antMatchers()和 regexMatchers()没有这个方法。在` servletPath()`中配置了`servletPath `后，mvcMatchers()直接写 SpringMVC 中@RequestMapping()中设置的路径即可。

```java
// mvc匹配方式
.mvcMatchers("/demo").servletPath("/xxxx").permitAll()
```



### 内置访问控制方法



#### permitAll()

permitAll()表示所匹配的 URL 任何人都允许访问。



#### authenticated()

authenticated()表示所匹配的 URL 都需要被认证才能访问。

#### anonymous()

anonymous()表示可以匿名访问匹配的URL。和permitAll()效果类似，只是设置为 anonymous()的 url 会执行 filter 链中

#### denyAll()

denyAll()表示所匹配的 URL 都不允许被访问。

#### rememberMe()

被“remember me”的用户允许访问

#### fullyAuthenticated()

如果用户不是被 remember me 的，才可以访问。(就是勾选了rememberme选项的，就不允许访问)



### 角色权限判断

​	除了之前讲解的内置权限控制。Spring Security 中还支持很多其他权限控制。这些方法一般都用于用户已经被认证后，判断用户是否具有特定的要求。

#### hasAuthority(String)

　判断用户是否具有特定的权限，用户的权限是在自定义登录逻辑中创建 User 对象时指定的。下图中 admin和normal 就是用户的权限。admin和normal 严格区分大小写。

```
.antMatchers("/main1.html").hasAuthority("admin")
```



#### hasAnyAuthority(String ...)

​	如果用户具备给定权限中某一个，就允许访问。

​	下面代码中由于大小写和用户的权限不相同，所以拥有admin权限的用户无权访问

```
.antMatchers("/main1.html").hasAnyAuthority("adMin","admiN")
```



#### hasRole(String)

如果用户具备给定角色就允许访问。否则出现 403。

参数取值来源于自定义登录逻辑 `UserDetailsService `实现类中创建 User 对象时给 User 赋予的授权。

　在给用户赋予角色时角色需要以：`ROLE_开头`，后面添加角色名称。例如：ROLE_abc 其中 abc 是角色名，ROLE_是固定的字符开头。

使用 hasRole()时参数也只写 abc 即可。否则启动报错。

给用户赋予角色：

　在配置类中直接写 abc 即可。

```java
.antMatchers("/main1.html").hasRole("abc")
```



#### hasAnyRole(String ...) 规则同上

如果用户具备给定角色的任意一个，就允许被访问



#### hasIpAddress(String)

如果请求是指定的 IP 就运行访问。

可以通过 `request.getRemoteAddr()`获取 ip 地址。

```java
public class MyAuthenticationSucessHandler implements AuthenticationSuccessHandler {

    private final String url;

    public MyAuthenticationSucessHandler(String url) {
        this.url = url;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        // 打印ip
        System.out.println(httpServletRequest.getRemoteAddr());
        System.out.println(user.getUsername());
        // 会输出null,这是因为出于安全原因考虑的
        System.out.println(user.getPassword());
        System.out.println(user.getAuthorities());
        httpServletResponse.sendRedirect(url);
    }
}
```

需要注意的是在本机进行测试时 localhost 和 127.0.0.1 输出的 ip地址是不一样的。

当浏览器中通过 localhost 进行访问时控制台打印的内容：

```
0:0:0:0:0:0:0:1
```

当浏览器中通过 127.0.0.1 访问时控制台打印的内容：

```
127.0.0.1
```

当浏览器中通过具体 ip 进行访问时控制台打印内容：

```java
192.168.88.1
```



### 自定义403处理方案

使用 Spring Security 时经常会看见 403（无权限），默认情况下显示的效果如下：

![](图片/Snipaste_2022-11-06_17-53-14.png)

而在实际项目中可能都是一个异步请求，显示上述效果对于用户就不是特别友好了。Spring Security 支持自定义权限受限。

因此我们需要自定义403异常处理类，为了页面更加友好，我们可以自定义一个entity用于返回前端信息的封装

```java

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-06-17:55
 * Description:
 */
@ToString
public class Response <T>{
    private int code;

    private String message;

    T data;

    public Response() {
    }

    public Response(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


    public int getCode() {
        return code;
    }

    public Response code(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Response message(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Response data(T data) {
        this.data = data;
        return this;
    }


}
```



自定义403异常处理类

```java
/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-06-17:54
 * Description: 自定义403异常处理类
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
     @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        Response resp = new Response().code(HttpServletResponse.SC_FORBIDDEN).message("权限不足");
        PrintWriter writer = response.getWriter();
        String respJson = JSON.toJSONString(resp);
        writer.write(respJson);
        writer.close();

    }
}
```



然后需要在配置类中引用该异常处理类并设置到http过滤器当中

```java
@Autowired
// 引入异常处理类
private MyAccessDeniedHandler myAccessDeniedHandler;
```

```java
http.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler);
```



得到的效果如图

![](图片/Snipaste_2022-11-06_18-15-50.png)

### 基于表达式的访问控制



#### 1、access()方法使用

之前学习的登录用户权限判断实际上底层实现都是调用access(表达式)

可以通过` access()`实现和之前学习的权限控制完成相同的功能。

```java
public ExpressionUrlAuthorizationConfigurer<H>.ExpressionInterceptUrlRegistry hasRole(String role) {
    return this.access(ExpressionUrlAuthorizationConfigurer.hasRole(role));
}

public ExpressionUrlAuthorizationConfigurer<H>.ExpressionInterceptUrlRegistry hasAnyRole(String... roles) {
    return this.access(ExpressionUrlAuthorizationConfigurer.hasAnyRole(roles));
}

public ExpressionUrlAuthorizationConfigurer<H>.ExpressionInterceptUrlRegistry hasAuthority(String authority) {
    return this.access(ExpressionUrlAuthorizationConfigurer.hasAuthority(authority));
}

public ExpressionUrlAuthorizationConfigurer<H>.ExpressionInterceptUrlRegistry hasAnyAuthority(String... authorities) {
    return this.access(ExpressionUrlAuthorizationConfigurer.hasAnyAuthority(authorities));
}

public ExpressionUrlAuthorizationConfigurer<H>.ExpressionInterceptUrlRegistry hasIpAddress(String ipaddressExpression) {
    return this.access(ExpressionUrlAuthorizationConfigurer.hasIpAddress(ipaddressExpression));
}

public ExpressionUrlAuthorizationConfigurer<H>.ExpressionInterceptUrlRegistry permitAll() {
    return this.access("permitAll");
}

public ExpressionUrlAuthorizationConfigurer<H>.ExpressionInterceptUrlRegistry anonymous() {
    return this.access("anonymous");
}

public ExpressionUrlAuthorizationConfigurer<H>.ExpressionInterceptUrlRegistry rememberMe() {
    return this.access("rememberMe");
}

public ExpressionUrlAuthorizationConfigurer<H>.ExpressionInterceptUrlRegistry denyAll() {
    return this.access("denyAll");
}

public ExpressionUrlAuthorizationConfigurer<H>.ExpressionInterceptUrlRegistry authenticated() {
    return this.access("authenticated");
}

public ExpressionUrlAuthorizationConfigurer<H>.ExpressionInterceptUrlRegistry fullyAuthenticated() {
    return this.access("fullyAuthenticated");
}

public ExpressionUrlAuthorizationConfigurer<H>.ExpressionInterceptUrlRegistry access(String attribute) {
    if (this.not) {
        attribute = "!" + attribute;
    }

    ExpressionUrlAuthorizationConfigurer.this.interceptUrl(this.requestMatchers, SecurityConfig.createList(new String[]{attribute}));
    return ExpressionUrlAuthorizationConfigurer.this.REGISTRY;
}
```

以hasRole为例子：

```java
.antMatchers("/main1.html").access("hasRole('abc')")
```

同样达到和以下一样的效果

```java
.antMatchers("/main1.html").hasRole("abc")
```



#### 2、 使用自定义权限校验方法

​	虽然springsecurity里面已经包含了很多权限校验的方法，但是在我们实际生产过程当中还是有可能出现需要自己实现自定义的权限校验的方法，用来判断当前登录用户是否具有访问当前 URL 权限。

​	如何实现自定义的权限校验的方法呢？

##### 新建接口及实现类

```java
public interface MyService {
    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
```

```java
/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-06-18:23
 * Description: 自定义权限校验：这里我们校验用户的权限点是否包含当前路径
 *
 */
@Service
public class MyServiceImpl implements MyService {
    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object obj = authentication.getPrincipal();
        if (obj instanceof UserDetails){
            UserDetails userDetails = (UserDetails) obj;
            // 拿到权限点
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            // 判断我们拿到的权限点是否包含当前访问路径
            return authorities.contains(new SimpleGrantedAuthority(request.getRequestURI()));
        }
        return false;
    }
}
```



##### 修改配置类

在WebSecurityConfigurerAdapter 配置类当中设置 access 中通过@bean的id名.方法(参数)的形式进行调用配置类中修改如下:

```java
// 授权
        http.authorizeRequests()
                // 自定义权限校验方法
                .anyRequest()
                .access("@myServiceImpl.hasPermission(request,authentication)");
```

要注意.access("@myServiceImpl.hasPermission(request,authentication)");设置的方法参数的类名、方法名、参数名一定都要和我们自己自定义实现的权限校验类一致，否则会报错。



### 基于注解的访问控制

在 Spring Security 中提供了一些访问控制的注解。这些注解都是默认是都不可用的，需要通过`@EnableGlobalMethodSecurity `进行开启后使用。

如果设置的条件允许，程序正常执行。如果不允许会报 500错误

```
这句话是什么意思呢？
	首先一个请求过来并不是直接报错500，而是先判断这个请求是否包含登录信息，如果还没有登陆过，则先进行登录，登陆成功之后再进行权限的判断，如果还是没有相应的权限，才会报500错误。
```

这些注解可以写到 Service 接口或方法上，也可以写到 Controller或 Controller 的方法上。通常情况下都是写在控制器方法上的，控制接口URL是否允许被访问。



#### @Secured

​	@Secured 是专门用于判断是否具有角色的。能写在方法或类上。参数要以 ROLE_开头。

##### 开启注解

在 启 动 类 ( 也 可 以 在 配 置 类 等 能 够 扫 描 的 类 上 ) 上 添 加

EnableGlobalMethodSecurity(securedEnabled = true)

此时配置类的那些我们之前配置的权限需要都去掉，否则会干扰我们的测试。

```java
package com.xxx.springsecuritydemo.config;

import com.xxx.springsecuritydemo.handler.MyAccessDeniedHandler;
import com.xxx.springsecuritydemo.handler.MyAuthenticationSucessHandler;
import com.xxx.springsecuritydemo.handler.MyForwardAuthenticationFailureHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-06-12:10
 * Description:
 */
@Configuration
@AllArgsConstructor 
@EnableGlobalMethodSecurity(securedEnabled = true)  // 开启注解控制权限
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    // 引入异常处理类
    private final MyAccessDeniedHandler myAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public MyAuthenticationSucessHandler sucessHandler(){
        //return new MyAuthenticationSucessHandler("http://www.baidu.com");
        return new MyAuthenticationSucessHandler("/main.html");
    }

    @Bean
    public MyForwardAuthenticationFailureHandler forwardAuthenticationFailureHandler(){
        return new MyForwardAuthenticationFailureHandler("/error.html");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 表单提交
        http
                .formLogin()
                .usernameParameter("userName")
                .passwordParameter("pwd")
                 // 登陆成功之后跳转的路径
                .successForwardUrl("/toMain")
                .loginPage("/login.html")
     
                // 指定登陆成功之后跳转的url的拦截器,不能和successForwardUrl共存
                .successHandler(sucessHandler())

            // 指定登陆失败之后跳转的url的拦截器,不能和failureForwardUrl共存
                    .failureHandler(forwardAuthenticationFailureHandler());


        // 授权认证
        http.authorizeRequests()
                //login.html、/error.html不需要被认证就能访问
                .antMatchers("/login.html","/error.html").permitAll()
                // 放行静态资源
                .antMatchers("/img/**","/css/**").permitAll()
                .anyRequest()
                .authenticated();








        // 关闭跨站伪造保护，类似于关闭防火墙，不懂的自行百度
        http.csrf().disable();

        http.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler);
    }
}

```

然后在需要进行权限控制的controller类(在类上使用就是这个类的所有方法都需要该权限)或者方法上添加相应的注解（必须以ROLE_为前缀,并且严格区分大小写，否则就会报错）

```java
@Controller
public class LoginController {

    @PostMapping("toMain")
    @Secured("ROLE_abc") // 必须以ROLE_为前缀
    public String toMain(){
        System.out.println("执行登录");
        return "redirect:main.html";
    }
}
```



如果为以下这种情况，

@Controller
public class LoginController {

```java
@PostMapping("toMain")
@Secured("abc") 
public String toMain(){
    System.out.println("执行登录");
    return "redirect:main.html";
}
```
}

那么因为我们在配置类当中指定了登陆成功之后跳转到该方法，但是由于我们已经加了权限注解，而我们的权限在userDetails查出来的是这样的"admin,normal,ROLE_abc"，没有abc这个权限，那么就会直接报错500

![](图片/Snipaste_2022-11-06_18-59-57.png)



#### @PreAuthorize/@PostAuthorize

@PreAuthorize 和@PostAuthorize 都是方法或类级别注解。

- `@PreAuthorize `表示访问方法或类在执行之前先判断权限，大多情况下都是使用这个注解，注解的参数和access()方法参数取值相同，都是权限表达式。
- `@PostAuthorize` 表示方法或类执行结束后判断权限，此注解很少被使用到。

```java
@Controller
public class LoginController {
    //@RequestMapping("login")
    //public String login(){
    //    System.out.println("执行登录");
    //    return "redirect:main.html";
    //}

    @PostMapping("toMain")
    @PreAuthorize("hasRole('abc')")
    //@PreAuthorize("hasRole('ROLE_abc')")
    public String toMain(){
        System.out.println("执行登录");
        return "redirect:main.html";
    }
}
```

访问localhost:8080/login.html

![1667732875775](图片/1667732875775.png)



点击跳转，访问受控制资源toMain发现是可以访问成功的

```
在配置类当中我们配置hasRole的时候是不允许要ROLE_开头的，否则会报错。

但是我们使用注解控制权限的时候，发现既可以使用ROLE_开头也可以不用ROLE_开头,效果都一样

但是权限都是需要区分大小写
```



### RememberMe功能实现

​	Spring Security 中 Remember Me 为“记住我”功能，用户只需要在登录时添加 remember-me复选框，取值为true。Spring Security 会自动把用户信息存储到数据源中，下次再登录的时候就会从数据库当中去到这个信息，帮我们去登录，免除我们自己登录的功能

#### 添加依赖

​	Spring Security 实 现 Remember Me 功 能 时 底 层 实 现 依 赖Spring-JDBC，所以需要导入 Spring-JDBC。但是大部分场景都是使用 MyBatis 框架而很少直接导入 spring-jdbc，所以此处导入 mybatis 启动器同时还需要添加 MySQL 驱动

```xml
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.5.2</version>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
```



#### 功能实现

applciation.yml添加数据库连接信息

```yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/springsecurity?characterEncoding=utf-8&serverTimezone=UTC
    username: root
    password: 123456
    driver-class-name: com.mysql.cj.jdbc.Driver
```



配置类信息需要添加的配置

```java
/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-06-12:10
 * Description:
 */
@Configuration
@AllArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true) // 开启注解控制权限
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //引入权限校验类(用户信息持久化rememberme功能需要引入)
    private final UserDetailsService detailsService;

    // 引入数据源(用户信息持久化rememberme功能需要引入)
    private final DataSource dataSource;
    
    
    
    
     @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public MyAuthenticationSucessHandler sucessHandler(){
        //return new MyAuthenticationSucessHandler("http://www.baidu.com");
        return new MyAuthenticationSucessHandler("/main.html");
    }

    @Bean
    public MyForwardAuthenticationFailureHandler forwardAuthenticationFailureHandler(){
        return new MyForwardAuthenticationFailureHandler("/error.html");
    }
    
     @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 表单提交
        http
                .formLogin()
                .usernameParameter("userName")
                .passwordParameter("pwd")
                //当发现是/login的时候就认为是登录，必须和表单提交的地址是一致的
                .loginProcessingUrl("/login")
                .loginPage("/login.html")
                // 登陆成功之后跳转的路径
                .successForwardUrl("/toMain")
            // 指定登陆失败之后跳转的url的拦截器,不能和failureForwardUrl共存
                    .failureHandler(forwardAuthenticationFailureHandler());

        // 授权认证
        http.authorizeRequests()
                //login.html、/error.html不需要被认证就能访问
                .antMatchers("/login.html","/error.html").permitAll()
                // 放行静态资源
                .antMatchers("/img/**","/css/**").permitAll()
                .anyRequest()
                .authenticated();


        // 关闭跨站伪造保护，类似于关闭防火墙，不懂的自行百度
        http.csrf().disable();

        http.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler);

        //注入我们之前编写的detailsService
        http.rememberMe().userDetailsService(detailsService)
                // 持久成对象？
                .tokenRepository(persistentTokenRepository());
    }
    
     // 持久层对象注入
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        // 设置源数据
        repository.setDataSource(dataSource);
        // 设置表信息,这个表信息也可以我们自己创建，也可以让springsecurity框架创建只需要设置为true即可
        // 自动创建表，第一次启动的时候需要设置为true,第二次启动的时候就不需要了
        // 如果一直设置为true.第一次以后的每次启动就会报错
        repository.setCreateTableOnStartup(true);
        return repository;

    }
```



登录页面添加rememberme选项

```html
<form action="/login" method="post">
    用户名：<input type="text" name="userName" /><br/>
    密码：<input type="password" name="pwd" /><br/>
    <input type="checkbox" name="remember-me" value="true"/><br/>
    <input type="submit" value="登录" />
</form>
```



要注意的是有可能会出现循环依赖

```
┌─────┐
|  securityConfig defined in file [D:\自学\oauth2\优极限\springsecuritydemo\target\classes\com\xxx\springsecuritydemo\config\SecurityConfig.class]
↑     ↓
|  userDetailsImpl (field private org.springframework.security.crypto.password.PasswordEncoder com.xxx.springsecuritydemo.service.impl.UserDetailsImpl.passwordEncoder)
└─────┘
```



此时需要在权限校验类UserDetailsImpl当中的引入的PasswordEncoder类上加个@Lazy懒加载注解即可解决

```java
@Service
@Slf4j
public class UserDetailsImpl implements UserDetailsService {

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /*
            此处的逻辑是：
                1。查询数据库判断用户名是否存在，如果不存在则抛出UsernameNotFoundException异常
                2。把查询出来的密码进行解析，或者把密码放入构造方法当中
         */
        log.info("执行了loadUserByUsername方法........");
        if (!"admin".equals(username)){
            throw new UsernameNotFoundException("用户名不存在！");
        }
        // 模拟数据库当中查询出来的密码
        String password = passwordEncoder.encode("123456");

        // 数据库当中查询出来的权限
        List<GrantedAuthority> permissions = AuthorityUtils.commaSeparatedStringToAuthorityList("admin,normal,ROLE_abc");

        // 构建springsecurity的user对象并返回
        return new User(username,password,permissions);
    }
}
```



启动成功之后security就会在数据库当中创建一张表

![1667734756164](图片/1667734756164.png)



然后我们需要将配置类当中的

![1667734790339](图片/1667734790339.png)

把repository.setCreateTableOnStartup(true);去掉，否则下次启动的时候就会报错java.sql.SQLSyntaxErrorException: Table 'persistent_logins' already exists



访问localhost:8080/login.html,输入用户名和密码，并且勾选rememberme，

![1667735384288](图片/1667735384288.png)

登陆成功

![1667735324679](图片/1667735324679.png)



此时数据库当中就会保存这次登录的记录

![1667735407377](图片/1667735407377.png)



把网页关掉，再次打开网页访问受控制资源http://localhost:8080/main1.html，就不会跳转到登录页面即可直接访问了

![1667735514095](图片/1667735514095.png)





记住我的功能默认保存的记录是两周，当然也可以自定义保留的时长

```java
//注入我们之前编写的detailsService
http.rememberMe().userDetailsService(detailsService)
        // 持久成对象？
        .tokenRepository(persistentTokenRepository())
        // 记住我的失效时间
        .tokenValiditySeconds(60);
        // 自定义rememberMe接收的前端传过来的参数名
        //.rememberMeParameter();
```

超过60s之后再次访问受控制资源，就需要我们再次登录

![1667735825736](图片/1667735825736.png)





### Thymeleaf中SpringSecurity的使用

Spring Security 可以在一些视图技术中进行控制显示效果。例如：`JSP `或 `Thymeleaf`。在非前后端分离且使用 Spring Boot 的项目中多使用 `Thymeleaf `作为视图展示技术。

　Thymeleaf 对 Spring Security 的 支 持 都 放 在`thymeleaf-extras-springsecurityX `中，目前最新版本为 5。所以需要在项目中添加此 jar 包的依赖和 thymeleaf 的依赖。

```xml
<!--thymeleaf springsecurity5 依赖-->
<dependency>
   <groupId>org.thymeleaf.extras</groupId>
   <artifactId>thymeleaf-extras-springsecurity5</artifactId>
</dependency>
<!--thymeleaf依赖-->
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

在 html 页面中引入 thymeleaf 命名空间和 security 命名空间

```html
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity5">
```

通过这些命名空间去获取相应的内容。

这些获取到的内容实际上就是UsernamePasswordAuthenticationToken里面的内容

```java
package org.springframework.security.authentication;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

public class UsernamePasswordAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 520L;
    private final Object principal; // 主体信息：
    private Object credentials; // 凭证

    public UsernamePasswordAuthenticationToken(Object principal, Object credentials) {
        super((Collection)null);
        this.principal = principal;
        this.credentials = credentials;
        this.setAuthenticated(false);
    }

    public UsernamePasswordAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    public Object getCredentials() {
        return this.credentials;
    }

    public Object getPrincipal() {
        return this.principal;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }
}
```

```java
public abstract class AbstractAuthenticationToken implements Authentication, CredentialsContainer {
    private final Collection<GrantedAuthority> authorities; // 权限信息
    private Object details; // 详情
    private boolean authenticated = false; // 认证
    ......
        
   // 这个方法是获取相应的权限信息
   public Collection<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
    // 获取详情
    public Object getDetails() {
        return this.details;
    }
    
    // 这个方法是获取用户名
     public String getName() {
        if (this.getPrincipal() instanceof UserDetails) {
            return ((UserDetails)this.getPrincipal()).getUsername();
        } else if (this.getPrincipal() instanceof AuthenticatedPrincipal) {
            return ((AuthenticatedPrincipal)this.getPrincipal()).getName();
        } else if (this.getPrincipal() instanceof Principal) {
            return ((Principal)this.getPrincipal()).getName();
        } else {
            return this.getPrincipal() == null ? "" : this.getPrincipal().toString();
        }
    }
}
```

通过这两个类我们可以获取登录账号名name、principal(主体信息，就是UserDetails)、credentials（凭证）、authorities（权限信息）、details（详情:这个是一个WebAuthenticationDetails的实例）

```java
public class WebAuthenticationDetails implements Serializable {
    private static final long serialVersionUID = 520L;
    private final String remoteAddress; // ip地址
    private final String sessionId; 
    ......
}
```



#### 获取属性

可以在html页面中通过`sec:authentication=""`获取

`UsernamePasswordAuthenticationToken`中所有 `getXXX `的内容，包含父类中的 `getXXX `的内容。

根据源码得出下面属性：

- `name`：登录账号名称
- `principal`：登录主体，在自定义登录逻辑中是 UserDetails
- `credentials`：凭证
- `authorities`：权限和角色
- `details`：实际上是 `WebAuthenticationDetails `的实例。可以获取`remoteAddress`(客户端 ip)和 `sessionId`(当前 sessionId)





#### 实现

##### 新建demo.html

![1667737061099](图片/1667737061099.png)

```html
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity5">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    登录账号:<span sec:authentication="name"></span><br/>
    登录账号:<span sec:authentication="principal.username"></span><br/>
    凭证：<span sec:authentication="credentials"></span><br/>
    权限和角色：<span sec:authentication="authorities"></span><br/>
    客户端地址：<span sec:authentication="details.remoteAddress"></span><br/>
    sessionId：<span sec:authentication="details.sessionId"></span><br/>
</body>
</html>
```



##### 编写Controller

thymeleaf 页面需要控制转发，在控制器类中编写下面方法

```java
/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-06-11:16
 * Description:
 */
@Controller
public class LoginController {

    /**
     * 页面跳转
     *
     * @return
     */
    @RequestMapping("/demo")
    public String demo(){
        return "demo";
    }
}
```



##### 测试

先登录http://localhost:8080/login.html

![1667737226512](图片/1667737226512.png)



![1667737260682](图片/1667737260682.png)

登陆成功之后跳转到我们写的demo

![1667737276136](图片/1667737276136.png)



发现可以获取到相应的权限和角色信息





#### 前端页面按钮显示权限控制

​	Thymeleaf除了可以获取相应的属性，也可以进行相应的权限判断。通过sec:authentication后面跟上表达式（跟security中的配置类当中的access("@myServiceImpl.hasPermission(request,authentication)")中的表达式是一样的，忘记的可以看上边的文档）去进行相应的权限判断，判断它能否显示这个内容，如果表达式成立，就显示这个内容，不成立就不显示这个被sec:authentication控制的内容。比如我们有增删改查的按钮，我们可以根据权限 去判断哪些按钮用户或者角色可以显示，哪些按钮用户或者角色不可以显示。



##### 实现

假设我们在UserDetailsService当中从数据库获取到的权限有admin,normal,ROLE_abc,insert,delete这几个

```java
/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-06-12:12
 * Description: UserDetailsService模拟从数据库当中查询出用户并认证
 */
@Service
@Slf4j
public class UserDetailsImpl implements UserDetailsService {

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /*
            此处的逻辑是：
                1。查询数据库判断用户名是否存在，如果不存在则抛出UsernameNotFoundException异常
                2。把查询出来的密码进行解析，或者把密码放入构造方法当中
         */
        log.info("执行了loadUserByUsername方法........");
        if (!"admin".equals(username)){
            throw new UsernameNotFoundException("用户名不存在！");
        }
        // 模拟数据库当中查询出来的密码
        String password = passwordEncoder.encode("123456");

        // 数据库当中查询出来的权限
        List<GrantedAuthority> permissions = AuthorityUtils.commaSeparatedStringToAuthorityList("admin,normal,ROLE_abc,/insert,/delete");

        // 构建springsecurity的user对象并返回
        return new User(username,password,permissions);
    }
}
```



##### 控制页面显示效果

```html
通过权限判断：
<button sec:authorize="hasAuthority('/insert')">新增</button>
<button sec:authorize="hasAuthority('/delete')">删除</button>
<button sec:authorize="hasAuthority('/update')">修改</button>
<button sec:authorize="hasAuthority('/select')">查看</button>
<br/>
通过角色判断：
<button sec:authorize="hasRole('abc')">新增</button>
<button sec:authorize="hasRole('abc')">删除</button>
<button sec:authorize="hasRole('abc')">修改</button>
<button sec:authorize="hasRole('abc')">查看</button>
```



重新登录，查看效果

![1667738094284](图片/1667738094284.png)



这个只是实现方式的一种，当然在实际项目当中有其他的实现方式





### 退出功能的实现

#### 实现

退出功能实际上很简单，只需要页面上配上退出登录的地址，然后在配置类当中配置退出的地址（默认是/logout）,即可实现

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
登陆成功！<a href="main1.html">跳转</a>
<a href="/logout">退出登录</a>
</body>
</html>
```



退出之后返回的地址http://localhost:8080/login.html?logout会携带一个logout参数，如果不想有logout参数的显示就直接到登录的页面，也可以在security配置类当中配置

```java
// 退出登录
http.logout()
        // 退出登录跳转的页面
        .logoutSuccessUrl("/login.html");
```

这个时候就不会有logout参数了，直接跳转到指定的url



自定义退出的地址

```java
// 退出登录
http.logout()
        //自定义退出的url,一般也不会修改，因为有可能会影响到我们已经实现的参数
        .logoutUrl("/user/logout");
     
```

demo.html

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
登陆成功！<a href="main1.html">跳转</a>
<a href="/user/logout">退出登录</a>
</body>
</html>
```



#### 源码解读

​	登出的功能实际上是调用了LogoutConfigurer当中的addLogoutHandler方法

```java
public final class LogoutConfigurer<H extends HttpSecurityBuilder<H>> extends AbstractHttpConfigurer<LogoutConfigurer<H>, H> {
    private List<LogoutHandler> logoutHandlers = new ArrayList();
    private SecurityContextLogoutHandler contextLogoutHandler = new SecurityContextLogoutHandler();
    private String logoutSuccessUrl = "/login?logout";
    private LogoutSuccessHandler logoutSuccessHandler;
    private String logoutUrl = "/logout";
    private RequestMatcher logoutRequestMatcher;
    private boolean permitAll;
    private boolean customLogoutSuccess;
    private LinkedHashMap<RequestMatcher, LogoutSuccessHandler> defaultLogoutSuccessHandlerMappings = new LinkedHashMap();

    public LogoutConfigurer<H> addLogoutHandler(LogoutHandler logoutHandler) {
        Assert.notNull(logoutHandler, "logoutHandler cannot be null");
        this.logoutHandlers.add(logoutHandler);
        return this;
    }
    
    // 退出登录的拦截器
    private LogoutFilter createLogoutFilter(H http) {
        this.logoutHandlers.add(this.contextLogoutHandler);
        this.logoutHandlers.add(this.postProcess(new LogoutSuccessEventPublishingLogoutHandler()));
        LogoutHandler[] handlers = (LogoutHandler[])this.logoutHandlers.toArray(new LogoutHandler[0]);
        LogoutFilter result = new LogoutFilter(this.getLogoutSuccessHandler(), handlers);
        result.setLogoutRequestMatcher(this.getLogoutRequestMatcher(http));
        result = (LogoutFilter)this.postProcess(result);
        return result;
    }
    
    // 创建默认的退出登录的拦截器
    private LogoutSuccessHandler createDefaultSuccessHandler() {
        SimpleUrlLogoutSuccessHandler urlLogoutHandler = new SimpleUrlLogoutSuccessHandler();
        urlLogoutHandler.setDefaultTargetUrl(this.logoutSuccessUrl);
        if (this.defaultLogoutSuccessHandlerMappings.isEmpty()) {
            return urlLogoutHandler;
        } else {
            DelegatingLogoutSuccessHandler successHandler = new DelegatingLogoutSuccessHandler(this.defaultLogoutSuccessHandlerMappings);
            successHandler.setDefaultLogoutSuccessHandler(urlLogoutHandler);
            return successHandler;
        }
    }
    
    // 获取退出登录成功的拦截器
    private LogoutSuccessHandler getLogoutSuccessHandler() {
        LogoutSuccessHandler handler = this.logoutSuccessHandler;
        if (handler == null) {
            handler = this.createDefaultSuccessHandler();
        }

        return handler;
    }
    ......
}
```

```java
public interface LogoutHandler {
    void logout(HttpServletRequest var1, HttpServletResponse var2, Authentication var3);
}
```

LogoutHandler实际上是个接口，它的实现类非常多，实际上我们关注的是SecurityContextLogoutHandler

```java
public class SecurityContextLogoutHandler implements LogoutHandler {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private boolean invalidateHttpSession = true;
    private boolean clearAuthentication = true;

    public SecurityContextLogoutHandler() {
    }

    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Assert.notNull(request, "HttpServletRequest required");
        // 判断session 是否是true
        if (this.invalidateHttpSession) {
            // 获取session
            HttpSession session = request.getSession(false);
            // 判断session不为空？不为空则拿到session
            if (session != null) {
                this.logger.debug("Invalidating session: " + session.getId());
                session.invalidate();
            }
        }

        // 清除session
        if (this.clearAuthentication) {
            // 把上下文的认证信息设置为null
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication((Authentication)null);
        }

        SecurityContextHolder.clearContext();
    }

    public boolean isInvalidateHttpSession() {
        return this.invalidateHttpSession;
    }

    public void setInvalidateHttpSession(boolean invalidateHttpSession) {
        this.invalidateHttpSession = invalidateHttpSession;
    }

    public void setClearAuthentication(boolean clearAuthentication) {
        this.clearAuthentication = clearAuthentication;
    }
}
```

首先logout的执行流程就是

​	1、通过/logout进行页面跳转

​	2、销毁httpSession对象

​	3、清除认证状态



### SpringSecurity中的CSRF

从刚开始学习Spring Security时，在配置类中一直存在这样一行代码：`http.csrf().disable();`如果没有这行代码导致用户无法被认证。这行代码的含义是：关闭 csrf 防护。

#### 1、什么是CSRF

​	CSRF（Cross-site request forgery）跨站请求伪造，也被称为“OneClick Attack” 或者 Session Riding。通过伪造用户请求访问受信任站点的非法请求访问。

　	跨域：只要网络协议，ip 地址，端口中任何一个不相同就是跨域请求。

　	客户端与服务进行交互时，由于 http 协议本身是无状态协议，所以引入了cookie进行记录客户端身份。在cookie中会存放session id用来识别客户端身份的。在跨域的情况下，session id 可能被第三方恶意劫持，通过这个 session id 向服务端发起请求时，服务端会认为这个请求是合法的，可能发生很多意想不到的事情。



#### 2、Spring Security中的CSRF

　从 Spring Security4开始CSRF防护默认开启。默认会拦截请求。进行CSRF处理。CSRF为了保证不是其他第三方网站访问，要求访问时携带参数名为`_csrf`值为token(token 在服务端产生)的内容，如果token和服务端的token匹配成功，则正常访问。

实例：

##### 修改配置类

​	在配置类中注释掉 CSRF 防护失效

```java
//关闭csrf防护
http.csrf().disable();
```

在template当中创建一个登录的页面(需要携带命名空间)

```html
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org"
>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<form action="/login" method="post">
    用户名：<input type="text" name="userName" /><br/>
    密码：<input type="password" name="pwd" /><br/>
    <input type="checkbox" name="remember-me" value="true"/><br/>
    <input type="submit" value="登录" />
</form>
</body>
</html>
```



修改配置类

```java
/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-06-12:10
 * Description:
 */
@Configuration
@AllArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true) // 开启注解控制权限
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    // 引入异常处理类
    private final MyAccessDeniedHandler myAccessDeniedHandler;

    //引入权限校验类
    private final UserDetailsService detailsService;

    // 引入数据源
    private final DataSource dataSource;



    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public MyAuthenticationSucessHandler sucessHandler(){
        //return new MyAuthenticationSucessHandler("http://www.baidu.com");
        return new MyAuthenticationSucessHandler("/main.html");
    }

    @Bean
    public MyForwardAuthenticationFailureHandler forwardAuthenticationFailureHandler(){
        return new MyForwardAuthenticationFailureHandler("/error.html");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 表单提交
        http
                .formLogin()
                .usernameParameter("userName")
                .passwordParameter("pwd")
                //当发现是/login的时候就认为是登录，必须和表单提交的地址是一致的
                .loginProcessingUrl("/login")
                //.loginPage("/login.html")
                .loginPage("/showLogin")
                // 登陆成功之后跳转的路径
                .successForwardUrl("/toMain");

            // 指定登陆失败之后跳转的url的拦截器,不能和failureForwardUrl共存
                   // .failureHandler(forwardAuthenticationFailureHandler());


        // 授权认证
        http.authorizeRequests()
                //login.html、/error.html不需要被认证就能访问
                .antMatchers("/showLogin","/error.html").permitAll()
                .anyRequest()
                .authenticated();

         // 退出登录
        http.logout()
                //自定义退出的url
                //.logoutUrl("/user/logout")
                // 退出登录跳转的页面
                .logoutSuccessUrl("/login.html");



        // 关闭跨站伪造保护，类似于关闭防火墙，不懂的自行百度
        //http.csrf().disable();

        //http.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler);

        //注入我们之前编写的detailsService
        http.rememberMe().userDetailsService(detailsService)
                // 持久成对象？
                .tokenRepository(persistentTokenRepository())
                // 记住我的失效时间
                .tokenValiditySeconds(60);
                // 自定义rememberMe接收的前端传过来的参数名
                //.rememberMeParameter();

 


    }
```



编写controller



```java
/**
 * 页面跳转
 *
 * @return
 */
@RequestMapping("showLogin")
public String showLogin(){
    return "login";
}
```

发现访问localhost:8080/showLogin，输入正确的用户名和密码都不能访问成功

![1667740881117](图片/1667740881117.png)

在login.html的form表单当中加入

```html
<input type="hidden" th:value="${_csrf.token}" name="_csrf" th:if="${_csrf}">
```

上面的意思就是去服务器端取token，拿过来之后判断是否为null，不为空则将token放到请求信息当中



登陆测试

![1667741170305](图片/1667741170305.png)



发现能够登录成功，但是此时csrf是默认开启状态的

![1667741311858](图片/1667741311858.png)



然后发现是存在csrf的，这就是服务器端自己生成的token

![1667741447495](图片/1667741447495.png)



在学习阶段我们都是直接把crsf关掉

```java
http.csrf().disable();
```

保证能够访问到后端，但是正式开发过程当中都是按照

```html
<input type="hidden" th:value="${_csrf.token}" name="_csrf" th:if="${_csrf}">
```

从服务端获取到token，然后再带回给服务端，服务端会判断带回来的token是否等于当前token,一致就允许访问，不一致就不允许访问。





# Oauth2认证

## Oauth2简介

### 简介

第三方认证技术方案最主要是解决认证协议的通用标准问题，因为要实现跨系统认证，各系统之间要遵循一定的接口协议。

　OAUTH协议为用户资源的授权提供了一个安全的、开放而又简易的标准。同时，任何第三方都可以使用OAUTH认证服务，任何服务提供商都可以实现自身的OAUTH认证服务，因而OAUTH是开放的。业界提供了OAUTH的多种实现如PHP、JavaScript，Java，Ruby等各种语言开发包，大大节约了程序员的时间，因而OAUTH是简易的。互联网很多服务如Open API，很多大公司如Google，Yahoo，Microsoft等都提供了OAUTH认证服务，这些都足以说明OAUTH标准逐渐成为开放资源授权的标准。

　Oauth协议目前发展到2.0版本，1.0版本过于复杂，2.0版本已得到广泛应用。

参考：<https://baike.baidu.com/item/oAuth/7153134?fr=aladdin>

Oauth 协议：<https://tools.ietf.org/html/rfc6749>

下边分析一个Oauth2认证的例子，网站使用微信认证的过程：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210619234417598.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQ1Mjk3NTc4,size_16,color_FFFFFF,t_70)



1. 用户进入网站的登录页面，点击微信的图标以微信账号登录系统，用户是自己在微信里信息的资源拥有者。

![在这里插入图片描述](图片/2021061923043568.png)

![在这里插入图片描述](图片/2021061923045740.png)

点击“微信”出现一个二维码，此时用户扫描二维码，开始给网站授权。

1、资源拥有者同意给客户端授权

　资源拥有者扫描二维码表示资源拥有者同意给客户端授权，微信会对资源拥有者的身份进行验证，验证通过后，微信会询问用户是否给授权网站访问自己的微信数据，用户点击“确认登录”表示同意授权，微信认证服务器会颁发一个授权码，并重定向到网站。

2、客户端获取到授权码，请求认证服务器申请令牌

　此过程用户看不到，客户端应用程序请求认证服务器，请求携带授权码。

3、认证服务器向客户端响应令牌

　认证服务器验证了客户端请求的授权码，如果合法则给客户端颁发令牌，令牌是客户端访问资源的通行证。此交互过程用户看不到，当客户端拿到令牌后，用户在网站看到已经登录成功。

4、客户端请求资源服务器的资源

　客户端携带令牌访问资源服务器的资源。网站携带令牌请求访问微信服务器获取用户的基本信息。

5、资源服务器返回受保护资源

　资源服务器校验令牌的合法性，如果合法则向用户响应资源信息内容。

注意：资源服务器和认证服务器可以是一个服务也可以分开的服务，如果是分开的服务资源服务器通常要请求认证服务器来校验令牌的合法性。

Oauth2.0认证流程如下：

引自Oauth2.0协议rfc6749 <https://tools.ietf.org/html/rfc6749>



#### 角色

**客户端**

本身不存储资源，需要通过资源拥有者的授权去请求资源服务器的资源，比如：Android客户端、Web客户端（浏览器端）、微信客户端等。

**资源拥有者**

通常为用户，也可以是应用程序，即该资源的拥有者。

**授权服务器（也称认证服务器）**

用来对资源拥有的身份进行认证、对访问资源进行授权。客户端要想访问资源需要通过认证服务器由资源拥有者授权后方可访问。

**资源服务器**

存储资源的服务器，比如，网站用户管理服务器存储了网站用户信息，网站相册服务器存储了用户的相册信息，微信的资源服务存储了微信的用户信息等。客户端最终访问资源服务器获取资源信息。

#### 常用术语

- `客户凭证(client Credentials)`：客户端的clientId和密码用于认证客户
- `令牌(tokens)`：授权服务器在接收到客户请求后，颁发的访问令牌
- `作用域(scopes)`：客户请求访问令牌时，由资源拥有者额外指定的细分权限(permission)

#### 令牌类型

- `授权码`：仅用于授权码授权类型，用于交换获取访问令牌和刷新令牌
- `访问令牌`：用于代表一个用户或服务直接去访问受保护的资源
- `刷新令牌`：用于去授权服务器获取一个刷新访问令牌
- `BearerToken`：不管谁拿到Token都可以访问资源，类似现金
- `Proof of Possession(PoP) Token`：可以校验client是否对Token有明确的拥有权

#### 特点

**优点**：

 	更安全，客户端不接触用户密码，服务器端更易集中保护

 	广泛传播并被持续采用

 	短寿命和封装的token

 	资源服务器和授权服务器解耦

​	 集中式授权，简化客户端

 	HTTP/JSON友好，易于请求和传递token

 	考虑多种客户端架构场景

 	客户可以具有不同的信任级别

**缺点**：

 	协议框架太宽泛，造成各种实现的兼容性和互操作性差

 	不是一个认证协议，本身并不能告诉你任何用户信息。



### 授权模式

​	授权码模式（Authorization Code）

​	密码模式（Resource Owner PasswordCredentials）

​	简化授权模式（Implicit）

​	客户端模式（Client Credentials）



#### 刷新令牌



## Spring Security Oauth2

### 授权服务器

- `Authorize Endpoint`：授权端点，进行授权
- `Token Endpoint`：令牌端点，经过授权拿到对应的Token
- `Introspection Endpoint`：校验端点，校验Token的合法性
- `Revocation Endpoint`：撤销端点，撤销授权





### 验证授权码模式



快速开始

新建一个springboot项目

pom文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.xxxx</groupId>
    <artifactId>springsecurityoauth2demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>springsecurityoauth2demo</name>
    <description>springsecurityoauth2demo</description>

    <properties>
        <!-- Environment Settings -->
        <java.version>1.8</java.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>

        <!-- Spring Settings -->
        <spring-cloud.version>Hoxton.SR12</spring-cloud.version>
        <spring-cloud-alibaba.version>2.2.1.RELEASE</spring-cloud-alibaba.version>
        <boot.version>2.3.5.RELEASE</boot.version>
    </properties>

    <dependencyManagement>
        <dependencies>

            <!-- Spring Security OAuth2 -->
            <dependency>
                <groupId>org.springframework.security.oauth</groupId>
                <artifactId>spring-security-oauth2</artifactId>
                <version>2.2.1.RELEASE</version>
            </dependency>
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>fastjson</artifactId>
                <version>1.2.83</version>
            </dependency>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${boot.version}</version>
                <scope>import</scope>
                <type>pom</type>
            </dependency>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>${spring-cloud-alibaba.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <dependencies>
        <!-- web 依赖-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <!-- Spring Security OAuth2 -->
        <dependency>
            <groupId>org.springframework.security.oauth</groupId>
            <artifactId>spring-security-oauth2</artifactId>
        </dependency>
        <!--jwt依赖-->
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt</artifactId>
            <version>0.9.0</version>
        </dependency>
        <!--// 添加security提供的jwt包-->
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-jwt</artifactId>
            <version>1.1.0.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>


        <!--以下为新增-->
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-core</artifactId>
            <version>5.2.2.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-test</artifactId>
            <version>2.2.5.RELEASE</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <!--引入本地资源-->
        <resources>
            <!--加载lib文件，特殊情况下会有lib，但大部分都靠依赖下载了-->
            <resource>
                <directory>lib</directory>
                <targetPath>BOOT-INF/lib/</targetPath>
                <includes>
                    <include>**/*.jar</include>
                </includes>
            </resource>
            <!--打jar包-->
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
            </resource>
            <!--将properties和xml文件编译-->
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                    <include>**/*.yml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
        </resources>


    </build>

</project>
```

#### 编写实体类

User.java

```java
package com.xxxx.springsecurityoauth2demo.service.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-06-23:16
 * Description: 自定义User类
 */
@Data
@NoArgsConstructor
public class User implements UserDetails {

    private String username;

    private String password;

    private List<GrantedAuthority> authorities;


    public User(String username, String password, List<GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    // 要注意这几个参数都必须设置为true,忘记的可以查看上边security部分或者百度
    // 表示账户是否已过期
    public boolean isAccountNonExpired() {
        return true;
    }

    //表示账户是否被锁定
    public boolean isAccountNonLocked() {
        return true;
    }

    // 表示凭证是否已经过期
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 表示账户是否可用
    public boolean isEnabled() {
        return true;
    }
}
```



#### 编写Service

```java
package com.xxxx.springsecurityoauth2demo.service.impl;

import com.xxxx.springsecurityoauth2demo.service.pojo.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-06-23:10
 * Description:
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;


    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        String password = passwordEncoder.encode("123456");

        return new User("admin",password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}

```



#### 编写Controller

```java
package com.xxxx.springsecurityoauth2demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-06-23:33
 * Description:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 获取当前用户
     *
     * @param authentication 认证信息
     * @return
     */
    @RequestMapping("/getCurrentUser")
    public Object getCurrentUser(Authentication authentication){
        return authentication.getPrincipal();
    }

}
```

#### 编写配置类

1、安全配置类

```java
package com.xxxx.springsecurityoauth2demo.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-06-23:13
 * Description:
 */
@EnableWebSecurity // 作用1: 加载了WebSecurityConfiguration配置类, 配置安全认证策略。
                    // 2: 加载了AuthenticationConfiguration, 配置了认证信息。
                    //它还有一个debug的功能，如果设置为true，则开启debug功能，每个经过那些过滤器都会被展示出来。
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // http配置
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 因为是测试阶段，所以把csrf跨站伪造给关闭了
                .csrf().disable()
                .authorizeRequests()
                // 放行指定的url
                .antMatchers("/oauth/**","/login/**","/logout/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                //表单认证放行
                .formLogin()
                .permitAll();
    }
}
```



oauth2授权服务器

```java
package com.xxxx.springsecurityoauth2demo.service.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-06-23:24
 * Description: 授权服务器
 */
@Configuration
@EnableAuthorizationServer // 标注为授权服务器
@AllArgsConstructor
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //内存存储,正常情况下是从授权服务器数据库当中查询出来的
        clients.inMemory()
                // 配置client-id
                .withClient("admin")
                //配置secret
                .secret(passwordEncoder.encode("123456"))
                // 配置访问的token时间有效期
                .accessTokenValiditySeconds(3600)
                // 配置redirect_uri用于授权成功之后跳转的url
                .redirectUris("http://www.baidu.com")
                // 授权范围
                .scopes("all")
                // 配置grant_type 表示授权类型
                .authorizedGrantTypes("authorization_code");


    }
}
```





oauth2资源服务器

```java
package com.xxxx.springsecurityoauth2demo.service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-06-23:29
 * Description: 资源服务器
 */
@EnableResourceServer
@Configuration
public class ResourceServer extends ResourceServerConfigurerAdapter {


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                // 所有的资源都需要认证才能访问
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .requestMatchers()
                // 以下请求需要经过oauth2鉴权,就是说以下路径只有拿到token才能访问，然后就是不是以下路径的资源都不受oauth2鉴权，因此就算拿到token也没有访问权限
                .antMatchers("/user/**");
    }
}
```





#### 测试

##### 获取授权码(url的参数要和授权服务器的参数要一致，否则拿不到授权码)

<http://localhost:8080/oauth/authorize?response_type=code&client_id=admin&redirect_uri=http://www.baidu.com&scope=all>

此时会跳转到登陆界面

![1667749854127](图片/1667749854127.png)

输入账户密码

会跳转到手动确认授权界面

![1667749895838](图片/1667749895838.png)



点击授权获取授权码

![1667749934232](图片/1667749934232.png)



此时会跳转到我们在授权服务器中设定的授权成功之后重定向的地址，同时该地址还会包含 授权码信息（因为我们启动的是授权码模式）

![1667750489147](图片/1667750489147.png)



##### 根据授权码获取令牌（POST请求）

- `grant_type`：授权类型，填写authorization_code，表示授权码模式
- `code`：授权码，就是刚刚获取的授权码，注意：授权码只使用一次就无效了，需要重新申请。
- `client_id`:客户端标识
- `redirect_uri`：申请授权码时的跳转url，一定和申请授权码时用的redirect_uri一致。
- `scope`:授权范围。

如果认证失败服务端返回 401 Unauthorized

注意：此时无法请求到令牌，访问服务器会报错



如果获取到授权码之后，我们就可以根据授权码获取令牌了

获取令牌地址：http://localhost:8080/oauth/token

![1667750328579](图片/1667750328579.png)

![1667750412599](图片/1667750412599.png)

点击发送

![1667750509072](图片/1667750509072.png)



最后获取得到token。

请注意：请求获取token令牌的授权码只能请求一次token,无论成功请求与否，都只能请求一次，再次请求只能再次获取授权码。同时授权码的有效期也是有限制的。



访问受限制资源

请求成功

![1667750728570](图片/1667750728570.png)





### 密码模式获取token令牌

密码模式非常简单，只需要把授权服务器当中的授权模式改成密码模式。

同时需要在服务器当中配置AuthorizationServerEndpointsConfigurer，为什么需要配置这个呢，实际上密码模式是将我们的用户名和密码传给我们的授权服务器去验证的，这个时候就需要AuthenticationManager将密码传给服务器，因此我们还需要将AuthenticationManager注入容器当中

```java
/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-06-23:13
 * Description:
 */
@EnableWebSecurity // 作用1: 加载了WebSecurityConfiguration配置类, 配置安全认证策略。
                    // 2: 加载了AuthenticationConfiguration, 配置了认证信息。
                    //它还有一个debug的功能，如果设置为true，则开启debug功能，每个经过那些过滤器都会被展示出来。
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // oauth2密码模式所需配置
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManager();
    }

    // http配置
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 因为是测试阶段，所以把csrf跨站伪造给关闭了
                .csrf().disable()
                .authorizeRequests()
                // 放行指定的url
                .antMatchers("/oauth/**","/login/**","/logout/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                //表单认证放行
                .formLogin()
                .permitAll();
    }
}
```





```java
/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-06-23:24
 * Description: 授权服务器
 */
@Configuration
@EnableAuthorizationServer // 标注为授权服务器
@AllArgsConstructor
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    private final UserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //内存存储,正常情况下是从授权服务器数据库当中查询出来的
        clients.inMemory()
                // 配置client-id
                .withClient("admin")
                //配置secret
                .secret(passwordEncoder.encode("123456"))
                // 配置访问的token时间有效期
                .accessTokenValiditySeconds(3600)
                // 配置redirect_uri用于授权成功之后跳转的url
                .redirectUris("http://www.baidu.com")
                // 授权范围
                .scopes("all")
                // 配置grant_type 表示授权类型
                .authorizedGrantTypes("authorization_code","password");


    }

    /**
     * 使用密码模式所需配置
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }
}
```



启动项目，postman测试

![](图片/Snipaste_2022-11-07_18-19-32.png)



![](图片/Snipaste_2022-11-07_00-24-04.png)

![1667816628761](图片/1667816628761.png)



点击发送

成功拿到token令牌

![1667752129664](图片/1667752129664.png)



根据令牌访问受限制资源

![1667752195664](图片/1667752195664.png)





### Redis存储Token

之前的代码我们将token直接存在内存中，这在生产环境中是不合理的，下面我们将其改造成存储在Redis中

#### 添加依赖及配置

pom文件

```xml
<!--redis 依赖-->
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
<!-- commons-pool2 对象池依赖 -->
<dependency>
   <groupId>org.apache.commons</groupId>
   <artifactId>commons-pool2</artifactId>
</dependency>
```



yml配置redis

```yml
redis:
  host: localhost
  port: 6379
```



#### 编写Redis配置类

```java
/**
 * 使用redis存储token的配置
 * @since 1.0.0
 */
@Configuration
public class RedisConfig {
   @Autowired
   private RedisConnectionFactory redisConnectionFactory;

   @Bean
   public TokenStore redisTokenStore(){
      return new RedisTokenStore(redisConnectionFactory);
   }

}
```





#### 在授权服务器配置中指定令牌的存储策略为Redis

```java
/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-06-23:24
 * Description: 授权服务器
 */
@Configuration
@EnableAuthorizationServer // 标注为授权服务器
@AllArgsConstructor
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    private final UserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;

    private final TokenStore redisTokenStore;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //内存存储,正常情况下是从授权服务器数据库当中查询出来的
        clients.inMemory()
                // 配置client-id
                .withClient("admin")
                //配置secret
                .secret(passwordEncoder.encode("123456"))
                // 配置访问的token时间有效期
                .accessTokenValiditySeconds(3600)
                // 配置redirect_uri用于授权成功之后跳转的url
                .redirectUris("http://www.baidu.com")
                // 授权范围
                .scopes("all")
                // 配置grant_type 表示授权类型
                .authorizedGrantTypes("authorization_code","password");


    }

    /**
     * 使用密码模式所需配置
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                // 把token设置为redis存储
                .tokenStore(redisTokenStore);
    }
}
```



启动项目

获取token

![1667752886947](图片/1667752886947.png)

此时token已经存储到redis当中了（这在开发当中是经常用到的）

![1667753003398](图片/1667753003398.png)



## JWT

### 常见的认证机制

#### HTTP Basic Auth

　HTTP Basic Auth简单点说明就是每次请求API时都提供用户的username和password，简言之，Basic Auth是配合RESTful API 使用的最简单的认证方式，只需提供用户名密码即可，但由于有把用户名密码暴露给第三方客户端的风险，在生产环境下被使用的越来越少。因此，在开发对外开放的RESTful API时，尽量避免采用HTTP Basic Auth。（我们在上面讲的认证全都是基础认证）

#### Cookie Auth

　Cookie认证机制就是为一次请求认证在服务端创建一个Session对象，同时在客户端的浏览器端创建了一个Cookie对象；通过客户端带上来Cookie对象来与服务器端的session对象匹配来实现状态管理的。默认的，当我们关闭浏览器的时候，cookie会被删除。但可以通过修改cookie 的expire time使cookie在一定时间内有效。



#### OAuth

　	OAuth（开放授权,Open Authorization）是一个开放的授权标准，允许用户让第三方应用访问该用户在某一web服务上存储的私密的资源（如照片，视频，联系人列表），而无需将用户名和密码提供给第三方应用。如网站通过微信、微博登录等，主要用于第三方登录。

　	OAuth允许用户提供一个令牌，而不是用户名和密码来访问他们存放在特定服务提供者的数据。每一个令牌授权一个特定的第三方系统（例如，视频编辑网站)在特定的时段（例如，接下来的2小时内）内访问特定的资源（例如仅仅是某一相册中的视频）。这样，OAuth让用户可以授权第三方网站访问他们存储在另外服务提供者的某些特定信息，而非所有内容。

​	下面是OAuth2.0的流程：

![img](图片/dc7224f7169ae9313f257e60e28e86b3.png)

　	这种基于OAuth的认证机制适用于个人消费者类的互联网产品，如社交类APP等应用，但是不太适合拥有自有认证权限管理的企业应用。

![img](图片/13a9a971c0467c24104b32d164dd0445.png)

缺点：过重。





#### Token Auth

使用基于 Token 的身份验证方法，在服务端不需要存储用户的登录记录。大概的流程是这样的：

1. 客户端使用用户名跟密码请求登录
2. 服务端收到请求，去验证用户名与密码
3. 验证成功后，服务端会签发一个 Token，再把这个 Token 发送给客户端
4. 客户端收到 Token 以后可以把它存储起来，比如放在 Cookie 里
5. 客户端每次向服务端请求资源的时候需要带着服务端签发的 Token
6. 服务端收到请求，然后去验证客户端请求里面带着的 Token，如果验证成功，就向客户端返回请求的数据



比第一种方式更安全，比第二种方式更节约服务器资源，比第三种方式更加轻量。

具体，Token Auth的优点（Token机制相对于Cookie机制又有什么好处呢？）：

1. 支持跨域访问: Cookie是不允许垮域访问的，这一点对Token机制是不存在的，前提是传输的用户认证信息通过HTTP头传输.
2. 无状态(也称：服务端可扩展行):Token机制在服务端不需要存储session信息，因为Token 自身包含了所有登录用户的信息，只需要在客户端的cookie或本地介质存储状态信息.
3. 更适用CDN: 可以通过内容分发网络请求你服务端的所有资料（如：javascript，HTML,图片等），而你的服务端只要提供API即可.
4. 去耦: 不需要绑定到一个特定的身份验证方案。Token可以在任何地方生成，只要在你的API被调用的时候，你可以进行Token生成调用即可.
5. 更适用于移动应用: 当你的客户端是一个原生平台（iOS, Android，Windows 10等）时，Cookie是不被支持的（你需要通过Cookie容器进行处理），这时采用Token认证机制就会简单得多。
6. CSRF:因为不再依赖于Cookie，所以你就不需要考虑对CSRF（跨站请求伪造）的防范。
7. 性能: 一次网络往返时间（通过数据库查询session信息）总比做一次HMACSHA256计算的Token验证和解析要费时得多.
8. 不需要为登录页面做特殊处理: 如果你使用Protractor 做功能测试的时候，不再需要为登录页面做特殊处理.
9. 基于标准化:你的API可以采用标准化的 JSON Web Token (JWT). 这个标准已经存在多个后端库（.NET, Ruby, Java,Python, PHP）和多家公司的支持（如：Firebase,Google, Microsoft）。





### JWT简介

​	JSON Web Token（JWT）是一个开放的行业标准（RFC 7519），它定义了一种简介的、自包含的协议格式，用于在通信双方传递json对象，传递的信息经过数字签名可以被验证和信任。JWT可以使用HMAC算法或使用RSA的公钥/私钥对来签名，防止被篡改。

官网： <https://jwt.io/>

标准： <https://tools.ietf.org/html/rfc7519>

JWT令牌的优点：

1. jwt基于json，非常方便解析。
2. 可以在令牌中自定义丰富的内容，易扩展。
3. 通过非对称加密算法及数字签名技术，JWT防止篡改，安全性高。
4. 资源服务使用JWT可不依赖认证服务即可完成授权。

缺点：

1. JWT令牌较长，占存储空间比较大。



#### JWT组成

​	一个JWT实际上就是一个字符串，它由三部分组成，头部、载荷与签名。



##### 头部(Header)

​	头部用于描述关于该JWT的最基本的信息，例如其类型（即JWT）以及签名所用的算法（如HMAC SHA256或RSA）等。这也可以被表示成一个JSON对象。

```json
{
  "alg": "HS256",
  "typ": "JWT"
}
```



- `typ`：是类型。
- `alg`：签名的算法，这里使用的算法是HS256算法



我们对头部的json字符串进行BASE64编码（网上有很多在线编码的网站），编码后的字符串如下：

```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9 
```



​	Base64是一种基于64个可打印字符来表示二进制数据的表示方法。由于2的6次方等于64，所以每6个比特为一个单元，对应某个可打印字符。三个字节有24个比特，对应于4个Base64单元，即3个字节需要用4个可打印字符来表示。JDK 中提供了非常方便的 BASE64Encoder 和BASE64Decoder，用它们可以非常方便的完成基于 BASE64 的编码和解码。

##### 载荷(Payload)

第二部分是载荷，就是存放有效信息的地方。这个名字像是特指飞机上承载的货品，这些有效信息包含三个部分：

- 标准中注册的声明（建议但不强制使用）

```xml
iss: jwt签发者
sub: jwt所面向的用户
aud: 接收jwt的一方
exp: jwt的过期时间，这个过期时间必须要大于签发时间
nbf: 定义在什么时间之前，该jwt都是不可用的.
iat: jwt的签发时间
jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
```



- 公共的声明

​        公共的声明可以添加任何的信息，一般添加用户的相关信息或其他业务需要的必要信息.但不建议添加敏感信息，因为该部分在客户端可解密。



- 私有的声明

　    私有声明是提供者和消费者所共同定义的声明，一般不建议存放敏感信息，因为base64是对称解密的，意味着该部分信息可以归类为明文信息。

　    这个指的就是自定义的claim。比如下面那个举例中的name都属于自定的claim。这些claim跟JWT标准规定的claim区别在于：JWT规定的claim，JWT的接收方在拿到JWT之后，都知道怎么对这些标准的claim进行验证(还不知道是否能够验证)；而private claims不会验证，除非明确告诉接收方要对这些claim进行验证以及规则才行。

```json
{
  "sub": "1234567890",
  "name": "John Doe",
  "iat": 1516239022
}
```

其中`sub`是标准的声明，`name`是自定义的声明（公共的或私有的）

然后将其进行base64编码，得到Jwt的第二部分：

```
eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkphbWVzIiwiYWRtaW4iOnRydWV9  
```

提示：声明中不要放一些敏感信息。



##### 签证、签名（signature）

jwt的第三部分是一个签证信息，这个签证信息由三部分组成：

1. header (base64后的)
2. payload (base64后的)
3. secret（盐，一定要保密）

​	这个部分需要base64加密后的header和base64加密后的payload使用.连接组成的字符串，然后通过header中声明的加密方式进行加盐secret组合加密，然后就构成了jwt的第三部分：

```
8HI-Lod0ncfVDnbKIPJJqLH998duF9DSDGkx3gRPNVI
```

将这三部分用.连接成一个完整的字符串,构成了最终的jwt:

```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.8HI-Lod0ncfVDnbKIPJJqLH998duF9DSDGkx3gRPNVI  
```



注意：`secret`是保存在服务器端的，`jwt`的签发生成也是在服务器端的，`secret`就是用来进行`jwt`的签发和`jwt`的验证，所以，它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个`secret`, 那就意味着客户端是可以自我签发`jwt`了。



### JJWT简介

#### 什么是JJWT

　	JJWT是一个提供端到端的JWT创建和验证的Java库。永远免费和开源(Apache License，版本2.0)，JJW很容易使用和理解。它被设计成一个以建筑为中心的流畅界面，隐藏了它的大部分复杂性。

规范官网：<https://jwt.io/>



#### 快速入门

创建SpringBoot工程，引入依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.xxxx</groupId>
    <artifactId>jjwtdemo</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>jjwtdemo</name>
    <description>jjwtdemo</description>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.2.2.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <properties>
        <java.version>1.8</java.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <spring-boot.version>2.3.7.RELEASE</spring-boot.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!--JWT依赖-->
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt</artifactId>
            <version>0.9.0</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
    </dependencies>

</project>
```



测试

```java
@SpringBootTest
class JjwtdemoApplicationTests {

    @Test
    void testCreateToken() {
        //创建一个JwtBuilder对象
        JwtBuilder jwtBuilder = Jwts.builder()
                //声明的标识{"jti":"888"}
                .setId("888")
                //主体，用户{"sub":"Rose"}
                .setSubject("Rose")
                //创建日期{"ita":"xxxxxx"}
                .setIssuedAt(new Date())
                //签名手段，参数1：算法，参数2：盐
                .signWith(SignatureAlgorithm.HS256,"xxxx");
        //获取jwt的token
        String token = jwtBuilder.compact();
        System.out.println(token);

        //三部分的base64解密
        System.out.println("--------------------");
        String[] split = token.split("\\.");
        System.out.println(Base64Codec.BASE64.decodeToString(split[0]));
        System.out.println(Base64Codec.BASE64.decodeToString(split[1]));
        //无法解密，盐是永远无法解密的
        System.out.println(Base64Codec.BASE64.decodeToString(split[2]));

    }

}
```



控制台打印

运行结果如下

再次运行，会发现每次运行的结果是不一样的，因为我们的载荷中包含了时间

```
eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiJSb3NlIiwiaWF0IjoxNjY3NzU0Mzk4fQ.cBP4QZPZUaNcvva-kNrZIEHFONN5GJYF-bHN81b5Cfw
--------------------
{"alg":"HS256"}
{"jti":"888","sub":"Rose","iat":1667754398
p�A��Q�\���6�HqN4�F%�[�5o�� 
2022-11-07 01:06:39.049  INFO 2688 --- [extShutdownHook]

```



##### token的验证解析

​	我们刚才已经创建了token ，在web应用中这个操作是由服务端进行然后发给客户端，客户端在下次向服务端发送请求时需要携带这个token（这就好像是拿着一张门票一样），那服务端接到这个token 应该解析出token中的信息（例如用户id）,根据这些信息查询数据库返回相应的结果。



```java
@Test
public void testParseToken(){
    //token
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiJSb3NlIiwiaWF0IjoxNjY3NzU0Mzk4fQ.cBP4QZPZUaNcvva-kNrZIEHFONN5GJYF-bHN81b5Cfw";
    //解析token获取负载中的声明对象
    Claims claims = Jwts.parser()
            .setSigningKey("xxxx") // 密钥
            .parseClaimsJws(token)
            .getBody();
    //打印声明的属性
    System.out.println("id:"+claims.getId());
    System.out.println("subject:"+claims.getSubject());
    System.out.println("issuedAt:"+claims.getIssuedAt());
}
```

控制台打印

```
id:888
subject:Rose
issuedAt:Mon Nov 07 01:06:38 CST 2022
2022-11-07 01:09:45.661  INFO 280 --- [extShutdownHook]

```



试着将token或签名秘钥篡改一下，会发现运行时就会报错，所以解析token也就是验证token



##### token过期校验

​	有很多时候，我们并不希望签发的token是永久生效的（上节的token是永久的），所以我们可以为token添加一个过期时间。原因：从服务器发出的token，服务器自己并不做记录，就存在一个弊端就是，服务端无法主动控制某token的立刻失效。

测试用例：

```java
@Test
public void testCreatTokenHasExp() {
   //当前系统时间的长整型
   long now = System.currentTimeMillis();
   //过期时间，这里是1分钟后的时间长整型
   long exp = now + 60 * 1000;
   //创建一个JwtBuilder对象
   JwtBuilder jwtBuilder = Jwts.builder()
         //声明的标识{"jti":"888"}
         .setId("888")
         //主体，用户{"sub":"Rose"}
         .setSubject("Rose")
         //创建日期{"ita":"xxxxxx"}
         .setIssuedAt(new Date())
         //签名手段，参数1：算法，参数2：盐
         .signWith(SignatureAlgorithm.HS256, "xxxx")
         //设置过期时间
         .setExpiration(new Date(exp));
   //获取jwt的token
   String token = jwtBuilder.compact();
   System.out.println(token);
}


@Test
public void testParseTokenHasExp() {
   //token
   String token = "eyJhbGciOiJIUzI1NiJ9" +
         ".eyJqdGkiOiI4ODgiLCJzdWIiOiJSb3NlIiwiaWF0IjoxNTc4ODE1MDYyLCJleHAiOjE1Nzg4MTUxMjIsInJvbGVzIjoiYWRtaW4iLCJsb2dvIjoic2hzeHQuanBnIn0.hKog0RsZ9_6II_R8kUCp0HLAouUAYXAJVbz3xtLTUh4";
   //解析token获取负载中的声明对象
   Claims claims = Jwts.parser()
         .setSigningKey("xxxx")
         .parseClaimsJws(token)
         .getBody();
   //打印声明的属性
   System.out.println("id:" + claims.getId());
   System.out.println("subject:" + claims.getSubject());
   System.out.println("issuedAt:" + claims.getIssuedAt());
   DateFormat sf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   System.out.println("签发时间:"+sf.format(claims.getIssuedAt()));
   System.out.println("过期时间:"+sf.format(claims.getExpiration()));
   System.out.println("当前时间:"+sf.format(new Date()));
	}
}
```

测试：当未过期时可以正常读取，当过期时会引发io.jsonwebtoken.ExpiredJwtException异常。

控制台打印

```
id:888
subject:Rose
issuedAt:Mon Nov 07 01:14:46 CST 2022
签发时间:2022-11-07 01:14:46
过期时间:2022-11-07 01:15:46
当前时间:2022-11-07 01:15:13
```



##### 自定义claims

我们刚才的例子只是存储了id和subject两个信息，如果你想存储更多的信息（例如角色）可以定义自定义claims

测试用例：

```java
@Test
public void testCreatTokenByClaims() {
    //当前系统时间的长整型
    long now = System.currentTimeMillis();
    //过期时间，这里是1分钟后的时间长整型
    long exp = now + 60 * 1000;
    //创建一个JwtBuilder对象
    JwtBuilder jwtBuilder = Jwts.builder()
            //声明的标识{"jti":"888"}
            .setId("888")
            //主体，用户{"sub":"Rose"}
            .setSubject("Rose")
            //创建日期{"ita":"xxxxxx"}
            .setIssuedAt(new Date())
            //签名手段，参数1：算法，参数2：盐
            .signWith(SignatureAlgorithm.HS256, "xxxx")
            //设置过期时间
            .setExpiration(new Date(exp))
            //直接传入map
            // .addClaims(map)
            .claim("roles","admin")
            .claim("logo","shsxt.jpg");
    //获取jwt的token
    String token = jwtBuilder.compact();
    System.out.println(token);
}


@Test
public void testParseTokenByClaims() {
    //token
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiJSb3NlIiwiaWF0IjoxNjY3NzU0OTgyLCJleHAiOjE2Njc3NTUwNDIsInJvbGVzIjoiYWRtaW4iLCJsb2dvIjoic2hzeHQuanBnIn0.mZbREbhGtpCfE0nimY6zC8yDWbY5Xo2hqZ8N1Q0np-U";
    //解析token获取负载中的声明对象
    Claims claims = Jwts.parser()
            .setSigningKey("xxxx")
            .parseClaimsJws(token)
            .getBody();
    //打印声明的属性
    System.out.println("id:" + claims.getId());
    System.out.println("subject:" + claims.getSubject());
    System.out.println("issuedAt:" + claims.getIssuedAt());
    DateFormat sf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    System.out.println("签发时间:"+sf.format(claims.getIssuedAt()));
    System.out.println("过期时间:"+sf.format(claims.getExpiration()));
    System.out.println("当前时间:"+sf.format(new Date()));

    System.out.println("roles:"+claims.get("roles"));
    System.out.println("logo:"+claims.get("logo"));
}
```

控制台打印

```
id:888
subject:Rose
issuedAt:Mon Nov 07 01:16:22 CST 2022
签发时间:2022-11-07 01:16:22
过期时间:2022-11-07 01:17:22
当前时间:2022-11-07 01:16:45
roles:admin
logo:shsxt.jpg
```



## Spring Security Oauth2 整合JWT

### 整合JWT

我们拿之前Spring Security Oauth2的完整代码进行修改,去掉redis配置和依赖，同时去掉redis在之前的配置，不使用redis存储token

#### 添加配置文件JwtTokenStoreConfig.java

```java
/**
 * 使用Jwt存储token的配置
 * @author ferryup
 * @since 1.0.0
 */
@Configuration
public class JwtTokenStoreConfig {

   @Bean
   public TokenStore jwtTokenStore(){
      return new JwtTokenStore(jwtAccessTokenConverter());
   }

   @Bean
   public JwtAccessTokenConverter jwtAccessTokenConverter(){
      JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
      //配置JWT使用的秘钥
      accessTokenConverter.setSigningKey("test_key");
      return accessTokenConverter;
   }
}
```



#### 在授权服务器配置中指定令牌的存储策略为JWT

```java
/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-06-23:24
 * Description: 授权服务器
 */
@Configuration
@EnableAuthorizationServer // 标注为授权服务器
@AllArgsConstructor
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    private final UserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;

    //private final TokenStore redisTokenStore;
    private final TokenStore jwtTokenStore;

    private final AccessTokenConverter jwtAccessTokenConverter;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //内存存储,正常情况下是从授权服务器数据库当中查询出来的
        clients.inMemory()
                // 配置client-id
                .withClient("admin")
                //配置secret
                .secret(passwordEncoder.encode("123456"))
                // 配置访问的token时间有效期
                .accessTokenValiditySeconds(3600)
                // 配置redirect_uri用于授权成功之后跳转的url
                .redirectUris("http://www.baidu.com")
                // 授权范围
                .scopes("all")
                // 配置grant_type 表示授权类型
                .authorizedGrantTypes("authorization_code","password");
    }

    /**
     * 使用密码模式所需配置
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                //// 把token设置为redis存储
                //.tokenStore(redisTokenStore);
                // 配置存储策略
                .tokenStore(jwtTokenStore)
                .accessTokenConverter(jwtAccessTokenConverter);
    }
}
```



postman测试

![1667756153571](图片/1667756153571.png)



发现获取到的令牌已经变成了JWT令牌，将access_token拿到https://jwt.io/ 网站上去解析下可以获得其中内容。



### 扩展JWT中存储的内容

​	有时候我们需要扩展JWT中存储的内容，这里我们在JWT中扩展一个`key为enhance，value为enhance info`的数据。

​	继承TokenEnhancer实现一个JWT内容增强器

```java
/**
 * jwt内容增强器
 *
 * @author admin
 */
@Component
public class JwtTokenEnhancer implements TokenEnhancer {


   @Override
   public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
      Map<String,Object> info = new HashMap<>(1);
      info.put("enhance","enhance info");
      ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(info);
      return accessToken;
   }
}
```



在授权服务器配置中配置JWT的内容增强器

```java
@Configuration
@EnableAuthorizationServer // 标注为授权服务器
@AllArgsConstructor
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    private final UserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;

    //private final TokenStore redisTokenStore;
    private final TokenStore jwtTokenStore;

    private final JwtAccessTokenConverter jwtAccessTokenConverter;

    private final JwtTokenEnhancer tokenEnhancer;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //内存存储,正常情况下是从授权服务器数据库当中查询出来的
        clients.inMemory()
                // 配置client-id
                .withClient("admin")
                //配置secret
                .secret(passwordEncoder.encode("123456"))
                // 配置访问的token时间有效期
                .accessTokenValiditySeconds(3600)
                // 配置redirect_uri用于授权成功之后跳转的url
                .redirectUris("http://www.baidu.com")
                // 授权范围
                .scopes("all")
                // 配置grant_type 表示授权类型
                .authorizedGrantTypes("authorization_code","password");
    }

    /**
     * 使用密码模式所需配置
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        //内容增强器
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> list = new ArrayList<>();
        list.add(tokenEnhancer);
        // 这里为什么还要添加转换器，因为正常我们生成的都是access_token,就是短的字符串，现在我们要把它转成长的jwt
        list.add(jwtAccessTokenConverter);
        enhancerChain.setTokenEnhancers(list);

       // 端点配置
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                //// 把token设置为redis存储
                //.tokenStore(redisTokenStore);
                // 配置存储策略
                .tokenStore(jwtTokenStore)
                .accessTokenConverter(jwtAccessTokenConverter)
                .tokenEnhancer(enhancerChain);
    }
}
```

重启项目

postman测试

![1667757076398](图片/1667757076398.png)





将token拿到https://jwt.io/ 去解析，发现里面包含了我们的增强的信息

![1667757204656](图片/1667757204656.png)



### Java中解析JWT中的内容

添加依赖

```
<!--jwt 依赖-->
<dependency>
   <groupId>io.jsonwebtoken</groupId>
   <artifactId>jjwt</artifactId>
   <version>0.9.0</version>
</dependency>
```

修改UserController类，使用jjwt工具类来解析Authorization头中存储的JWT内容

```java
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 获取当前用户
     *
     * @param authentication 认证信息
     * @return
     */
    @RequestMapping("/getCurrentUser")
    public Object getCurrentUser(HttpServletRequest request,Authentication authentication){
        String header = request.getHeader("Authorization");
        String token = header.substring(header.indexOf("bearer") + 7);
        return Jwts.parser()
                .setSigningKey("test_key".getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
    }

}
```



启动项目

获取令牌

![1667757567770](图片/1667757567770.png)



访问解析受控制资源，拿到了jwt解析后的信息

![1667757506014](图片/1667757506014.png)





### 刷新令牌

​	在Spring Cloud Security 中使用oauth2时，如果令牌失效了，可以使用刷新令牌通过refresh_token的授权模式再次获取access_token。

​	只需修改授权服务器的配置，添加refresh_token的授权模式即可。

```java
@Override
public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    //内存存储,正常情况下是从授权服务器数据库当中查询出来的
    clients.inMemory()
            // 配置client-id
            .withClient("admin")
            //配置secret
            .secret(passwordEncoder.encode("123456"))
            // 配置访问的token时间有效期
            .accessTokenValiditySeconds(3600)
            // 配置redirect_uri用于授权成功之后跳转的url
            .redirectUris("http://www.baidu.com")
            // 授权范围
            .scopes("all")
            // 配置grant_type 表示授权类型
            .authorizedGrantTypes("authorization_code","password","refresh_token");
}
```



postman测试

获取令牌，此时也获得刷新令牌

![1667757700896](图片/1667757700896.png)



根据刷新令牌获取token

基础认证要有

![1667759297060](图片/1667759297060.png)

![1667759323820](图片/1667759323820.png)



发送请求通过刷新令牌拿到新的token

![1667759346484](图片/1667759346484.png)



## Spring Security Oauth2 整合单点登录（SSO）

单点登录我们可以拿之前的项目进行改造，把它当成服务端，此时我们需要新建一个客户端

新建client客户端项目

pom文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.xxxx</groupId>
    <artifactId>aouth2client01-demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>aouth2client01-demo</name>
    <description>aouth2client01-demo</description>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.2.2.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <properties>
        <java.version>1.8</java.version>
        <spring-cloud.version>Greenwich.SR2</spring-cloud.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-oauth2</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt</artifactId>
            <version>0.9.0</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
</project>
```



修改配置文件

```properties
server.port=8081
#防止Cookie冲突，冲突会导致登录验证不通过
server.servlet.session.cookie.name=OAUTH2-CLIENT-SESSIONID01
#授权服务器地址
oauth2-server-url: http://localhost:8080
#与授权服务器对应的配置
security.oauth2.client.client-id=admin
security.oauth2.client.client-secret=123456
security.oauth2.client.user-authorization-uri=${oauth2-server-url}/oauth/authorize
security.oauth2.client.access-token-uri=${oauth2-server-url}/oauth/token
security.oauth2.resource.jwt.key-uri=${oauth2-server-url}/oauth/token_key
```



### 在启动类上添加@EnableOAuth2Sso注解来启用单点登录功能

```java
@SpringBootApplication
@EnableOAuth2Sso // 开启单点登录功能
public class Aouth2client01DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(Aouth2client01DemoApplication.class, args);
    }

}
```



### 添加接口用于获取当前登录用户信息

```java
@RestController
@RequestMapping("/user")
public class UserController {
    
    @GetMapping("/getCurrentUser")
    public Object getCurrentUser(Authentication authentication) {
        return authentication;
    }

}
```



### 修改认证服务器配置

修改授权服务器中的AuthorizationServerConfig类，将绑定的跳转路径为

http://localhost:8081/login，并添加获取秘钥时的身份认证

```java
@Configuration
@EnableAuthorizationServer // 标注为授权服务器
@AllArgsConstructor
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    private final UserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;

    //private final TokenStore redisTokenStore;
    private final TokenStore jwtTokenStore;

    private final JwtAccessTokenConverter jwtAccessTokenConverter;

    private final JwtTokenEnhancer tokenEnhancer;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //内存存储,正常情况下是从授权服务器数据库当中查询出来的
        clients.inMemory()
                // 配置client-id
                .withClient("admin")
                //配置secret
                .secret(passwordEncoder.encode("123456"))
                // 配置访问的token时间有效期
                .accessTokenValiditySeconds(3600)
            	// 配置刷新令牌的有效期
                .refreshTokenValiditySeconds(300000)
                // 配置redirect_uri用于授权成功之后跳转的url
                //.redirectUris("http://www.baidu.com")
                .redirectUris("http://localhost:8081/login") // 单点登录
                // 授权范围
                .scopes("all")
                //配置自动授权
                .autoApprove(true)
                // 配置grant_type 表示授权类型
                .authorizedGrantTypes("authorization_code","password","refresh_token");
    }
    
     /**
     * 单点登录要实现的，是根据密钥获取身份认证
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 获取密钥需要身份认证，单点登录的时候必须配置
        security.tokenKeyAccess("isAuthenticated()");
    }
    
   .......
}
```



### 测试

启动授权服务和客户端服务；

访问客户端需要授权的接口http://localhost:8081/user/getCurrentUser

会跳转到授权服务的登录界面；

![1667761407595](图片/1667761407595.png)

授权后会跳转到原来需要权限的接口地址，展示登录用户信息；

![1667761425275](图片/1667761425275.png)