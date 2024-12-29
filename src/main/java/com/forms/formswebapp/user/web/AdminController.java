package com.forms.formswebapp.user.web;

import com.forms.formswebapp.user.domain.UserService;
import com.forms.formswebapp.user.domain.model.response.UserDto;
import com.forms.formswebapp.user.domain.model.response.UserResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
class AdminController {

    private final UserService userService;


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users successfully retrieved"),
            @ApiResponse(responseCode = "403", description = "Unauthorized")
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<UserResponse> getUsers(final Authentication authentication, @RequestParam(required = false) final String email) {
        log.info("Getting users for admin = {}", authentication.getName());
        return ResponseEntity.ok(userService.getUsersAdmin(email));
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User successfully deleted"),
            @ApiResponse(responseCode = "400", description = "User not found"),
            @ApiResponse(responseCode = "403", description = "Unauthorized")
    })
    @DeleteMapping("/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Void> deleteUser(final Authentication authentication, @PathVariable final String email) {
        log.info("Deleting user for admin = {}", authentication.getName());
        userService.deleteUser(email);
        return ResponseEntity.noContent().build();
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully promoted"),
            @ApiResponse(responseCode = "400", description = "User not found"),
            @ApiResponse(responseCode = "403", description = "Unauthorized")
    })
    @PatchMapping("/promote/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<UserDto> promoteUser(final Authentication authentication, @PathVariable final String email) {
        log.info("Promoting user for admin = {}", authentication.getName());
        return ResponseEntity.ok(userService.promoteUser(email));
    }

}
