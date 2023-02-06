package aldentebackend.support.sector;

import aldentebackend.dto.sector.SectorInfoDTO;
import aldentebackend.dto.table.TableInfoDTO;
import aldentebackend.model.RestaurantTable;
import aldentebackend.model.Sector;
import aldentebackend.support.AbstractConverter;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class SectorToSectorInfoDTO extends AbstractConverter<Sector, SectorInfoDTO> {

    @Override
    public SectorInfoDTO convert(@NonNull Sector source) {
        return getModelMapper().map(source, SectorInfoDTO.class);
    }
}
