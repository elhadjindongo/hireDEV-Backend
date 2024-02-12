/* Created by El Hadji M. NDONGO  */
/* on 16 01 2024     */
/* Project: can-you       */

package com.canyou.canyou.repositories;

import com.canyou.canyou.entities.Developer;
import com.canyou.canyou.enums.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {


    List<Developer> findAllByAvailability(Availability availability);
    List<Developer> findAllByYearsOfExperiencesGreaterThanEqual(int number);
    List<Developer> findAllBySpecialitiesIn(Set<String> specialities);
}
