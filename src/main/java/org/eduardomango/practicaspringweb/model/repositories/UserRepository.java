package org.eduardomango.practicaspringweb.model.repositories;

import org.eduardomango.practicaspringweb.model.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserRepository implements IRepository<UserEntity> {

    private List<UserEntity> users;

    public UserRepository() {
        this.users = new ArrayList<>();

        users.add(new UserEntity(1, "alice", "alice@example.com", "password1"));
        users.add(new UserEntity(2, "bob", "bob@example.com", "password2"));
        users.add(new UserEntity(3, "charlie", "charlie@example.com", "password3"));
        users.add(new UserEntity(4, "diana", "diana@example.com", "password4"));
        users.add(new UserEntity(5, "edgar", "edgar@example.com", "password5"));
        users.add(new UserEntity(6, "frank", "frank@example.com", "password6"));
        users.add(new UserEntity(7, "grace", "grace@example.com", "password7"));
        users.add(new UserEntity(8, "hannah", "hannah@example.com", "password8"));
        users.add(new UserEntity(9, "ian", "ian@example.com", "password9"));
        users.add(new UserEntity(10, "julia", "julia@example.com", "password10"));
        users.add(new UserEntity(11, "kyle", "kyle@example.com", "password11"));
        users.add(new UserEntity(12, "laura", "laura@example.com", "password12"));
        users.add(new UserEntity(13, "michael", "michael@example.com", "password13"));
        users.add(new UserEntity(14, "nina", "nina@example.com", "password14"));
        users.add(new UserEntity(15, "oscar", "oscar@example.com", "password15"));
        users.add(new UserEntity(16, "paula", "paula@example.com", "password16"));
        users.add(new UserEntity(17, "quentin", "quentin@example.com", "password17"));
        users.add(new UserEntity(18, "rachel", "rachel@example.com", "password18"));
        users.add(new UserEntity(19, "sam", "sam@example.com", "password19"));
        users.add(new UserEntity(20, "tina", "tina@example.com", "password20"));
    }

    public List<UserEntity> findAll() {
        return List.copyOf(users);
    }

    public void save(UserEntity user) {
        users.add(user);
    }

    public void delete(UserEntity user) {
        users.remove(user);
    }

    public void update(UserEntity user) {
        int index = users.indexOf(user);
        users.set(index, user);
    }
}
