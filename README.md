<h2 align="center">Webflux Security Project 🛡️</h2>
<body align="left">
Это небольшое приложение в котором я попробовал использовать технологии реактивного программирования на Java и написать систему авторизации пользователей.<p>


### Общее:

 - Стек: Java 17 & Lombok + SpringBoot (Webflux, Security - JWT);
 - СУБД: Postgres (R2DBC) + flyway;
 - API & DTO генерировать через OpenAPI, документация API через swagger; (В работе)
 - Маппинг сущностей: mapstruct;


### Api:

Позволяет зарегистрировать нового пользователя
    
    POST: /api/v1/auth/register

<hr>
Позволяет авторизовать пользователя и выдаёт ему токен доступа

    POST: /api/v1/auth/login
<hr>
Возвращает информацию о пользователе (пароль скрыт)

    GET: /api/v1/auth/info

</body>