package com.kallen.api.message.sms;

import com.kallen.api.message.service.SmsLogService;
import com.kallen.common.constant.Constant;
import com.kallen.common.exception.ErrorCode;
import com.kallen.common.exception.KallenException;
import com.kallen.common.utils.SpringContextUtils;
import com.qiniu.http.Response;
import com.qiniu.sms.SmsManager;
import com.qiniu.util.Auth;

import java.util.LinkedHashMap;

/**
 * <p>Title: QiniuSmsService</p >
 * <p>Description: 七牛短信服务</p >
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
public class QiniuSmsService extends AbstractSmsService {
    private SmsManager smsManager;

    public QiniuSmsService(SmsConfig config){
        this.config = config;

        //初始化
        init();
    }


    private void init(){
        Auth auth = Auth.create(config.getQiniuAccessKey(), config.getQiniuSecretKey());
        smsManager = new SmsManager(auth);
    }

    @Override
    public void sendSms(String smsCode, String mobile, LinkedHashMap<String, String> params) {
        this.sendSms(smsCode, mobile, params, null, config.getQiniuTemplateId());
    }

    @Override
    public void sendSms(String smsCode, String mobile, LinkedHashMap<String, String> params, String signName, String template) {
        Response response;
        try {
            response = smsManager.sendSingleMessage(template, mobile, params);
        } catch (Exception e) {
            throw new KallenException(ErrorCode.SEND_SMS_ERROR, e, "");

//            throw new KallenException("短信发送错误");
        }

        int status = Constant.SUCCESS;
        if(!response.isOK()){
            status = Constant.FAIL;
        }

        //保存短信记录
        SmsLogService smsLogService = SpringContextUtils.getBean(SmsLogService.class);
        smsLogService.save(smsCode, Constant.SmsService.QCLOUD.getValue(), mobile, params, status);

        if(status == Constant.FAIL){
            throw new KallenException(ErrorCode.SEND_SMS_ERROR, response.error);

//            throw new KallenException("短信发送错误");
        }
    }
}
