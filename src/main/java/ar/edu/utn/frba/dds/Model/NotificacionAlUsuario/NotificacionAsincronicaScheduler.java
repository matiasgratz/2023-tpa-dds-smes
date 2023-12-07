package ar.edu.utn.frba.dds.Model.NotificacionAlUsuario;

import ar.edu.utn.frba.dds.Model.Repositorios.RepositorioUsuarios;
import org.quartz.*;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Usuario;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
public class NotificacionAsincronicaScheduler {
    static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    public static void main(String[] args) throws SchedulerException {


            RepositorioUsuarios repositorio = new RepositorioUsuarios();

            repositorio.getRepositorioUsuarios().forEach(NotificacionAsincronicaScheduler::programarTareaDiaria);

    }

        private static void programarTareaDiaria(Usuario usuario) {
        int startNotificacion = usuario.getStartNotificacion();
        int finishNotificacion = usuario.getFinishNotificacion();

        // Calcula el retraso hasta la primera ejecución en función de la hora actual
        LocalDateTime now = LocalDateTime.now();
        int currentHour = now.getHour();
        long initialDelay = calcularInitialDelay(currentHour, startNotificacion);

        // Programa la tarea para ejecutarse diariamente a la hora de inicio de notificaciones
        scheduler.scheduleAtFixedRate(usuario::notificarmeIncidentesActivosYNoNotificados,
                initialDelay, 24, TimeUnit.HOURS);
    }

    private static long calcularInitialDelay(int currentHour, int startNotificacion) {
        if (currentHour <= startNotificacion) {
            // Si la hora actual es antes de la hora de inicio, la tarea se programa para hoy
            return startNotificacion - currentHour;
        } else {
            // Si la hora actual es después de la hora de inicio, la tarea se programa para mañana
            return 24 - (currentHour - startNotificacion);
        }
    }

}
