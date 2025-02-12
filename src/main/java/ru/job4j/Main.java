package ru.job4j;

import org.apache.log4j.Logger;

import ru.job4j.grabber.service.*;
import ru.job4j.grabber.stores.JdbcStore;
import ru.job4j.grabber.utils.HabrCareerDateTimeParser;

import java.sql.SQLException;

public class Main {

    private static final Logger LOG = Logger.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        try {
            var config = new Config();
            config.load("application.properties");
            LOG.info("Configuration loaded");

            var connection = config.initConnection();
            LOG.info("Database connection established");

            var dateTimeParser = new HabrCareerDateTimeParser();
            var habrCareerParse = new HabrCareerParse(dateTimeParser);

            var store = new JdbcStore(connection);

            var scheduler = new SchedulerManager();
            scheduler.init();
            LOG.info("Scheduler initialized");
            scheduler.load(
                    Integer.parseInt(config.get("rabbit.interval")),
                    SuperJobGrab.class,
                    store,
                    habrCareerParse
            );
            LOG.info("Jobs started");
            new Web(store).start(Integer.parseInt(config.get("server.port")));
        } catch (SQLException e) {
            LOG.error("When create a connection", e);
        }
    }

}