# KursGoldenRingWeather

Android-приложение для курсовой работы: прогноз погоды по городам **Золотого кольца России**.

## Возможности

- список классических городов Золотого кольца;
- выбор города и загрузка текущей погоды;
- прогноз на 7 дней;
- определение координат через Api-Ninjas;
- загрузка погоды через Open-Meteo;
- сохранение последнего выбранного города через Preferences DataStore;
- XML UI + MVVM + Repository + Retrofit + Coroutines + StateFlow.

## Города

- Сергиев Посад
- Переславль-Залесский
- Ростов Великий
- Ярославль
- Кострома
- Иваново
- Суздаль
- Владимир

## Как запустить

1. Откройте проект в Android Studio.
2. Добавьте в `local.properties` API-ключ Api-Ninjas:

```properties
API_NINJAS_KEY=your_api_key_here
```

3. Выполните Gradle Sync и запустите приложение.

### Если ключ не указан

Приложение всё равно запускается: для восьми городов используется встроенный резервный список координат.  
Для полного соответствия заданию рекомендуется указать ключ и использовать получение координат через Api-Ninjas.

## Архитектура

- `ui/` — Activity, ViewModel, adapters
- `data/remote/` — Retrofit API и DTO
- `data/repository/` — репозиторий
- `data/local/` — DataStore
- `data/source/` — список городов
- `util/` — форматирование дат и интерпретация кодов погоды

## Git workflow для сдачи

- название репозитория: `KursGoldenRingWeather`
- главная ветка: `main`
- рабочая ветка: `Kurs`

Примеры коммитов:

- `chore: create android project`
- `feat: add city selector and weather screen`
- `feat: integrate open-meteo and api-ninjas`
- `feat: persist selected city with datastore`
- `docs: update readme`

## Не коммитить

- `.idea`
- `.gradle`
- `build`
- `local.properties`
