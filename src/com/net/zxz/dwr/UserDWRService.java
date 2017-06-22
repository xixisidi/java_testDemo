package com.net.zxz.dwr;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.net.zxz.bean.User;
import com.net.zxz.dao.BaseUserDao;
import com.net.zxz.service.UserService;

@Service("userDWRService")
public class UserDWRService {
    @Autowired
    private UserService userService;
    private static final Log log = LogFactory.getLog(BaseUserDao.class);
    
    public String sayHello(String name) {
        System.out.println("first dwr invoke");
        return "Hello," + name;
    }

    public List<User> userList(String id) {
        log.debug("dwr 调用 userList");
        return userService.listUser();
    }
}
