package com.ievgenrudenko.service;

import com.ievgenrudenko.pojo.MatchingResult;
import org.jsoup.nodes.Element;

import java.util.List;

public interface MatchingService {
    List<MatchingResult> getBestMatch(Element origin, List<Element> others);
}
