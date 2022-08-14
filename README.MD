## Начало работы:

### Необходимо для запуска:
- JDK 11
- Apache Maven 3.8.5+ 

Скачать:
JDK:
```
https://adoptium.net/temurin/releases/?version=11
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
### Скачать:
```shell
git clone https://github.com/yukitteru/temnet_parser.git
cd temnet_parser
```
или
```
wget https://github.com/yukitteru/temnet_parser/archive/refs/heads/master.zip
cd your/unzip/archive/save/location/temnet_parser-master
```
### Запустить:
```
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
