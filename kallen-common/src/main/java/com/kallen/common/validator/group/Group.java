package com.kallen.common.validator.group;

import javax.validation.GroupSequence;

/**
 * <p>Title: Group</p >
 * <p>Description: 定义校验顺序，如果AddGroup组失败，则UpdateGroup组不会再校验</p >
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
@GroupSequence({AddGroup.class, UpdateGroup.class})
public interface Group {

}
