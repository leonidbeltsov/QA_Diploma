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
4. **Запустить приложение**
    1. Запуск SUT для работы с MySQL
        - Открыть новое окно терминала и выполнить команду `java -jar ./artifacts/aqa-shop.jar`
    2. Запуск с подключением к PostgresSQL
        - Открыть файл application.properties
        - Закомментировать строку `spring.datasource.url=jdbc:mysql://localhost:3306/app`
        - Раскомментировать строку `spring.datasource.url=jdbc:postgresql://localhost:5432/app`
        - Открыть файл DBHelper.java
        - Закомментировать строку `private static final String url = "jdbc:mysql://localhost:3306/app";`
        - Раскомментировать строку `private static final String url = "jdbc:postgresql://localhost:5432/app";`
        - Открыть новое окно терминала и выполнить команду `java -jar ./artifacts/aqa-shop.jar`
5. **Запустить тесты**
   - Открыть новое окно терминала и выполнить команду `./gradlew clean test`
6. **Сформировать отчёт**
    - Выполнить в терминале команду `./gradlew allureServe`
