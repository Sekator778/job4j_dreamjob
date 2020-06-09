package ru.job4j.dream.servlet;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.store.PsqlStore;
import ru.job4j.dream.store.Store;
import ru.job4j.dream.store.StoreSub;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PsqlStore.class)
public class CandidateServletTest {

    @Test
    public void whenAddUserThenStoreIt() throws ServletException, IOException {
        Store store = new StoreSub();
        PowerMockito.mockStatic(PsqlStore.class);
        PowerMockito.when(PsqlStore.instOf()).thenReturn(store);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("name")).thenReturn("Alex");
        new RegServlet().doPost(req, resp);
        assertThat(store.findAllUsers().iterator().next().getName(), is("Alex"));
    }

    @Ignore
    public void whenAddPostThenStoreIt() throws ServletException, IOException {
        Store store = new StoreSub();
        PowerMockito.mockStatic(PsqlStore.class);
        PowerMockito.when(PsqlStore.instOf()).thenReturn(store);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("name")).thenReturn("Alex");
        new PostServlet().doPost(req, resp);
        Collection<Post> map = new ArrayList<>();
               map = store.findAllPosts();
        Post post = map.iterator().next();
        System.out.println("post " + post.getName());
        System.out.println("map have = " + map.toString());
        assertThat(map.iterator().next().getName(), is("Alex"));
    }
}