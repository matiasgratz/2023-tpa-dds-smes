## Luego de la devolución de Lowi surgieron algunas correcciones:

1. - [x] En el DER, corregir campos en tabla Comunidad: hay un campo que se llama miembros.
2. - [x] En el DER, corregir campos en tabla intermedia Usuario_x_Entidad: los id.
3. - [x] En el DER, completar la tabla Usuarios con los atributos que falten.
4. - [x] En el DER, código y DDC, refactor en atributo mailONumero y agregar teléfono.
5. - [ ] En el DER, código y DDC, unificar escritura en atributos: elegir entre camelCase o Snake. MUCHO CUIDADO EN EL REFACTOR.   
6. - [ ] Revisar si las comunidades pueden tener más de un criterio de agrupamiento de servicios de interés.


Notas adicionales:

Comentario de Marce: En la realización del punto 3, encontré que Usuario tiene un nuevo atributo ServicioUbicacionGps servicioUbicacion.
Lucas, entiendo que vos lo incorporaste cuando modificaste lo de las ubicaciones. Este tiene puesto un @Transient. 
Creo que esto podría no ser un atributo de Usuario ya que vamos a usar un único motor de servicioUbicación para toda la aplicación.
Quizás podemos invocarlo siempre desde un singleton. Es más, vos en el DDC ya pusiste una relación de Usa y no de Conoce.
Así también nos evitamos hacerlo persistente. Por ahora no lo agregué  entre los campos de la tabla Usuario en el DER. (Si lo agrego, Lowi nos va a preguntar). Lucas fijate si estás de acuerdo 
y de ser así habría que cambiarlo en el código y en el DDC.



