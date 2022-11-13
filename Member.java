package ecell.app.ecellteam;

public class Member implements Comparable<Member> {
    public String department;
    public String name;
    public Integer score;

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String department2) {
        this.department = department2;
    }

    public Integer getScore() {
        return this.score;
    }

    public void setScore(int score2) {
        this.score = Integer.valueOf(score2);
    }

    public int compareTo(Member member) {
        return getScore().compareTo(member.getScore());
    }
}
