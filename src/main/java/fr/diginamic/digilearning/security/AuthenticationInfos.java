package fr.diginamic.digilearning.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationInfos {
    private List<String> roles = new ArrayList<>();
    private String email;
    private String token;
}
