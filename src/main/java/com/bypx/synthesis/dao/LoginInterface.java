package com.bypx.synthesis.dao;

import com.bypx.synthesis.bean.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginInterface {
    public List<User>login(User user);

    User queryUidByUname(String account);
}
