package com.nighthawk.spring_portfolio.mvc.leaderboard;

import java.util.ArrayList;
import java.util.Comparator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/pairPlayers")
    public ResponseEntity<List<Pair>> pairPlayers() {
        List<Pair> pairs = new ArrayList<>();
        pairs.addAll(pairPlayersInDivision(division1));
        pairs.addAll(pairPlayersInDivision(division2));
        return ResponseEntity.ok(pairs);
    }

    private List<Pair> pairPlayersInDivision(List<Player> division) {
        List<Pair> pairs = new ArrayList<>();
        List<Player> unpairedPlayers = new ArrayList<>(division);

        while (unpairedPlayers.size() > 1) {
            Player player1 = unpairedPlayers.remove(0);
            Player bestMatch = null;
            int minScoreDifference = Integer.MAX_VALUE;

            for (Player player2 : unpairedPlayers) {
                int scoreDifference = Math.abs(player1.getScore() - player2.getScore());
                if (scoreDifference < minScoreDifference) {
                    minScoreDifference = scoreDifference;
                    bestMatch = player2;
                }
            }

            if (bestMatch != null) {
                unpairedPlayers.remove(bestMatch);
                pairs.add(new Pair(player1, bestMatch));
            }
        }

        return pairs;
    }
}

class Pair {
    private Player player1;
    private Player player2;

    public Pair(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    // Getters and setters
    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
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
