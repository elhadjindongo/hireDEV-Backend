/* Created by El Hadji M. NDONGO  */
/* on 09 02 2024 */
/* Project: can-you */

package com.canyou.canyou.utils;

public class SharedConst {
    public static final String NAME_REGEX = "^[\\sa-zA-Z,\\.'-]+$";//can contain alphabet, space comma dot apostrophe and hyphen
    public static final String ROLE_REGEX = "^[\\s\\w/,\\.-]+$"; //can contain alphanumeric, space comma slash and hyphen
    public static final String AVAILABILITY_REGEX = "NOW|SOON";

    public static final String EXPERIENCE_LABEL = "experience";
    public static final String AVAILABILITY_LABEL = "availability";
    public static final int MAX_EXPERIENCE_VALUE = 100;
    public static final int MIN_EXPERIENCE_VALUE = 0;
    public static final int MIN_SPECIALITIES_VALUE = 1;
}
