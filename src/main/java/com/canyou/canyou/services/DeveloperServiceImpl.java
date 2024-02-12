/* Created by El Hadji M. NDONGO  */
/* on 22 01 2024 */
/* Project: can-you */

package com.canyou.canyou.services;

import com.canyou.canyou.dto.DeveloperDto;
import com.canyou.canyou.dto.SpecialitiesDto;
import com.canyou.canyou.dto.mapper.DeveloperMapper;
import com.canyou.canyou.entities.Developer;
import com.canyou.canyou.enums.Availability;
import com.canyou.canyou.exceptions.RessourceNotFoundException;
import com.canyou.canyou.repositories.DeveloperRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DeveloperServiceImpl implements DeveloperService {

    private final DeveloperRepository developerRepository;

    private final DeveloperMapper developerMapper;

    @Override
    public List<DeveloperDto> getAll() {
        List<Developer> all = developerRepository.findAll();
        return all.stream().map(developerMapper::toDto).toList();
    }

    @Override
    public DeveloperDto getOne(Long idDeveloper) {
        Developer developer = developerRepository.findById(idDeveloper).orElseThrow(() -> new RessourceNotFoundException(Developer.class.getSimpleName(), idDeveloper));
        return developerMapper.toDto(developer);
    }

    @Override
    public DeveloperDto saveOne(DeveloperDto developer) {
        Developer saved = developerRepository.save(developerMapper.toEntity(developer));
        return developerMapper.toDto(saved);
    }

    @Override
    public DeveloperDto modifyOne(Long idDeveloper, DeveloperDto developer) {
        this.getOne(idDeveloper);
        developer.setId(idDeveloper);
        Developer saved = developerRepository.save(developerMapper.toEntity(developer));
        return developerMapper.toDto(saved);
    }

    @Override
    public List<DeveloperDto> getDevsByMinimunExperience(int totalYearsOfExperience) {
        List<Developer> allByYearsOfExperiencesGreaterThanEqual = developerRepository.findAllByYearsOfExperiencesGreaterThanEqual(totalYearsOfExperience);
        return allByYearsOfExperiencesGreaterThanEqual.stream().map(developerMapper::toDto).toList();
    }

    @Override
    public List<DeveloperDto> getAvailableDev(Availability availability) {
        List<Developer> allByAvailability = developerRepository.findAllByAvailability(availability);
        return allByAvailability.stream().map(developerMapper::toDto).toList();
    }

    @Override
    public List<DeveloperDto> getDevBySpecialities(SpecialitiesDto specialities) {
        //empty means all specialities
        if (specialities.getSpecialities().isEmpty()) {
            return this.getAll();
        } else {
            List<Developer> allBySpecialities = this.developerRepository.findAllBySpecialitiesIn(specialities.getSpecialities());
            return allBySpecialities.stream().map(developerMapper::toDto).toList();
        }
    }

    @Override
    public void deleteOne(Long idDeveloper) {
        this.getOne(idDeveloper);
        developerRepository.deleteById(idDeveloper);
    }
}
