package com.kallen.generator.utils;

import com.kallen.generator.entity.ColumnEntity;
import com.kallen.generator.entity.TableEntity;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <p>Title: GenUtils</p >
 * <p>Description: 代码生成器-工具类</p >
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
public class GenUtils {

    public static List<String> getTemplates() {
        ArrayList<String> templates = new ArrayList<>();
        templates.add("template/Controller.java.vm");
        templates.add("template/Service.java.vm");
        templates.add("template/ServiceImpl.java.vm");
        templates.add("template/Dao.java.vm");
        templates.add("template/Req.java.vm");
        templates.add("template/DTO.java.vm");
        templates.add("template/Entity.java.vm");
        templates.add("template/VO.java.vm");
        templates.add("template/Excel.java.vm");
        templates.add("template/Dao.xml.vm");
        templates.add("template/index.vue.vm");
        templates.add("template/add-or-update.vue.vm");
        templates.add("template/mysql.vm");

        return templates;
    }

    public static void generatorCode(Map<String, String> table,
                                     List<Map<String, String>> columns,
                                     ZipOutputStream zip) {
        // 配置信息
        Configuration config = getConfig();
        boolean hasBigDecimal = false;
        // 表信息
        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableName(table.get("tableName"));
        tableEntity.setComments(table.get("tableComment"));
        // 表名转换成Java类名
        String className = tableToJava(tableEntity.getTableName(), config.getString("tablePrefix"));
        tableEntity.setClassName(className);
        tableEntity.setClassnamed(StringUtils.uncapitalize(className));

        // 列信息
        List<ColumnEntity> columnEntityList = new ArrayList<>();
        for (Map<String, String> column : columns) {
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setColumnName(column.get("columnName"));
            columnEntity.setDateType(column.get("dateType"));
            columnEntity.setComments(column.get("columnComment"));
            columnEntity.setExtra(column.get("extra"));

            // 列名转换成Java属性名
            String attrName = columnToJava(columnEntity.getColumnName());
            columnEntity.setAttrName(attrName);
            columnEntity.setAlternate(StringUtils.uncapitalize(attrName));

            // 列的数据类型，转换成Java类型
            String attrType = config.getString(columnEntity.getDateType(), "unkonwType");
            columnEntity.setAttrType(attrType);
            if (!hasBigDecimal && attrType.equals("BigDecimal")) {
                hasBigDecimal = true;
            }

            // 是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey")) && tableEntity.getPk() == null) {
                tableEntity.setPk(columnEntity);
            }

            columnEntityList.add(columnEntity);
        }
        tableEntity.setColumns(columnEntityList);

        // 没主键，则第一个字段为主键
        if (tableEntity.getPk() == null) {
            tableEntity.setPk(tableEntity.getColumns().get(0));
        }

        // 设置velocity资源加载器
        Properties properties = new Properties();
        properties.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(properties);

        // 封装模板数据
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", tableEntity.getTableName());
        map.put("comments", tableEntity.getComments());
        map.put("pk", tableEntity.getPk());
        map.put("className", tableEntity.getClassName());
        map.put("pathName", tableEntity.getClassName().toLowerCase());
        map.put("columns", tableEntity.getColumns());
        map.put("hasBigDecimal", hasBigDecimal);
        map.put("version", config.getString("version"));
        map.put("package", config.getString("package"));
        map.put("moduleName", config.getString("moduleName"));
        map.put("author", config.getString("author"));
        map.put("email", config.getString("email"));
        map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        map.put("date", DateUtils.format(new Date(), DateUtils.DATE_PATTERN));

        for (int i = 0; i <= 10; i++) {
            map.put("id + i", IdWorker.getId());
        }

        VelocityContext context = new VelocityContext(map);

        // 获取模板列表
        List<String> templates = getTemplates();
        for (String template : templates) {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);

            try {
                // 添加到zip
                zip.putNextEntry(new ZipEntry(getFileName(template, tableEntity.getClassName(), config.getString("package"), config.getString("moduleName"))));
                IOUtils.write(sw.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                throw new KallenException("渲染模板失败，表名：" + tableEntity.getTableName(), e);
            }
        }

    }

    /**
     * <p>获取文件名</p>
     *
     * @param template
     * @param className
     * @param packageName
     * @param moduleName
     * @author Kallen
     * @since 2020/11/23 15:25
    */
    public static String getFileName(String template, String className, String packageName, String moduleName) {

        String packagePath = "main" + File.separator + "java" + File.separator;

        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator + "modules" + File.separator + moduleName + File.separator;
        }

        if (template.contains("Entity.java.vm")) {
            return packagePath + "entity" + File.separator + className + "Entity.java";
        }

        if (template.contains("Excel.java.vm")) {
            return packagePath + "excel" + File.separator + className + "Excel.java";
        }

        if (template.contains("template/Dao.java.vm")) {
            return packagePath + "dao" + File.separator + className + "Dao.java";
        }

        /*if (template.contains("DTO.java.vm")) {
            return packagePath + "dto" + File.separator + className + "DTO.java";
        }

        if (template.contains("Req.java.vm")) {
            return packagePath + "req" + File.separator + className + "Req.java";
        }*/

        if (template.contains("Service.java.vm")) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if (template.contains("ServiceImpl.java.vm")) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (template.contains("Controller.java.vm")) {
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }

        if (template.contains("template/Dao.java.vm")) {
            return "main" + File.separator + "resources" + File.separator + "mapper" + File.separator + moduleName + File.separator + className + "Dao.xml";
        }

        if (template.contains("VO.java.vm")) {
            return packagePath + "dto" + File.separator + className + "DTO.java";
        }

        if (template.contains("index.vue.vm")) {
            return "vue" + File.separator + "views" + File.separator + "modules" + File.separator + moduleName + File.separator + className.toLowerCase() + ".vue";
        }

        if (template.contains("add-or-update.vue.vm")) {
            return "vue" + File.separator + "views" + File.separator + "modules" + File.separator + moduleName + File.separator + className.toLowerCase() + "-add-or-update.vue";
        }

        if (template.contains("mysql.vm")) {
            return className.toLowerCase() + ".mysql.sql";
        }
        return null;
    }

    /**
     * <p>表名转换成Java类名</p>
     *
     * @param tableName
     * @param tablePrefix
     * @author Kallen
     * @since 2020/11/23 14:56
    */
    public static String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replaceFirst(tablePrefix, "");
        }
        return columnToJava(tableName);
    }

    /**
     * <p>列名转换成Java属性名</p>
     *
     * @param columnName
     * @author Kallen
     * @since 2020/11/23 14:59
    */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    /**
     * <p>获取配置信息</p>
     *
     * @author Kallen
     * @since 2020/11/23 11:27
     * @return
    */
    private static PropertiesConfiguration getConfig() {
        try {
            return new PropertiesConfiguration("generator.properties");
        } catch (ConfigurationException e) {
            throw new KallenException("获取配置文件失败：", e);
        }
    }
}
