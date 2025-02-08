package ru.job4j.grabber.service;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Config {

    private static final Logger LOG = Logger.getLogger(Config.class);
    private final Properties properties = new Properties();

    public void load(String file) {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(file)) {
            properties.load(input);
        } catch (IOException io) {
            LOG.error(String.format("When load file : %s", file), io);
        }
    }

    public Connection initConnection() throws SQLException, ClassNotFoundException {
        Class.forName(properties.getProperty("db.driver-class-name"));
        return DriverManager.getConnection(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.username"),
                    properties.getProperty("db.password")
            );
        }

    public String get(String key) {
        return properties.getProperty(key);
    }

}
