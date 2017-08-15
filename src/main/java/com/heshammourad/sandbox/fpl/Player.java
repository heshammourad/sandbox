package com.heshammourad.sandbox.fpl;

public class Player {

  private final String name;
  private final String team;
  private final Position position;
  private final int value;
  private final int price;

  public Player(String name, String team, Position position, int value, int price) {
    this.name = name;
    this.team = team;
    this.position = position;
    this.value = value;
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public String getTeam() {
    return team;
  }

  public Position getPosition() {
    return position;
  }

  public int getValue() {
    return value;
  }

  public int getPrice() {
    return price;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((position == null) ? 0 : position.hashCode());
    result = prime * result + price;
    result = prime * result + ((team == null) ? 0 : team.hashCode());
    result = prime * result + value;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Player other = (Player) obj;
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    if (position != other.position) {
      return false;
    }
    if (price != other.price) {
      return false;
    }
    if (team == null) {
      if (other.team != null) {
        return false;
      }
    } else if (!team.equals(other.team)) {
      return false;
    }
    if (value != other.value) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "Player [name=" + name + ", team=" + team + ", position=" + position + ", value=" + value
        + ", price=" + price + "]";
  }

}
