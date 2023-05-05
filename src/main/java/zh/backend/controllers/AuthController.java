package zh.backend.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zh.backend.dtos.UserLoginDto;
import zh.backend.dtos.UserRegisterDto;
import zh.backend.responses.AuthenticationResponse;
import zh.backend.services.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController extends BaseController {

    private final AuthService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserRegisterDto userRegisterDto) {
        return ResponseEntity.ok(authenticationService.register(userRegisterDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody UserLoginDto userLoginDto) {
        return ResponseEntity.ok(authenticationService.login(userLoginDto));
    }

}
