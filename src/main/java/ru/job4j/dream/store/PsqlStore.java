package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store {

    private final BasicDataSource pool = new BasicDataSource();

    private PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new FileReader("db.properties")
        )) {
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
    }

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public Collection<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM post")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(it.getInt("id"), it.getString("name")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM candidates")
        ) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    candidates.add(new Candidate(resultSet.getInt("id"), resultSet.getString("name")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidates;
    }

    @Override
    public void save(Post post) {
        if (post.getId() == 0) {
            create(post);
        } else {
            update(post);
        }
    }

    @Override
    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            create(candidate);
        } else {
            update(candidate);
        }
    }

    private Post create(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO post(name) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
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

    private Candidate create(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO candidates(name) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
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

    private void update(Post post) {
        try (Connection conn = pool.getConnection();
             PreparedStatement st = conn.prepareStatement("update post set name = ? where id = ?")
        ) {
            st.setString(1, post.getName());
            st.setInt(2, post.getId());
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void update(Candidate candidate) {
        try (Connection conn = pool.getConnection();
             PreparedStatement st = conn.prepareStatement("update candidates set name = ? where id = ?")
        ) {
            st.setString(1, candidate.getName());
            st.setInt(2, candidate.getId());
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Post findById(int id) {
        Post post = null;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT post.id, post.name from post WHERE id = ?")) {
            statement.setInt(1, id);
            List<Post> rsl = findSet(statement);
            if (!rsl.isEmpty()) {
                post = rsl.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    private List<Post> findSet(PreparedStatement statement) {
        List<Post> result = new ArrayList<>();
        try (ResultSet set = statement.executeQuery()) {
            while (set.next()) {
                int id = set.getInt(1);
                String name = set.getString(2);
                Post post = new Post(0, name);
                post.setId(id);
                result.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Candidate findByIdCandidate(int id) {
        Candidate candidate = null;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT candidates.id, candidates.name from candidates WHERE id = ?")) {
            statement.setInt(1, id);
            List<Candidate> rsl = findSetCandidate(statement);
            if (!rsl.isEmpty()) {
                candidate = rsl.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return candidate;
    }

    private List<Candidate> findSetCandidate(PreparedStatement statement) {
        List<Candidate> result = new ArrayList<>();
        try (ResultSet set = statement.executeQuery()) {
            while (set.next()) {
                int id = set.getInt(1);
                String name = set.getString(2);
                Candidate candidate = new Candidate(0, name);
                candidate.setId(id);
                result.add(candidate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}