package com.kallen.api.tian.service;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: TianService</p >
 * <p>Description: </p >
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
@Service
public class TianService {

    private static String userKey = "380410d2cd4e4386e070123175b47196";


    public Map<String, Object> wechatContent() {
        String url = "http://api.tianapi.com/txapi/pyqwenan/index";

        Map<String, Object> map = new HashMap<>();
        map.put("key", userKey);

        String s = HttpUtil.get(url, map);
        JSONObject jsonObject = JSONUtil.parseObj(s);
        JSONArray newslist = jsonObject.getJSONArray("newslist");

        Map<String, Object> okMap = new HashMap<>();
        okMap.put("title", "朋友圈文案");

        for (Object json : newslist) {
            JSONObject parseObj = JSONUtil.parseObj(json);

            okMap.put("content", parseObj.get("content"));
            okMap.put("source", parseObj.get("source"));
        }
        return okMap;
    }

    public Map<String, Object> dialogue() {
        String url = "http://api.tianapi.com/txapi/dialogue/index";

        Map<String, Object> map = new HashMap<>();
        map.put("key", userKey);

        String s = HttpUtil.get(url, map);

        JSONObject jsonObject = JSONUtil.parseObj(s);
        JSONArray newslist = jsonObject.getJSONArray("newslist");

        Map<String, Object> okMap = new HashMap<>();
        okMap.put("title", "经典台词");

        for (Object json : newslist) {
            JSONObject parseObj = JSONUtil.parseObj(json);

            okMap.put("dialogue", parseObj.get("dialogue"));
            okMap.put("english", parseObj.get("english"));
            okMap.put("source", parseObj.get("source"));
            okMap.put("type", parseObj.get("type"));
        }
        return okMap;
    }

    public Map<String, Object> caihp() {

        String url = "http://api.tianapi.com/txapi/caihongpi/index";

        Map<String, Object> map = new HashMap<>();
        map.put("key", userKey);

        String s = HttpUtil.get(url, map);

        JSONObject jsonObject = JSONUtil.parseObj(s);
        JSONArray newslist = jsonObject.getJSONArray("newslist");

        Map<String, Object> okMap = new HashMap<>();
        okMap.put("title", "彩虹屁");

        for (Object json : newslist) {
            JSONObject parseObj = JSONUtil.parseObj(json);

            okMap.put("content", parseObj.get("content"));
        }
        return okMap;
    }

    public Map<String, Object> tiangou() {

        String url = "http://api.tianapi.com/txapi/tiangou/index";

        Map<String, Object> map = new HashMap<>();
        map.put("key", userKey);

        String s = HttpUtil.get(url, map);

        JSONObject jsonObject = JSONUtil.parseObj(s);
        JSONArray newslist = jsonObject.getJSONArray("newslist");

        Map<String, Object> okMap = new HashMap<>();
        okMap.put("title", "舔狗日记");

        for (Object json : newslist) {
            JSONObject parseObj = JSONUtil.parseObj(json);

            okMap.put("content", parseObj.get("content"));
        }
        return okMap;
    }

    public Map<String, Object> hotreview() {

        String url = "http://api.tianapi.com/txapi/hotreview/index";

        Map<String, Object> map = new HashMap<>();
        map.put("key", userKey);

        String s = HttpUtil.get(url, map);

        JSONObject jsonObject = JSONUtil.parseObj(s);
        JSONArray newslist = jsonObject.getJSONArray("newslist");

        Map<String, Object> okMap = new HashMap<>();
        okMap.put("title", "网抑云热评");

        for (Object json : newslist) {
            JSONObject parseObj = JSONUtil.parseObj(json);

            okMap.put("content", parseObj.get("content"));
            okMap.put("source", parseObj.get("source"));
        }
        return okMap;

    }

    public Map<String, Object> lzmy() {

        String url = "http://api.tianapi.com/txapi/lzmy/index";

        Map<String, Object> map = new HashMap<>();
        map.put("key", userKey);

        String s = HttpUtil.get(url, map);

        JSONObject jsonObject = JSONUtil.parseObj(s);
        JSONArray newslist = jsonObject.getJSONArray("newslist");

        Map<String, Object> okMap = new HashMap<>();
        okMap.put("title", "励志古文");

        for (Object json : newslist) {
            JSONObject parseObj = JSONUtil.parseObj(json);

            okMap.put("saying", parseObj.get("saying"));
            okMap.put("transl", parseObj.get("transl"));
            okMap.put("source", parseObj.get("source"));
        }
        return okMap;
    }
}
