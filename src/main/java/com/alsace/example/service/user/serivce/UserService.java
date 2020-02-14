package com.alsace.example.service.user.serivce;

import com.alsace.example.service.user.domain.User;
import com.alsace.framework.common.basic.BaseService;

public interface UserService extends BaseService<User,Long> {

  /**
   * 根据用户名获取用户信息
   */
  User findByUserName(String userName);
}
