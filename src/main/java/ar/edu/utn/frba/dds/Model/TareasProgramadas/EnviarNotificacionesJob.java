package ar.edu.utn.frba.dds.Model.TareasProgramadas;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class EnviarNotificacionesJob extends QuartzJobBean {

    private final ITareasProgramadas tarea;
    public static final String NOTIFICATION_SENDER_MAP_KEY = "NotificationSender";

    public EnviarNotificacionesJob(ITareasProgramadas tarea) {
        this.tarea = tarea;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            this.tarea.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
