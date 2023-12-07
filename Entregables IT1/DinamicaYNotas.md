# Dinamica del TP
   ## 1. Definir ciertas cuestiones de la dinámica del trabajo. 
 ### - ¿Como vamos a trabajar? Dinamica propuesta por mi.
   - Nos juntamos en la semana en meet, muy posiblemente seguido porque tenemos poco tiempo.
   - Todos trabajamos sobre todos, así todos sabemos como viene el trabajo, que se hizo y que no. Como venimos haciendo en SYO / ADS y Paradigmas.
   - *Opcional dividimos los requeremientos, para mi no se justifica, no me parece que sea taanto.
1. Yo comenzaría ahora haciendo un diagrama de clases modelando el dominio.
2. ¿A que se refiere con modelo de casos de Uso? **Se refiere a la interacción con el sistema** por lo cual sería el
de las personitas
###  Entregables requeridos
1.	Modelo de Casos de Uso: diagrama de casos de uso inicial, que contemple las funcionalidades requeridas y los actores involucrados.
2.	Diagrama de clases que contemple las abstracciones principales, para ir entiendo el modelo de negocio y las necesarias para los requerimientos de seguridad.
3.	Documento con las decisiones de diseño tomadas y su justificación
4.	Implementación de un algoritmo validador de contraseñas basado en requerimiento 6.

# TODO-List
- [X] Seguimos con el diagrama de casos de uso, al trabarnos con el diagrama de clases
- [X] Le mando un mail a franco por ayudante lowi

# Reunion 30/04/2023
- [X] Discutir el diagrama de clases
- [X] Revisar el Casos de uso
- [X] Unirse al telegram de lowi
- [ ] Implementacion algoritmo
- [X] Preguntar al ayudante
- [ ] Documento de diseño
- 
# DUDAS Para Lowi
- Prestación de servicio -> **Es una clase en el sistema aparte de estacion o se maneja desde la propia estación? (responsabilidad de estacion)**
- Agrupacion de servicios, se modela o no?


# Distribucion del equipo 30/04/2023:
Para optimizar los tiempos y generar nuevas perspectivas:
Marcelo - Mora - Lucas -> Diseño
Matias - Mariano - Christian -> Implementacion del algoritmo Seguridad

**MORA --> Llevar su notebook el viernes!**

# TODO-List Seguridad:
- Crear algoritmo que contraste que la contraseña no este con el top 10.000 del archivo de texto que tenemos en el repo. (any)
- Longitud 8 caracteres? 
- Investigar los siguiente requerimiento:
- [ ]	Alinear la política de longitud, complejidad y rotación de contraseñas con las recomendaciones de la Sección 5.1.1.2 para Secretos Memorizados de la Guía NIST  800-63 .
- [ ] Limitar tiempo de contraseña tras intento fallido
y hacer el trabajo de aplicarlo


# TODO-List Seguridad END:
- [X] a menudo se requiere que las contraseñas incluyan al menos un carácter especial, un número y una letra mayúscula y minúscula.
Expresion regular o 4 archivos con el diccionario de minusculas,mayusculas,especiales y numeros
- [X] Algunas políticas pueden requerir que las contraseñas se cambien cada 90 días, mientras que otras pueden permitir períodos más largos.
**Investigar como maneja las fechas java**
Guardar dia de registro de sesion, sumarle 90 dias y comparar que el 
dia de registro de sesion+90dias sea distintos al dia de registro de sesion
FechaInicioSesion > FechadeHoy - 90 Dias



###  Entregables requeridos
1. [X]	Modelo de Casos de Uso: diagrama de casos de uso inicial, que contemple las funcionalidades requeridas y los actores involucrados.
2.	[ ] Diagrama de clases que contemple las abstracciones principales, para ir entiendo el modelo de negocio y las necesarias para los requerimientos de seguridad. 
    Faltan agregar las clases del algoritmo validador
4. Documento con las decisiones de diseño tomadas y su justificación
    Google Docs --> Colaboración
4.	[X] Implementación de un algoritmo validador de contraseñas basado en requerimiento 6.
    