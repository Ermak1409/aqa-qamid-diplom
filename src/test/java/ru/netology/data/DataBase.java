package ru.netology.data;

import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
    static String url = "jdbc:mysql://localhost:3306/mysql_1";
    static String user = "adm";
    static String password = "pass";


    @SneakyThrows
    public static void cleanTable() {
        val deletePaymentEntity = "DELETE FROM payment_entity ";
        val runner = new QueryRunner();
        try (val conn = DriverManager.getConnection(url, user, password)) {
            runner.update(conn, deletePaymentEntity);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @SneakyThrows
    public static String getPaymentStatus() {
        String statusSQL = "SELECT status FROM payment_entity";
        return getStatus(statusSQL);
    }

    @SneakyThrows
    private static String getStatus(String query) {
        val runner = new QueryRunner();
        try (val conn = DriverManager.getConnection(url, user, password)) {
            return runner.query(conn, query, new ScalarHandler<>());
        }
    }

}
