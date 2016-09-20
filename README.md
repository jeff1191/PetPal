# PetPal
Proyecto de PAD

Este proyecto es una aplicación móvil híbrida utilizando Android como parte nativa y tecnologias web para la parte web(valga la redundancia). El proyecto consiste en una red social de mascotas, la idea es ayudar a encontrar a tus mascotas usando esta app destinada a ello(exclusivamente).

La aplicación tiene dos partes fundamentales en cuanto a su arquitectura, una es la parte de la WebView y otra la parte nativa de Android. 

- Para la WebView primero la enlazamos a una página php en el servidor   y después utilizamos peticiones GET, POST con AJAX/JQueryMobile. Una vez que obtenemos la información utilizamos una vez más JQueryMobile para mostrar resultados dependiendo de lo que nos ha devuelto el  correspondiente archivo php, esto  depende del evento que ha generado el usuario desde la interfaz gráfica.
Para enviar peticiones en Android utilizamos la librería Volley desarrollada por Google para optimizar el envío de peticiones Http desde los dispositivos hacia servidores externos, la comunicación lo hace a través de JSON(JavaScript Object Notation), que es un formato ligero para el intercambio de datos. En la figura 12 se muestra un esquema general de la librería Volley.

![Alt text](https://lh3.googleusercontent.com/XHfMV2JldJTvICSmcQOZFFJKWv0RE1qcgHeet5bXgK9eVLvMEg7HYmiOcoreUH5m0iyYo5kcJ0k0UwY=w1366-h585?raw=true "Web/Volley")

- En cuanto a la estructura de la aplicación nativa de Android hemos utilizado la arquitectura multicapa, ya que nos proporciona integración y reusabilidad, encapsulación, distribución, escalabilidad, manejabilidad, mejora el rendimiento y mejora la fiabilidad.

  En resumen, la arquitectura multicapa se divide en tres capas: presentación, negocio e integración o acceso a los datos.
  La capa de presentación es la encargada de la interacción directa con el usuario, es la responsable de proporcionar los servicios del sistema. Además debe ser amigable, entendible y fácil de usar. 
  La capa de negocio proporciona las reglas  y servicios del sistema. Es la encargada de recibir las peticiones que le llegan de la capa de presentación, comunicarse con la capa de integración, procesar los datos y devolver los resultados.
  La capa de integración es responsable de la comunicación con recursos y sistemas externos. Normalmente está formada por uno o más gestores de bases de datos.


![Alt text](https://lh5.googleusercontent.com/6JOaNJY7l82h0D3cCtxMwkYfiU9HwvXQW4Ie3iOMI0fbwvR_kfyBAHS19CxGXIZ1tBBMYLzlkRspBf4=w1366-h585?raw=true "Arquitectura multicapa")
