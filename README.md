# T1-homework

## На данный момент активная ветка: `featurer/task-3`

---

## Структура проекта:

В корне проекта находятся:

- Файлы для докера
- Родительский pom.xml
- Три микросервиса:
  - AccountProcessing
  - ClientProcessing
  - CreditProcessing

Все сервисы имеют схожую структуру:

```text
# Внутренняя структура каждого микросервиса

* pom.xml                                 - дочерние pom файлы
* src/
    * main/
    |    * java/my/project/...Processing/
    |    |    * entity/                   - сущности
    |    |    * ...ProcessingApplication  - входная точка приложения
    |    |
    |    * resources/
    |    |    * db/migration/              - миграции + генерация тестовых данных
    |    |    * application.yaml
```

## Запуск проекта:

```bash
    docker compose build
    docker compose up
```
Будет запущено:

- Три контейнера для сервисов: AccountProcessing, ClientProcessing и CreditProcessing
- Три контейнера для баз данных - для каждого сервиса своя БД + заполнены тестовыми данными
