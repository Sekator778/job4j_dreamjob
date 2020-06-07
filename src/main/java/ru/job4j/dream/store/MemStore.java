package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * хранилище Store. Оно будет синглтон
 */
public class MemStore implements Store{
    /**
     * ключ для генерации ID
     */
    private static final AtomicInteger POST_ID = new AtomicInteger(4);
    private static final AtomicInteger CANDIDATE_ID = new AtomicInteger(4);
    private static final MemStore INST = new MemStore();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private MemStore() {
        posts.put(1, new Post(1, "Junior Java Job"));
        posts.put(2, new Post(2, "Middle Java Job"));
        posts.put(3, new Post(3, "Senior Java Job"));
        candidates.put(1, new Candidate(1, "Junior Java candidate"));
        candidates.put(2, new Candidate(2, "Middle Java candidate"));
        candidates.put(3, new Candidate(3, "Senior Java candidate"));
    }

    public static MemStore instOf() {
        return INST;
    }

    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }

    /**
     * метод добавления
     * Если id вакансии равен 0, то сгенерировать новую id
     */
    public void save(Post post) {
        if (post.getId() == 0) {
            post.setId(POST_ID.incrementAndGet());
        }
        posts.put(post.getId(), post);
    }

    /**
     * метод поиска по Id
     */
    public Post findById(int id) {
        return posts.get(id);
    }

    /**
     * метод добавления кандидата
     */
    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(CANDIDATE_ID.incrementAndGet());
        }
        candidates.put(candidate.getId(), candidate);
    }

    /**
     * метод поиска по Id candidate
     */
    public Candidate findByIdCandidate(int id) {
        return candidates.get(id);
    }

    @Override
    public void update(Post post) {

    }

    @Override
    public void update(Candidate candidate) {

    }

    @Override
    public void deleteCandidate(int id) {

    }

    @Override
    public void addUser(User user) {

    }

    @Override
    public List<User> findAllUsers() {
        return null;
    }

    @Override
    public User findUserByEmail(String email) {
        return null;
    }
}