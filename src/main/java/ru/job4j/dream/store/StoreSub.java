package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class StoreSub implements Store {
    private final Map<Integer, Post> postMap = new HashMap<>();
    private final Map<Integer, Candidate> candidateMap = new HashMap<>();
    private final List<User> userList = new ArrayList<>();
    private final List<String> cityList = new ArrayList<>();
    private int idPost = 0;
    private int idCandidate = 0;
    private int idUser = 0;

    @Override
    public Collection<Post> findAllPosts() {
        return postMap.values();
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        return candidateMap.values();
    }

    @Override
    public void save(Post post) {
        post.setId(idPost++);
        postMap.put(post.getId(), post);
    }

    @Override
    public void save(Candidate candidate) {
        candidate.setId(idCandidate++);
        candidateMap.put(candidate.getId(), candidate);
    }

    @Override
    public Post findById(int id) {
        return postMap.get(id);
    }

    @Override
    public Candidate findByIdCandidate(int id) {
        return candidateMap.get(id);
    }

    @Override
    public void update(Post post) {
        postMap.get(post.getId()).setName(post.getName());
    }

    @Override
    public void update(Candidate candidate) {
        candidateMap.get(candidate.getId()).setName(candidate.getName());

    }

    @Override
    public void deleteCandidate(int id) {
        candidateMap.remove(id);
    }

    @Override
    public void addUser(User user) {
        user.setId(idUser++);
        userList.add(user);
    }

    @Override
    public List<User> findAllUsers() {
        return userList;
    }

    @Override
    public User findUserByEmail(String email) {
        User result = null;
        for (User u : userList
        ) {
            if (u.getEmail().equals(email)) {
                result = u;
                break;
            }
        }
        return result;
    }

    @Override
    public List<String> findAllCities() {
        return cityList;
    }

    @Override
    public int findByIdCity(String city) {
        int result = 0;
        int i = 0;
        AtomicInteger index = new AtomicInteger();
        for (String sCity : cityList
        ) {
            i = index.incrementAndGet();
            if (sCity.equals(city)) {
                result = i;
                break;
            }
        }
        return result;
    }

    @Override
    public String findCity(int id) {
        return cityList.get(id);
    }
}
