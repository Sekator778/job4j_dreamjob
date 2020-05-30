package ru.job4j.dream.servlet;

import ru.job4j.dream.model.Post;
import ru.job4j.dream.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
public class PostServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        /* Метод getParameter позволяет получить значения в запросе */
        Store.instOf().save(
                new Post(
                        Integer.parseInt(req.getParameter("id")),
                        req.getParameter("name")
                )
        );        /*После того, как сервлет выполнил свою работу мы просим сервер отправить другой запрос,
         но уже к странице posts.jsp.
         */
        resp.sendRedirect(req.getContextPath() + "/posts.do");
    }

        @Override
        protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            req.setAttribute("posts", Store.instOf().findAllPosts());
            req.getRequestDispatcher("/post/posts.jsp").forward(req, resp);
        }
}