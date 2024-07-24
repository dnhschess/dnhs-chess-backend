package com.nighthawk.spring_portfolio.mvc.leaderboard;

import java.util.ArrayList;
import java.util.Comparator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {
        "http://127.0.0.1:4200",
        "http://localhost:4200",
        "http://localhost:4100",
        "http://127.0.0.1:4100",
        "https://dnhschess.github.io/"
})
@RequestMapping("/leaderboard")
public class PlayerController {
    private ArrayList<Player> division1 = new ArrayList<>();
    private ArrayList<Player> division2 = new ArrayList<>();

    @PostMapping("/add")
    public ResponseEntity<String> addPlayer(@RequestBody Player player) {
        if (player.getDivisionNumber() == 1) {
            division1.add(player);
        } else if (player.getDivisionNumber() == 2) {
            division2.add(player);
        } else {
            return ResponseEntity.status(400).body("Invalid division number");
        }
        return ResponseEntity.ok("Player added successfully");
    }

    @GetMapping("/allPlayers")
    public ResponseEntity<ArrayList<ArrayList<Player>>> getAllPlayers() {
        ArrayList<ArrayList<Player>> playersByDivision = new ArrayList<>();
        playersByDivision.add(division1);
        playersByDivision.add(division2);
        return ResponseEntity.ok(playersByDivision);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removePlayer(@RequestParam String name) {
        boolean removed = division1.removeIf(player -> player.getName().equals(name)) ||
                division2.removeIf(player -> player.getName().equals(name));
        if (removed) {
            return ResponseEntity.ok("Player removed successfully");
        } else {
            return ResponseEntity.status(404).body("Player not found");
        }
    }

    @DeleteMapping("/removeAll")
    public ResponseEntity<String> removeAllPlayers() {
        division1.clear();
        division2.clear();
        return ResponseEntity.ok("All players removed successfully");
    }

    @PatchMapping("/updateScore")
    public ResponseEntity<String> updateScore(@RequestBody ScoreUpdateRequest request) {
        for (Player player : division1) {
            if (player.getName().equals(request.getName())) {
                player.setScore(player.getScore() + request.getPoints());
                return ResponseEntity.ok("Score updated successfully");
            }
        }
        for (Player player : division2) {
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