package com.ultimate.ultimatelinks.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Link_clicks")
public class LinkClickEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "link_id")
    private LinkEntity link;

    @Column(name = "click_timestamp")
    private LocalDateTime clickTimestamp;

    public LinkClickEntity(LinkEntity link, LocalDateTime clickTimestamp) {
        this.link = link;
        this.clickTimestamp = clickTimestamp;
    }
}
