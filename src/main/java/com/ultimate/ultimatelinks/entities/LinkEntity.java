package com.ultimate.ultimatelinks.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "links")
public class LinkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sourceLink;
    @Column(unique = true)
    private String shortLink;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "link")
    @JsonIgnore
    private List<LinkClickEntity> linkClicks;

    public LinkEntity(String sourceLink, String shortLink, UserEntity user) {
        this.sourceLink = sourceLink;
        this.shortLink = shortLink;
        this.user = user;
    }
}