## Начало работы:

### Необходимо для запуска:
- JDK 1.8+
- Apache Maven 3.8.6+ 

Скачать:
JDK:
```
https://adoptium.net/temurin/releases/?version=8
```
Apache Maven:
```
https://maven.apache.org/download.cgi
```
## Установка:
JDK:
```
https://adoptium.net/installation/windows/
```

Apache Maven:
```
https://maven.apache.org/install.html
```


## Запуск:
```shell
git clone https://github.com/yukitteru/temnet_parser.git
cd temnet_parser-master
mvn install
./mvnw spring-boot:run
```
или
```
https://github.com/yukitteru/temnet_parser/archive/refs/heads/master.zip
cd your/archive/save/location/temnet_parser-master
mvn install
./mvnw spring-boot:run
```

Ресурс доступен по адресу:
```
http://localhost:8080/
```

## Дополнительно:
Вы можете поменять URL к вашей БД, а также логин и пароль для аутентификации в файле: 
```
application.properties
```
По пути
```
src\main\resources\application.properties
```
