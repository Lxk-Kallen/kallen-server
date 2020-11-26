package com.kallen.api.uc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.kallen.api.uc.dao.AccountDao;
import com.kallen.api.uc.entity.AccountEntity;
import com.kallen.api.uc.entity.UserEntity;
import com.kallen.api.uc.entity.dto.AccountDTO;
import com.kallen.api.uc.service.AccountService;
import com.kallen.common.service.impl.CrudServiceImpl;
import com.kallen.common.utils.ConvertUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>Title: AccountServiceImpl</p >
 * <p>Description: 用户账户-服务层实现类</p >
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
@Service
public class AccountServiceImpl extends CrudServiceImpl<AccountDao, AccountEntity, AccountDTO> implements AccountService {

    @Override
    protected QueryWrapper<AccountEntity> getWrapper(Map<String, Object> params) {
        String id = (String) params.get("id");

        QueryWrapper<AccountEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);
        return wrapper;
    }

    /**
     * <p>根据用户ID和账号类型，查询账号信息</p>
     *
     * @param userId                用户ID
     * @param accountType           账号类型
     * @return {@link AccountDTO}   账号信息
     * @author Kallen
     * @since 2020/11/26 12:48
     */
    @Override
    public AccountDTO queryAccountByUserIdAndType(Long userId, String accountType) {
        return queryAccount(new LambdaQueryWrapper<AccountEntity>()
                .eq(AccountEntity::getUserId, userId)
                .eq(AccountEntity::getType, accountType));
    }

    //<editor-fold desc="私有方法">

    /**
     * <p>查询账户信息</p>
     *
     * @param lambdaQueryWrapper    lambada条件构造器
     * @return {@link AccountDTO}   账户信息
     * @author Kallen
     * @since 2020/11/26 12:52
    */
    private AccountDTO queryAccount(LambdaQueryWrapper<AccountEntity> lambdaQueryWrapper) {
        AccountEntity accountEntity = baseDao.selectOne(lambdaQueryWrapper);
        return accountEntity == null ? null : ConvertUtils.sourceToTarget(accountEntity, AccountDTO.class);
    }

    //</editor-fold>
}
