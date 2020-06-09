package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.*;

public class StoreSub implements Store {
    private final Map<Integer, Post> postMap = new HashMap<>();
    private final Map<Integer, Candidate> candidateMap = new HashMap<>();
    private final List<User> userList = new ArrayList<>();
    private int idPost = 0;
    private int idCandidate = 0;
    private int idUser = 0;

    @Override
    public Collection<Post> findAllPosts() {
        return this.postMap.values();
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        return this.candidateMap.values();
    }

    @Override
    public void save(Post post) {
        post.setId(this.idPost++);
        postMap.put(post.getId(), post);
    }

    @Override
    public void save(Candidate candidate) {
        candidate.setId(this.idCandidate++);
        candidateMap.put(candidate.getId(), candidate);
    }

    @Override
    public Post findById(int id) {
        return this.postMap.get(id);
    }

    @Override
    public Candidate findByIdCandidate(int id) {
        return this.candidateMap.get(id);
    }

    @Override
    public void update(Post post) {
        this.postMap.get(post.getId()).setName(post.getName());
    }

    @Override
    public void update(Candidate candidate) {
        this.candidateMap.get(candidate.getId()).setName(candidate.getName());

    }

    @Override
    public void deleteCandidate(int id) {
        this.candidateMap.remove(id);
    }

    @Override
    public void addUser(User user) {
        user.setId(this.idUser++);
        this.userList.add(user);
    }

    @Override
    public List<User> findAllUsers() {
        return this.userList;
    }

    @Override
    public User findUserByEmail(String email) {
        User result = null;
        for (User u : this.userList
        ) {
            if (u.getEmail().equals(email)) {
                result = u;
                break;
            }
        }
        return result;
    }
}
