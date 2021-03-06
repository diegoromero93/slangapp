package com.slangapp.demo.models;

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
@RequiredArgsConstructor
@ToString(of = { "id" })
@EqualsAndHashCode(of = { "id" })
@Table(name = "phoneme_distractors")
public class PhonemeDistractor implements Serializable {

    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phoneme_distractor_id", nullable = false)
    private Long id;

    @Column(name = "phoneme")
    private String phoneme;

    @Column(name = "distractor")
    private String distractor;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedOn;
}
