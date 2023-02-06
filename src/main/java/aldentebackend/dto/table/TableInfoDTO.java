package aldentebackend.dto.table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class TableInfoDTO {
    private Long id;

    @NotNull
    private Short tableNumber;

    @NotNull
    private Short numberOfSeats;

    @NotNull
    private Short positionX;

    @NotNull
    private Short positionY;

    @NotNull
    private Short tableWidth;

    @NotNull
    private Short tableHeight;

    @NotBlank
    private String sectorName;

    @NotNull
    private Long sectorId;

    private Boolean active;
}
