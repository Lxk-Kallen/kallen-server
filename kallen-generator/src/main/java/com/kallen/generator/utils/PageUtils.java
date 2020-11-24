package com.kallen.generator.utils;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Title: PageUtils</p >
 * <p>Description: 分页工具类</p >
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
public class PageUtils implements Serializable {

    //<editor-fold desc="属性">

    /**
     * 总记录数
     */
    private int totalCount;

    /**
     * 每页记录数
     */
    private int pageSize;

    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 当前页数
     */
    private int currPage;

    /**
     * 列表数据
     */
    private List<?> list;

    //</editor-fold>

    public PageUtils(List<?> list, int totalCount, int pageSize, int currPage) {
        this.list = list;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currPage = currPage;
        this.totalPage = (int) Math.ceil((double) totalCount/pageSize);
    }
}
