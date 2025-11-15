package group.triade.catalogo.dtos.produto;

import java.math.BigDecimal;

public record ProdutoRequestDTO (String nome,
                                 BigDecimal preco,
                                 String descricao,
                                 //Admin
                                 Long lojistaId
) {
}
