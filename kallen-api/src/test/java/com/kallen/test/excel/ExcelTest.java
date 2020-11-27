package com.kallen.test.excel;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import com.kallen.test.BaseTest;
import org.apache.poi.ss.usermodel.CellStyle;
import org.junit.Test;

import java.util.List;

/**
 * <p>Title: ExcelTest</p >
 * <p>Description: Excel导入导出</p >
 * <p>Copyright: Kallen. Copyright(c) 2020</p >
 * <link>http://www.buqu.icu</link>
 * <p>版权所有，侵权必究！</p >
 *
 * @author Kallen
 * @version 1.0.0
 * <pre>History:
 *       2020/11/27    Kallen    Created
 * </pre>
 * @mail LXK00515@163.com
 */
public class ExcelTest extends BaseTest {

    @Test
    public void queryExcel() {

        ExcelUtil.readBySax("C:\\Users\\admin\\Desktop\\\u8d44\u914d\u95ee\u5377\u8bdd\u672f.xlsx", 0, new RowHandler() {
            @Override
            public void handle(int sheetIndex, long rowIndex, List<Object> rowList) {
                for (int i = 0; i < 6; i++) {
                    Object o = rowList.get(i);
                    System.out.println(o + "-------------****");
                }
            }
        });
    }
}
