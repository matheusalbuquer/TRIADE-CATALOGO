package group.triade.catalogo.services;

import group.triade.catalogo.dtos.produto.ProdutoRequestDTO;
import group.triade.catalogo.dtos.produto.ProdutoResponseDTO;
import group.triade.catalogo.entities.Lojista;
import group.triade.catalogo.entities.Produto;
import group.triade.catalogo.repositories.LojistaRepository;
import group.triade.catalogo.repositories.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private LojistaRepository lojistaRepository;


    @Transactional
    public ProdutoResponseDTO criar (ProdutoRequestDTO dto){
      String login = SecurityContextHolder.getContext().getAuthentication().getName();

      Lojista lojista = lojistaRepository.findByEmail(login);
      if(lojista == null){
        throw new IllegalStateException("Usuario não encontrado");
      }

      Produto produto = new Produto();
      produto.setNome(dto.nome());
      produto.setPreco(dto.preco());
      produto.setDescricao(dto.descricao());
      produto.setLojista(lojista);

      var salvo = produtoRepository.save(produto);

      return new ProdutoResponseDTO(
        salvo.getId(),
        salvo.getNome(),
        salvo.getPreco(),
        salvo.getDescricao()
      );
    }


}
