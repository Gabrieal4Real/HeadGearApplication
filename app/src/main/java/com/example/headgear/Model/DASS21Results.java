package com.example.headgear.Model;

public class DASS21Results {
    private String AnxietyPoints;
    private String DepressionPoints;
    private String StressPoints;
    private String UserID;

    public DASS21Results() {
    }

    public DASS21Results(String anxietyPoints, String depressionPoints,
                         String stressPoints, String userID) {
        AnxietyPoints = anxietyPoints;
        DepressionPoints = depressionPoints;
        StressPoints = stressPoints;
        UserID = userID;
    }

    public String getAnxietyPoints() {
        return AnxietyPoints;
    }

    public void setAnxietyPoints(String anxietyPoints) {
        AnxietyPoints = anxietyPoints;
    }

    public String getDepressionPoints() {
        return DepressionPoints;
    }

    public void setDepressionPoints(String depressionPoints) {
        DepressionPoints = depressionPoints;
    }

    public String getStressPoints() {
        return StressPoints;
    }

    public void setStressPoints(String stressPoints) {
        StressPoints = stressPoints;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }
}
