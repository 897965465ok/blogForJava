package com.jiang.blog.service;

import java.util.Map;

public interface UserService {


    Integer register(String account, String password);

    Map userLogin(String account, String password);
}
