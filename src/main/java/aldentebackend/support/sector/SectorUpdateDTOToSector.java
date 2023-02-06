package aldentebackend.support.sector;

import aldentebackend.dto.sector.SectorCreateDTO;
import aldentebackend.dto.sector.SectorUpdateDTO;
import aldentebackend.model.Sector;
import aldentebackend.service.SectorService;
import aldentebackend.support.AbstractConverter;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class SectorUpdateDTOToSector extends AbstractConverter<SectorUpdateDTO, Sector> {

    private final SectorService sectorService;

    @Override
    public Sector convert(@NonNull SectorUpdateDTO source) {
        Sector sector = sectorService.findOne(source.getId());
        sector.setName(source.getName());
        return sector;
    }
}
