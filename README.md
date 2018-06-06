# XML-Analyzer

To run the app:
java -jar <path_to_app>.jar <input_origin_file_path> <input_other_sample_file_path> <input_attribute_id>

Output result for sample-1-evil-gemini.html
Calling TextMatchingStrategy{priority=1}
Ready to give the best match
html[0] > body[1] > div[0] > div[1] > div[2] > div[0] > div[0] > div[1] > a[1] 
MatchingResult: Elements have the same text


Output result for sample-2-container-and-clone.html
Calling TextMatchingStrategy{priority=1}
List of matches found:
html[0] > body[1] > div[0] > div[1] > div[2] > div[0] > div[0] > div[1] > div[0] > a[0] 
MatchingResult: Elements have the same text
html[0] > body[1] > div[0] > div[1] > div[2] > div[1] > div[0] > div[1] > a[2] 
MatchingResult: Elements have the same text

Calling AttributesMatchingStrategy{priority=2}
Ready to give the best match
html[0] > body[1] > div[0] > div[1] > div[2] > div[0] > div[0] > div[1] > div[0] > a[0] 
MatchingResult: {sharedAttributes = [href="#ok", title="Make-Button", rel="next"]}


Output result for sample-3-the-escape.html
Calling TextMatchingStrategy{priority=1}
No matches found
Calling AttributesMatchingStrategy{priority=2}
Ready to give the best match
html[0] > body[1] > div[0] > div[1] > div[2] > div[0] > div[0] > div[2] > a[0] 
MatchingResult: {sharedAttributes = [class="btn btn-success", href="#ok", rel="next", onclick="javascript:window.okDone(); return false;"]}


Output result for sample-4-the-mash.html
Calling TextMatchingStrategy{priority=1}
No matches found
Calling AttributesMatchingStrategy{priority=2}
Ready to give the best match
html[0] > body[1] > div[0] > div[1] > div[2] > div[0] > div[0] > div[2] > a[0] 
MatchingResult: {sharedAttributes = [class="btn btn-success", href="#ok", title="Make-Button", rel="next"]}

