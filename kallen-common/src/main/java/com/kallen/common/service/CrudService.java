package com.kallen.common.service;

import com.kallen.common.page.PageDate;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: CrudService</p >
 * <p>Description: Crud基础服务类</p >
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
public interface CrudService<T, D> extends BaseService<T> {

    PageDate<D> page(Map<String, Object> params);

    List<D> list(Map<String, Object> params);

    D get(Long id);

    void save(D dto);

    void update(D dto);

    void delete(Long[] ids);
}
