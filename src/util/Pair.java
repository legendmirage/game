package util;

public class Pair<T,U> {
    
    public T val1;
    public U val2;
    
    /**
     * Instantiates a new pair.
     * @param val1 the first value. Must not be null.
     * @param val2 the second value. Must not be null.
     */
    public Pair(T val1,U val2) {
        this.val1 = val1;
        this.val2 = val2;
        checkRep();
    }
    
    /**
     * Checks the rep invariant.
     */
    private void checkRep() {
        assert val1!=null : "Pair.checkRep() - val1!=null";
        assert val2!=null : "Pair.checkRep() - val2!=null";
    }
    
    /**
     * Gets the first value.
     * @return the first value
     */
    public T getVal1() {
        return val1;
    }
    
    /**
     * Gets the second value.
     * @return the second value
     */
    public U getVal2() {
        return val2;
    }
    
    /**
     * Changes the first value.
     * @param newVal1 the new first value
     * @return a new pair containing the new first value and the same second value
     */
    public Pair<T,U> changeVal1(T newVal1) {
        return new Pair<T,U>(newVal1,val2);
    }
    
    /**
     * Changes the second value.
     * @param newVal2 the new second value
     * @return a new pair containing the same first value and the new second value
     */
    public Pair<T,U> changeVal2(U newVal2) {
        return new Pair<T,U>(val1,newVal2);
    }
    
}
