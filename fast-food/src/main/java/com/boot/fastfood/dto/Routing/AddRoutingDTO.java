package com.boot.fastfood.dto.Routing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddRoutingDTO {

    private String itCode;
    //private String pcCode;
    private List<String> pcCode;
    private int sequence;

}
