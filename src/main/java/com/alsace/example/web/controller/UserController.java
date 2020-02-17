package com.alsace.example.web.controller;

import com.alsace.example.service.user.domain.User;
import com.alsace.example.service.user.serivce.UserService;
import com.alsace.framework.annotation.LogModify;
import com.alsace.framework.common.basic.AlsaceResponse;
import com.alsace.framework.common.basic.BaseController;
import com.alsace.framework.common.shiro.JwtToken;
import com.alsace.framework.utils.JwtUtils;
import com.alsace.framework.utils.PasswordUtils;
import javax.validation.constraints.NotNull;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

  @Autowired
  private UserService userService;

  @RequestMapping("/save")
  @ResponseBody
  public AlsaceResponse save(User user) {
    User res = userService.create(user);
    return new AlsaceResponse.Builder(true).msg("创建成功").data(res).build();
  }

  @RequestMapping("/login")
  @ResponseBody
  @LogModify(operationType = "login-jwt")
  public AlsaceResponse doLoginJwt(@NotNull(message = "用户名不能为空！") String userName,
      @NotNull(message = "密码不能为空！") String password) {
    User dbUser = userService.findByUserName(userName);
    if (dbUser == null) {
      return new AlsaceResponse.Builder(false).msg("用户名或者密码错误！").build();
    }

    if (!PasswordUtils.md5(password).equals(dbUser.getPassword())) {
      return new AlsaceResponse.Builder(false).msg("用户名或者密码错误！").build();
    }
    JwtToken token = new JwtToken(JwtUtils.sign(userName, dbUser.getPassword(), 60));
    SecurityUtils.getSubject().login(token);
    return new AlsaceResponse.Builder(true).msg("登录成功").data(token.getToken()).build();
  }



}
