package com.company.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
public class SearchResultRecord {

    @Getter
    private String id;
    @Getter
    private String indexId;
    @Getter
    private String[] matchedQueries;
    @Getter
    private String contentId;
    @Getter
    @Setter
    private float score;
    @Getter
    private ResultPosition position;
    //@Getter private DocumentContent content;
    //@Getter private Graph linkedGraph;
    @Getter
    private String title;
    @Getter
    private String titleDesc;
    @Getter
    private List<String> externalKBIds;
    @Getter
    private String serviceActionUrl;
    @Getter
    public String subject;
    @Getter
    private String sourceAddress;
    // @Getter private SourceChannelType channelType;
    @Getter
    private String rawDataURI;
    //@Getter private DocumentContent sourceDocument;
    @Getter
    private IntentRecord intent;
    @Getter
    private String documentTitle;
    @Getter
    @Setter
    private List<MatchedKeywords> matches;
    @Getter
    @Setter
    private List<MatchStat> matchStats;
    @Getter
    @Setter
    private float reScore = 0.0f;
    @Getter
    @Setter
    List<String> roles;
    @Getter
    @Setter
    List<String> assetTypes;

    @Builder
    public static class ResultPosition {
        @Getter
        private long position;
        @Getter
        private long queryHitCount;
    }

    // IntentRecord for Intent related APIs
    @Builder
    public static class IntentRecord {
        @Getter
        @Builder.Default
        private String questionIntents = "";
        @Getter
        @Builder.Default
        private String questionEntities = "";
        @Getter
        @Builder.Default
        private String subjectIntents = "";
        @Getter
        @Builder.Default
        private String subjectSuperIntents = "";
        @Getter
        @Builder.Default
        private String subjectEntities = "";
        // subject of a document. It is field for title as well.
        @Getter
        @Builder.Default
        private String subject = "";
        //single intent of question or subject
        @Getter
        @Builder.Default
        private String intent = "";
        //entity of question or subject
        @Getter
        @Builder.Default
        private String entity = "";
        @Getter
        private String channelType;
        @Getter
        private int count;
        @Getter
        private String overrideId;
        @Getter
        private String overrideText;
        @Getter
        @Builder.Default
        private String sourceUri = "";
        @Getter
        @Builder.Default
        private String rawDataUri = "";
    }

    @Builder
    public static class MatchedKeywords {
        @Getter
        @Setter
        List<String> keywords;
        @Getter
        @Setter
        String type;
        @Getter
        @Setter
        String field;
        @Getter
        @Setter
        float score;
    }

    @Builder
    public static class MatchStat {
        @Getter
        @Setter
        String type;
        @Getter
        @Setter
        String field;
        @Getter
        @Setter
        int totalCount;
        @Getter
        @Setter
        int deDupCount;
        @Getter
        @Setter
        float totalScore;
    }
}
