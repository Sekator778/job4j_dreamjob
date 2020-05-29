package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  хранилище Store. Оно будет синглтон
 */
public class Store {

    private static final Store INST = new Store();

    private Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

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
}