## Начало работы:

### Необходимо для запуска:
- JDK 1.8
- Apache Maven 3.8.6 

Скачать:
JDK 1.8
```
https://adoptium.net/temurin/releases/?version=8
```
Apache Maven
```
https://maven.apache.org/download.cgi
```
## Установка:
JDK 1.8
```
https://adoptium.net/installation/windows/
```

Apache Maven
```
https://maven.apache.org/install.html
```


## Запуск:
```shell
git clone https://github.com/yukitteru/temnet_parser.git
cd temnet_parser-master/
mvn install
```
или
```
https://github.com/yukitteru/temnet_parser/archive/refs/heads/master.zip
cd your/archive/save/location/temnet_parser-master
mvn install
```

## Дополнительно:
Вы можете поменять URL к вашей БД, а также логин и пароль для аутентификации в файле 
```
application.properties
```
По пути
```
src\main\resources\application.properties
```