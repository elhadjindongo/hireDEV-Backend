/* Created by El Hadji M. NDONGO  */
/* on 16 01 2024     */
/* Project: can-you       */

package com.canyou.canyou.repositories;

import com.canyou.canyou.entities.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository  extends JpaRepository<Developer,Long> {
}
