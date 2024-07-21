package com.nighthawk.spring_portfolio.mvc.leaderboard;

import java.util.ArrayList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
// @CrossOrigin(origins = "http://127.0.0.1:4200/")
@CrossOrigin(origins = "https://dnhschess.github.io/")
@RequestMapping("/leaderboard")
public class LeaderBoardController {
    private ArrayList<Player> players = new ArrayList<Player>();

    @PostMapping("/add")
    public ResponseEntity<String> addPlayer(@RequestBody Player player) {
        players.add(player);
        return ResponseEntity.ok("Player added successfully");
    }

    @GetMapping("/allPlayers")
    public ResponseEntity<ArrayList<Player>> getAllPlayers() {
        return ResponseEntity.ok(players);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removePlayer(@RequestParam String name) {
        boolean removed = players.removeIf(player -> player.getName().equals(name));
        if (removed) {
            return ResponseEntity.ok("Player removed successfully");
        } else {
            return ResponseEntity.status(404).body("Player not found");
        }
    }

    @DeleteMapping("/removeAll")
    public ResponseEntity<String> removeAllPlayers() {
        players.clear();
        return ResponseEntity.ok("All players removed successfully");
    }

    @PatchMapping("/updateScore")
    public ResponseEntity<String> updateScore(@RequestBody ScoreUpdateRequest request) {
        for (Player player : players) {
            if (player.getName().equals(request.getName())) {
                player.setScore(player.getScore() + request.getPoints());
                return ResponseEntity.ok("Score updated successfully");
            }
        }
        return ResponseEntity.status(404).body("Player not found");
    }
}

class ScoreUpdateRequest {
    private String name;
    private int points;

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
