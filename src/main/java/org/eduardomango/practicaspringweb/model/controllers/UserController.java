package org.eduardomango.practicaspringweb.model.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.eduardomango.practicaspringweb.model.entities.user.dtos.UserRequestDTO;
import org.eduardomango.practicaspringweb.model.entities.user.dtos.UserResponseDTO;
import org.eduardomango.practicaspringweb.model.entities.user.entity.UserEntity;
import org.eduardomango.practicaspringweb.model.exceptions.ErrorMessage;
import org.eduardomango.practicaspringweb.model.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "API para la administración de usuarios del sistema")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Obtener todos los usuarios")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        List<UserResponseDTO> response = userService.findAll().stream()
                .map(u -> new UserResponseDTO(u.getId(), u.getUsername(), u.getEmail()))
                .toList();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener usuario por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable Long id) {
        UserEntity user = userService.findById(id);
        return ResponseEntity.ok(new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail()));
    }

    @Operation(summary = "Obtener usuario por Username")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponseDTO> getByUsername(@PathVariable String username) {
        UserEntity user = userService.findByUsername(username);
        return ResponseEntity.ok(new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail()));
    }

    @Operation(summary = "Obtener usuario por Email")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDTO> getByEmail(@PathVariable String email) {
        UserEntity user = userService.findByEmail(email);
        return ResponseEntity.ok(new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail()));
    }

    @Operation(summary = "Crear un nuevo usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error de validación en los datos de entrada", content = @Content)
    })
    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserRequestDTO requestDTO) {
        long newId = userService.findAll().stream().mapToLong(UserEntity::getId).max().orElse(0L) + 1;

        UserEntity user = new UserEntity(newId, requestDTO.getUsername(), requestDTO.getEmail(), requestDTO.getPassword());
        userService.save(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail()));
    }

    @Operation(summary = "Actualizar información del usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario actualizado"),
            @ApiResponse(responseCode = "400", description = "Error de validación en los datos de entrada", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @Valid @RequestBody UserRequestDTO requestDTO) {
        UserEntity existingUser = userService.findById(id);

        existingUser.setUsername(requestDTO.getUsername());
        existingUser.setEmail(requestDTO.getEmail());
        existingUser.setPassword(requestDTO.getPassword());

        userService.update(existingUser);

        return ResponseEntity.ok(new UserResponseDTO(
                existingUser.getId(), existingUser.getUsername(), existingUser.getEmail()));
    }

    @Operation(summary = "Eliminar un usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        UserEntity user = userService.findById(id);
        userService.delete(user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
