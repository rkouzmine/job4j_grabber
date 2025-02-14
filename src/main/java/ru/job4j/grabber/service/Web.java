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

        app.get("/", ctx -> {
            /* Создаем HTML-страницу */
            var html = new StringBuilder();
            html.append("<html>");
            html.append("<head><title>Super Java Job</title></head>");
            html.append("<body>");
            html.append("<h1>Список вакансий</h1>");
            html.append("<ul>");

            /* Добавляем каждую вакансию как элемент списка */
            store.getAll().forEach(post -> {
                html.append("<li>");
                html.append("<strong>").append(post.getTitle()).append("</strong><br>");
                html.append("<p>").append(post.getDescription()).append("</p>");
                html.append("<p>").append(post.getLink()).append("</p>");
                html.append("<p>").append(post.getTime()).append("</p>");
                html.append("</li>");
            });

            html.append("</ul>");
            html.append("</body>");
            html.append("</html>");

            /* Явно указываем кодировку UTF-8 */
            ctx.contentType("text/html; charset=utf-8");

            /* Настраиваем обработчик для корневого URL */
            ctx.result(html.toString());
        });
    }
}
