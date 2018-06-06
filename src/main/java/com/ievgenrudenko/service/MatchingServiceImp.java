package com.ievgenrudenko.service;

import com.ievgenrudenko.pojo.MatchingResult;
import com.ievgenrudenko.strategy.AttributesMatchingStrategy;
import com.ievgenrudenko.strategy.ChildrenMatchingStrategy;
import com.ievgenrudenko.strategy.MatchingStrategy;
import com.ievgenrudenko.strategy.TextMatchingStrategy;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class MatchingServiceImp implements MatchingService {

    private Set<MatchingStrategy> matchingStrategies = new TreeSet<>();

    private static final Logger LOGGER = Logger.getLogger(MatchingServiceImp.class);

    public MatchingServiceImp() {
        matchingStrategies.add(new AttributesMatchingStrategy());
        matchingStrategies.add(new TextMatchingStrategy());
        matchingStrategies.add(new ChildrenMatchingStrategy());
    }

    public MatchingServiceImp(List<MatchingStrategy> matchingStrategies) {
        this.matchingStrategies.addAll(matchingStrategies);
    }

    @Override
    public List<MatchingResult> getBestMatch(Element origin, List<Element> others){
        return  getShortenedList(getMatchingElements(origin, others));
    }

    private void printMatches(List<MatchingResult> matchingResults) {
        if (matchingResults.isEmpty()){
            LOGGER.info("No matches found");
            return;
        }
        List<MatchingResult> shortened =  getShortenedList(matchingResults);
        if (shortened.size()>1)
            LOGGER.info("List of matches found:");
        for (MatchingResult matchingResult: shortened) {
            LOGGER.info(getPathToElement(matchingResult.getOther()));
            LOGGER.info(matchingResult);
        }
        LOGGER.info("");
    }

    private List<MatchingResult> getShortenedList(List<MatchingResult> matchingResults){
        if (matchingResults.isEmpty())
            return matchingResults;
        int numberOfMatches = matchingResults.get(0).getNumberOfMatches();
        return matchingResults.stream().filter(item -> item.getNumberOfMatches()==numberOfMatches).collect(Collectors.toList());
    }


    private List<MatchingResult> getMatchingElements(Element origin, List<Element> others){
        List<MatchingResult> matchedElementList = new ArrayList<>();
        List<Element> copyOfOthers = new ArrayList<>(others);
        List<MatchingStrategy> matchingStrategies = new ArrayList<>(this.matchingStrategies);
        for (int i = 0; i < matchingStrategies.size(); i++) {
            MatchingStrategy matchingStrategy = matchingStrategies.get(i);
            List<MatchingResult> tempResultList = getMatchingElementsForStrategy(origin, copyOfOthers, matchingStrategy);
            if (!tempResultList.isEmpty())
                matchedElementList = tempResultList;
            boolean readyToQuit = readyToTellTheBestMatch(tempResultList);
            logStep(matchingStrategies, i, readyToQuit);
            printMatches(matchedElementList);
            if (readyToQuit)
                break;
        }
        return matchedElementList;
    }

    private List<MatchingResult> getMatchingElementsForStrategy(Element origin, List<Element> others, MatchingStrategy strategy){
        List<MatchingResult> matchedElementList = new ArrayList<>();
        List<Element> notSuitables = new ArrayList<>();
        Optional<MatchingResult> matchingResult;
        for (Element element: others) {
            matchingResult = strategy.match(origin, element);
            if ((matchingResult.isPresent())) {
                matchedElementList.add(matchingResult.get());
            } else {
                notSuitables.add(element);
            }
        }
        if (!matchedElementList.isEmpty())
            others.removeAll(notSuitables);
        return matchedElementList;
    }

    private boolean readyToTellTheBestMatch(List<MatchingResult> matchedElementList) {
        if (matchedElementList.size()==0)
            return false;
        if (matchedElementList.size()==1)
            return true;
        Collections.sort(matchedElementList);
        return matchedElementList.get(0).getNumberOfMatches()!=
                matchedElementList.get(1).getNumberOfMatches();
    }

    private void logStep(List<MatchingStrategy> matchingStrategies, int i, boolean ready){
        LOGGER.info("Calling " + matchingStrategies.get(i));
        if (ready)
            LOGGER.info("Ready to give the best match");
    }

    private String getPathToElement(Element element) {
        final StringBuilder builder = new StringBuilder();
        for (int i = element.parents().size() - 1; i >= 0; i--) {
            builder.append(formatPath(element.parents().get(i))).append("> ");
        }
        builder.append(formatPath(element));
        return builder.toString();
    }

    private static String formatPath(Element element) {
        return String.format("%s[%s] ", element.tagName(), element.elementSiblingIndex());
    }

}
