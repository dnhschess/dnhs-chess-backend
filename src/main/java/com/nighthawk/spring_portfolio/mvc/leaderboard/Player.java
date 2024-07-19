package com.nighthawk.spring_portfolio.mvc.leaderboard;

public class Player {
    private String name;
    private int divisionNumber; // This represents the division numbers (1 and 2)
    private int score;

    public Player() {
        // Default constructor needed for JSON mapping
    }

    public Player(String Name, int DivisionNumber, int Score) {
        this.name = Name;
        this.divisionNumber = DivisionNumber;
        this.score = Score;
    }

    public Player(String Name, int DivisionNumber) {
        this.name = Name;
        this.divisionNumber = DivisionNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDivisionNumber() {
        return divisionNumber;
    }

    public void setDivisionNumber(int number) {
        this.divisionNumber = number;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
