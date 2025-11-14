package group.triade.catalogo.services;

import group.triade.catalogo.dtos.produto.ProdutoRequestDTO;
import group.triade.catalogo.dtos.produto.ProdutoResponseDTO;
import group.triade.catalogo.entities.Admin;
import group.triade.catalogo.entities.Produto;
import group.triade.catalogo.repositories.AdminRepository;
import group.triade.catalogo.repositories.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private AdminRepository adminRepository;


    @Transactional
    public ProdutoResponseDTO criar (ProdutoRequestDTO dto){
      String login = SecurityContextHolder.getContext().getAuthentication().getName();

      Admin admin = adminRepository.findByEmail(login);
      if(admin == null){
        throw new IllegalStateException("Usuario não encontrado");
      }

      Produto produto = new Produto();
      produto.setNome(dto.nome());
      produto.setPreco(dto.preco());
      produto.setDescricao(dto.descricao());
      produto.setAdmin(admin);

      var salvo = produtoRepository.save(produto);

      return new ProdutoResponseDTO(
        salvo.getId(),
        salvo.getNome(),
        salvo.getPreco(),
        salvo.getDescricao()
      );
    }


}
