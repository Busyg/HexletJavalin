package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import org.example.hexlet.dto.courses.CoursePage;
import org.example.hexlet.dto.courses.CoursesPage;
import org.example.hexlet.model.Course;

import java.util.List;

import static io.javalin.rendering.template.TemplateUtil.model;

public class HelloWorld {
    public static void main(String[] args) {
        // Создаем приложение
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });
        // Описываем, что загрузится по адресу /
        app.get("/", ctx -> ctx.render("index.jte"));
        app.get("/users", ctx -> ctx.result("GET /users"));
        app.get("/hello", ctx -> {
            var name = ctx.queryParam("name");
            ctx.result("Hello, " + (name != null ? name : "World") + "!");
        });
        app.get("users/{id}/post/{postId}", ctx -> {
            var id = ctx.pathParam("id");
            var postId = ctx.pathParam("postId");
            ctx.result("Id: " + id);
            ctx.result("Post Id: " + postId);
        });
        app.post("/users", ctx -> ctx.result("POST /users"));
        app.get("/courses", ctx -> {
            var courses = List.of(new Course("1", "2"), new Course("3", "4"));
            var header = "Курсы по программированию";
            var page = new CoursesPage(courses, header);
            ctx.render("courses/index.jte", model("page", page));
        });
        app.get("/courses/{id}", ctx -> {
            var id = ctx.pathParam("id");
            var course = new Course("First Course", "test");
            var page = new CoursePage(course);
            ctx.render("courses/show.jte", model("page", page));
        });
        app.start(7070); // Стартуем веб-сервер
    }
}