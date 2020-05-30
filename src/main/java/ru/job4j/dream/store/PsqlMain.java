package ru.job4j.dream.store;

import ru.job4j.dream.model.Post;

import java.util.List;

public class PsqlMain {
    public static void main(String[] args) {
        Store store = PsqlStore.instOf();
        store.save(new Post(0, "Java Job"));
        for (Post post : store.findAllPosts()) {
            System.out.println(post.getId() + " " + post.getName());
        }
        List<Post> posts = (List<Post>) store.findAllPosts();
        System.out.println(posts.get(0).getId() + ":" + posts.get(0).getName());
        Post post = store.findById(1);
        System.out.println(post.getName());
        System.out.println("=====update========");
        post.setName("new name");
        store.update(post);
        Post post2 = store.findById(1);
        System.out.println(post2.getName());

    }
}