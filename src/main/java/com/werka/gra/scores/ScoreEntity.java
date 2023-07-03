package com.werka.gra.scores;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

@Entity
@Table(name = "scores")
public class ScoreEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "player_name")
    private String playerName;

    @Column(name = "score")
    private int score;

    public ScoreEntity() {
    }

    public ScoreEntity(String playerName, int score) {
        this.id = UUID.randomUUID();
        this.playerName = playerName;
        this.score = score;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScoreEntity that = (ScoreEntity) o;
        return score == that.score && Objects.equals(id, that.id) && Objects.equals(playerName, that.playerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, playerName, score);
    }
}
