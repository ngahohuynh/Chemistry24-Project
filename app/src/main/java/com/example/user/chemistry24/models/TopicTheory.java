package com.example.user.chemistry24.models;

public class TopicTheory {
    private int _idTopic;
    private String topicName;
    private String topicData;

    public TopicTheory(int _idTopic, String topicName, String topicData) {
        this._idTopic = _idTopic;
        this.topicName = topicName;
        this.topicData = topicData;
    }

    public int get_idTopic() {
        return _idTopic;
    }

    public void set_idTopic(int _idTopic) {
        this._idTopic = _idTopic;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicData() {
        return topicData;
    }

    public void setTopicData(String topicData) {
        this.topicData = topicData;
    }
}
