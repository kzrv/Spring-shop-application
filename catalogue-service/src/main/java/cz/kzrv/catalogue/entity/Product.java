package cz.kzrv.catalogue.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "catalogue",name= "t_product")
@NamedQueries(
        @NamedQuery(name="Product.findAllByTitleLikeIgnoringCase",
            query = "select p from Product p where p.title ilike :filter")
)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @Column(name = "c_title")
    @Size(min = 3, max = 50)
    private String title;
    @Size(max = 1000)
    @Column(name = "c_details")
    private String details;

}
