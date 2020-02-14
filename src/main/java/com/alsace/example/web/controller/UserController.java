package com.alsace.example.web.controller;

import com.alsace.example.service.user.domain.User;
import com.alsace.example.service.user.serivce.UserService;
import com.alsace.framework.annotation.LogModify;
import com.alsace.framework.common.basic.AlsaceResponse;
import com.alsace.framework.common.basic.BaseController;
import com.alsace.framework.common.shiro.JwtToken;
import com.alsace.framework.utils.JwtUtils;
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
  public String save(User user) {
    userService.create(user);
    return "success";
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

    if (!password.equals(dbUser.getPassword())) {
      return new AlsaceResponse.Builder(false).msg("用户名或者密码错误！").build();
    }
    JwtToken token = new JwtToken(JwtUtils.sign(userName, password, 60));
    SecurityUtils.getSubject().login(token);
    return new AlsaceResponse.Builder(true).msg("登录成功").data(token).build();
  }

//  @RequestMapping("/do-login")
//  @ResponseBody
//  @LogModify(operationType = "login")
//  public String doLogin(@NotNull(message = "用户名不能为空！") String userName,@NotNull(message = "密码不能为空！") String password){
//    User dbUser = userService.findByUserName(userName);
//    if(dbUser == null){
//      return "fail";
//    }
//
//    if(!password.equals(dbUser.getPassword())){
//      return "fail";
//    }
//    JwtToken token = new JwtToken(JwtUtils.sign(userName,password,60));
//    SecurityUtils.getSubject().login(token);
//    return token.getToken();
//  }


}
