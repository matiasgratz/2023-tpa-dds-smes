package ar.edu.utn.frba.dds.main;

import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Entidad;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Establecimiento;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Incidentes.Incidente;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.PrestacionDeServicio;
import ar.edu.utn.frba.dds.Model.EntidadesEstablecimientosServicios.Servicio;
import ar.edu.utn.frba.dds.Model.GeoRefNormalizacion.GeorefGob;
import ar.edu.utn.frba.dds.Model.GeoRefNormalizacion.ServicioDeNormalizacion;
import ar.edu.utn.frba.dds.Model.IniciarSesionYRegistrarUsuario.excepciones.EmailYaRegistrado;
import ar.edu.utn.frba.dds.Model.NotificacionAlUsuario.NotificarAlUsuario;
import ar.edu.utn.frba.dds.Model.NotificacionAlUsuario.NotificarPorMail;
import ar.edu.utn.frba.dds.Model.Repositorios.RepositorioUsuarios;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Comunidad;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.checkerframework.checker.units.qual.C;


/** Ejecutar antes de levantar el servidor por primera vez */

public class Bootstrap implements WithSimplePersistenceUnit {

  public static void main(String[] args) {
    new Bootstrap().run();
  }

  public void run() {
    withTransaction(() -> {
      try {
        inicializarEstados();
      } catch (IOException | EmailYaRegistrado e) {
        throw new RuntimeException(e);
      }

    });
  }

  public void inicializarEstados() throws IOException, EmailYaRegistrado {

    Usuario usuario1 = RepositorioUsuarios.instance().registrarUsuario("Lucas", "lucasaffre@gmail.com", "Prueba$159");
    Usuario usuario2 = RepositorioUsuarios.instance().registrarUsuario("Pedro", "affre@gmail.com", "Prueba$159");
    Usuario usuario3 = RepositorioUsuarios.instance().registrarUsuario("Matias", "matiasgratz11@gmail.com", "Prueba$159");
    Usuario usuario4 = RepositorioUsuarios.instance().registrarUsuario("Marcelo", "mimail@gmail.com", "Prueba$159");
    Usuario usuario5 = RepositorioUsuarios.instance().registrarUsuario("Mora","momahidalgo@gmail.com","Prueba$123");
    usuario1.setNotificaciones(0,2400);
    usuario2.setNotificaciones(0,2400);
    usuario3.setNotificaciones(0,2400);
    usuario4.setNotificaciones(0,2400);
    usuario5.setNotificaciones(0, 2400);

    RepositorioUsuarios.instance().getRepositorioUsuarios().forEach(Usuario::programarNotificaciones);

    ArrayList<Entidad> entidades = new ArrayList<>();
    List<Comunidad> comunidades = new ArrayList<>();


    ServicioDeNormalizacion servicioDeNormalizacion = new GeorefGob();

    Establecimiento diaSanMartin = new Establecimiento(servicioDeNormalizacion.normalizacionLocalizacion("Moreno", "2526", "Buenos Aires", "San Martin"), "Supermercado Día Suc. San Martín");
    persist(diaSanMartin);
    Establecimiento santanderFloresta = new Establecimiento(servicioDeNormalizacion.normalizacionLocalizacion("Mercedes", "1100", "Ciudad Autónoma de Buenos Aires", "Comuna 10"), "Banco Santander Suc. Floresta");
    persist(santanderFloresta);
    Establecimiento utnCampus = new Establecimiento(servicioDeNormalizacion.normalizacionLocalizacion("Mozart", "2300", "Ciudad Autónoma de Buenos Aires", "Comuna 8"), "UTN Campus");
    persist(utnCampus);
    Establecimiento subteBEstacionTronador = new Establecimiento(servicioDeNormalizacion.normalizacionLocalizacion("Avenida Triunvirato", "3100", "Ciudad Autónoma de Buenos Aires", "Comuna 15"), "Línea de Subte B Estación Tronador");
    persist(subteBEstacionTronador);
    Establecimiento shoppingAbasto = new Establecimiento(servicioDeNormalizacion.normalizacionLocalizacion("Avenida Corrientes", "3247", "Ciudad Autónoma de Buenos Aires", "Comuna 3"), "Shopping Abasto");
    persist(shoppingAbasto);

    Servicio puertaAccesible = new Servicio("Acceso ancho");
    persist(puertaAccesible);
    Servicio banio = new Servicio("Baño");
    persist(banio);
    Servicio escaleraMecanica = new Servicio("Escalera Mecánica");
    persist(escaleraMecanica);
    Servicio ascensor = new Servicio("Ascensor");
    persist(ascensor);
    Servicio rampa = new Servicio("Acceso por rampa");
    persist(rampa);


    PrestacionDeServicio puertaDeAccesoAnchaALaCalle = new PrestacionDeServicio("Puerta ancha de acceso al edificio", puertaAccesible, diaSanMartin);
    persist(puertaDeAccesoAnchaALaCalle);
    PrestacionDeServicio rampaALaCalle = new PrestacionDeServicio("Acceso al edificio con rampa", rampa, santanderFloresta);
    persist(rampaALaCalle);
    PrestacionDeServicio banioHombres = new PrestacionDeServicio("Baño de hombres segundo pasillo", banio, utnCampus);
    persist(banioHombres);
    PrestacionDeServicio banioMujeres = new PrestacionDeServicio("Baño de mujeres comedor", banio, utnCampus);
    persist(banioMujeres);
    PrestacionDeServicio ascensor0 = new PrestacionDeServicio("Único Ascensor", ascensor, utnCampus);
    persist(ascensor0);
    PrestacionDeServicio rampaEntrada = new PrestacionDeServicio("Rampa de acceso desde la Calle", rampa, utnCampus);
    persist(rampaEntrada);
    PrestacionDeServicio puertaEntrada = new PrestacionDeServicio("Puerta principal al edificio", puertaAccesible, utnCampus);
    persist(puertaEntrada);
    PrestacionDeServicio escaleraMecanica1 = new PrestacionDeServicio("Escalera mecánica acceso a la estación", escaleraMecanica, subteBEstacionTronador);
    persist(escaleraMecanica1);
    PrestacionDeServicio escaleraMecanica2 = new PrestacionDeServicio("Escalera mecánica acceso al andén", escaleraMecanica, subteBEstacionTronador);
    persist(escaleraMecanica2);
    PrestacionDeServicio ascensor1 = new PrestacionDeServicio("Ascensor acceso a la estación", ascensor, subteBEstacionTronador);
    persist(ascensor1);
    PrestacionDeServicio ascensor2 = new PrestacionDeServicio("Ascensor acceso al andén", ascensor, subteBEstacionTronador);
    persist(ascensor2);
    PrestacionDeServicio banio1 = new PrestacionDeServicio("Baño de hombres", banio, subteBEstacionTronador);
    persist(banio1);
    PrestacionDeServicio banio2 = new PrestacionDeServicio("Baño de mujeres", banio, subteBEstacionTronador);
    persist(banio2);
    PrestacionDeServicio escaleraMecanica3 = new PrestacionDeServicio("Escalera mecánica PB", escaleraMecanica, shoppingAbasto);
    persist(escaleraMecanica3);
    PrestacionDeServicio escaleraMecanica4 = new PrestacionDeServicio("Escalera mecánica 1er piso", escaleraMecanica, shoppingAbasto);
    persist(escaleraMecanica4);
    PrestacionDeServicio ascensor3 = new PrestacionDeServicio("Ascensor principal", ascensor, shoppingAbasto);
    persist(ascensor3);
    PrestacionDeServicio banio3 = new PrestacionDeServicio("Baño Hombres PB", banio, shoppingAbasto);
    persist(banio3);
    PrestacionDeServicio banio4 = new PrestacionDeServicio("Baño Mujeres PB", banio, shoppingAbasto);
    persist(banio4);



    Entidad supermercadoDia = new Entidad("Supermercado Dia");
    Entidad bancoSantander = new Entidad("Banco Santander");
    Entidad utn = new Entidad("UTN");
    Entidad subteB = new Entidad("Subte Línea B");
    Entidad grupoIrsa = new Entidad("Grupo IRSA");


    Comunidad pipiClub = new Comunidad("Pipi Club");
    Comunidad subenYBajan = new Comunidad("Suben y Bajan");
    Comunidad dejamePasar = new Comunidad("Dejame Pasar");
    Comunidad ondaEscalonada = new Comunidad("Onda Escalonada");
    Comunidad superExigentes = new Comunidad("Super Exigentes");
    Comunidad escalonesHaters = new Comunidad("Escalones Haters");



    diaSanMartin.prestacionDeServicio(puertaDeAccesoAnchaALaCalle);
    persist(diaSanMartin);
    santanderFloresta.prestacionDeServicio(rampaALaCalle);
    persist(santanderFloresta);
    utnCampus.prestacionDeServicio(banioHombres);
    utnCampus.prestacionDeServicio(banioMujeres);
    utnCampus.prestacionDeServicio(ascensor0);
    utnCampus.prestacionDeServicio(rampaEntrada);
    utnCampus.prestacionDeServicio(puertaEntrada);
    persist(utnCampus);
    subteBEstacionTronador.prestacionDeServicio(escaleraMecanica1);
    subteBEstacionTronador.prestacionDeServicio(escaleraMecanica2);
    subteBEstacionTronador.prestacionDeServicio(ascensor1);
    subteBEstacionTronador.prestacionDeServicio(ascensor2);
    subteBEstacionTronador.prestacionDeServicio(banio1);
    subteBEstacionTronador.prestacionDeServicio(banio2);
    persist(subteBEstacionTronador);
    shoppingAbasto.prestacionDeServicio(escaleraMecanica3);
    shoppingAbasto.prestacionDeServicio(escaleraMecanica4);
    shoppingAbasto.prestacionDeServicio(ascensor3);
    shoppingAbasto.prestacionDeServicio(banio3);
    shoppingAbasto.prestacionDeServicio(banio4);
    persist(shoppingAbasto);


    supermercadoDia.nuevoEstablecimiento(diaSanMartin);
    persist(supermercadoDia);
    bancoSantander.nuevoEstablecimiento(santanderFloresta);
    persist(bancoSantander);
    utn.nuevoEstablecimiento(utnCampus);
    persist(utn);
    subteB.nuevoEstablecimiento(subteBEstacionTronador);
    persist(subteB);
    grupoIrsa.nuevoEstablecimiento(shoppingAbasto);
    persist(grupoIrsa);



    Incidente accesoImprovisadoNoAccesible = new Incidente(puertaDeAccesoAnchaALaCalle, "Como están pintando el frente, habilitaron un acceso provisorio que no es lo suficientemente  ancho para pasar con silla de ruedas.", "Acceso improvisado no accesible");
    persist(accesoImprovisadoNoAccesible);
    Incidente rampaPintada = new Incidente(rampaALaCalle, "Pintura fresca en la rampa de acceso", "Rampa con pintura fresca");
    persist(rampaPintada);
    Incidente banioHombresInundado = new Incidente(banioHombres, "Se rompió una canilla y está todo inundado", "Baño inundado");
    persist(banioHombresInundado);
    Incidente banioMujeresClausurado = new Incidente(banioHombres, "Cerrado con llave con cartel de clausurado", "Baño clausurado");
    persist(banioMujeresClausurado);
    Incidente ascensorNoFunciona = new Incidente(ascensor0, "Se quedó clavado en el primer piso", "Ascensor fuera de servicio");
    persist(ascensorNoFunciona);
    Incidente rampaEntradaObstruida = new Incidente(rampaEntrada, "Algún copado dejó atada su bici y obstruye el acceso", "Rampa de acceso obstruida");
    persist(rampaEntradaObstruida);
    Incidente puertaPpalCerrada = new Incidente(puertaEntrada, "Tareas de mantenimiento", "Puerta principal cerrada");
    persist(puertaPpalCerrada);
    Incidente escaleraMecanicaParada = new Incidente(escaleraMecanica1, "No se mueve", "Escalera mecánica se frenó");
    persist(escaleraMecanicaParada);
    Incidente escaleraMecanicaEnMantenimiento = new Incidente(escaleraMecanica2, "La están arreglando. Está toda desarmada, pinta para rato", "Escalera mecánica en mantenimiento");
    persist(escaleraMecanicaEnMantenimiento);
    Incidente ascensor1NoFunciona = new Incidente(escaleraMecanica1, "Fuera de Servicio", "Ascensor fuera de servicio");
    persist(ascensor1NoFunciona);
    Incidente banio2sinLuz = new Incidente(banio2, "No se ve nada. Difícil hacer con la linterna del celu", "Baño sin luz");
    persist(banio2sinLuz);
    Incidente escaleraMecanica3Out = new Incidente(escaleraMecanica3, "Toda desarmada", "Escalera en mantenimiento");
    persist(escaleraMecanica3Out);
    Incidente ascensor3Dificil = new Incidente(ascensor3, "Por un desperfecto no se frena a nivel del piso. Difícil acceso con silla de ruedas.", "Acceso al ascensor con desnivel");
    persist(ascensor3Dificil);
    Incidente banio4Asco = new Incidente(banio4, "No sé si es más sucia la que lo hizo o el Shopping que no lo limpia. Creeme que no se puede entrar.", "Baño no en condiciones");
    persist(banio4Asco);


    pipiClub.agregarServicioInteres(banio);
    subenYBajan.agregarServicioInteres(ascensor);
    subenYBajan.agregarServicioInteres(escaleraMecanica);
    dejamePasar.agregarServicioInteres(puertaAccesible);
    dejamePasar.agregarServicioInteres(rampa);
    ondaEscalonada.agregarServicioInteres(escaleraMecanica);
    superExigentes.agregarServicioInteres(puertaAccesible);
    superExigentes.agregarServicioInteres(banio);
    superExigentes.agregarServicioInteres(escaleraMecanica);
    superExigentes.agregarServicioInteres(ascensor);
    superExigentes.agregarServicioInteres(rampa);
    escalonesHaters.agregarServicioInteres(ascensor);


    pipiClub.agregarIncidente(banioHombresInundado);
    pipiClub.agregarIncidente(banioMujeresClausurado);
    pipiClub.agregarIncidente(banio2sinLuz);
    pipiClub.agregarIncidente(banio4Asco);

    subenYBajan.agregarIncidente(ascensorNoFunciona);
    subenYBajan.agregarIncidente(escaleraMecanicaParada);
    subenYBajan.agregarIncidente(escaleraMecanicaEnMantenimiento);
    subenYBajan.agregarIncidente(ascensor1NoFunciona);
    subenYBajan.agregarIncidente(escaleraMecanica3Out);
    subenYBajan.agregarIncidente(ascensor3Dificil);

    dejamePasar.agregarIncidente(accesoImprovisadoNoAccesible);
    dejamePasar.agregarIncidente(rampaPintada);
    dejamePasar.agregarIncidente(rampaEntradaObstruida);
    dejamePasar.agregarIncidente(puertaPpalCerrada);

    ondaEscalonada.agregarIncidente(escaleraMecanicaParada);
    ondaEscalonada.agregarIncidente(escaleraMecanicaEnMantenimiento);
    ondaEscalonada.agregarIncidente(escaleraMecanica3Out);

    superExigentes.agregarIncidente(accesoImprovisadoNoAccesible);
    superExigentes.agregarIncidente(rampaPintada);
    superExigentes.agregarIncidente(banioHombresInundado);
    superExigentes.agregarIncidente(banioMujeresClausurado);
    superExigentes.agregarIncidente(ascensorNoFunciona);
    superExigentes.agregarIncidente(rampaEntradaObstruida);
    superExigentes.agregarIncidente(puertaPpalCerrada);
    superExigentes.agregarIncidente(escaleraMecanicaParada);
    superExigentes.agregarIncidente(escaleraMecanicaEnMantenimiento);
    superExigentes.agregarIncidente(ascensor1NoFunciona);
    superExigentes.agregarIncidente(banio2sinLuz);
    superExigentes.agregarIncidente(escaleraMecanica3Out);
    superExigentes.agregarIncidente(ascensor3Dificil);
    superExigentes.agregarIncidente(banio4Asco);

    escalonesHaters.agregarIncidente(ascensorNoFunciona);
    escalonesHaters.agregarIncidente(ascensor1NoFunciona);
    escalonesHaters.agregarIncidente(ascensor3Dificil);


    persist(pipiClub);
    persist(subenYBajan);
    persist(ondaEscalonada);
    persist(dejamePasar);
    persist(superExigentes);
    persist(escalonesHaters);


    usuario1.unirseAComunidad(pipiClub, false);
    usuario1.unirseAComunidad(subenYBajan, true);
    usuario1.unirseAComunidad(superExigentes, true);
    usuario1.unirseAComunidad(escalonesHaters, false);

    usuario2.unirseAComunidad(pipiClub, false);
    usuario2.unirseAComunidad(ondaEscalonada, true);
    usuario2.unirseAComunidad(superExigentes, false);

    usuario3.unirseAComunidad(subenYBajan, true);
    usuario3.unirseAComunidad(dejamePasar, false);
    usuario3.unirseAComunidad(superExigentes, true);
    usuario3.unirseAComunidad(escalonesHaters, true);

    usuario4.unirseAComunidad(dejamePasar, true);
    usuario4.unirseAComunidad(ondaEscalonada, false);
    usuario4.unirseAComunidad(superExigentes, true);
    usuario4.unirseAComunidad(escalonesHaters, false);

    usuario5.unirseAComunidad(dejamePasar, false);
    usuario5.unirseAComunidad(ondaEscalonada, true);
    usuario5.setServiciosDeInteres(puertaAccesible);
    usuario5.setServiciosDeInteres(banio);
    usuario5.setServiciosDeInteres(escaleraMecanica);


    persist(usuario1);
    persist(usuario2);
    persist(usuario3);
    persist(usuario4);
    persist(usuario5);
  }
}









