# Despliegue con Docker

En una carpeta vacía ubicar el archivo docker-compose.yml (que esta en la raiz del proyecto del repositorio) y en esa misma carpeta ubicar el repositorio:

- **https://github.com/stevenv17/PruebaNexos.git**

![imagen](./imagenes/Imagen1.png)

Ubicarse en la raíz del microservicio y crear el archivo jar con el comando “**./mvnw clean package -DskipTest**”:

![imagen](./imagenes/Imagen2.png)

![imagen](./imagenes/Imagen3.png)

Después desde la ruta donde está ubicado el archivo docker-compose.yml ejecutar el comando “docker-compose up -d” para crear y subir los contenedores de los microservicio y la base de datos:

![imagen](./imagenes/Imagen4.png)


![imagen](./imagenes/Imagen5.png)

Y ejecutar los scripts del **archivo nexos_scripts-mysql.sql** (en la raiz del proyecto) para crear modelo de base de datos.

![imagen](./imagenes/Imagen6.png)


# Colección peticiones:

En la raiz del proyecto esta la colección de peticiones (Insomnia_collection_nexos.json) que puede importarse a Insomnia o Postman:

![imagen](./imagenes/Imagen7.png)


![imagen](./imagenes/Imagen8.png)


# Modelo base de datos:

![imagen](./imagenes/Imagen9.png)

### Data inicial:

Se creó data inicial para cumplir con el objetivo de la prueba. Se crearon personas, productos, tipos de productos, estados de tarjeta, tipos de tarjeta y estados de transacciones.
Por ejemplo, si el producto es de tipo cuenta de ahorros (SAVING_ACCOUNT), se crea una tarjeta de tipo débito (DEBIT).

![imagen](./imagenes/Imagen9.png)
![imagen](./imagenes/Imagen10.png)
![imagen](./imagenes/Imagen11.png)
![imagen](./imagenes/Imagen12.png)
![imagen](./imagenes/Imagen13.png)
![imagen](./imagenes/Imagen14.png)