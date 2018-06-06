package com.ievgenrudenko.strategy;

import com.ievgenrudenko.pojo.MatchingResult;
import org.jsoup.nodes.Element;

import java.util.Optional;

public interface MatchingStrategy {
    Optional<MatchingResult> match(Element origin, Element other);
}
