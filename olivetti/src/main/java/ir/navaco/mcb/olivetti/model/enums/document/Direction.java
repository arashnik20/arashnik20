package ir.navaco.mcb.olivetti.model.enums.document;



import ir.navaco.mcb.olivetti.model.WordParts;

import java.util.Comparator;

public enum Direction {
    LEFT_TO_RIGHT,
    RIGHT_TO_LEFT;

    public Comparator<WordParts> comparator(){
       return Direction.RIGHT_TO_LEFT.equals(this) ? (first, second) -> second.getStart() - first.getStart() : Comparator.comparingInt(WordParts::getStart);
    }
}
