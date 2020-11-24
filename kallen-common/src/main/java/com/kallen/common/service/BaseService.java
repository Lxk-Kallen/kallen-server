package com.kallen.common.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;

import java.util.Collection;

/**
 * <p>Title: BaseService</p >
 * <p>Description: 基础服务接口-所有Service接口都要继承</p >
 * <p>Copyright: Kallen. Copyright(c) 2020</p >
 * <link>http://www.buqu.icu</link>
 * <p>版权所有，侵权必究！</p >
 *
 * @author Kallen
 * @version 1.0.0
 * <pre>History:
 *       2020/11/20    Kallen    Created
 * </pre>
 * @mail LXK00515@163.com
 */
public interface BaseService<T> {

    /**
     * <p>插入一条记录（选择字段，策略插入）</p>
     *
     * @param entity            要插入的信息
     * @return {@link boolean}  true/false
     * @author Kallen
     * @since 2020/11/20 15:38
    */
    boolean insert(T entity);

    /**
     * <p>插入（批量），该方法不支持 Oracle、SQL Server</p>
     *
     * @param entityList        要插入的集合
     * @return {@link boolean}  true/false
     * @author Kallen
     * @since 2020/11/20 15:44
    */
    boolean insertBatch(Collection<T> entityList);

    /**
     * <p>插入（批量），该方法不支持 Oracle、SQL Server</p>
     *
     * @param entityList        实体对象集合
     * @param batchSize         插入批次数量
     * @return {@link boolean}  true/false
     * @author Kallen
     * @since 2020/11/20 16:01
    */
    boolean insertBatch(Collection<T> entityList, int batchSize);

    /**
     * <p>根据 ID 选择修改</p>
     *
     * @param entity            实体对象
     * @return {@link boolean}  true/false
     * @author Kallen
     * @since 2020/11/20 16:02
    */
    boolean updateById(T entity);

    /**
     * <p>根据 whereEntity 条件，更新记录</p>
     *
     * @param entity            实体对象
     * @param updateWrapper     实体对象封装操作类
     * @return {@link boolean}  true/false
     * @author Kallen
     * @since 2020/11/20 16:06
    */
    boolean update(T entity, Wrapper<T> updateWrapper);

    /**
     * <p>根据 ID 批量更新</p>
     *
     * @param entityList        实体对象集合
     * @return {@link boolean}  true/false
     * @author Kallen
     * @since 2020/11/20 16:09
    */
    boolean updateBatchById(Collection<T> entityList);

    /**
     * <p>根据 ID 批量更新</p>
     *
     * @param entityList        实体对象集合
     * @param batchSize         更新批次数量
     * @return {@link boolean}  true/false
     * @author Kallen
     * @since 2020/11/20 16:12
    */
    boolean updateBatchById(Collection<T> entityList, int batchSize);
}
