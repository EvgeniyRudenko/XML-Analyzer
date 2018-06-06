package com.ievgenrudenko.strategy;

import com.ievgenrudenko.pojo.MatchingResult;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AttributesMatchingStrategy extends AbstractMatchingStrategy {

    public AttributesMatchingStrategy() {
        this(2);
    }

    public AttributesMatchingStrategy(int priority) {
        super(priority);
    }

    @Override
    public Optional<MatchingResult> match(Element origin, Element other) {
        List<Attribute> originAttributes = origin.attributes().asList();
        List<Attribute> otherAttributes = other.attributes().asList();
        List<Attribute> intersection = new ArrayList<>(originAttributes);
        intersection.retainAll(otherAttributes);
        return intersection.isEmpty() ?
                Optional.empty():
                Optional.of(new MatchingResult(other,intersection.size(), false, intersection, null));
    }

}
