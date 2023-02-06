package aldentebackend.dto.meniitemcategory;


import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MenuItemCategoryInfoDTO {

    @NotNull
    private Long id;

    @NotNull
    private String name;

    public MenuItemCategoryInfoDTO() {}

    public MenuItemCategoryInfoDTO(long l, String s) {
        id = l; name = s;
    }
}
