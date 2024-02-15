/* Created by El Hadji M. NDONGO  */
/* on 16 01 2024     */
/* Project: can-you       */

package com.canyou.canyou.repositories;

import com.canyou.canyou.entities.Developer;
import com.canyou.canyou.enums.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {


    List<Developer> findByAvailability(Availability availability);

    List<Developer> findByYearsOfExperiencesGreaterThanEqual(int number);

    /*
     * find dev that have the given speciality
     *
     * @param speciality a particular speciality
     * @return all dev that have the given speciality in their specialities attribute
     */
//    List<Developer> findBySpecialitiesLike(String speciality);
}
