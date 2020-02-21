package com.company.common;

public enum SearchType {
    PHRASE_IN_SUBJECT,
    BIGRAM_IN_SUBJECT,
    SPAN_IN_SUBJECT,
    SPAN_IN_CONTENT,
    SPAN_IN_SUBJECT_AND_CONTENT,
    SPAN_IN_HIERARCHICAL_SUBJECT,
    BIGRAM_IN_HIERARCHICAL_SUBJECT,
    KEYWORD_IN_TITLE,
    EXTERNAL_KB_ID,
    INTENT,
    ENTITY,
    ENTITY_IN_SUBJECT,
    ENTITY_IN_HIERARCHICAL_SUBJECT,
    ENTITY_IN_CONTENT,
    ENTITY_IN_TITLE,
    INTENT_ENTITY,
    QUAD,
    ABSTRACT,
    FULLTEXT_KEYWORD,
    FULLTEXT_PHRASE,
    KEYWORD_IN_SUBJECT,
    KEYWORD_IN_HIERARCHICAL_SUBJECT,
    KEYWORD_IN_CONTENT,
    INTENT_ENTITY_IN_OVERRIDE,
    EXCLUDE_DOCS,
    INTENT_OVERRIDE,
    INCIDENT,
    ONTOLOGY,
    ONTOLOGY_IN_SUBJECT,
    ONTOLOGY_IN_CONTENT,
    SYNONYM,
    FILTER_BY_ROLES,
    MATCH_CONTENT_ID,
    MATCH_ASSET,
    NONE
}
