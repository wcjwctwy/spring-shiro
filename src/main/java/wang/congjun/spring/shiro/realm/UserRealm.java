package wang.congjun.spring.shiro.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangcongjun
 * @date 2019/4/11 16:16
 */
public class UserRealm extends AuthorizingRealm {
    private static ConcurrentHashMap<String, String> users = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, String[]> userPerms = new ConcurrentHashMap<>();

    static {
        users.put("susan", "123456");
        users.put("jack", "123456");
        userPerms.put("susan", new String[]{"user:add"});
        userPerms.put("jack", new String[]{"user:edit"});
    }

    /**
     * 用户授权处理
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("用户授权处理");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        String username = (String)subject.getPrincipal();
        info.addStringPermissions(Arrays.asList(userPerms.get(username)));
        return info;
    }

    /**
     * 用户认证处理
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException (login 方法传入的token)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //数据库中的用户
        System.out.println("用户认证处理");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        if(StringUtils.isEmpty(username)){
            return null;
        }
        String password = users.get(username);
        if (password == null) {
            return null;
        }
        //这里会自动处理密码是否正确
        return new SimpleAuthenticationInfo(username, password, "");
    }
}
