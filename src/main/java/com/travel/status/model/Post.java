package com.travel.status.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "post")
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Integer postId;

    @NotNull
    @Column(name = "user_id")
    private String userId;

    @Column(name = "status")
    @NotNull
    private String status;

    @Column(name = "privacy")
    @NotNull
    private boolean privacy;

    @NotNull
    @Column(name = "check_in")
    private String checkIn;

    @Column(name = "pin_status")
    @NotNull
    private boolean pinStatus;

    @Column(name = "posted_at")
    @NotNull
    private Date postedAt;

}
