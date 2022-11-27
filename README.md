# Trianafy

_Trianafy propone una API REST que permitirá generar artistas, canciones y playlist por medio de peticiones http del tipo GET, POST, PUT y DELETE. 
Pudiendo salvar los cambios en una base de datos._

_La aplicación corre bajo el framework Spring empaquetado con maven._

## Entidades

###### Artist
_Esta entidad está compuesta por su id y el nombre del artista_

###### Song
_Esta entidad está compuesta por su id, el titulo de la canción, el nombre del album, año y un Artist, teniendo está última una relación OneToMany con Artist a través de su id._

###### Playlist
_Esta entidad está compuesta por su id, su nombre, una descripción y una lista de canciones, teniendo está última una relación ManyToMany con Song a través de su id_

## Controladores
_Cada entidad está relacionada con su controlador y su repositorio de tal modo que los endpoints en cada controlador respete la estructura REST_

## Sugerencia de pruebas 📋

_Las pruebas se realizarán por defecto en el puerto designado (localhost:8080/)_

###### Colecciones json

_Por defecto se generan algunos datos para realizar pruebas.

###### Se sugiere prestar atención a los id de las entidades con los métodos GET para poder realizar las peticiones POST,PUT y DELETE correctamente.

## Documentación con swagger
_Para acceder a la documentación ejecute el proyecto maven con el plugin boot-boot:run y en su navegador acceda a esta ruta:_


* **Documentación** - [Documentación](http://localhost:8080/swagger-ui.html)

## Autores ✒️

* **Luis Miguel López Magaña** - *Proyecto Trianafy-Base* - [navalclmlopezmagana](https://github.com/lmlopezmagana)

* **Ignacio Moreno Gómez** - *Proyecto Trianafy* - [illoquehambre](https://github.com/illoquehambre)
