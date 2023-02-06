package aldentebackend.dto.menuitem;

import aldentebackend.dto.item.ItemCreateDTO;
import aldentebackend.dto.meniitemcategory.MenuItemCategoryInfoDTO;
import aldentebackend.dto.price.PriceCreateDTO;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class MenuItemCreateDTO {

    @NotNull
    private ItemCreateDTO item;

    @NotNull
    private PriceCreateDTO price;

    @NotNull
    private MenuItemCategoryInfoDTO category;
    //private Long categoryId;

    private Boolean isAlcoholic;

    @NotBlank
    private String type;

    private String imageUrl;

    public MenuItemCreateDTO(ItemCreateDTO item, PriceCreateDTO price, MenuItemCategoryInfoDTO category, Boolean isAlcoholic, String type) {
        this.item = item;
        this.price = price;
        this.category = category;
        this.isAlcoholic = isAlcoholic;
        this.type = type;
    }
}
