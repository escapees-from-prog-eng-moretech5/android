# MORE.Tech 5.0 Трек MOBILE - Android-приложение

![Screenshot](https://github.com/escapees-from-prog-eng-moretech5/android/blob/master/Screenshot_20231015_053605.png?raw=true | width=100)

Реализовано:
* Вход
* Регистрация
* Получение с бэкенда авторизационного токена
* Получение с бэкенда данных об отделениях и банкоматах
* Отображение карты
* Масштабирование карты
* Кластеризация маркеров https://github.com/escapees-from-prog-eng-moretech5/android/blob/49969ca96ec62f46b55608906e15ac6e57dfabbf/features/map/src/main/java/dev/argraur/moretech/map/ui/MapScreen.kt#L74
* Отображение отделений и банкоматов
* Выбор отделения и построение маршрута
* Скрытие отделений/банкоматов
* Нахождение текущего местоположения

Стек:
* Kotlin
* Jetpack Compose
* ConstraintLayout
* Google Maps SDK (Directions API, Compose, Compose Utils)
* kotlinx-serialization
* Hilt (DI)
* Room (Database)
* Retrofit (Network)
* Preferences (Token)
* Clean Architecture
* Coroutines
* Flow
* Material 3
* Dynamic Colors
