package com.kallen.generator.dao;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: GeneratorDao</p >
 * <p>Description: 代码生成器-持久层</p >
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
public interface GeneratorDao {

    List<Map<String, Object>> selectList(Map<String, Object> map);

    Map<String, String> selectTable(String tableName);

    List<Map<String, String>> selectColums(String tableName);
}
