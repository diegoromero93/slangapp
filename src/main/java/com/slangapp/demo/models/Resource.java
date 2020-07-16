package com.slangapp.demo.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.slangapp.demo.enums.ResourceTypeEnum;
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id", nullable = false)
    private Word word;

    @Column(name = "resource_url")
    private String resourceUrl;

    @Column(name = "resource_type", nullable = false)
    private String resourceType;


    public ResourceTypeEnum getResourceType(){
        return ResourceTypeEnum.getResourceTypeEnum(this.resourceType);
    }

    public void setResourceType(ResourceTypeEnum resourceTypeEnum){
        this.resourceType = resourceTypeEnum != null ? resourceTypeEnum.getCode() : null;
    }

}
