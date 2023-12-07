package ar.edu.utn.frba.dds.Model.NotificacionAlUsuario.MedioEnvio;


import ar.edu.utn.frba.dds.Model.UsuariosComunidad.Usuario;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioAdapter{

  public static final String ACCOUNT_SID = "AC633f793a5342b2c3101261ca4ab56062";
  public static final String AUTH_TOKEN = "5919cb814cfa5818e0c45e216f0c14fb";

  public void send(Usuario usuario, String texto) {

    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

    String telefonoDeEnvio = "whatsapp:+14155238886";
    String telefonoRecepcion = "whatsapp:"+usuario.getTel();


    Message message = Message.creator(
            new PhoneNumber(telefonoRecepcion),
            new PhoneNumber(telefonoDeEnvio),
            texto)
        .create();
  }

}
