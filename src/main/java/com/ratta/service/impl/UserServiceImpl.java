package com.ratta.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ratta.domain.UserDO;
import com.ratta.mapper.UserMapper;
import com.ratta.service.UserService;
import com.ratta.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ******Title:<p> 程序的奥秘 </p> ******
 * ******Description:<p> </p>******
 * ******Company: <p> 深圳汇星数字技术有限公司 </p> ******
 *
 * @version :
 * @author: bright
 * @date:Created in 2020/7/9 14:00
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserVO getUser(String username, String password) {
        UserVO userVO = new UserVO();
        UserDO userDO = userMapper.getUser(username, password);
        BeanUtils.copyProperties(userDO, userVO);
        List<String> list = new ArrayList<>();
        list.add("中文1");
        list.add("张三2");
        list.add("李四3");
        list.add("中文");
        userVO.setVolist(list);
        return userVO;
    }
}
