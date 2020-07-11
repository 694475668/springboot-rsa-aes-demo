package com.ratta.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ******Title:<p> 程序的奥秘 </p> ******
 * ******Description:<p> </p>******
 * ******Company: <p> 深圳汇星数字技术有限公司 </p> ******
 *
 * @version :
 * @author: bright
 * @date:Created in 2020/7/9 13:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;

    private String password;

}
