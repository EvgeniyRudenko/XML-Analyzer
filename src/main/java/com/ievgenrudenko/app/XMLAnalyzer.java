package com.ievgenrudenko.app;

import com.ievgenrudenko.pojo.MatchingResult;
import com.ievgenrudenko.service.MatchingService;
import com.ievgenrudenko.service.MatchingServiceImp;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class XMLAnalyzer {

    private static final String DEFAULT_CHARSET_NAME = "UTF-8";
    private static final Logger LOGGER = Logger.getLogger(XMLAnalyzer.class);
    private static final MatchingService matchingService = new MatchingServiceImp();


    public static void main(String[] args) {
        final String originPath = args[0];
        final String otherPath = args[1];
        final String elementId = args[2];

        final Document originDocument = parseFile(new File(originPath));
        final Element origin = findElementById(originDocument, elementId);

        if (origin==null) {
            LOGGER.info(String.format("Element id=\"%s\" not found", elementId));
            return;
        }

        final Document secondDocument = parseFile(new File(otherPath));
        final Elements others = findElementsByTag(secondDocument, origin.tagName());

        if (others.isEmpty()) {
            LOGGER.info(String.format("No matches for element id=\"%s\" found", elementId));
            return;
        }

        final List<MatchingResult> bestMatch = matchingService.getBestMatch(origin, others);
        if (bestMatch.isEmpty()){
            LOGGER.info(String.format("No matches for element id=\"%s\" found", elementId));
        }

    }

    private static Element findElementById(Document source, String elementId) {
        return source.body().getElementById(elementId);
    }

    private static Elements findElementsByTag(Document source, String tag) {
        return source.body().getElementsByTag(tag);
    }

    private static Document parseFile(File source) {
        try {
            return Jsoup.parse(source, DEFAULT_CHARSET_NAME, source.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Unable to parse the file", e);
        }
    }

}
