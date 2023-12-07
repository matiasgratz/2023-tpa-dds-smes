package ar.edu.utn.frba.dds.Model.TareasProgramadas;

import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

@Component("notificar")
public interface ITareasProgramadas {
    void execute() throws Exception;
}
