package com.boot.fastfood.dto.Routing;

import com.boot.fastfood.entity.Routing;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class RoutingListDTO {


    private String itCode;
    private String pcCode;
    private int sequence;

    /*
    public RoutingListDTO(Routing routing) {
        this.itCode = routing.getItems();
        this.itCode = routing.getProcess();
        this.sequence = routing.getSequence();
    }

     */

    public RoutingListDTO(String itCode, String pcCode, int sequence) {
        this.itCode = itCode;
        this.pcCode = pcCode;
        this.sequence = sequence;
    }

}
