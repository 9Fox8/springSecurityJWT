package com.fox.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @Date 2020-11-20-14:46
 * @Author fox
 */
@DisallowConcurrentExecution
public class ScheduledTask extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        System.out.println(System.currentTimeMillis() + "定时任务，" + Thread.currentThread().getName());
    }
}
