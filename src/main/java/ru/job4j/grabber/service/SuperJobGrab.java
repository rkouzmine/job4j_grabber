package ru.job4j.grabber.service;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import ru.job4j.grabber.stores.Store;

public class SuperJobGrab implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        var parse = (Parse) context.getJobDetail().getJobDataMap().get("parse");
        var store = (Store) context.getJobDetail().getJobDataMap().get("store");

        var posts = parse.fetch();
        posts.forEach(store::save);
    }

}