package com.shakti.service;

import com.shakti.model.Receipe;
import com.shakti.model.User;
import java.util.List;

public interface ReceipeService {

    Receipe createReceipe(Receipe receipe, User user);

    Receipe findReceipeById(Long id) throws Exception;

    void deleteReceipe(Long id) throws Exception;

    Receipe updateReceipe(Receipe receipe, Long id) throws Exception;

    List<Receipe> findAllReceipe();

    Receipe likeReceipe(Long receipeId, User user) throws Exception;
}
