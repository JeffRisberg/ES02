package com.company.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Builder
@Slf4j
public class SearchQuery {

    // namespace is same as TenantId
    @Getter
    private String namespace;
    @Getter private long botId;
    @Getter @Singular
    private List<String> indexes;
    @Getter private String allEntities = "";
    //@Getter @Singular private List<Quad> questionQuads;
    @Getter private Map<String, List<String>> synonymsMap;
    @Getter private Sentence sentence;
    @Getter @Singular private List<Map<String, String>> entityCategories;
    @Getter @Singular private List<String> tokens;
    @Getter private String questionText;
    @Getter private String assetDesc;
    @Getter
    @Setter
    private SearchType searchType = SearchType.PHRASE_IN_SUBJECT;
    @Getter @Setter private List<String> aclRoles;

    public boolean hasTokens() {
        return ((this.sentence != null) && (this.sentence.getWordsCount() > 0));
    }

    public String[] getIndexesAsArray() {
        return this.indexes.stream().toArray(String[]::new);
    }

    /*
    public Optional<String> getWHWord() {
        AtomicReference<String> word = new AtomicReference<>();
        if (this.sentence != null) {
            ProtoUtils.iterateOnWords(this.sentence, theWord -> {
                if (WordUtils.WordTagUtils.isQuestionWord(theWord.getType())) {
                    word.set(theWord.getWord());
                }
            });
        }

        return (word.get() != null) ? Optional.of(word.get()) : Optional.empty();
    }
    */

    /**
     * Only use nouns for now...
     * Think about verbs/synonyms/hyponyms
     * @return
     */
//  public List<String> getTokens() {
//    List<String> tokens = new ArrayList<>();
//    this.sentence.getWordsList().forEach((w) -> {
//      if (WordUtils.WordTagUtils.isNoun(w.getType())) {
//        tokens.add(w.getWord());
//      }
//    });
//    return tokens;
//  }

    /*
    public Collection<String> getIntents() {
        Set<String> intents = new HashSet<>();
        this.questionQuads.forEach(quad -> {
            String[] words = quad.getPredicate().getWords().split(" ");
            for (String word : words) {
                // if the word matches a  WH word don't use it in the intent
                ProtoUtils.iterateOnWords(sentence, w -> {
                    if (word.equalsIgnoreCase(w.getWord()) &&
                            (!WordUtils.WordTagUtils.isQuestionWord(w.getType()))) {
                        intents.add(word);
                    }
                });
            }
        });
        logger.info("Question intents : {}", intents);
        return intents;
    }
    */

    public Collection<String> getEntities() {
        return this.getEntities(false, false);
    }

    public Collection<String> getEntities(boolean withSynonyms, boolean withHypernyms) {
        Set<String> entities = new HashSet<>();

        /*
        entities.addAll(ProtoUtils.getEntities(this.getQuestionQuads()));
        WordUtils.getEntities(this.sentence, entity -> {
            if (withSynonyms) {
                entity.getSynonymsList().forEach(sym -> entities.add(sym));
            }
            if (withHypernyms) {
                entity.getHypernymsList().forEach(hypernym -> entities.add(hypernym.getWord()));
            }
        });

        ProtoUtils.iterateOnWords(this.sentence, (w) ->
                questionQuads.forEach(quad -> {
                    quad.getSubject().getTokensList().forEach(word -> {
                        if (word.getWord().contains(w.getWord())) {
                            if (WordUtils.WordTagUtils.isNoun(w.getType())) {
                                entities.add(w.getWord());
                                if (withHypernyms) {
                                    w.getHypernymsList().forEach(hypernym -> entities.add(hypernym.getWord()));
                                }
                            }
                        }
                    });

                    quad.getObject().getTokensList().forEach(word -> {
                        if (word.getWord().contains(w.getWord())) {
                            if (WordUtils.WordTagUtils.isNoun(w.getType())) {
                                entities.add(w.getWord());
                                if (withHypernyms) {
                                    w.getHypernymsList().forEach(hypernym -> entities.add(hypernym.getWord()));
                                }
                            }
                        }
                    });
                })
        );
        */

        // if there are no entities in the question
        if (entities.isEmpty()) {

        }

        log.info("Question entities : {}", entities);
        return entities;
    }

    /**
     * Only use nouns for now...
     * Think about verbs/synonyms/hyponyms
     * @return
     */
    /*
    public List<List<String>> getSynonyms() {
        List<List<String>> tokens = new ArrayList<>();
        this.sentence.getWordsList().forEach((w) -> {
            if (WordUtils.WordTagUtils.isNoun(w.getType())) {
                tokens.add(Arrays.asList(w.getWord()));
            } else if (WordUtils.WordTagUtils.isVerb(w.getType())) {
                List<String> tmp = new ArrayList<>();
                tmp.clear();
                tmp.add(w.getWord());
                tmp.addAll(w.getSynonymsList());
                tokens.add(tmp);
            }
        });
        return tokens;
    }
    */

    /**
     * Only use nouns for now...
     * Think about verbs/synonyms/hyponyms
     * @return
     */
    /*
    public List<String> getAbstracts() {
        List<String> terms = new ArrayList<>();
        this.sentence.getWordsList().forEach((w) -> {
            if (WordUtils.WordTagUtils.isNoun(w.getType())) {
                terms.add(w.getWord());
                terms.addAll(w.getHyponymsList());
            } else if (WordUtils.WordTagUtils.isVerb(w.getType())) {
                terms.add(w.getWord());
                terms.addAll(w.getHyponymsList());
            }
        });
        return terms;
    }
    */
}
