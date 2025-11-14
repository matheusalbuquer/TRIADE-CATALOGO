package group.triade.catalogo.dtos.produto;

public record ProdutoResponseDTO(Long id,
                                 String nome,
                                 String preco,
                                 String descricao
) {
}
