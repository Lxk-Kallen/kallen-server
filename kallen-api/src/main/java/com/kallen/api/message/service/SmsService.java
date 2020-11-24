package com.kallen.api.message.service;

import com.kallen.api.message.dto.SmsDTO;
import com.kallen.api.message.entity.SmsEntity;
import com.kallen.common.service.CrudService;

/**
 * <p>Title: SmsService</p >
 * <p>Description: 短信日志-服务层</p >
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
public interface SmsService extends CrudService<SmsEntity, SmsDTO> {

    /**
     * 发送短信
     * @param smsCode   短信编码
     * @param mobile   手机号
     * @param params   短信参数
     */
    void send(String smsCode, String mobile, String params);

    SmsEntity getBySmsCode(String smsCode);

}

