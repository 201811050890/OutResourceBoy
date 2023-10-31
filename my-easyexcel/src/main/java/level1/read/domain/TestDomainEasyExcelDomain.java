package level1.read.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import level1.read.annotation.HutoolExcelAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author outresource boy
 * @date 2023/10/4 16:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestDomainEasyExcelDomain {
    @HutoolExcelAlias(alias = "序号")
    @ExcelProperty(index = 0)
    private String serialNum;
    @HutoolExcelAlias(alias = "代号")
    @ExcelProperty(index = 1)
    private String code;
    @HutoolExcelAlias(alias = "名称")
    @ExcelProperty(index = 2)
    private String name;
}
