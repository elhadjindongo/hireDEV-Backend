/* Created by El Hadji M. NDONGO  */
/* on 07 02 2024 */
/* Project: can-you */

package com.canyou.canyou.utils;

import static com.canyou.canyou.utils.SharedConst.AVAILABILITY_LABEL;

public class ObjectDataExemple {
    public static final String NOT_FOUND =
            """
                       {
                            "error": " Developer with id 123 is not found !"
                       }
                    """;
    public static final String BAD_REQUEST =
            """
                       {
                            "availability": "You should provide either NOW or SOON for availability"
                       }
                    """;
    public static final String SAVE_EXEMPLE =
            """
                        {
                          "fullName": "Sarah Smith",
                          "role": "Front Developer",
                          "yearsOfExperiences": 8,
                          "availability": "NOW",
                          "specialities": [
                            "HTML / CSS",
                            "Angular",
                            "React",
                            "JS / TS",
                            "Docker"
                          ]
                        }
                    """;
    public static final String EDIT_EXEMPLE =
            """
                        {
                          "fullName": "John Doe",
                          "role": "Software & Devops Engineer",
                          "yearsOfExperiences": 12,
                          "availability": "NOW",
                          "specialities": [
                            "Java",
                            "Spring / Spring Boot",
                            "React",
                            "Kubernetes",
                            "Openshift",
                            "Jenkins",
                            "Docker"
                          ]
                        }
                    """;
}
