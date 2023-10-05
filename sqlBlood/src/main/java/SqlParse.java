import cn.hutool.json.JSONUtil;
import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlASTVisitor;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.stat.TableStat;
import com.alibaba.druid.util.JdbcConstants;

import java.util.Collection;
import java.util.List;

/**
 * @author OutResource Boy
 * @date 2023/8/18 13:53
 */
public class SqlParse {
    public static void main(String[] args) {
        DbType mysql = JdbcConstants.MYSQL;
        String sql = "SELECT c.customer_name, p.product_name, SUM(o.quantity) AS total_quantity\n" +
                "FROM customers c\n" +
                "JOIN orders o ON c.customer_id = o.customer_id\n" +
                "JOIN products p ON o.product_id = p.product_id\n" +
                "WHERE YEAR(o.order_date) = 2023\n" +
                "  AND p.category = 'Electronics'\n" +
                "  AND EXISTS (\n" +
                "    SELECT 1\n" +
                "    FROM order_details od\n" +
                "    WHERE od.order_id = o.order_id\n" +
                "      AND od.quantity >= 5\n" +
                "  )\n" +
                "GROUP BY c.customer_name, p.product_name\n" +
                "HAVING SUM(o.quantity) > 10\n" +
                "ORDER BY total_quantity DESC;\n";
        List<SQLStatement> sqlStatements = SQLUtils.parseStatements(sql, mysql);
        for (SQLStatement s:
             sqlStatements) {
            SchemaStatVisitor schemaStatVisitor = SQLUtils.createSchemaStatVisitor(mysql);
            s.accept(schemaStatVisitor);
        }



    }
}
