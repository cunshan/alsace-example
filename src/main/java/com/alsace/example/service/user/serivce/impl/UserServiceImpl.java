package com.alsace.example.service.user.serivce.impl;

import com.alsace.example.service.user.domain.User;
import com.alsace.example.service.user.mapper.UserMapper;
import com.alsace.framework.annotation.AutoFill;
import com.alsace.framework.annotation.LogModify;
import com.alsace.framework.annotation.PageQuery;
import com.alsace.example.service.user.repository.UserRepository;
import com.alsace.example.service.user.serivce.UserService;
import com.github.pagehelper.Page;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

  @Resource
  private UserMapper userMapper;

  @Resource
  private UserRepository userRepository;

  @Override
  @PageQuery
  public Page<User> queryPage(User param) {
    Page<User> page = userMapper.queryPage(param);
    return page;
  }

  @Override
  @LogModify(operationType = "")
  @AutoFill
  public User save(User domain) {
    return userRepository.save(domain);
  }

  @Override
  public List<User> saveBatch(List<User> list) {
    return (List<User>) userRepository.saveAll(list);
  }

  @Override
  public User update(User user) {
    return userRepository.save(user);
  }

  @Override
  public List<User> updateBatch(List<User> list) {
    return (List<User>) userRepository.saveAll(list);
  }

  @Override
  public User findById(Long id) {
    return userRepository.findById(id).orElse(null);
  }

  @Override
  public void deleteById(Long id) {
    userRepository.deleteById(id);
  }

  @Override
  public User findByUserName(String userName) {
    return userRepository.findByUserName(userName);
  }
}
