package fr.diginamic.digilearning.page.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
public class HomeService {

    public void irrigateModel(Model model){
        model.addAttribute("title", "Bienvenu sur Digelearning !");
    }
}
