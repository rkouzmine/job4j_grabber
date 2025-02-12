package ru.job4j.grabber.service;

import io.javalin.Javalin;
import ru.job4j.grabber.stores.Store;

public class Web {
    private final Store store;

    public Web(Store store) {
        this.store = store;
    }

    public void start(int port) {
        /* Создаем сервер Javalin */
        var app = Javalin.create();

        /* Указываем порт, на котором будет работать сервер */
        app.start(port);

        /* Формируем страницу с вакансиями */
        app.get("/", ctx -> {
            /* Данные запрашиваются при каждом обращении */
            var page = new StringBuilder();
            store.getAll().forEach(post ->
                    page.append(post).append("\n")
            );

            /* Явно указываем кодировку UTF-8 */
            ctx.contentType("text/html; charset=utf-8");

            /* Настраиваем обработчик для корневого URL */
            ctx.result(page.toString());
        });
    }
}
