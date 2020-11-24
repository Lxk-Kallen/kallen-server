package com.kallen.api.core.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * <p>Title: FieldMetaObjectHandler</p >
 * <p>Description: 公共字段，自动填充值</p >
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
public class FieldMetaObjectHandler implements MetaObjectHandler {

    private final static String CREATE_TIME = "createTime";

    private final static String CREATOR = "creator";

    private final static String UPDATE_TIME = "updateTime";

    private final static String UPDATER = "updater";

    private final static String DEPT_ID = "deptId";

    @Override
    public void insertFill(MetaObject metaObject) {
        Date date = new Date();
        //创建时间
        setFieldValByName(CREATE_TIME, date, metaObject);
        //更新时间
        setFieldValByName(UPDATE_TIME, date, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //更新时间
        setFieldValByName(UPDATE_TIME, new Date(), metaObject);
    }
}
