package com.kallen.generator.utils;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>Title: Query</p >
 * <p>Description: 查询参数生成</p >
 * <p>Copyright: Kallen. Copyright(c) 2020</p >
 * <link>http://www.buqu.icu</link>
 * <p>版权所有，侵权必究！</p >
 *
 * @author Kallen
 * @version 1.0.0
 * <pre>History:
 *       2020/11/23    Kallen    Created
 * </pre>
 * @mail LXK00515@163.com
 */
@Data
public class Query extends LinkedHashMap<String, Object> {

    /**
     * 当前页码
     */
    private int page;

    /**
     * 每页条数
     */
    private int limit;

    public Query(Map<String, Object> params) {
        this.putAll(params);

        //分页参数
        this.page = Integer.parseInt(params.get("page").toString());
        this.limit = Integer.parseInt(params.get("limit").toString());
        this.put("offset", (page - 1) * limit);
        this.put("page", page);
        this.put("limit", limit);
    }
}
