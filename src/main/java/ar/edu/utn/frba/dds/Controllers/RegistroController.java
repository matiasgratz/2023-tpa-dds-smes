package ar.edu.utn.frba.dds.Controllers;
import ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario.excepciones.EmailYaRegistrado;
import ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario.excepciones.PasswordException;
import ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario.registradores.RegistrarUsuario;
import ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario.validaciones.*;
import ar.edu.utn.frba.dds.Model.NotificacionAlUsuario.NotificarAlUsuario;
import ar.edu.utn.frba.dds.Model.NotificacionAlUsuario.NotificarPorMail;
import ar.edu.utn.frba.dds.Model.NotificacionAlUsuario.NotificarPorWhatsapp;
import ar.edu.utn.frba.dds.Model.Repositorios.RepositorioUsuarios;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class RegistroController implements WithSimplePersistenceUnit {

    boolean error_email = false;
    private final List<ValidacionContrasenia> validaciones = Arrays.asList(new CaracterRepetido(), new LongitudMinimaContrasenia(), new ValidacionTopPeoresContrasenia(), new FormatoContrasenia());

    public ModelAndView mostrarFormularioRegistro(Request request, Response response) {
        Map<String, Object> modelo = new HashMap<>();
        modelo.put("test2", true);
        modelo.put("error_email", error_email);
        return new ModelAndView(modelo, "registro.html.hbs");
    }

    public void registrarUsuario(String usuario, String email, String contrasenia, int start, int fin, NotificarAlUsuario medioDeComunicacionPreferido) throws EmailYaRegistrado {

        Usuario usuarioNuevo = RepositorioUsuarios.instance().registrarUsuario(usuario, email, contrasenia);

        usuarioNuevo.setMedioDeComunicacionPreferido(medioDeComunicacionPreferido);
        usuarioNuevo.setNotificaciones(start, fin);

        entityManager().persist(medioDeComunicacionPreferido);
        entityManager().persist(usuarioNuevo);

    }

    public ModelAndView registrar(Request request, Response response) {

        Map<String, Object> modelo = new HashMap<>();
        String usuario = request.queryParams("usuario");
        String email = request.queryParams("email");
        int telefono = Integer.parseInt(request.queryParams("telefono"));
        String contrasenia = request.queryParams("contrasenia");

        String notificacion = request.queryParams("notificacion");
        String startNoti = request.queryParams("startNotificacion");
        String finishNoti = request.queryParams("finishNotificacion");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        int startNotificacion = 0;
        int finishNotificacion = 0;
        try {

            LocalTime horaInicio = LocalTime.parse(startNoti, formatter);
            LocalTime horaFin = LocalTime.parse(finishNoti, formatter);

            int horasInicio = horaInicio.getHour();
            int minutosInicio = horaInicio.getMinute();
            int horasFin = horaFin.getHour();
            int minutosFin = horaFin.getMinute();

            startNotificacion = (horasInicio * 100) + minutosInicio;
            finishNotificacion = (horasFin * 100) + minutosFin;

        } catch (Exception e) {
            // Maneja la excepción si la cadena no tiene el formato esperado
            System.out.println("La cadena no tiene el formato HH:MM");
        }
        NotificarAlUsuario notificarAlUsuario;
        if ("email".equals(notificacion)) {
            notificarAlUsuario = new NotificarPorMail();
        } else if ("telefono".equals(notificacion)) {
            notificarAlUsuario = new NotificarPorWhatsapp();
        } else {
            notificarAlUsuario = null;
        }
        try {
            int finalStartNotificacion = startNotificacion;
            int finalFinishNotificacion = finishNotificacion;
            withTransaction(() -> {
                try {
                    RepositorioUsuarios.instance().registrarUsuario(usuario, email, contrasenia);
                    //registrarUsuario(usuario, email, contrasenia, finalStartNotificacion, finalFinishNotificacion, notificarAlUsuario);

                } catch (EmailYaRegistrado e) {
                    throw new RuntimeException(e);
                }catch (PasswordException e) {
                    response.redirect("http://50.19.51.60/registro");
                }
            });
            response.redirect("http://50.19.51.60/login");
        }
        catch (RuntimeException e) {
            error_email = true;
            response.redirect("http://50.19.51.60/registro");
        }


        return new ModelAndView(modelo, "registro.html.hbs");
    }
}
