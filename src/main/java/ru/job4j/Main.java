package ru.job4j;

import org.apache.log4j.Logger;

import ru.job4j.grabber.model.Post;
import ru.job4j.grabber.service.Config;
import ru.job4j.grabber.service.SchedulerManager;
import ru.job4j.grabber.service.SuperJobGrab;
import ru.job4j.grabber.stores.JdbcStore;

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

            var store = new JdbcStore(connection);
            var post = new Post();
            post.setTitle("Super Java Job");
            store.save(post);

            var scheduler = new SchedulerManager();
            scheduler.init();
            LOG.info("Scheduler initialized");
            scheduler.load(
                    Integer.parseInt(config.get("rabbit.interval")),
                    SuperJobGrab.class,
                    store);

            LOG.info("Jobs started");
        } catch (SQLException e) {
            LOG.error("When create a connection", e);
        }
    }

}