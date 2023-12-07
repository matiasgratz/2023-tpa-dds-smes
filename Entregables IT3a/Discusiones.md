Temas que quedan para dividir:
MARIANO 1- Reporte: entidades con mayor promedio de tiempo de cierre de incidentes (diferencia entre horario de cierre de incidente y horario de apertura) en la semana.
MORA 2- Reporte: entidades con mayor cantidad de incidentes reportados en la semana.
MATIAS 3- Notificaciones whatsapp y gmail (No habrá que implementarlas pero si codearlas). Armar interfaces
CHRISTIAN 4- Configurar notificaciones diaraias, todos los dias se corre un job para las notificaciones
MARCELO 5- Sugerencia de revisión de incidentes
TODOS 6- Corregir lo discutido con lowi las otras iteraciones. (colaborativamente)
TODOS 7- Ordenar el SRC eliminar cosas que no van. Comentarios fuera.
*Hacer test por cada cosa que codeemos*


TODO PARTE A:
1. [ ] RankingcantidadDeIncidentes ->  Estoy medio perdido, pero compare busqué y devuelve 0, 1 o -1. ¿Como eso se arma en un reporte?
   // https://www.geeksforgeeks.org/how-compare-method-works-in-java/  // si una comunidad lo cierra => no se toman incidentes x 24hs --> TODO Esto está implementado?
2. [ ] Class Entidad | incidentesDeLaSemana No retorna nada como debería ser la lógica

3. [ ]  Clase Usuario | configurarNotificacionesDiarias
4. [X]  Clase Usuario     notificarSiEsNecesario 
if (incidente.esDeInteres(serviciosDeInteres)) /* && No fue notificado anteriormente. */{
6. [X]  REQ 5 | Falta agregar Sugerencia de revision de incidente 
7. [X]  REQ 3 | Incidente no deberia tener un estado, abierto / cerrado.
8. [ ] Actualizar Diagrama de clases
9. [ ] Testing

TODO PARTE B:
1. [ ] Se debe permitir generar reportes de rankings en formato CSV
2. [X] Se debe permitir que las entidades reporten incidentes directamentente a los usuarios interesados