package com.universal.scheduler.job.impl;

import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.universal.scheduler.job.Job;


@Component
@EnableScheduling
public class TestJob implements Job {

    @Scheduled(cron = "0 */1 *  * * * ")
    @Override
    public void run() {
        System.out.println(DateTime.now());

    }

    @Override
    public void run(String paramters) {

    }

}
