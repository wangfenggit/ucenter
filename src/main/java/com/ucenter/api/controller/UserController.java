package com.ucenter.api.controller;

import com.ucenter.api.config.ConfigBean;
import com.ucenter.api.dao.UserRepository;
import com.ucenter.api.entity.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by wangfeng on 2018/6/28.
 * (exclude = DataSourceAutoConfiguration.class)
 */
@RestController
@SpringBootApplication
@RequestMapping("/user")
public class UserController {

    @Autowired
    ConfigBean configBean;

    @Autowired
    private UserRepository userRepository;

    /**
     * 用户列表
     *
     * @return
     */
    @RequestMapping("/list")
    public List<User> listUser() {
        return userRepository.getUserList();
    }


    /**
     * 根据id查询User实体
     *
     * @param id
     * @return
     */
    @RequestMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        return userRepository.getUserById(id);
    }


}                                                     
                                                            