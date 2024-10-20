package br.com.gabrielklein.authserviceapi.controllers.impl;

import br.com.gabrielklein.authserviceapi.controllers.AuthController;
import lombok.RequiredArgsConstructor;
import models.requests.AuthenticateRequest;
import models.responses.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {


    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticateRequest request) throws Exception {
        return ResponseEntity.ok().body(AuthenticationResponse.builder()
                        .type("Bearer")
                        .token("token")
                .build());
    }
}
