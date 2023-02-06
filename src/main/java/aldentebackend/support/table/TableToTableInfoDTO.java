package aldentebackend.support.table;

import aldentebackend.dto.table.TableInfoDTO;
import aldentebackend.model.RestaurantTable;
import aldentebackend.support.AbstractConverter;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class TableToTableInfoDTO extends AbstractConverter<RestaurantTable, TableInfoDTO> {

    @Override
    public TableInfoDTO convert(@NonNull RestaurantTable source) {
        return getModelMapper().map(source, TableInfoDTO.class);
    }
}
