/* Created by El Hadji M. NDONGO  */
/* on 22 01 2024 */
/* Project: can-you */

package com.canyou.canyou.exceptions;

import static com.canyou.canyou.utils.ErrorMsg.RESSOURCE_NOT_FOUND_MSG;

public class RessourceNotFoundException extends RuntimeException {
    public RessourceNotFoundException(String ressourceName, Long ressourceId) {
        super(String.format(RESSOURCE_NOT_FOUND_MSG,ressourceName,ressourceId));
    }
}
