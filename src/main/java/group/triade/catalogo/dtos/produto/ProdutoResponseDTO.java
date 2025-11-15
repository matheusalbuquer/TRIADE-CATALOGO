package group.triade.catalogo.dtos.produto;

import java.math.BigDecimal;

public record ProdutoResponseDTO(Long id,
                                 String nome,
                                 BigDecimal preco,
                                 String descricao
) {
}
