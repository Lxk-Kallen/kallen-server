package com.kallen.api.uc.dao;

import com.kallen.api.uc.entity.AccountEntity;
import com.kallen.common.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>Title: AccountDao</p >
 * <p>Description: </p >
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
@Mapper
public interface AccountDao extends BaseDao<AccountEntity> {
}
