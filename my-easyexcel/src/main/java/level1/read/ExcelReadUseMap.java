package level1.read;

import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.excel.EasyExcel;
import level1.read.domain.TestDomainEasyExcelDomain;
import level1.read.listener.MyLevel1MapListener;
import level1.read.util.HutoolExcelUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Sun
 * @date 2023/10/4 14:47
 */
public class ExcelReadUseMap {
    public static void main(String[] args) throws IOException {
//        easyExcelReadMap();
        hutoolExcelReadMap();
    }

    /**
     * EasyExcel读取Map
     */
    public static void easyExcelReadMap() throws IOException {
        String filePath = "你的excel";
        Path path = Paths.get(filePath);
        InputStream inputStream = Files.newInputStream(path);

        MyLevel1MapListener listener = new MyLevel1MapListener();
        EasyExcel
                .read(inputStream)
                .registerReadListener(listener)
                .headRowNumber(1)
                .sheet(0)
                .doRead();


        System.out.println(JSONUtil.toJsonPrettyStr(listener.getDataList()));
    }

    public static void hutoolExcelReadMap() throws IOException {
        String filePath = "你的excel";
        Path path = Paths.get(filePath);
        InputStream inputStream = Files.newInputStream(path);

        ExcelReader reader = ExcelUtil.getReader(inputStream, 0);
        Map<String, String> aliasMap = HutoolExcelUtils.aliasAnnotation2Map(TestDomainEasyExcelDomain.class);
        reader.setHeaderAlias(aliasMap);
        List<Map<String, Object>> mapList = reader.read(0, 1, Integer.MAX_VALUE);

        System.out.println(JSONUtil.toJsonPrettyStr(mapList));
    }
}
