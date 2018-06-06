package com.ievgenrudenko.strategy;

import com.ievgenrudenko.pojo.MatchingResult;
import org.jsoup.nodes.Element;

import java.util.Optional;

public class TextMatchingStrategy extends AbstractMatchingStrategy {

    public TextMatchingStrategy() {
        this(1);
    }

    public TextMatchingStrategy(int priority) {
        super(priority);
    }

    @Override
    public Optional<MatchingResult> match(Element origin, Element other) {
        return origin.ownText().equalsIgnoreCase(other.ownText()) ?
                Optional.of(new MatchingResult(other, 1, true, null, null)):
                Optional.empty();
    }
}
