package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Entidad;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Establecimiento;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Incidentes.Incidente;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.PrestacionDeServicio;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Servicio;
import ar.edu.utn.frba.dds.Model.GeoRefNormalizacion.GeorefGob;
import ar.edu.utn.frba.dds.Model.GeoRefNormalizacion.ServicioDeNormalizacion;
import ar.edu.utn.frba.dds.Model.NotificacionAlUsuario.NotificarAlUsuario;
import ar.edu.utn.frba.dds.Model.NotificacionAlUsuario.NotificarPorMail;
import ar.edu.utn.frba.dds.Model.NotificacionAlUsuario.NotificarPorWhatsapp;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Comunidad;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Usuario;
import org.junit.jupiter.api.Test;


import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;

public class NotiTest {

  @Test
  void mandarWpp() throws MessagingException {
    NotificarPorWhatsapp wpp = new NotificarPorWhatsapp();
    Usuario usuarioNuevo = new Usuario("Mora", "Prueba$123", "mora@gmail.com", LocalDateTime.now());
    usuarioNuevo.setTel("+5491155729328");
    ((NotificarAlUsuario) wpp).send(usuarioNuevo, null, "Bienvenido a la comunidad");
  }

  @Test
  void mandarMail() throws MessagingException {
    NotificarPorMail mail = new NotificarPorMail();
    Usuario usuarioNuevo = new Usuario("Mora", "Prueba$123", "morasofiahidalgo@gmail.com", LocalDateTime.now());
    ((NotificarAlUsuario) mail).send(usuarioNuevo, "Bienvenido!", "Bienvenido a la comunidad");
  }

  @Test
  void cronTest() throws IOException {
    Usuario usuarioNuevo = new Usuario("Mora", "Prueba$123", "mora@gmail.com", LocalDateTime.now());
    Usuario usuarioNuevo2 = new Usuario("Mora", "Prueba$123", "morasofiahidalgo@gmail.com", LocalDateTime.now());
    usuarioNuevo.setMedioDeComunicacionPreferido(new NotificarPorWhatsapp());
    usuarioNuevo2.setMedioDeComunicacionPreferido(new NotificarPorMail());
    usuarioNuevo.setTel("+5491155729328");
    usuarioNuevo.setNotificaciones(0, 2400);
    usuarioNuevo2.setNotificaciones(0, 2400);
    ServicioDeNormalizacion servicioDeNormalizacion = new GeorefGob();

    Establecimiento diaSanMartin = new Establecimiento(servicioDeNormalizacion.normalizacionLocalizacion("Moreno", "2526", "Buenos Aires", "San Martin"), "Supermercado Día Suc. San Martín");
    Servicio puertaAccesible = new Servicio("Acceso ancho");
    PrestacionDeServicio puertaDeAccesoAnchaALaCalle = new PrestacionDeServicio("Puerta ancha de acceso al edificio", puertaAccesible, diaSanMartin);
    Entidad supermercadoDia = new Entidad("Supermercado Dia");
    Comunidad dejamePasar = new Comunidad("Dejame Pasar");
    usuarioNuevo.unirseAComunidad(dejamePasar, false);
    usuarioNuevo2.unirseAComunidad(dejamePasar, false);
    usuarioNuevo.setServiciosDeInteres(puertaAccesible);
    usuarioNuevo2.setServiciosDeInteres(puertaAccesible);
    diaSanMartin.prestacionDeServicio(puertaDeAccesoAnchaALaCalle);
    supermercadoDia.nuevoEstablecimiento(diaSanMartin);
    Incidente accesoImprovisadoNoAccesible = new Incidente(puertaDeAccesoAnchaALaCalle, "Como están pintando el frente, habilitaron un acceso provisorio que no es lo suficientemente  ancho para pasar con silla de ruedas.", "Acceso improvisado no accesible");
    dejamePasar.agregarServicioInteres(puertaAccesible);
    dejamePasar.agregarIncidente(accesoImprovisadoNoAccesible);
    dejamePasar.cerrarIncidente(accesoImprovisadoNoAccesible);
  }
}