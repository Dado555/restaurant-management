package aldentebackend.dto.sector;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class SectorUpdateDTO {

    @NotNull
    private Long id;

    @NotNull
    private String name;

}
