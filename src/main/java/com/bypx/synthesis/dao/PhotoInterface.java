package com.bypx.synthesis.dao;

import com.bypx.synthesis.bean.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoInterface {
    public List<User> queryPhoto(User user);

}
