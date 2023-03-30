# O! Market

O! Market - это маркетплейс для абонентов мобильного оператора О!. Данное приложение создано для
размещения и продажи товаров с возможностью использования, при оплате, электронного кошелька О! Деньги.

# Project in developing stage

**В нашем проекте мы использовали следующие инструменты:**
- Android Gradle Plugin Version 7.3.1
- Gradle Version 7.4

Есть риск, что проект не собереться, если версии Gradle на вашем персональном компьютере будут выше или ниже указанных.
Настоятельно советуем проверить версии Gradle для стабильной работы.

Возможный вариант разрешения этой проблемы
1) Нажимаем File
2) Project Structure
3) Project
4) В Android Gradle Plugin Version выбираем версию 7.3.1
5) Gradle Version выбираем версию 7.4
6) Затем Apply и ОК
7) Пересобираем проект. Для этого в правом вверхнем углу в панели инструментов есть иконка Gradle
8) При появлении сообщения Sync Project with Gradle Files? нажимаем на него

# Permissions

В нашем приложении нам нужно разрешение для доступа к медиафайлам, чтобы загрузить изображения для
создания обновления. Если ваша версия Android >= 11, то вас перенаправит в настройки для получения
разрешений. Если меньше, то разрешение можно дать через всплывающее диалоговое окно.

# Technologies

* [Android](https://developer.android.com/) - a mobile operating system based on a modified version of the Linux kernel and other open source software

* [Kotlin](https://kotlinlang.org/docs/home.html) - is a cross-platform, statically typed, general-purpose programming language with type inference

* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - a dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in your project

* [Retrofit](https://square.github.io/retrofit/) - a type-safe HTTP client for Android and Java

* [OkHttp](https://www.baeldung.com/guide-to-okhttp) - an efficient HTTP & HTTP/2 client for Android and Java applications

* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - a class is designed to store and manage UI-related data in a lifecycle conscious way.

* [Navigation](https://developer.android.com/jetpack/androidx/releases/navigation) - a framework for navigating between 'destinations' within an Android application that provides a consistent API whether destinations are implemented as Fragments, Activities, or other components

* [Coroutines](https://developer.android.com/kotlin/coroutines#:~:text=A%20coroutine%20is%20a%20concurrency,established%20concepts%20from%20other%20languages.) - a concurrency design pattern that you can use on Android to simplify code that executes asynchronously

* [Flow](https://developer.android.com/kotlin/flow) - In coroutines, a flow is a type that can emit multiple values sequentially, as opposed to suspend functions that return only a single value.

* [Paging](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) - library helps you load and display pages of data from a larger dataset from local storage or over network

* [Glide](https://github.com/bumptech/glide) - a fast and efficient open-source media management and image loading framework for Android that wraps media decoding, memory and disk caching, and resource pooling into a simple and easy to use interface

# Authors

Приложение разработано в рамках проекта Intern Labs 4.0 2022 командой O! Market Android Team.
