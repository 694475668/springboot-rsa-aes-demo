package com.ratta.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ratta.domain.UserDO;
import com.ratta.vo.UserVO;

/**
 * ******Title:<p> 程序的奥秘 </p> ******
 * ******Description:<p> </p>******
 * ******Company: <p> 深圳汇星数字技术有限公司 </p> ******
 *
 * @version :
 * @author: bright
 * @date:Created in 2020/7/9 14:00
 */
public interface UserService extends IService<UserDO> {

    UserVO getUser(String username, String password);

}
