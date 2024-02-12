/* Created by El Hadji M. NDONGO  */
/* on 02 02 2024     */
/* Project: can-you       */

package com.canyou.canyou.dto.mapper;

import com.canyou.canyou.dto.DeveloperDto;
import com.canyou.canyou.entities.Developer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeveloperMapper {
    Developer toEntity(DeveloperDto developerDto);

    DeveloperDto toDto(Developer developer);
}
