package com.system.config.shiro;

import com.system.bean.Admin;
import com.system.bean.Permission;
import com.system.common.util.DESEncryptUtil;
import com.system.common.util.StrUtil;
import com.system.service.AdminService;
import com.system.service.PermissionService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author zzy
 * @Date 2018/7/20 上午11:45
 */
public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    private AdminService adminService;
    @Autowired
    private PermissionService permissionService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Admin admin = (Admin) principals.getPrimaryPrincipal();

        List<Permission> permissions = permissionService.getRoleFunction(admin.getAdminType());


        for (Permission permission : permissions) {
            authorizationInfo.addRole(admin.getAdminType().toString());
            authorizationInfo.addStringPermission(permission.getVisitUrl());
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken authToken = (UsernamePasswordToken)token;

        //验证用户名
        String username = authToken.getUsername();
        String password = String.valueOf(authToken.getPassword());

        if (StrUtil.isEmpty(username) || StrUtil.isEmpty(password)) {
            throw new AuthenticationException("用户名和密码不可以为空");
        }
        String decryptPwd = null;
        try {
            decryptPwd = DESEncryptUtil.desEncrypt(password,username+"admin");
        } catch (Exception e) {
            throw new AuthenticationException("密码加密有误!");
        }

        Admin admin = adminService.login(username, StrUtil.getMD5(decryptPwd));

        if( null != admin ){
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                    admin,
                    password,
                    getName()
            );
            return authenticationInfo;
        }

        throw new AuthenticationException("密码错误!");
    }

}
