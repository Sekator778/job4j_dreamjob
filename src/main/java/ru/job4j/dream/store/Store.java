package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * хранилище Store. Оно будет синглтон
 */
public class Store {
    /**
     * ключ для генерации ID
     */
    private static final AtomicInteger POST_ID = new AtomicInteger(4);
    private static final AtomicInteger CANDIDATE_ID = new AtomicInteger(4);
    private static final Store INST = new Store();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private Store() {
        posts.put(1, new Post(1, "Junior Java Job", "descr_1", "21/01/1990"));
        posts.put(2, new Post(2, "Middle Java Job", "descr_2", "01/11/1998"));
        posts.put(3, new Post(3, "Senior Java Job", "descr_3", "11/10/1990"));
        candidates.put(1, new Candidate(1, "Junior Java candidate"));
        candidates.put(2, new Candidate(2, "Middle Java candidate"));
        candidates.put(3, new Candidate(3, "Senior Java candidate"));
    }

    public static Store instOf() {
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
}