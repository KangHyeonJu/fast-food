package com.boot.fastfood.entity;

import com.boot.fastfood.dto.ClientDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "clients")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Clients {
    @Id
    @Column(name = "clCode")
    private String clCode;

    @Column(name = "clName")
    private String clName;

    @Column(name = "clType")
    private String clType;

    @Column(name = "clPhone")
    private String clPhone;

    @Column(name = "clAmount")
    private Long clAmount;

    //고객 등록
    public static Clients createClient(ClientDto clientDto){
        return Clients.builder()
                .clCode(clientDto.getClCode())
                .clName(clientDto.getClName())
                .clType(clientDto.getClType())
                .clPhone(clientDto.getClPhone())
                .build();
    }


}
