/*
 * Player.java - A class defines a player.
 */
package wheelvideo;

/**
 *
 * @author cxu
 */
public class Player {

    private String name;
    private String sex;
    private int totalScore;

    public Player() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }
}
