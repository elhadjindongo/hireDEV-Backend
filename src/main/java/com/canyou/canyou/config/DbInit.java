/* Created by El Hadji M. NDONGO  */
/* on 16 01 2024 */
/* Project: can-you */

package com.canyou.canyou.config;

import com.canyou.canyou.entities.Developer;
import com.canyou.canyou.enums.Availability;
import com.canyou.canyou.repositories.DeveloperRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class DbInit {
    private static final Logger log = LoggerFactory.getLogger(DbInit.class);

    @Bean
    CommandLineRunner loadDev(DeveloperRepository developerRepository) {
        return args -> {
            log.info("----------------------- START Preloading Developers !!!");
            Developer dev1 = Developer.builder().id(null)
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
                    .availability(Availability.NOW)
                    .specialities(Set.of("Kubernates", "Ansible", "AWS", "Docker", "Jenkins"))
                    .build();
            Developer dev3 = Developer.builder()
                    .fullName("Awa Sambou")
                    .role("Backend Software Engineer")
                    .yearsOfExperiences(7)
                    .availability(Availability.NOW)
                    .specialities(Set.of("Java", "Java EE", "Spring / SpringBoot", "MongoDB"))
                    .build();
            log.info(developerRepository.save(dev1).toString());
            log.info(developerRepository.save(dev2).toString());
            log.info(developerRepository.save(dev3).toString());
            log.info("----------------------- END Preloading Developers !!!");

        };
    }
}
