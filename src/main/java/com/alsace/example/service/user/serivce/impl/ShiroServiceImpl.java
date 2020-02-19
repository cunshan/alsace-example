package com.alsace.example.service.user.serivce.impl;

import com.alsace.example.service.user.domain.User;
import com.alsace.example.service.user.serivce.UserService;
import com.alsace.framework.common.shiro.ShiroPrincipal;
import com.alsace.framework.common.shiro.ShiroService;
import com.alsace.framework.utils.JwtUtils;
import com.alsace.framework.utils.PasswordUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class ShiroServiceImpl implements ShiroService {


  @Resource
  private UserService userService;

  @Override
  public Map<String, String> getPathDefinitionsMap() {
    Map<String, String> map = new HashMap<>();
    map.put("/**","jwt[POST,GET]");
    map.put("/user/login","anon");
    return map;
  }

  @Override
  public List<String> getPermissionList(String loginAccount) {
    return new ArrayList<>();
  }

  /**
   * 用户登录逻辑
   */
  @Override
  public ShiroPrincipal login(String loginAccount, String token) {
    User user = userService.findByUserName(loginAccount);
    if (user == null) {
      throw new AuthenticationException("登录账号不存在！");
    }
    if (!JwtUtils.verify(token, loginAccount, user.getPassword())) {
      throw new AuthenticationException("Token失效，请重新登录！");
    }
    ShiroPrincipal shiroPrincipal = new ShiroPrincipal();
    shiroPrincipal.setLoginAccount(user.getUserName());
    shiroPrincipal.setPassword(user.getPassword());
    return shiroPrincipal;
  }


}
