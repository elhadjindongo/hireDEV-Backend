package com.canyou.canyou.repositories;

import com.canyou.canyou.entities.Developer;
import com.canyou.canyou.enums.Availability;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Set;


@DataJpaTest
class DeveloperRepositoryTest {

    @Autowired
    DeveloperRepository underTest;

    @Autowired
    TestEntityManager em;

    @BeforeEach
    void setUp() {
        Developer dev1 = Developer.builder()
                .fullName("Fatou Fall")
                .role("Front End Software Engineer")
                .yearsOfExperiences(3)
                .availability(Availability.NOW)
                .specialities(Set.of("HTML", "CSS", "JavaScript", "React"))
                .build();
        Developer dev2 = Developer.builder()
                .fullName("Demba Ly")
                .role("Devops Engineer")
                .yearsOfExperiences(5)
                .availability(Availability.SOON)
                .specialities(Set.of("Kubernetes", "Ansible", "AWS", "Docker", "Jenkins"))
                .build();
        Developer dev3 = Developer.builder()
                .fullName("Awa Sambou")
                .role("Backend Software Engineer")
                .yearsOfExperiences(7)
                .availability(Availability.NOW)
                .specialities(Set.of("Java", "Java EE", "Spring / SpringBoot", "MongoDB"))
                .build();
        em.persist(dev1);
        em.persist(dev2);
        em.persist(dev3);
    }

    @Test
    void findAllByAvailability_NOW() {
        List<Developer> result = underTest.findByAvailability(Availability.NOW);
        Assertions.assertThat(result).isNotEmpty();
        Assertions.assertThat(result.size()).isEqualTo(2);
        result.forEach((d) -> Assertions.assertThat(d.getAvailability()).isEqualTo(Availability.NOW));
    }

    @Test
    void findAllByAvailability_SOON() {
        List<Developer> result = underTest.findByAvailability(Availability.SOON);
        Assertions.assertThat(result).isNotEmpty();
        Assertions.assertThat(result.size()).isEqualTo(1);
        result.forEach((d) -> Assertions.assertThat(d.getAvailability()).isEqualTo(Availability.SOON));
    }

    @Test
    void findAllByYearsOfExperiencesGreaterThanEqual() {
        List<Developer> result = underTest.findByYearsOfExperiencesGreaterThanEqual(5);
        Assertions.assertThat(result).isNotEmpty();
        Assertions.assertThat(result.size()).isEqualTo(2);
        result.forEach((d) -> Assertions.assertThat(d.getYearsOfExperiences()).isGreaterThanOrEqualTo(5));

    }
}