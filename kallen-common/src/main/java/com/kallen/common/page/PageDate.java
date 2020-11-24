package com.kallen.common.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Title: PageDate</p >
 * <p>Description: 分页工具类</p >
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
@Data
@ApiModel(value = "分页数据")
public class PageDate<T> implements Serializable {

    @ApiModelProperty(value = "总记录数")
    private int total;

    @ApiModelProperty(value = "列表数据")
    private List<T> list;

    /**
     * <p>分页</p>
     *
     * @param list      列表数据
     * @param total     总记录数
     * @author Kallen
     * @since 2020/11/24 11:22
    */
    public PageDate(List<T> list, long total) {
        this.list = list;
        this.total = (int) total;
    }
}
