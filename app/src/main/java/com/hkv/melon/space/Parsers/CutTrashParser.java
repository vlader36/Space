package com.hkv.melon.space.Parsers;

import com.hkv.melon.space.interfaces.IParser;

public class CutTrashParser implements IParser <String, String> {
    @Override
    public String parse(String cutMe) {
        cutMe = cutMe.replace("[", "");
        cutMe = cutMe.replace("]", "");
        cutMe = cutMe.replace("{", "");
        cutMe = cutMe.replace("}", "");
        cutMe = cutMe.replace(",", " ");
        cutMe = cutMe.replace("\"", "");
        cutMe = cutMe.replaceAll("[\\s]{2,}", " ");
        String[] cuted = cutMe.split(" ");
        String boofEntry = "\n";
        boofEntry = boofEntry.concat(cuted[0]);
        cuted[0] = boofEntry;
        boofEntry = "";
        for (int i = 0; i < cuted.length; i++){
            cuted[i]+='\n';
            boofEntry = boofEntry.concat(cuted[i]);
        }

        return boofEntry;
    }
}
