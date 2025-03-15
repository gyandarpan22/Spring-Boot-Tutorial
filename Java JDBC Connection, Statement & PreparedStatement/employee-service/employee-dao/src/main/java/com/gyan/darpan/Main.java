package com.gyan.darpan;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/employee_service";
        String user = "springboottutorial";
        String password = "springboottutorial";

        String userFromCity = "Banglore";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(url, user, password)) {

//            try (Statement statement = connection.createStatement()) {
//
//                try (ResultSet rs = statement.executeQuery("select name , age ,city from users where city = '" + userFromCity + "'")) {
//
//                    while (rs.next()) {
//                        String name = rs.getString("name");
//                        int age = rs.getInt("age");
//                        String city = rs.getString("city");
//
//                        System.out.println("name : " + name + " age : " + age + " city : " + city);
//                    }
//                }
//            }

//            try (PreparedStatement pstmt = connection.prepareStatement("insert into users(name,age,city) values(?,?,?)")) {
//
//                pstmt.setString(1, "Rock");
//                pstmt.setInt(2, 50);
//                pstmt.setString(3, "Delhi");
//
//                int count = pstmt.executeUpdate();
//
//                System.out.println(count);
//
//            }

            try (PreparedStatement pstmt = connection.prepareStatement("select name , age ,city from users")) {


                try (ResultSet rs = pstmt.executeQuery()) {

                    while (rs.next()) {
                        String name = rs.getString("name");
                        int age = rs.getInt("age");
                        String city = rs.getString("city");

                        System.out.println("name : " + name + " age : " + age + " city : " + city);
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
