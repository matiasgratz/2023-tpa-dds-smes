# Iteracion 2 - Intercambio y ToDOList
Charlamos sobre los puntos del dominio con distintas perspectivas.
Removimos la parte de validacion, ya que queda en el sistema pero avanzamos con otra iteracion.

## Tenemos que definir:
### DISEÑO
- [X] El boolean funciona en la clase Servicio, relacionado con serviciosDeInteres
de la clase UsuarioPersona. (HACEMOS UNA CLASE INTERMEDIA O NUEVA CLASE)
- [ ] Terminar definir entidadPrestadora y organismo de control. (+ LO DEL CSV)
- [ ] API y localizacion de las personas y entidades (ENTENDER QUE NECESITAMOS DE LA API)
- [X] Perdemos abstraccion en servicioPublico (ferrocarril y subte). Supermercados,centros
comunales y bancos ¿va con ENUM?


UsuarioEmpresa y UsuarioPersona heredan de un Usuario *HECHO*


### CODIGO
CSV: CODEAR LA CLASE QUE IMPORTA ESO.

### QUE DIA NOS JUNTAMOS: PLANTEO (MIENTRAS SEAN 3-4)
JUEVES A LA TARDE DE 17-19 (MARC-LUCAS-MATIAS)
VIERNES (MARC OUT - MATIAS FREE)
SABADO (MATIAS FREE)


## Entregables requeridos
1. [ ]	Modelo de Casos de Uso (CU): actualización del modelo con las funcionalidades previstas en esta entrega
2. [ ] Modelo de Objetos (DDC): actualización del modelo con las funcionalidades previstas en esta entrega
3. [ ]	Implementación de la carga masiva de datos de entidades prestadoras y organismos de control
4. [ ]	Implementación de la integración con el servicio GeoRef API
5. [ ]	Documento con el diseño de archivo csv para la carga de entidades prestadoras y organismos de control
6. [ ]	Documento con las decisiones de diseño tomadas y su justificación


## Cosultas a Lowi

1. Preguntar sobre la API 
    a. Si la usamos para normalizar los datos cuando creamos una nueva entidad Ubicación para el establecimient y Localizacion para el Usuario
    b. Como hacemos el llamado a la API para normalizarlo.
2. Preguntar sobre Modelo de prestadoras y organismos de control
    a. Si el usuario de Coto delega a un UsuarioMiembro o Si el usuarioCoto es directamente quien tiene la responsabilidad
    b. Que tipos de datos hay que cargar? Es el usuarioEmpresa el que se carga, o es la entidad de la empresa y/o Organismo de control que luego delega a el UsuarioPersona
    c. Si es el usuario persona al que se delega se carga en el CSV eso? 

