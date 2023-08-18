package log;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * opLog status 相关常量
 * @author OutResource Boy
 * @date 2023/8/18 10:58
 */
@AllArgsConstructor
public enum OpLogEnum {
    /**
     * 成功
     */
    SUCCESS(1, "成功"),
    /**
     * 失败
     */
    FAIL(0, "失败");
    @Getter
    private final Integer FLAG;
    @Getter
    private final String DESC;
}
