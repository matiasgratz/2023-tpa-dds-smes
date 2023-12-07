package ar.edu.utn.frba.dds.Model.TareasProgramadas;

import lombok.Setter;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.ParseException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
@Setter
public class ConfiguracionJob {

    private static final String CRON_CADA_UNA_HORA = "* * * ? * * *";

    private static final String JOB_GROUP = "JobGroup";
    private static final String TRIGGER_GROUP = "TriggerGroup";

    @Autowired
    @Qualifier("notificar")
    private ITareasProgramadas notificar;

    @Bean
    public JobDetail jobDetailRecordatorioRecomendacionesPorEmail() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put(EnviarNotificacionesJob.NOTIFICATION_SENDER_MAP_KEY, notificar);
        String jobDescription = "Job que envia Notificaciones";
        return buildJobDetail(EnviarNotificacionesJob.class, JOB_GROUP, jobDescription, dataMap);
    }

    @Bean
    public Trigger triggerRecordatorioRecomendacionesPorEmail(@Qualifier("jobDetailRecordatorioRecomendacionesPorEmail") JobDetail jobDetailRecordatorioRecomendacionesPorEmail) throws ParseException, ParseException {
        String triggerDescription = "Trigger para el Job que envia notificaciones";
        ZonedDateTime startAt = ZonedDateTime.now();
        CronExpression cronExpression = new CronExpression(CRON_CADA_UNA_HORA);
        return buildTrigger(jobDetailRecordatorioRecomendacionesPorEmail, TRIGGER_GROUP, triggerDescription, startAt, cronExpression);
    }

    private JobDetail buildJobDetail(Class<? extends Job> jobClass, String jobGroup, String jobDescription, Map<?, ?> dataMap) {
        JobBuilder jobBuilder = JobBuilder.newJob(jobClass)
                .withIdentity(UUID.randomUUID().toString(), jobGroup)
                .withDescription(jobDescription)
                .storeDurably(true);

        if (dataMap != null) {
            JobDataMap jobDataMap = new JobDataMap(dataMap);
            jobBuilder = jobBuilder.usingJobData(jobDataMap);
        }

        return jobBuilder.build();
    }

    private Trigger buildTrigger(JobDetail jobDetail, String triggerGroup, String triggerDescription, ZonedDateTime startAt, CronExpression jobCronExpression) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), triggerGroup)
                .withDescription(triggerDescription)
                .startAt(Date.from(startAt.toInstant()))
                .withSchedule(CronScheduleBuilder.cronSchedule(jobCronExpression))
                .build();
    }

}
