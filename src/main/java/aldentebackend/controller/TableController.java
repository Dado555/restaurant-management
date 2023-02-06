package aldentebackend.controller;

import aldentebackend.dto.sector.SectorCreateDTO;
import aldentebackend.dto.sector.SectorInfoDTO;
import aldentebackend.dto.sector.SectorUpdateDTO;
import aldentebackend.dto.table.TableCreateDTO;
import aldentebackend.dto.table.TableInfoDTO;
import aldentebackend.model.RestaurantTable;
import aldentebackend.model.Sector;
import aldentebackend.service.SectorService;
import aldentebackend.service.TableService;
import aldentebackend.support.IConverter;
import aldentebackend.support.sector.SectorToSectorInfoDTO;
import aldentebackend.support.table.TableToTableInfoDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/tables")
public class TableController {

    private final TableService tableService;
    private final SectorService sectorService;
    private final SectorToSectorInfoDTO toSectorInfoDTO;
    private final TableToTableInfoDTO toTableInfoDTO;
    private final IConverter<TableCreateDTO, RestaurantTable> tableCreateDTOToRestaurantTable;

    private final IConverter<SectorCreateDTO, Sector> sectorCreateDTOToSector;
    private final IConverter<SectorUpdateDTO, Sector> sectorUpdateDTOToSector;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<TableInfoDTO>> getAllTables()  {
        Collection<RestaurantTable> tables = tableService.findAll();
        return new ResponseEntity<>(toTableInfoDTO.convert(tables), HttpStatus.OK);
    }

    @GetMapping(value = "sector/{sectorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<TableInfoDTO>> getAllTablesInSector(@PathVariable("sectorId") Long sectorId)  {
        Collection<RestaurantTable> tables = tableService.getTablesInSection(sectorId);
        return new ResponseEntity<>(toTableInfoDTO.convert(tables), HttpStatus.OK);
    }

    @DeleteMapping(value = "sector/{sectorId}")
    public ResponseEntity<?> deleteSector(@PathVariable("sectorId") Long sectorId)  {
        sectorService.delete(sectorId);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<TableInfoDTO>> saveAllTables(@RequestBody List<TableCreateDTO> tables)  {
        Collection<RestaurantTable> restaurantTables = tableCreateDTOToRestaurantTable.convert(tables);
        restaurantTables = tableService.saveAll(restaurantTables);

        return new ResponseEntity<>(toTableInfoDTO.convert(restaurantTables), HttpStatus.OK);
    }

    @GetMapping(value = "all-sectors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<SectorInfoDTO>> getAllSectors()  {
        Collection<Sector> sectors = sectorService.findAll();
        return new ResponseEntity<>(toSectorInfoDTO.convert(sectors), HttpStatus.OK);
    }

    @PostMapping(value = "sector", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SectorInfoDTO> saveSector(@RequestBody @Valid SectorCreateDTO sectorDTO)  {
        Sector sector = sectorCreateDTOToSector.convert(sectorDTO);
        sector = sectorService.save(sector);
        return new ResponseEntity<>(toSectorInfoDTO.convert(sector), HttpStatus.OK);
    }

    @PutMapping (value = "sector", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SectorInfoDTO> updateSector(@RequestBody SectorUpdateDTO sectorDTO)  {
        Sector sector = sectorUpdateDTOToSector.convert(sectorDTO);
        sector = sectorService.save(sector);
        return new ResponseEntity<>(toSectorInfoDTO.convert(sector), HttpStatus.OK);
    }


}
