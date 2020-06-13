package ru.job4j.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 *
 */

public class AutoHtmlRegServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
        //тут приходит массив send из avtoriz.html надо его тут распарсить пока не знаю как
        String[] strings = req.getParameterValues("send");
        System.out.println("send: " + Arrays.toString(strings));
        String name = req.getParameter("inputName");
        String email = req.getParameter("inputEmail");
        String password = req.getParameter("inputPassword");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.println("Nice to meet you, " + name + password + email);
        writer.flush();
    }
}
