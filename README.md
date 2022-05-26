#### Необходимое окружение:
- установленный Docker

### Запуск SUT
1. Клонировать репозиторий командой git clone
2. Открыть проект в IntelliJ IDEA Ultimate
3. Запустить контейнеры БД командой docker-compose up -d
4. Ввести в терминале команду "java -jar artifacts/aqa-shop.jar"

### Запуск тестов и отчетов Allure
1. Для запуска тестов в терминале ввести команду ./gradlew clean test allureReport -Dheadless=true (подготовка данных для отчета Allure и запуск авто-тестов в headless-режиме (без открытия браузера)).
2. Для просмотра отчета Allure в терминале ввести команд ./gradlew allureReport
