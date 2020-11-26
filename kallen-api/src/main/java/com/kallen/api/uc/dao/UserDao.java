package com.kallen.api.uc.dao;

import com.kallen.api.uc.entity.UserEntity;
import com.kallen.api.uc.entity.dto.UserDTO;
import com.kallen.common.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>Title: UserDao</p >
 * <p>Description: 用户Dao</p >
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
public interface UserDao extends BaseDao<UserEntity> {

    /**
     * <p>通过手机号查询用户信息</p>
     *
     * @param account            手机号
     * @param type              类型
     * @return {@link UserDTO}  用户信息
     * @author Kallen
     * @since 2020/11/24 18:26
    */
    UserDTO getByAccount(@Param("account") String account, @Param("type") String type);
}
