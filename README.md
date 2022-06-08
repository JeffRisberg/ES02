# ES02
 Prerequisite : Java 8 and Later version

### Start ElasticSearch
1) Download elasticsearch from [here](https://www.elastic.co/downloads/elasticsearch)
2) Extract downloaded elasticsearch
3) cd elasticsearch-7.15.2
4) $ bin/elasticsearch

### Start the application

1) Test with http://localhost:8080/hello
2) Then try http://localhost:8080/get/products/1
3) Then try http://localhost:8080/matchTerm/category/meat

This will perform a TermQuery for products with "meat" in the their category.  We
only fetch the first 20 (there about about 19 of them).

4) Then try http://localhost:8080/match/description/Pellentesque

This will perform a MatchQuery for products with "Pellentesque" in their description.
There are about 190 of them of them, but we only fetch the first 10.

5) Then try http://localhost:8080/matchPhrase/description/Pellentesque ultrices

This will perform a MatchPhraseQuery for products with "Pellentesque ultrices" in
their description (as a phrase, meaning that words must be adjacent within the slop
factor).  We only fetch the first 10.

6) Then try http://localhost:8080/multiMatch/category,description/meat

This will look for the work "meat" in two different fields.
