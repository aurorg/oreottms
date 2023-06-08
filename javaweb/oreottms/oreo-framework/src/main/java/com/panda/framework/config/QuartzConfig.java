package com.panda.framework.config;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.panda.common.exception.DataNotFoundException;
import com.panda.common.utils.ApplicationContextUtils;
import com.panda.system.domin.SysBill;
import com.panda.system.domin.SysSession;
import com.panda.system.service.impl.SysBillServiceImpl;
import com.panda.system.service.impl.SysSessionServiceImpl;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class QuartzConfig {

    @Autowired
    private SysBillServiceImpl sysBillService;

    @Autowired
    private SysSessionServiceImpl sysSessionService;


    /*
    返回一个Quartz框架中的JobDetail对象，用于定义一个需要被调度执行的任务

    QuartzJobBean对象，并重写了它的executeInternal方法，
    该方法定义了任务具体的执行逻辑。
    在任务中，打印了一条日志，
    并调用了ApplicationContextUtils.getBean方法获取名为"cancelTimeoutBill"的Bean，
    Bean是一个Spring组件，用于取消所有超时订单并释放占座资源
     */
    @Bean
    public JobDetail jobDetail() {
        QuartzJobBean quartzJob = new QuartzJobBean() {
            @Override
            protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.println("执行取消超时订单处理" + sdf.format(new Date()));
                // 取消所有超时订单并释放占座资源
                ApplicationContextUtils.getBean("cancelTimeoutBill");
            }
        };
        /*
        使用JobBuilder.newJob方法创建一个JobDetail对象，
        并传入quartzJob.getClass()作为参数，
        该参数表示要执行的任务类型。
        使用storeDurably方法指定JobDetail对象持久化存储，
        即使没有关联的Trigger也不会删除该JobDetail对象。
        最后使用build方法构建JobDetail对象。
         */
        return JobBuilder.newJob(quartzJob.getClass()).storeDurably().build();
    }

    /*
    返回一个Quartz框架中的SimpleTrigger对象，用于定义一个触发器，触发任务的执行。

    SimpleScheduleBuilder对象，用于定义任务执行的时间策略

    SimpleScheduleBuilder.simpleSchedule方法创建SimpleScheduleBuilder对象，
    并使用withIntervalInSeconds方法指定任务执行的时间间隔

    使用repeatForever方法指定任务需要一直重复执行。

     */
    @Bean
    public SimpleTrigger trigger() {
        //每5分钟执行一次，一直重复执行
        SimpleScheduleBuilder scheduleBuilder =
                SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(5 * 60)
                        .repeatForever();
        /*
        在调用TriggerBuilder.newTrigger方法创建TriggerBuilder对象后，
        使用forJob方法指定该触发器关联的任务，
        调用jobDetail()方法返回的JobDetail对象
         */
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail())
                .withSchedule(scheduleBuilder).build();
    }

}
