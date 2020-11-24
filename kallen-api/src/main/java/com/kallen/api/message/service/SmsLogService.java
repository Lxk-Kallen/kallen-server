package com.kallen.api.message.service;


import com.kallen.api.message.dto.SmsLogDTO;
import com.kallen.api.message.entity.SmsLogEntity;
import com.kallen.common.service.CrudService;

import java.util.LinkedHashMap;

/**
 * <p>Title: SmsLogService</p >
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
public interface SmsLogService extends CrudService<SmsLogEntity, SmsLogDTO> {

    /**
     * 保存短信发送记录
     * @param smsCode   短信编码
     * @param platform  平台
     * @param mobile    手机号
     * @param params    短信参数
     * @param status    发送状态
     */
    void save(String smsCode, Integer platform, String mobile, LinkedHashMap<String, String> params, Integer status);
}