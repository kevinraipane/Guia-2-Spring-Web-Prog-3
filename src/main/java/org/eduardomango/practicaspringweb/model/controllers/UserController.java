package org.eduardomango.practicaspringweb.model.controllers;

import org.eduardomango.practicaspringweb.model.entities.UserEntity;
import org.eduardomango.practicaspringweb.model.exceptions.UserNotFoundException;
import org.eduardomango.practicaspringweb.model.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /// ========================== GET ========================== ///
    @GetMapping
    public ResponseEntity<List<UserEntity>> getAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(userService.findById(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserEntity> getByUsername(@PathVariable String username) {
        try {
            return ResponseEntity.ok(userService.findByUsername(username)); // 200 OK
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserEntity> getByEmail(@PathVariable String email) {
        try {
            return ResponseEntity.ok(userService.findByEmail(email)); // 200 OK
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        }
    }

    /// ========================== POST ========================== ///
    @PostMapping
    public ResponseEntity<UserEntity> create(@RequestBody UserEntity user) {
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    /// ========================== PUT ========================== ///
    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> update(
            @PathVariable long id,
            @RequestBody UserEntity user
    ) {
        try {
            // Validamos que exista antes de actualizar
            UserEntity existingUser = userService.findById(id);
            user.setId(existingUser.getId());
            userService.update(user);
            return ResponseEntity.ok(user); // 200 cuanda actualiza correctamente
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 si el ID solicitado no existe
        }
    }

    /// ========================== DELETE ========================== ///
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        try {
            // Validamos que exista antes de borrar
            UserEntity user = userService.findById(id);
            userService.delete(user);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 al eliminar con éxito
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404
        }
    }
}
