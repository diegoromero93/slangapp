package com.slangapp.demo.models;


import com.slangapp.demo.enums.ResourceTypeEnum;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "resources")
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Resource implements Serializable {

    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resource_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id", nullable = false)
    private Word word;

    @Column(name = "resource_url")
    private String resourceUrl;

    @Column(name = "resource_type", nullable = false)
    private String resourceType;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedOn;


    public ResourceTypeEnum getResourceType(){
        return ResourceTypeEnum.getResourceTypeEnum(this.resourceType);
    }

    public void setResourceType(ResourceTypeEnum resourceTypeEnum){
        this.resourceType = resourceTypeEnum != null ? resourceTypeEnum.getCode() : null;
    }

}
