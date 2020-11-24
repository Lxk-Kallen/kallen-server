package com.kallen.generator.entity;

import lombok.Data;

/**
 * <p>Title: ColumnEntity</p >
 * <p>Description: 列属性</p >
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
public class ColumnEntity {

    /**
     * 列名
     */
    private String columnName;

    /**
     * 列名类型
     */
    private String dateType;

    /**
     * 列名备注
     */
    private String comments;

    /**
     * 属性名称（第一个大写字母），如：user_name => userName
     */
    private String attrName;

    /**
     * 属性名称（第一个字母小写），如：user_name => userName
     */
    private String alternate;

    /**
     * 属性类型
     */
    private String attrType;

    /**
     * auto_increment
     */
    private String extra;
}
