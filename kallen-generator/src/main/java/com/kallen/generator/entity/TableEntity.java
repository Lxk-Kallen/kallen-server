package com.kallen.generator.entity;

import lombok.Data;

import java.util.List;

/**
 * <p>Title: TableEntity</p >
 * <p>Description: 表数据</p >
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
public class TableEntity {

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表备注
     */
    private String comments;

    /**
     * 表主键
     */
    private ColumnEntity pk;

    /**
     * 表列名（不包含主键）
     */
    private List<ColumnEntity> columns;

    /**
     * 类名（第一个字母大写），如：sys_user => SysUser
     */
    private String className;

    /**
     * 类型（第一个字母小写），如：sys_user => sysUser
     */
    private String classnamed;
}
