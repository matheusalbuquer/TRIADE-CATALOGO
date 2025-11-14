package group.triade.catalogo.dtos.produto;

public record ProdutoRequestDTO (String nome,
                                 String preco,
                                 String descricao,
                                 //Admin
                                 Long adminId
) {
}
