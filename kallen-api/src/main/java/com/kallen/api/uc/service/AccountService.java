package com.kallen.api.uc.service;

import com.kallen.api.uc.entity.AccountEntity;
import com.kallen.api.uc.entity.dto.AccountDTO;
import com.kallen.common.service.CrudService;

/**
 * <p>Title: AccountService</p >
 * <p>Description: 用户账户-服务层</p >
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
public interface AccountService extends CrudService<AccountEntity, AccountDTO> {

    /**
     * <p>根据用户ID和账号类型，查询账号信息</p>
     *
     * @param id                    用户ID
     * @param accountType           账号类型
     * @return {@link AccountDTO}   账号信息
     * @author Kallen
     * @since 2020/11/26 12:48
    */
    AccountDTO queryAccountByUserIdAndType(Long id, String accountType);

    /**
     * <p>根据用户ID修改密码</p>
     *
     * @param userId        用户ID
     * @param passwordMD5   加密后的密码
     * @author Kallen
     * @since 2020/12/2 16:13
    */
    void updateUserPasswordByUserId(Long userId, String passwordMD5);

    /**
     * <p>根据手机号查询用户信息</p>
     *
     * @param mobile            手机号
     * @return {@link AccountDTO}   用户账户信息
     * @author Kallen
     * @since 2020/12/2 16:46
    */
    AccountDTO queryAccountByAccount(String mobile);
}
