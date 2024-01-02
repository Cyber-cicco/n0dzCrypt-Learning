package fr.diginamic.digilearning.utils.parser;

import java.util.ArrayList;
import java.util.List;

public class SyntaxGroup {

    public String prefixHTML;
    public String suffixHTML;
    public List<SyntaxGroup> children = new ArrayList<>();
    public String content;
    public SyntaxKind syntaxKind;
}
