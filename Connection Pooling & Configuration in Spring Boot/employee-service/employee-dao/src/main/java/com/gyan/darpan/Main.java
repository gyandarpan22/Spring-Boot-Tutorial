package com.gyan.darpan;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


@Service
public class Main {

    private final DataSource dataSource;

    @Autowired
    public Main(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @PostConstruct
    public void init() {

        System.out.println("Datasource testing method is called");

        try (Connection connection = dataSource.getConnection()) {

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
