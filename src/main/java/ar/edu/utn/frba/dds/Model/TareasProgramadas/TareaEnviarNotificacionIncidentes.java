package ar.edu.utn.frba.dds.Model.TareasProgramadas;

import ar.edu.utn.frba.dds.Model.Repositorios.RepositorioUsuarios;
import ar.edu.utn.frba.dds.Model.Repositorios.RepositorioUsuarios;
import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Usuario;
import org.springframework.stereotype.Component;

@Component("notificar")
public class TareaEnviarNotificacionIncidentes implements ITareasProgramadas{
    @Override
    public void execute() throws Exception {
        RepositorioUsuarios.instance().getRepositorioUsuarios().forEach(Usuario::notificarmeIncidentesActivosYNoNotificados);
    }
}
