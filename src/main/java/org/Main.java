package org;


import java.sql.SQLException;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args)  throws SQLException {

        contactPersonDao dao = new contactPersonDao();
        List<contactperson> list = List.of(
                new contactperson("Ali", "ali@test.com", "111"),
                new contactperson("Sara", "sara@test.com", "222"),
                new contactperson("Omar", "omar@test.com", "333")
        );

        dao.addContactsBatch(list);
    }
}