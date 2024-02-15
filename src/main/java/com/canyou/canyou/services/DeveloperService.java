/* Created by El Hadji M. NDONGO  */
/* on 22 01 2024     */
/* Project: can-you       */

package com.canyou.canyou.services;

import com.canyou.canyou.dto.DeveloperDto;
import com.canyou.canyou.enums.Availability;

import java.util.List;

public interface DeveloperService {
    //getting all dev
    List<DeveloperDto> getAll();
    //getting one dev
    DeveloperDto getOne(Long id);
    //saving one dev
    DeveloperDto saveOne(DeveloperDto developer);
    //modifying one dev
    DeveloperDto modifyOne(Long idDeveloper, DeveloperDto developer);
    //delete one dev
    void deleteOne(Long id);

    List<DeveloperDto> getDevsByMinimumExperience(int totalYearsOfExperience);

    List<DeveloperDto> getAvailableDev(Availability availability);

    //todo: available and exp
    //todo: available and specialities
    //todo: exp and specialities
    //todo: available and exp and specialities
    //todo: dev by role
    //todo: implements pagination

}
