package pingball.simulation;

import physics.Vect;
import pingball.util.Pair;

/**
 * A grid location on the board.
 * 
 * This is a lightweight wrapper around Pair.
 * 
 * Rep-Invariant:
 *      None. The pair of integer can be anything within Integer range.
 */
public class GridLocation extends Pair<Integer, Integer> {

    public GridLocation(Integer x, Integer y) {
        super(x, y);
    }

    public static GridLocation of(Integer x, Integer y) {
        return new GridLocation(x, y);
    }

    public Integer x() {
        return getFirst();
    }

    public Integer y() {
        return getSecond();
    }
    
    public GridLocation add (Integer dx, Integer dy){
        return new GridLocation(this.getFirst()+dx,this.getSecond()+dy);
    }
    
    public GridLocation add (int[] displaceBy){
        return new GridLocation(this.getFirst()+displaceBy[0],this.getSecond()+displaceBy[1]);
    }

    public Vect toVect(){
        return new Vect(this.getFirst(),this.getSecond());
    }
}
