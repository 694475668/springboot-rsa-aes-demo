package com.ratta.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ratta.domain.UserDO;
import org.apache.ibatis.annotations.Param;

/**
 * ******Title:<p> 程序的奥秘 </p> ******
 * ******Description:<p> </p>******
 * ******Company: <p> 深圳汇星数字技术有限公司 </p> ******
 *
 * @version :
 * @author: bright
 * @date:Created in 2020/7/9 13:46
 */
public interface UserMapper extends BaseMapper<UserDO> {

    UserDO getUser(@Param("username") String username, @Param("password") String password);
}
