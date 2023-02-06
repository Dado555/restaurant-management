package aldentebackend.dto.menuitem;

import aldentebackend.dto.item.ItemInfoDTO;
import aldentebackend.dto.price.PriceInfoDTO;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MenuItemInfoDTO {

    @NotNull
    private Long id;

    @NotNull
    private ItemInfoDTO item;

    @NotNull
    private PriceInfoDTO price;

    @NotNull
    private Long categoryId;

    @NotNull
    private String type;

    private Boolean isAlcoholic;

    private String imageUrl;

    private Boolean active;
}
