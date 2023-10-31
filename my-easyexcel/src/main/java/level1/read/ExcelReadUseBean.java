package level1.read;

import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.excel.EasyExcel;
import level1.read.domain.TestDomainEasyExcelDomain;
import level1.read.listener.MyLevel1BeanListener;
import level1.read.util.HutoolExcelUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * @author outresource
 * @date 2023/10/4 14:42
 */
public class ExcelReadUseBean {

    public static void main(String[] args) throws IOException {
//        easyExcelRead();
//        easyExcelReadWithSheet();
        hutoolExcelRead();
    }

    /**
     * easy-excel readAllSync() -> excel中所有sheet页都会读取,直接映射到对应的实体类中
     * @throws IOException ex
     */
    public static void easyExcelRead() throws IOException {
        String filePath = "你的excel";
        Path path = Paths.get(filePath);
        InputStream inputStream = Files.newInputStream(path);

        List<TestDomainEasyExcelDomain> list = EasyExcel
                .read(inputStream)
                .head(TestDomainEasyExcelDomain.class)
                .headRowNumber(1)
                .doReadAllSync();

        System.out.println(JSONUtil.toJsonPrettyStr(list));
    }

    /**
     * easy-excel读取指定sheet页，监听器
     * @throws IOException ex
     */
    public static void easyExcelReadWithSheet() throws IOException {
        String filePath = "你的excel";
        Path path = Paths.get(filePath);
        InputStream inputStream = Files.newInputStream(path);
        MyLevel1BeanListener listener = new MyLevel1BeanListener();

        EasyExcel
                .read(inputStream)
                .head(TestDomainEasyExcelDomain.class)
                .headRowNumber(1)
                .registerReadListener(listener)
                .sheet(0)
                .doRead();
        List<TestDomainEasyExcelDomain> dataList = listener.getDataList();

        System.out.println(JSONUtil.toJsonPrettyStr(dataList));

    }

    /**
     * hutool-excel读取
     */
    public static void hutoolExcelRead() throws IOException {

        String filePath = "你的excel";
        Path path = Paths.get(filePath);
        InputStream inputStream = Files.newInputStream(path);

        // 读取指定sheet
        ExcelReader reader = ExcelUtil.getReader(inputStream, 0);
        // 这里需要自设置别名 --> 自定义注解
        Map<String, String> aliasMap =
                HutoolExcelUtils.aliasAnnotation2Map(TestDomainEasyExcelDomain.class);
        reader.setHeaderAlias(aliasMap);
        List<TestDomainEasyExcelDomain> list = reader.readAll(TestDomainEasyExcelDomain.class);


        System.out.println(JSONUtil.toJsonPrettyStr(list));
    }

}
