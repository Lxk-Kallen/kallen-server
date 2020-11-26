package com.kallen.api.uc.service;

import com.kallen.api.uc.entity.UserEntity;
import com.kallen.api.uc.entity.dto.UserDTO;
import com.kallen.api.uc.entity.req.CodeLoginReq;
import com.kallen.api.uc.entity.req.SendCodeReq;
import com.kallen.api.uc.entity.vo.LoginSuccessVO;
import com.kallen.common.service.CrudService;

/**
 * <p>Title: UserService</p >
 * <p>Description: 用户-服务层</p >
 * <p>Copyright: Kallen. Copyright(c) 2020</p >
 * <link>http://www.buqu.icu</link>
 * <p>版权所有，侵权必究！</p >
 *
 * @author Kallen
 * @version 1.0.0
 * <pre>History:
 *       2020/11/24    Kallen    Created
 * </pre>
 * @mail LXK00515@163.com
 */
public interface UserService extends CrudService<UserEntity, UserDTO> {

    /**
     * <p>发送手机验证码</p>
     *
     * @param sendCodeReq     入参实体
     * @author Kallen
     * @since 2020/11/24 14:15
    */
    String sendCode(SendCodeReq sendCodeReq);

    /**
     * <p>验证码登录</p>
     *
     * @param codeLoginReq              入参实体
     * @return {@link LoginSuccessVO}   用户基本信息，包含token信息
     * @author Kallen
     * @since 2020/11/24 17:38
    */
    LoginSuccessVO codeLogin(CodeLoginReq codeLoginReq);

    /**
     * <p>通过userId查询用户信息</p>
     *
     * @param userId            用户ID
     * @return {@link UserDTO}  用户信息
     * @author Kallen
     * @since 2020/11/26 15:32
    */
    UserDTO getById(Long userId);
}
