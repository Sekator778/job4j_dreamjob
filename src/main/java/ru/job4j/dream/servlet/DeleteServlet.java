package ru.job4j.dream.servlet;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("method doget DeleteServlet");
        Candidate id = PsqlStore.instOf().findByIdCandidate(Integer.parseInt(req.getParameter("id")));
        System.out.println(id.getName());
        req.setAttribute("candidate", id);
        req.getRequestDispatcher("/candidate/deleteCandidate.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("doPost deleteServlet");
        int id = Integer.parseInt(req.getParameter("id"));
        System.out.println(id);
        File file = new File("images");
        String imageName = PsqlStore.instOf().findByIdCandidate(id).getPhotoId();
        System.out.println(imageName);
        for (File f : file.listFiles()) {
            if (f.getName().equals(imageName)) {
                f.delete();
                break;
            }
        }
        PsqlStore.instOf().deleteCandidate(id);
        resp.sendRedirect(req.getContextPath() + "/candidate/candidate.do");
    }
}
