package com.acme.user;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String uuid;
    private String firstName;
    private String lastName;
    private String email;
}
