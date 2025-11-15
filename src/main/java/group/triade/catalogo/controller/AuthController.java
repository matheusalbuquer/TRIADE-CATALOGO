package group.triade.catalogo.controller;

import group.triade.catalogo.dtos.AuthRequestDTO;
import group.triade.catalogo.dtos.AuthResponseDTO;
import group.triade.catalogo.entities.Lojista;
import group.triade.catalogo.repositories.LojistaRepository;
import group.triade.catalogo.services.AutenticacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private LojistaRepository adminRepository;

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO dto) {
        Lojista lojista = adminRepository.findByEmail(dto.email());
        if(lojista == null || passwordEncoder.matches(dto.email(),lojista.getSenha()) ){
            throw  new BadCredentialsException("Credenciais inválidas");
        }

        String token = autenticacaoService.gerarTokenJWT(lojista);

        AuthResponseDTO resp = new AuthResponseDTO(
                token,
                lojista.getId(),
                lojista.getNome(),
                lojista.getEmail(),
                LocalDateTime.now().plusHours(8).toString()
        );

        return ResponseEntity.ok(resp);
    }
}
