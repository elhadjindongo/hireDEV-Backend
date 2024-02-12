/* Created by El Hadji M. NDONGO  */
/* on 16 01 2024 */
/* Project: can-you */

package com.canyou.canyou.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Mission {
    @Id
    @GeneratedValue
    private Long id;
    private String description;
    private String company;
    private String location;
    private Set<String> requirements;
    private double salary;
    @Temporal(TemporalType.DATE)
    private LocalDate startDate;
    @Temporal(TemporalType.DATE)
    private LocalDate endDate;
    private LocalDateTime postedAt;
    private LocalDateTime Deadline;
    private boolean open;
}
