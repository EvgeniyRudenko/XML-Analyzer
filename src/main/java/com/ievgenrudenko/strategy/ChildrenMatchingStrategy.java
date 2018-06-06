package com.ievgenrudenko.strategy;

import com.ievgenrudenko.pojo.MatchingResult;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChildrenMatchingStrategy extends AbstractMatchingStrategy {

    public ChildrenMatchingStrategy() {
        this(3);
    }

    public ChildrenMatchingStrategy(int priority) {
        super(priority);
    }

    @Override
    public Optional<MatchingResult> match(Element origin, Element other) {
        List<Element> originChildren = new ArrayList<>(origin.children());
        List<Element> otherChildren = new ArrayList<>(other.children());
        if (otherChildren.isEmpty())
            return Optional.empty();
        List<Element> intersection = new ArrayList<>(originChildren);
        /*
        List retainAll method is not applicable
        https://github.com/jhy/jsoup/blob/master/src/main/java/org/jsoup/nodes/Node.java#L611
        public boolean equals(Object o)
        public boolean hasSameValue(Object o)
        There must be a misprint in the description because both method state they return true
        if the content of this node is the same as the other which is obviously not the case for equals method
        */

        /*List contains method is not applicable because it is based on equals as well*/
        boolean isContained;
        for (Element originChild :  originChildren) {
            isContained = false;
            for (Element otherChild : otherChildren) {
                if (originChild.hasSameValue(otherChild)) {
                    isContained = true;
                    break;
                }
            }
            if (!isContained)
                intersection.remove(originChild);
        }

        return intersection.isEmpty() ?
                Optional.empty():
                Optional.of(new MatchingResult(other,intersection.size(), false, null,intersection));
    }
}
