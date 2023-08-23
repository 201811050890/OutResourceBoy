package my.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author OutResource Boy
 * @date 2023/8/18 15:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginEncryptBO {
    private String token;
    private Long timeMillis;
}
