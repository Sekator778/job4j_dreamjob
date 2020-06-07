package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.Collection;
import java.util.List;

public interface Store {
    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidates();

    void save(Post post);

    void save(Candidate candidate);

    Post findById(int id);

    Candidate findByIdCandidate(int id);

    void update(Post post);

    void update(Candidate candidate);

    void deleteCandidate(int id);

    void addUser(User user);

    List<User> findAllUsers();

    User findUserByEmail(String email);
}