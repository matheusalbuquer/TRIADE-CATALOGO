package group.triade.catalogo.controller;

import group.triade.catalogo.dtos.produto.ProdutoRequestDTO;
import group.triade.catalogo.dtos.produto.ProdutoResponseDTO;
import group.triade.catalogo.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

  @Autowired
  private ProdutoService produtoService;

  @PostMapping
  public ResponseEntity<ProdutoResponseDTO> criar (@RequestBody ProdutoRequestDTO dto){
    ProdutoResponseDTO salvo = produtoService.criar(dto);
    return ResponseEntity.ok(salvo);
  }
}
