package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * почему тут так много логики ?????????????????????
 * все соединения берем из пула pool который билдаем в конструкторе
 */
public class PsqlStore implements Store {
    private final BasicDataSource pool = new BasicDataSource();
    private final String PATH = "/home/sekator/projects/job4j_dreamjob/db/schema.sql";

    private PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(new FileReader("db.properties"))) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
        createTable();
    }

    /**
     * инстанс
     */
    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    /**
     * запускаем скрипт на крейт таблиц
     * тут коряво читаем !!!!!!!!!
     * надо подправить !!!!!!
     */
    private void createTable() {
        String sql = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(PATH));
            int c;
            while ((c = reader.read()) != -1) {
                sql += (char) c;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (Connection con = pool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * селект все из таблицы пост в колекцию создать пост
     */
    @Override
    public Collection<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection con = pool.getConnection();
             PreparedStatement ps = con.prepareStatement("Select * from post")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    posts.add(new Post(
                            rs.getInt("id"),
                            rs.getString("name")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    /**
     * селект все из таблицы кандидат в колекцию создать кандидатов
     */
    @Override
    public Collection<Candidate> findAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection con = pool.getConnection();
             PreparedStatement ps = con.prepareStatement("Select * from candidate")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    candidates.add(new Candidate(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("photoId")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(candidates.toString());
        return candidates;
    }

    /**
     * если ид не ноль то создаем если ид уже есть то корректируем  запись
     */
    @Override
    public void save(Post post) {
        if (post.getId() == 0) {
            create(post);
        } else {
            update(post);
        }
    }

    /**
     * если ид не ноль то создаем если ид уже есть то корректируем  запись
     */
    @Override
    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            create(candidate);
        } else {
            update(candidate);
        }
    }

    /**
     * инсерт в таблицу екземпляр пост по полям
     */
    private Post create(Post post) {
        try (Connection con = pool.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "Insert into post (name) values (?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, post.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    /**
     * инсертим все поля из екземпляра кандидат
     */
    private Candidate create(Candidate candidate) {
        try (Connection con = pool.getConnection();
             PreparedStatement ps = con.prepareStatement("Insert into candidate (name, photoId) values (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getPhotoId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidate;
    }

    /**
     * по айди берем запись из таблицы и все значения апдейтим
     */
    @Override
    public void update(Post post) {
        try (Connection con = pool.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "UPDATE post " + "set name = ?" + "where id =" + post.getId())) {
            ps.setString(1, post.getName());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * по айди берем запись из таблицы и все значения апдейтим
     */
    @Override
    public void update(Candidate candidate) {
        try (Connection con = pool.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "UPDATE candidate " + "set name = ?" + "photoId = ?" + "where id =" + candidate.getId())) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getPhotoId());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * запускаем всю выборку из таблицы пост
     * потом достаем запись с нужным Айди
     */
    @Override
    public Post findById(int id) {
        Post result = null;
        List<Post> list = (List<Post>) findAllPosts();
        for (Post post : list) {
            if (post.getId() == id) {
                result = post;
            }
        }
        return result;
    }

    /**
     * запускаем всю выборку из таблицы пост
     * потом достаем запись с нужным Айди
     */
    @Override
    public Candidate findByIdCandidate(int id) {
        Candidate result = null;
        List<Candidate> list = (List<Candidate>) findAllCandidates();
        for (Candidate candidate : list) {
            if (candidate.getId() == id) {
                result = candidate;
            }
        }
        return result;
    }

    /**
     * удаляем запись из таблицы
     */
    @Override
    public void deleteCandidate(int id) {
        try (Connection con = pool.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "DELETE from candidate where id =" + id)) {
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * добавляем пользователя нашей системы в таблицу пользователей
     */
    @Override
    public void addUser(User user) {
        try (Connection con = pool.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "Insert into users (name, email, password) values (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * выборка всех юзеров из таблицы юзеров
     */
    @Override
    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection con = pool.getConnection();
             PreparedStatement ps = con.prepareStatement("Select * from users")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    users.add(new User(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * берем выборку всех юзеров из таблицы юзеров а потом из этой выборки
     * достаем нужного нам юзера по сверке емейлов
     */
    @Override
    public User findUserByEmail(String email) {
        User result = null;
        for (User u : findAllUsers()) {
            if (u.getEmail().equals(email)) {
                result = u;
            }
        }
        return result;
    }
}
