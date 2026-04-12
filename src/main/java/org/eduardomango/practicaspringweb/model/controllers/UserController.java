package org.eduardomango.practicaspringweb.model.controllers;

import jakarta.validation.Valid;

import org.eduardomango.practicaspringweb.model.entities.user.dtos.UserRequestDTO;
import org.eduardomango.practicaspringweb.model.entities.user.dtos.UserResponseDTO;
import org.eduardomango.practicaspringweb.model.entities.user.entity.UserEntity;
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

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        List<UserResponseDTO> response = userService.findAll().stream()
                .map(u -> new UserResponseDTO(u.getId(), u.getUsername(), u.getEmail()))
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable Long id) {
        UserEntity user = userService.findById(id);
        return ResponseEntity.ok(new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail()));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponseDTO> getByUsername(@PathVariable String username) {
        UserEntity user = userService.findByUsername(username);
        return ResponseEntity.ok(new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail()));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDTO> getByEmail(@PathVariable String email) {
        UserEntity user = userService.findByEmail(email);
        return ResponseEntity.ok(new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail()));
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserRequestDTO requestDTO) {
        long newId = userService.findAll().stream().mapToLong(UserEntity::getId).max().orElse(0L) + 1;

        UserEntity user = new UserEntity(
                newId,
                requestDTO.getUsername(),
                requestDTO.getEmail(),
                requestDTO.getPassword()
        );
        userService.save(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @Valid @RequestBody UserRequestDTO requestDTO) {
        UserEntity existingUser = userService.findById(id);

        existingUser.setUsername(requestDTO.getUsername());
        existingUser.setEmail(requestDTO.getEmail());
        existingUser.setPassword(requestDTO.getPassword());

        userService.update(existingUser);

        return ResponseEntity.ok(new UserResponseDTO(
                existingUser.getId(),
                existingUser.getUsername(),
                existingUser.getEmail()
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        UserEntity user = userService.findById(id);
        userService.delete(user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
