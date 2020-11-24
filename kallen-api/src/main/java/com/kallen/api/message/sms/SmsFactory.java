package com.kallen.api.message.sms;

import com.alibaba.fastjson.JSON;
import com.kallen.api.message.entity.SmsEntity;
import com.kallen.api.message.service.SmsService;
import com.kallen.common.constant.Constant;
import com.kallen.common.utils.SpringContextUtils;

/**
 * <p>Title: SmsFactory</p >
 * <p>Description: SmsFactory</p >
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
public class SmsFactory {
    private static SmsService sysSmsService;

    static {
        SmsFactory.sysSmsService = SpringContextUtils.getBean(SmsService.class);
    }

    public static AbstractSmsService build(String smsCode){
        //获取短信配置信息
        SmsEntity smsEntity = sysSmsService.getBySmsCode(smsCode);
        SmsConfig config = JSON.parseObject(smsEntity.getSmsConfig(), SmsConfig.class);

        if(smsEntity.getPlatform() == Constant.SmsService.ALIYUN.getValue()){
            return new AliyunSmsService(config);
        }else if(smsEntity.getPlatform() == Constant.SmsService.QCLOUD.getValue()){
            return new QcloudSmsService(config);
        }else if(smsEntity.getPlatform() == Constant.SmsService.QINIU.getValue()){
            return new QiniuSmsService(config);
        }

        return null;
    }
}