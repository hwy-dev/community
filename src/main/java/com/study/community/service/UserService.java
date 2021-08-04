package com.study.community.service;

import com.study.community.mapper.UserMapper;
import com.study.community.model.User;
import com.study.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        UserExample userExample=new UserExample();
        UserExample.Criteria cri = userExample.createCriteria();
        cri.andAccountIdEqualTo(user.getAccountId());

        List<User> dbUsers = userMapper.selectByExample(userExample);
        if (dbUsers.size()==0){
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }
        else
        {
            User dbUser=dbUsers.get(0);
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            UserExample example=new UserExample();
            userExample.equals(dbUser.getId());
            userMapper.updateByExampleSelective(dbUser,example);

        }
    }
}
