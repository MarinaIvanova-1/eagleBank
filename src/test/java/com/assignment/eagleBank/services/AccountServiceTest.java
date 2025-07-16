package com.assignment.eagleBank.services;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    DataSource dataSource;
    @Mock
    Connection conn;
    @Mock
    Statement stmt;
    @Mock
    ResultSet resultSet;
}
