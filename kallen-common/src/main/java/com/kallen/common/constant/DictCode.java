package com.kallen.common.constant;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>Title: DictCode</p >
 * <p>Description: 数据字典</p >
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
public interface DictCode {

    String getCode();
    String getName();
    String getValue();
    String getText();

    enum ACCOUNT_TYPE implements DictCode {
        MOBILE("A04.001", "MOBILE", "1", "手机"),
        WE_CHAT("A05.002", "WE_CHAT", "2", "微信"),
        OPEN_ID("A05.002", "WE_CHAT", "2", "微信_OPENID");

        @Getter
        private String code;
        @Getter
        private String name;
        @Getter
        private String value;
        @Getter
        private String text; //语义化

        ACCOUNT_TYPE(String code, String name, String value, String text) {
            this.code = code;
            this.name = name;
            this.value = value;
            this.text = text;
        }

        public static ACCOUNT_TYPE getEnumByValue(String value) {
            ACCOUNT_TYPE[] vs = ACCOUNT_TYPE.values();
            for (ACCOUNT_TYPE s : vs) {
                if (StringUtils.equalsIgnoreCase(value, s.getValue())) {
                    return s;
                }
            }
            return null;
        }
    }

    enum ORDER_SOURCE implements DictCode {
        JSAPI("A25.001", "JSAPI", "1", "公众号");

        @Getter
        private String code;
        @Getter
        private String name;
        @Getter
        private String value;
        @Getter
        private String text; //语义化

        ORDER_SOURCE(String code, String name, String value, String text) {
            this.code = code;
            this.name = name;
            this.value = value;
            this.text = text;
        }

        public static ORDER_SOURCE getEnumByValue(String value) {
            ORDER_SOURCE[] vs = ORDER_SOURCE.values();
            for (ORDER_SOURCE s : vs) {
                if (StringUtils.equalsIgnoreCase(value, s.getValue())) {
                    return s;
                }
            }
            return null;
        }
    }

    enum USER_STATUS implements DictCode {
        BAND("A02.001", "BAND", "0", "禁用"),
        NORMAL("A02.002", "NORMAL", "1", "正常");

        @Getter
        private String code;
        @Getter
        private String name;
        @Getter
        private String value;
        @Getter
        private String text; //语义化

        USER_STATUS(String code, String name, String value, String text) {
            this.code = code;
            this.name = name;
            this.value = value;
            this.text = text;
        }

        public static USER_STATUS getEnumByValue(String value) {
            USER_STATUS[] vs = USER_STATUS.values();
            for (USER_STATUS s : vs) {
                if (StringUtils.equalsIgnoreCase(value, s.getValue())) {
                    return s;
                }
            }
            return null;
        }
    }
}
