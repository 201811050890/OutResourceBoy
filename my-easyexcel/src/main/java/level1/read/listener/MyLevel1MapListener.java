package level1.read.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Sun
 * @date 2023/10/5 14:25
 */
public class MyLevel1MapListener extends AnalysisEventListener<Map<String, String>> {

    private List<Map<String, String>> dataList = new ArrayList<>();

    /**
     * When analysis one row trigger invoke function.
     *
     * @param data    one row value. It is same as {@link AnalysisContext#readRowHolder()}
     * @param context analysis context
     */
    @Override
    public void invoke(Map<String, String> data, AnalysisContext context) {
        dataList.add(data);
    }

    /**
     * if have something to do after all analysis
     *
     * @param context context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("读取完成");
    }

    public List<Map<String, String>> getDataList() {
        return dataList;
    }
}
