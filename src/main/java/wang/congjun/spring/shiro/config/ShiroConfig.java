package wang.congjun.spring.shiro.config;

import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wang.congjun.spring.shiro.realm.UserRealm;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author wangcongjun
 * @date 2019/4/11 16:15
 */
@Configuration
public class ShiroConfig {
    /**
     * 创建ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        Map<String,String> filterMap = new LinkedHashMap<>();
        filterMap.put("/","anon");
        filterMap.put("/index","anon");
        filterMap.put("/**login","anon");
        filterMap.put("/**test","anon");
        filterMap.put("/user/add","perms[user:add]");
        filterMap.put("/user/update","perms[user:edit]");
        filterMap.put("/**","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        return shiroFilterFactoryBean;
    }

    /**
     * 创建securityManager
     * @param userRealm
     * @return
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();

        defaultSecurityManager.setRealm(userRealm);
        return defaultSecurityManager;
    }

    /**
     * 创建userRealm
     * @return
     */
    @Bean(name = "userRealm")
    public UserRealm getUserRealm(){
        return new UserRealm();
    }
}
