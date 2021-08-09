package com.example.headgear.Aid.Stress;

public class AidStressSub {

    private String title;
    private String url;
    private String definition;
    private boolean expanded;

    public AidStressSub(String title, String url, String definition) {
        this.title = title;
        this.url = url;
        this.definition = definition;
        this.expanded = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    @Override
    public String toString() {
        return "Stress{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", definition='" + definition + '\'' +
                ", expanded=" + expanded +
                '}';
    }
}