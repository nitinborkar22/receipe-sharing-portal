package com.shakti.service;

import com.shakti.model.Receipe;
import com.shakti.model.User;
import com.shakti.repository.ReceipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReceipeServiceImplementation implements ReceipeService {

    @Autowired
    private ReceipeRepository receipeRepository;

    @Override
    public Receipe createReceipe(Receipe receipe, User user) {
        Receipe createdReceipe = new Receipe();
        createdReceipe.setTitle(receipe.getTitle());
        createdReceipe.setImage(receipe.getImage());
        createdReceipe.setDescription(receipe.getDescription());
        createdReceipe.setUser(user);
        createdReceipe.setCreatedAt(LocalDateTime.now());

        return receipeRepository.save(createdReceipe);
    }

    @Override
    public Receipe findReceipeById(Long id) throws Exception {
        Optional<Receipe> optional = receipeRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new Exception("Receipe not found with id: " + id);
    }

    @Override
    public void deleteReceipe(Long id) throws Exception {
        Receipe receipe = findReceipeById(id);
        receipeRepository.delete(receipe);
    }

    // ❌ Remove this method – not needed, was returning null
    // @Override
    // public Receipe updateReceipe(Long receipeId, Long id) throws Exception {
    //     return null;
    // }

    @Override
    public Receipe updateReceipe(Receipe receipe, Long id) throws Exception {
        Receipe oldReceipe = findReceipeById(id);

        if (receipe.getTitle() != null) {
            oldReceipe.setTitle(receipe.getTitle());
        }

        if (receipe.getImage() != null) {
            oldReceipe.setImage(receipe.getImage());
        }

        if (receipe.getDescription() != null) {
            oldReceipe.setDescription(receipe.getDescription());
        }

        oldReceipe.setUpdatedAt(LocalDateTime.now()); // 🔹 Optional: track updates

        return receipeRepository.save(oldReceipe);
    }

    @Override
    public List<Receipe> findAllReceipe() {
        return receipeRepository.findAll();
    }

    @Override
    public Receipe likeReceipe(Long receipeId, User user) throws Exception {
        Receipe receipe = findReceipeById(receipeId);

        if (receipe.getLikes().contains(user.getId())) {
            receipe.getLikes().remove(user.getId()); // unlike
        } else {
            receipe.getLikes().add(user.getId()); // like
        }

        // ✅ Save changes and return updated receipe
        return receipeRepository.save(receipe);
    }
}
