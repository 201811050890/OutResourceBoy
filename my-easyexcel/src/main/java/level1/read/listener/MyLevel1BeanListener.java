package level1.read.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import level1.read.domain.TestDomainEasyExcelDomain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Sun
 * @date 2023/10/5 08:50
 */
public class MyLevel1BeanListener extends AnalysisEventListener<TestDomainEasyExcelDomain>{
    private List<TestDomainEasyExcelDomain> dataList = new ArrayList<>();

    /**
     * When analysis one row trigger invoke function.
     *
     * @param data    one row value. It is same as {@link AnalysisContext#readRowHolder()}
     * @param context analysis context
     */
    @Override
    public void invoke(TestDomainEasyExcelDomain data, AnalysisContext context) {
        dataList.add(data);
    }

    /**
     * if have something to do after all analysis
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("读取数据完成");
    }

    public List<TestDomainEasyExcelDomain> getDataList() {
        return dataList;
    }
}
