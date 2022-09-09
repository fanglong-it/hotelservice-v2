package fiveman.hotelservice.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "overviewService")
public class OverviewService {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(required = true)
	private long id;
    private String title;
    private String imageUrl;
    private String description;
}
