/*
 * @Author : Thant Htoo Aung
 * @Date : 2/10/2025
 */
package org.oneProjectOneMonth.lms.security.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    
    private String phone;
    private LocalDate dob;
    private String address;
    private String profilePhoto;
    private String nrc;
    private String eduBackground;
}