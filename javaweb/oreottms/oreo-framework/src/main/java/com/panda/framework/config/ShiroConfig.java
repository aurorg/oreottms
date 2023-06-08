package com.panda.framework.config;

import com.panda.framework.shiro.JwtFilter;
import com.panda.framework.shiro.realms.CustomerRealm;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 配置shiro安全框架
 * 返回一个Shiro框架中的ShiroFilterFactoryBean对象，
 * 用于配置Shiro框架的过滤器链。
 */
@Configuration
public class ShiroConfig {

    // 创建shiroFilter
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 给shiroFilter设置安全管理器
        /*
        ShiroFilterFactoryBean对象。
        使用setSecurityManager方法将默认的Web安全管理器设置为参数defaultWebSecurityManager
         */
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        // 配置受限资源
        /*
        使用LinkedHashMap对象创建了一个map，
        用于配置受限资源及其对应的访问规则。
        使用"anon"表示该资源无需认证即可访问，
        使用"jwt"表示该资源需要认证和授权才能访问。
         */
        Map<String, String> map = new LinkedHashMap<>();
        // 放行注册和登录
        map.put("/sysUser/register", "anon");
        map.put("/sysUser/login", "anon");
        // 放行图片查询
        map.put("/images/**", "anon");
        // 请求这个资源需要认证与授权
        map.put("/sysCinema/update", "jwt");
        // 放行影院查询请求
        map.put("/sysCinema/**", "anon");
        // 放行电影查找相关请求
        map.put("/sysMovie/find/**", "anon");
        // 放行电影类别查找相关请求
        map.put("/sysMovieCategory/find/**", "anon");
        // 放行电影场次查找相关请求
        map.put("/sysSession/find/**", "anon");
        // 请求这个资源需要认证与授权
        map.put("/**", "jwt");

        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new HashMap<String, Filter>(1);
        filterMap.put("jwt", new JwtFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        //设置认证规则
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    /*
    返回一个Shiro框架中的DefaultWebSecurityManager对象，
    用于配置Shiro框架的安全管理器。

首先创建了一个DefaultWebSecurityManager对象。
使用setRealm方法将参数realm设置为该安全管理器的Realm。

接着，使用DefaultSubjectDAO对象和DefaultSessionStorageEvaluator对象关闭了Shiro框架自带的session存储机制，
使得Shiro框架不再保存登录状态，而是每次使用token进行验证。具体的步骤是：

创建一个DefaultSubjectDAO对象。
创建一个DefaultSessionStorageEvaluator对象，
并使用setSessionStorageEnabled(false)方法关闭session存储。
使用setSessionStorageEvaluator方法将DefaultSessionStorageEvaluator对象设置到DefaultSubjectDAO对象中。
使用setSubjectDAO方法将DefaultSubjectDAO对象设置到DefaultWebSecurityManager对象中。
最后，返回配置好的DefaultWebSecurityManager对象。
     */

    //创建安全管理器
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(Realm realm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();

        //给安全管理器设置realm
        defaultWebSecurityManager.setRealm(realm);

        //关闭shiro自带的session，使得不保存登录状态，每次使用token进行验证
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        defaultWebSecurityManager.setSubjectDAO(subjectDAO);

        return defaultWebSecurityManager;
    }

    //创建自定义realm
    @Bean(name = "realm")
    public Realm getRealm() {
        CustomerRealm realm = new CustomerRealm();
        return realm;
    }

}
