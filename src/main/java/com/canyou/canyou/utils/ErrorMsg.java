/* Created by El Hadji M. NDONGO  */
/* on 23 01 2024 */
/* Project: can-you */

package com.canyou.canyou.utils;

public class ErrorMsg {
    public static final String SPECIALITY_ERROR_MSG = "You should provide at least one speciality and elements inside can not be empty";

    public static final String AVAILABILITY_ERROR_MSG = "You should provide either NOW or SOON for availability";
    public static final String CAN_NOT_BE_BLANK = "can not be null or blank";
    public static final String YEARS_OF_EXPERIENCE_ERROR_MSG = CAN_NOT_BE_BLANK + " and value should be between 0 and 99 included";
    public static final String FULL_NAME_ERROR_MSG = "Full name " + CAN_NOT_BE_BLANK;
    public static final String ROLE_ERROR_MSG = "Role " + CAN_NOT_BE_BLANK;
    public static final String ILLEGAL_EXPERIENCE_MSG = " should be a positif number !";
    public static final String RESSOURCE_NOT_FOUND_MSG = " %s with id %d is not found !";
    public static final String CAN_NOT_CONTAIN_SPECIAL_CHAR_AND_DIGIT_ERROR_MSG = "can not contain special characters, only alphanumeric comma, hyphen, full stop and apostrophe";
    public static final String CAN_NOT_CONTAIN_SPECIAL_CHAR_ERROR_MSG = "can not contain special characters, only alphabet comma, hyphen, full stop and apostrophe";
}
