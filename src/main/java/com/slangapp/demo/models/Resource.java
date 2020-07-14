package com.slangapp.demo.models;


import com.slangapp.demo.enums.ResourceType;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "resources")
@EqualsAndHashCode(of = { "id" })
@ToString(of = { "id" })
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Resource {


    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resource_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "word_id", nullable = false)
    private Word word;

    @Column(name = "resource_url")
    private String resourceUrl;

    @Column(name = "resource_type", nullable = false)
    private String resourceType;


    public ResourceType getResourceType(){
        return ResourceType.getResourceTypeEnum(this.resourceType);
    }

    public void setResourceType(ResourceType resourceType){
        this.resourceType = resourceType != null ? resourceType.getCode() : null;
    }

}
