package com.bytedesk.core.event;

/**
 *
 * @author bytedesk.com
 */
public class QueryAnswerEvent {

    private String aid;

    private String question;

    public QueryAnswerEvent(String aid, String question) {
        this.aid = aid;
        this.question = question;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
