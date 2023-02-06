package aldentebackend.support.table;

import aldentebackend.dto.table.TableCreateDTO;
import aldentebackend.dto.table.TableInfoDTO;
import aldentebackend.exception.NotFoundException;
import aldentebackend.model.RestaurantTable;
import aldentebackend.model.Sector;
import aldentebackend.service.SectorService;
import aldentebackend.service.TableService;
import aldentebackend.support.AbstractConverter;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TableCreateDTOToTable extends AbstractConverter<TableCreateDTO, RestaurantTable> {

    private final TableService tableService;
    private final SectorService sectorService;

    @Override
    public RestaurantTable convert(@NonNull TableCreateDTO source) {

        RestaurantTable table = null;
        try {
            if (source.getId() != null)
                table = tableService.findOne(source.getId());
        } catch (NotFoundException exception) {
            if (source.getActive() != null && source.getActive().booleanValue() == true) return null;
        }


        if (table == null)
            table = new RestaurantTable();

        table.setTableHeight(source.getTableHeight());
        table.setTableWidth(source.getTableWidth());
        table.setPositionX(source.getPositionX());
        table.setPositionY(source.getPositionY());
        table.setNumberOfSeats(source.getNumberOfSeats());
        table.setTableNumber(source.getTableNumber());
        table.setSector(sectorService.findOne(source.getSectorId()));

        if (source.getActive() != null) table.setActive(source.getActive());

        return table;
    }
}
