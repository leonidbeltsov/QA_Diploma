# Дипломный проект профессии «Тестировщик»
## Документация
- [План автоматизации тестирования](https://github.com/leonidbeltsov/QA_Diploma/blob/master/docs/Plan.md);
- [Отчёт по итогам автоматизированного тестирования](https://github.com/leonidbeltsov/QA_Diploma/blob/master/docs/Report.md);
- [Отчёт по итогам автоматизации](https://github.com/leonidbeltsov/QA_Diploma/blob/master/docs/Summary.md).

## Инструкция по запуску
1. **Клонировать проект из репозитория**
    - Открыть `Git Bash` и выполнить команду `git clone https://github.com/leonidbeltsov/QA_Diploma`
   

2. **Открыть проект в IDE**
    - Открыть проект с помощью `IntelliJ IDEA`
   

3. **Запустить контейнер с СУБД и симулятором сервисов Банка**
    - Внутри `IntelliJ IDEA` запустить терминал и выполнить команду `docker-compose up -d`
    - Дождаться появления уведомления в терминале:
    ```
   - Network qa_diploma_default  Created
   - Container postgres          Started
   - Container node              Started
   - Container mysql             Started
   ```
   _!!! При первичном запуске контейнеров возможно ошибка загрузки, в этом случае необходимо повторить запуск контейнеров командой `docker-compose up -d`_


4. **Запустить приложение**

    В зависимости о выбранной БД выполнить соотвествующую команду в терминале IDE:

   - для MySQL:`java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar ./artifacts/aqa-shop.jar`
   - для Postgres:`java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar ./artifacts/aqa-shop.jar`
   - Проверить запуск приложениея открыв в браузере страницу [http://localhost:8080/](http://localhost:8080/)
   

5. **Запустить тесты**

   В зависимости о выбранной БД выполнить соотвествующую команду в терминале IDE:

    - для MySQL:`./gradlew clean test -Durl=jdbc:mysql://localhost:3306/app -Duser=app -Dpassword=pass`
    - для Postgres:`./gradlew clean test -Durl=jdbc:postgresql://localhost:5432/app -Duser=app -Dpassword=pass`


6. **Сформировать отчёт**
    - Открыть новую вкладку терминала IDE и выполнить команду `./gradlew allureServe`
