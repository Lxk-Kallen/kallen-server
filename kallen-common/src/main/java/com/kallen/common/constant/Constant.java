package com.kallen.common.constant;

/**
 * <p>Title: Constant</p >
 * <p>Description: 常量</p >
 * <p>Copyright: Kallen. Copyright(c) 2020</p >
 * <link>http://www.buqu.icu</link>
 * <p>版权所有，侵权必究！</p >
 *
 * @author Kallen
 * @version 1.0.0
 * <pre>History:
 *       2020/11/20    Kallen    Created
 * </pre>
 */
public interface Constant {

    /**
     * 成功
     */
    int SUCCESS = 1;

    /**
     * 失败
     */
    int FAIL = 0;

    /**
     * OK
     */
    String OK = "OK";

    /**
     * token header
     */
    String TOKEN_HEADER = "token";

    /**
     * 实体类
     */
    String ENTITY = "et";

    /**
     * 当前页码
     */
    String PAGE = "page";

    /**
     * 每页显示记录数
     */
    String LIMIT = "limit";

    /**
     * 排序字段
     */
    String ORDER_FIELD = "orderField";

    /**
     * 排序方式
     */
    String ORDER = "order";

    /**
     * 升序
     */
    String ASC = "asc";

    /**
     * 短信服务商
     */
    enum SmsService {
        /**
         * 阿里云
         */
        ALIYUN(1),

        /**
         * 腾讯云
         */
        QCLOUD(2),

        /**
         * 七牛
         */
        QINIU(3);

        private int value;

        SmsService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
