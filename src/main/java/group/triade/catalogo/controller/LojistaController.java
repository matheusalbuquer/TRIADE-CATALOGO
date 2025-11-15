package group.triade.catalogo.controller;

import group.triade.catalogo.dtos.LojistaRequestDTO;
import group.triade.catalogo.dtos.LojistaResponseDTO;
import group.triade.catalogo.services.LojistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lojista")
public class LojistaController {

    @Autowired
    private LojistaService lojistaService;

    @PostMapping
    public ResponseEntity<LojistaResponseDTO> criar (@RequestBody LojistaRequestDTO dto){
        LojistaResponseDTO salvo = lojistaService.criar(dto);
        return ResponseEntity.ok(salvo);
    }

}
