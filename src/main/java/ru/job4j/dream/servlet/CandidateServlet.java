package ru.job4j.dream.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
public class CandidateServlet extends HttpServlet {
    private final String index = "/WEB-INF/candidate/candidates.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Map<String, String> fields;
        fields = createFile(req);
        String name = fields.get("name");
        String photoId = fields.get("foto");
        int cityId = PsqlStore.instOf().findByIdCity(fields.get("cities"));
        System.out.println("=======save=============");
        System.out.println("cities: " + fields.get("cities"));
        System.out.println("name " + name);
        System.out.println("photoId " + photoId);
        System.out.println("cityId " + cityId);
        PsqlStore.instOf().save(new Candidate(name, photoId, cityId));
        doGet(req, resp);
    }

    /**
     * еще и апач либы подучить надо %)
     */
    private Map<String, String> createFile(HttpServletRequest req) throws UnsupportedEncodingException {
        req.setCharacterEncoding("UTF-8");
        Map<String, String> result = new HashMap<>();
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
        File newFile = null;
        try {
            List<FileItem> items = upload.parseRequest(req);
            File folder = new File("images");
            if (!folder.exists()) {
                folder.mkdir();
            }
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    File file = new File(folder + File.separator + item.getName());
                    try (FileOutputStream out = new FileOutputStream(file)) {
                        out.write(item.getInputStream().readAllBytes());
                        newFile = file;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    result.put(item.getFieldName(), item.getString());
                }
            }
            result.put("foto", newFile.getName());
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("candidates", PsqlStore.instOf().findAllCandidates());
        req.getRequestDispatcher(index).forward(req, resp);
    }
}

