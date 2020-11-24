package com.kallen.api.uc.service;

import com.kallen.api.uc.entity.UserEntity;
import com.kallen.api.uc.entity.dto.UserDTO;
import com.kallen.api.uc.entity.req.SendCodeReq;
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
    void sendCode(SendCodeReq sendCodeReq);
}
