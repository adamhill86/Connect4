package edu.odu.cs480.adamhill;

/**
 * Represents a piece in the game.
 */
public class Chip {
    public enum Color {AI, PLAYER}
    private Color color;

    public Chip(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chip chip = (Chip) o;

        return color == chip.color;
    }

    @Override
    public int hashCode() {
        return color.hashCode();
    }
}
