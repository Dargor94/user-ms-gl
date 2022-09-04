# Users Service
**Autor:** Francisco Masera

**A pedido de:** [Global Logic Latam](https://www.globallogic.com/latam/) y [Banco de Crédito e Inversiones](https://www.bci.cl/)

## Armado del proyecto en **Windows**
### Instalación de componentes necesarios:
  
  ### Clonamos este repositorio:
  Corremos el comando <code>git clone https://github.com/Dargor94/user-ms-gl.git</code> en la consola.
  
  ### Descargamos e instalamos Java:
  [JDK 1.8](https://www.oracle.com/ar/java/technologies/javase/javase8-archive-downloads.html)
  
  En este caso, usamos la versión **8u202 x64**
  Luego de instalado el jdk, ejecutamos en la consola el comando <code>java -version</code> para verificar que haya sido correcta la istalación.
  Si todo está bien, debería aparecer algo así: <code>java version #</code>, siendo "#" el número de la versión.
  
  ### Descargamos e instalamos Gradle:
  [Gradle](https://gradle.org/releases)
  
  Creamos el directorio C:\Gradle y descomprimos allí el archivo .zip descargado de la página de gradle (enlace más arriba en este documento).
  También debemos crear una variable de entorno. Para eso, abrimos un nuevo PowerShell como administador y ejecutamos el siguiente comando 
  <code>$env:PATH+=";C:\Gradle"</code>
  
  Por último, ejecutamos en la consola el comando <code>gradle -v</code> para verificar que haya sido correcta la instalación.
  Si todo está bien, debería aparecer algo así: <code>Gradle #</code>, siendo "#" el número de la versión.

  
## Corriendo el servicio
  Para correr el servicio, abrimos una consola dentro del directorio donde clonamos el repositorio y ejecutamos <code>./gradlew bootRun</code>
  Podemos entonces utilizar la colección de Postman que se encuentra en este repo bajo el directorio "/documentation/postman collection" para hacer pruebas del           servicio. Dado que la misma tiene tests para automatizar el seteo de variables, se recomienda crear un entorno en Postman, con una variable llamada TOKEN.
  También podemos usar este [workspace](https://www.postman.com/lively-capsule-969062/workspace/0f82b00e-e09e-4e80-a6bf-95a9ddf44abc/overview) público donde están la     colección accesible desde el navegador.
