import java.util.HashSet;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int w = sc.nextInt();
        
        // Set up the disjoint sets for all rooms
        DisjointSet ds = new DisjointSet(n*n);
        for (int i = 1; i <= n*n; i++) {
            ds.makeSet(i);
        }
        
        // elements for mouse, cat, and food
        int mouse = getIndex(0,0,n);
        int cat = getIndex(0,n-1,n);
        int food = getIndex(n-1,n-1,n);
        
        for (int i = 0; i < w; i++) {
            int r1 = sc.nextInt();
            int c1 = sc.nextInt();
            String dir1 = sc.next();
            int r2=r1, c2=c1;
            switch (dir1) {
                case "E": c2++; break;
                case "W": c2--; break;
                case "N": r2--; break;
                case "S": r2++; break;
            }            
            
            int set1 = ds.find(getIndex(r1, c1, n));
            int set2 = ds.find(getIndex(r2, c2, n));
            int catSet = ds.find(cat);
            int mouseSet = ds.find(mouse);
            
            if ((set1 == mouseSet && set2 == catSet) || 
                (set1 == catSet && set2 == mouseSet)) {
                // reject the change because the cat
                // could get the mouse
                System.out.println("C"); 
            }
            else if ((set1 == mouseSet && set2 == mouseSet) ||
                (set1 == mouseSet && ds.hasLoop(set2)) ||
                (set2 == mouseSet && ds.hasLoop(set1))) { 
                // reject the change because it would create a loop
                // in the portion of the maze reachable by the mouse
                System.out.println("X"); 
            }
            else {
                // accept the removal
                if (set1 == set2) {
                    // are creating a loop in this set
                    ds.setLoop(set1);
                }
                else {
                    ds.union(set1, set2);
                }
                if (ds.find(food) == ds.find(mouse)) {
                    // The mouse can safely reach the food!
                    System.out.println("D");
                }
                else {
                    System.out.println("K");                    
                }
            }
        }
    }
    
    private static int getIndex(int row, int col, int n) {
        return row * n + col + 1;
    }
}

/**
 * @inv    sets[i] = {parent of i}, if sets[i] > 0
 * @inv    sets[i] < 0 => i is the root of a tree
 * @inv    sets[i] = -{size of set i}, if sets[i] < 0
 * @inv    sets[i] = 0, if i is not yet in a set   
 * @inv    hasLoop[i] = true, iff the set named i has a loop associated with it
 */
class DisjointSet {
    int[] sets;
    boolean[] hasLoop;
    
    public DisjointSet(int n) {
        sets = new int[n+1];
        hasLoop = new boolean[n+1];
    }

    public void checkIndex(int x) {
        if (x < 1 || x >= sets.length) throw new IndexOutOfBoundsException();
    }
    
    public void makeSet(int x) {
        sets[x] = -1;
    }
    
    public int find(int x) {
        if (sets[x] < 0) return x;
        
        int root = find(sets[x]);

        sets[x] = root;
        
        return root;
    }
 
    public void union(int x, int y) {
        if (sets[x] < sets[y]) {
            sets[x] += sets[y];
            sets[y] = x;
            hasLoop[x] = hasLoop[y] | hasLoop[x];
        }
        else {
            sets[y] += sets[x];
            sets[x] = y;
            hasLoop[y] = hasLoop[y] | hasLoop[x];
        }
    }
    
    /** 
     * Given the name of a set return whether the set has a loop associated
     * with it.
     * @param x the name of the set
     * @return true if set x has a loop associated with it, and false otherwise
     */
    public boolean hasLoop(int x) {
        return hasLoop[x];
    }

    /** 
     * Given the name of a set update the data structure to indicate that it
     * has a loop.
     * @param x the name of the set
     * @return true if set x has a loop associated with it, and false otherwise
     */
    public void setLoop(int x) {
        hasLoop[x] = true;
    }

}
