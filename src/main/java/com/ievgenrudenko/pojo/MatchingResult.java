package com.ievgenrudenko.pojo;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;

import java.util.List;

public class MatchingResult implements Comparable <MatchingResult>{
    private Element other;
    private int numberOfMatches;
    private boolean sameText;
    private List<Attribute> sharedAttributes;
    private List<Element> sharedChildren;

    public Element getOther() {
        return other;
    }

    public int getNumberOfMatches() {
        return numberOfMatches;
    }

    public MatchingResult(Element other, int numberOfMatches, boolean sameText,
                          List<Attribute> sharedAttributes, List<Element> sharedChildren) {
        this.other = other;
        this.numberOfMatches = numberOfMatches;
        this.sameText = sameText;
        this.sharedAttributes = sharedAttributes;
        this.sharedChildren = sharedChildren;
    }

    @Override
    public int compareTo(MatchingResult other) {
        return Integer.compare(other.numberOfMatches, numberOfMatches);
    }

    @Override
    public String toString() {
        String explanation =  sameText ?
                "Elements have the same text" : sharedAttributes!=null ?
                "{sharedAttributes = " + sharedAttributes + "}":
                "{sharedChildren = " + sharedChildren.toString().replace("\n","") + "}";
        return getClass().getSimpleName() + ": " + explanation;
    }
}
