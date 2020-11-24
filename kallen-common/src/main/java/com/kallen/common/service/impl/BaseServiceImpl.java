package com.kallen.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.kallen.common.constant.Constant;
import com.kallen.common.exception.KallenException;
import com.kallen.common.page.PageDate;
import com.kallen.common.service.BaseService;
import com.kallen.common.utils.ConvertUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: BaseServiceImpl</p >
 * <p>Description: </p >
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
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T> implements BaseService<T> {

    @Autowired
    protected M baseDao;

    /**
     * <p>插入一条记录（选择字段，策略插入）</p>
     *
     * @param entity            实体对象
     * @return {@link boolean}  是否成功
     * @author Kallen
     * @since 2020/11/20 15:38
     */
    @Override
    public boolean insert(T entity) {
        return BaseServiceImpl.retBool(baseDao.insert(entity));
    }

    /**
     * <p>插入（批量），该方法不支持 Oracle、SQL Server</p>
     *
     * @param entityList        实体对象集合
     * @return {@link boolean}  true/false
     * @author Kallen
     * @since 2020/11/20 15:44
     */
    @Override
    @Transactional(rollbackFor = KallenException.class)
    public boolean insertBatch(Collection<T> entityList) {
        return insertBatch(entityList, 100);
    }

    /**
     * <p>判断数据库是否操作成功</p>
     *
     * @param result            数据库操作返回影响条数
     * @return {@link boolean}  true/false
     * @author Kallen
     * @since 2020/11/20 15:42
    */
    protected static boolean retBool(Integer result) {
        return SqlHelper.retBool(result);
    }

    /**
     * <p>插入（批量）</p>
     *
     * @param entityList        实体对象集合
     * @param batchSize         插入批次数量
     * @return {@link boolean}  true/false
     * @author Kallen
     * @since 2020/11/20 15:59
    */
    @Override
    @Transactional(rollbackFor = KallenException.class)
    public boolean insertBatch(Collection<T> entityList, int batchSize) {
        SqlSession batchSqlSession = sqlSessionBatch();
        int i = 0;
        String sqlStatement = sqlStatement(SqlMethod.INSERT_ONE);
        try {
            for (T anEntityList : entityList) {
                batchSqlSession.insert(sqlStatement, anEntityList);
                if (i >= 1 && i % batchSize == 0) {
                    batchSqlSession.flushStatements();
                }
                i++;
            }
            batchSqlSession.flushStatements();
        } finally {
            closeSqlSession(batchSqlSession);
        }
        return true;
    }

    /**
     * <p>根据 ID 选择修改</p>
     *
     * @param entity            实体对象
     * @return {@link boolean}  true/false
     * @author Kallen
     * @since 2020/11/20 16:02
     */
    @Override
    @Transactional(rollbackFor = KallenException.class)
    public boolean updateById(T entity) {
        return BaseServiceImpl.retBool(baseDao.updateById(entity));
    }

    /**
     * <p>根据 whereEntity 条件，更新记录</p>
     *
     * @param entity            实体对象
     * @param updateWrapper     实体对象封装操作类
     * @return {@link boolean}  true/false
     * @author Kallen
     * @since 2020/11/20 16:06
     */
    @Override
    @Transactional(rollbackFor = KallenException.class)
    public boolean update(T entity, Wrapper<T> updateWrapper) {
        return BaseServiceImpl.retBool(baseDao.update(entity, updateWrapper));
    }

    /**
     * <p>根据 ID 批量更新</p>
     *
     * @param entityList        实体对象集合
     * @return {@link boolean}  true/false
     * @author Kallen
     * @since 2020/11/20 16:09
     */
    @Override
    @Transactional(rollbackFor = KallenException.class)
    public boolean updateBatchById(Collection<T> entityList) {
        return updateBatchById(entityList, 30);
    }

    /**
     * <p>根据 ID 批量更新</p>
     *
     * @param entityList        实体对象集合
     * @param batchSize         更新批次数量
     * @return {@link boolean}  true/false
     * @author Kallen
     * @since 2020/11/20 16:12
     */
    @Override
    @Transactional(rollbackFor = KallenException.class)
    public boolean updateBatchById(Collection<T> entityList, int batchSize) {
        if (CollectionUtils.isEmpty(entityList)) {
            throw new IllegalArgumentException("实体对象集合不能为空");
        }
        SqlSession batchSqlSession = sqlSessionBatch();
        int i = 0;
        String sqlStatement = sqlStatement(SqlMethod.UPDATE_BY_ID);
        try {
            for (T anEntityList : entityList) {
                MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
                param.put(Constant.ENTITY, anEntityList);
                batchSqlSession.update(sqlStatement, param);
                if (i >= 1 && i % batchSize == 0) {
                    batchSqlSession.flushStatements();
                }
                i++;
            }
            batchSqlSession.flushStatements();
        } finally {
            closeSqlSession(batchSqlSession);
        }
        return true;
    }

    /**
     * <p>根据ID查询</p>
     *
     * @param id            主键ID
     * @return {@link T}    查询结果
     * @author Kallen
     * @since 2020/11/24 11:12
    */
    @Override
    public T selectById(Serializable id) {
        return baseDao.selectById(id);
    }

    /**
     * <p>根据 ID 删除</p>
     *
     * @param id                主键ID
     * @return {@link boolean}  true/false
     * @author Kallen
     * @since 2020/11/24 11:14
     */
    @Override
    @Transactional(rollbackFor = KallenException.class)
    public boolean deleteById(Serializable id) {
        return SqlHelper.retBool(baseDao.deleteById(id));
    }

    @Override
    @Transactional(rollbackFor = KallenException.class)
    public boolean deleteBatchIds(Collection<? extends Serializable> idList) {
        return SqlHelper.retBool(baseDao.deleteBatchIds(idList));
    }

    /**
     * <p>批量操作 SQLSession</p>
     *
     * @return {@link SqlSession}
     * @author Kallen
     * @since 2020/11/20 15:49
    */
    protected SqlSession sqlSessionBatch() {
     return SqlHelper.sqlSessionBatch(currentModelClass());
    }

    /**
     * <p>获取泛型对象</p>
     *
     * @return {@link Class<T>}
     * @author Kallen
     * @since 2020/11/20 15:52
    */
    protected Class<T> currentModelClass() {
        return (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(), 1);
    }

    /**
     * <p>获取 SQLStatement</p>
     *
     * @param sqlMethod
     * @return {@link String}
     * @throws
     * @author Kallen
     * @since 2020/11/20 15:54
     */
    protected String sqlStatement(SqlMethod sqlMethod) {
        return SqlHelper.table(currentModelClass()).getSqlStatement(sqlMethod.getMethod());
    }

    /**
     * <p>释放 SQLSession</p>
     *
     * @param sqlSession
     * @author Kallen
     * @since 2020/11/20 15:57
    */
    protected void closeSqlSession(SqlSession sqlSession) {
        SqlSessionUtils.closeSqlSession(sqlSession, GlobalConfigUtils.currentSessionFactory(currentModelClass()));
    }

    /**
     * <p>获取分页对象</p>
     *
     * @param params    分页查询参数
     * @param defaultOrderField
     * @param isAsc
     * @author Kallen
     * @since 2020/11/24 11:26
     */
    protected IPage<T> getPage(Map<String, Object> params, String defaultOrderField, boolean isAsc) {

        // 分页参数
        long curPage = 1;
        long limit = 10;

        if (params.get(Constant.PAGE) != null) {
            curPage = Long.parseLong((String) params.get(Constant.PAGE));
        }

        if (params.get(Constant.LIMIT) != null) {
            limit = Long.parseLong((String) params.get(Constant.LIMIT));
        }

        // 分页对象
        Page<T> page = new Page<>(curPage, limit);

        // 分页参数
        params.put(Constant.PAGE, page);

        // 排序字段
        String orderField = (String) params.get(Constant.ORDER_FIELD);
        String order = (String) params.get(Constant.ORDER);

        // 前端字段排序
        if (StringUtils.isNotBlank(orderField) && StringUtils.isNotBlank(order)) {
            if (Constant.ASC.equalsIgnoreCase(order)) {
                return page.addOrder(OrderItem.asc(orderField));
            }else {
                return page.addOrder(OrderItem.desc(orderField));
            }
        }

        // 没有排序子弹，则不排序
        if (StringUtils.isBlank(defaultOrderField)) {
            return page;
        }

        // 默认排序
        if (isAsc) {
            page.addOrder(OrderItem.asc(defaultOrderField));
        }else {
            page.addOrder(OrderItem.desc(defaultOrderField));
        }

        return page;
    }

    protected <T> PageDate<T> getPageDate(List<?> list, long total, Class<T> target) {
        List<T> targetList = ConvertUtils.sourceToTarget(list, target);

        return new PageDate<>(targetList, total);
    }

    protected <T> PageDate<T> getPageDate(IPage page, Class<T> target) {
        return getPageDate(page.getRecords(), page.getTotal(), target);
    }
}
