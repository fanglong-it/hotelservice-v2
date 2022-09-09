package fiveman.hotelservice.entities;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "hotel")
public class Hotel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(required = true)
    private long id;
    private String name;
    private String phoneNumber;
    private String description;
    private String longtidude;
    private String latitude;


    @ManyToMany(fetch = FetchType.LAZY,targetEntity = Location.class)
    @JoinColumn(name = "description")
    private List<Location> locations = new ArrayList<>();

}
