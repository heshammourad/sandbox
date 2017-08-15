package com.heshammourad.sandbox.fpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

public class Preseason {

  private final static List<Player> GKP = new ArrayList<>();
  private final static List<Player> DEF = new ArrayList<>();
  private final static List<Player> MID = new ArrayList<>();
  private final static List<Player> FWD = new ArrayList<>();

  private final static int[][] FORMATIONS =
      {{3, 4, 3}, {3, 5, 2}, {4, 3, 3}, {4, 4, 2}, {4, 5, 1}, {5, 2, 3}, {5, 3, 2}, {5, 4, 1}};
  private static final int TEAM_SIZE = 11;
  private static final int BUDGET = 200;

  private static void loadPlayers() {
    GKP.addAll(loadPlayers("GKP_fix.txt"));
    DEF.addAll(loadPlayers("DEF_fix.txt"));
    MID.addAll(loadPlayers("MID_fix.txt"));
    FWD.addAll(loadPlayers("FWD_fix.txt"));
  }

  private static List<Player> loadPlayers(String filename) {
    List<Player> players = new ArrayList<>();

    Position position = null;
    switch (filename.substring(0, 3)) {
      case "GKP":
        position = Position.GOALKEEPER;
        break;
      case "DEF":
        position = Position.DEFENDER;
        break;
      case "MID":
        position = Position.MIDFIELDER;
        break;
      case "FWD":
        position = Position.FORWARD;
        break;
    }

    try (BufferedReader inputStream = new BufferedReader(new FileReader(filename))) {
      String line;
      while ((line = inputStream.readLine()) != null) {
        Iterable<String> split = Splitter.on(CharMatcher.is('\t')).omitEmptyStrings().split(line);
        List<String> strings = Lists.newArrayList(split);

        String name = strings.get(0);
        String team = strings.get(1);
        int price = Math.round(Float.parseFloat(strings.get(2)) * 2);
        int value = Math.round(Float.parseFloat(strings.get(3)) * 10);

        players.add(new Player(name, team, position, value, price));
      }
    } catch (IOException ex) {
      // DO NOTHING
    }

    return players;
  }

  private static void knapsack() {
    for (int f = 0; f < FORMATIONS.length; f++) {
      int defenders = FORMATIONS[f][0];
      int midfielders = FORMATIONS[f][1];
      int forwards = FORMATIONS[f][2];

      int budget = BUDGET - 8;
      budget -= (5 - defenders) * 8;
      budget -= (5 - midfielders) * 9;
      budget -= (3 - forwards) * 9;

      int[][] knapsack = new int[TEAM_SIZE + 1][BUDGET + 1];
      List<List<List<Player>>> team = new ArrayList<>();

      team.add(new ArrayList<>());
      for (int i = 0; i <= budget; i++) {
        team.get(0).add(new ArrayList<>());
      }

      for (int i = 1; i < (TEAM_SIZE + 1); i++) {
        team.add(new ArrayList<>());
        team.get(i).add(new ArrayList<>());

        for (int s = 1; s <= budget; s++) {
          team.get(i).add(new ArrayList<>());

          List<Player> players;
          if (i == 1) {
            players = GKP;
          } else {
            int defIndex = defenders + 1;
            if (i <= defIndex) {
              players = DEF;
            } else {
              int midIndex = midfielders + defIndex;
              if (i <= midIndex) {
                players = MID;
              } else {
                players = FWD;
              }
            }
          }

          Player maxPlayer = null;
          int maxValue = 0;
          for (Player player : players) {
            try {
              List<Player> teamMembers = team.get(i - 1).get(s - player.getPrice());
              if (teamMembers.contains(player) || teamMembers.size() < (i - 1)) {
                continue;
              }

              int value = knapsack[i - 1][s - player.getPrice()] + player.getValue();
              if (value > maxValue) {
                maxPlayer = player;
                maxValue = value;
              }
            } catch (IndexOutOfBoundsException ex) {
              // DO NOTHING
            }
          }

          if (maxPlayer != null) {
            knapsack[i][s] = maxValue;
            List<Player> teamMembers = team.get(i).get(s);
            teamMembers.addAll(team.get(i - 1).get(s - maxPlayer.getPrice()));
            teamMembers.add(maxPlayer);
          }
        }
        if (i == TEAM_SIZE) {
          System.out.println("Best " + defenders + "-" + midfielders + "-" + forwards + " team:");
          List<Player> teamMembers = team.get(i).get(budget);
          if (teamMembers.size() != i) {
            continue;
          }
          int value = knapsack[i][budget];
          System.out.format("Budget: %.1f | Value: %.1f%n", budget / 2.0, value / 10.0);
          for (Player player : teamMembers) {
            System.out.println(player);
          }
          System.out.println();
        }
      }
    }
  }

  public static void main(String[] args) {
    loadPlayers();

    knapsack();
  }

}
