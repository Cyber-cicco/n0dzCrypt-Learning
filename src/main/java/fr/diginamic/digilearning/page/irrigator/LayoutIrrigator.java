package fr.diginamic.digilearning.page.irrigator;

import fr.diginamic.digilearning.components.service.NavBarService;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
public class LayoutIrrigator {
    public static final String ADR_BASE_LAYOUT = "layout/base";
    private final NavBarService navBarService;
    public void irrigateBaseLayout(Model model, AuthenticationInfos userInfos, String fragmentAdress) {
        model.addAttribute("insert", fragmentAdress);
        model.addAttribute("links", navBarService.getLinks(userInfos));
    }
}
