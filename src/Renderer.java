import java.util.HashMap;

public class Renderer {

    private static HashMap<Character, int[][]> letterCoords = new HashMap<Character, int[][]>(){{
        put('a', new int[][]{{4,0},{5,0},{4,1},{5,1},{4,0},{6,0},{6,1},{4,2},{5,2},{6,2},{7,0},{7,1},{7,2},{4,3},{5,3},{6,3},{7,3},{4,4},{5,4},{8,4},{3,5},{9,4},{8,5},{9,5},{2,5},{2,6},{3,6},{8,6},{9,6},{2,7},{3,7},{4,7},{7,7},{8,7},{9,7},{2,8},{3,8},{4,8},{5,7},{5,8},{6,7},{6,8},{7,8},{8,8},{9,8},{2,9},{3,9},{10,9},{2,10},{3,10},{11,9},{10,10},{11,10},{1,11},{10,11},{11,11},{0,11},{0,12},{1,12},{10,12},{11,12}});	
        put('b', new int[][]{{0,0},{1,0},{0,1},{1,1},{0,0},{2,0},{2,1},{0,2},{1,2},{3,0},{3,1},{0,3},{1,3},{4,0},{4,1},{0,4},{1,4},{5,0},{5,1},{0,5},{1,5},{2,5},{6,0},{6,1},{0,6},{1,6},{2,6},{3,5},{3,6},{7,0},{7,1},{0,7},{1,7},{4,5},{4,6},{8,2},{0,8},{1,8},{5,5},{5,6},{9,2},{8,3},{9,3},{0,9},{1,9},{6,5},{6,6},{8,4},{9,4},{0,10},{1,10},{7,5},{7,6},{0,11},{1,11},{2,11},{8,7},{0,12},{1,12},{2,12},{3,11},{3,12},{9,7},{8,8},{9,8},{4,11},{4,12},{8,9},{9,9},{5,11},{5,12},{8,10},{9,10},{6,11},{6,12},{7,11},{7,12}});	
        put('c', new int[][]{{2,0},{3,0},{2,1},{3,1},{2,0},{4,0},{4,1},{1,2},{2,2},{3,2},{5,0},{5,1},{0,2},{0,3},{1,3},{2,3},{3,3},{6,0},{6,1},{0,4},{1,4},{7,0},{7,1},{0,5},{1,5},{8,2},{0,6},{1,6},{9,2},{8,3},{9,3},{0,7},{1,7},{0,8},{1,8},{0,9},{1,9},{2,9},{0,10},{1,10},{2,10},{3,9},{3,10},{2,11},{3,11},{4,11},{2,12},{3,12},{4,12},{5,11},{5,12},{6,11},{6,12},{7,11},{7,12},{8,10},{8,9},{9,9},{9,10}});	
        put('d', new int[][]{{0,0},{1,0},{0,1},{1,1},{0,0},{2,0},{2,1},{0,2},{1,2},{3,0},{3,1},{0,3},{1,3},{4,0},{4,1},{0,4},{1,4},{5,0},{5,1},{0,5},{1,5},{6,0},{6,1},{6,2},{0,6},{1,6},{7,0},{7,1},{7,2},{6,3},{7,3},{0,7},{1,7},{8,2},{8,3},{8,4},{0,8},{1,8},{9,2},{9,3},{9,4},{8,5},{9,5},{0,9},{1,9},{8,6},{9,6},{0,10},{1,10},{8,7},{9,7},{0,11},{1,11},{2,11},{8,8},{9,8},{0,12},{1,12},{2,12},{3,11},{3,12},{7,9},{8,9},{9,9},{4,11},{4,12},{6,9},{6,10},{7,10},{8,10},{9,10},{5,11},{5,12},{6,11},{7,11},{6,12},{7,12}});	
        put('e', new int[][]{{0,0},{1,0},{0,1},{1,1},{0,0},{2,0},{2,1},{0,2},{1,2},{3,0},{3,1},{0,3},{1,3},{4,0},{4,1},{0,4},{1,4},{5,0},{5,1},{0,5},{1,5},{2,5},{6,0},{6,1},{0,6},{1,6},{2,6},{3,5},{3,6},{7,0},{7,1},{0,7},{1,7},{4,5},{4,6},{0,8},{1,8},{5,5},{5,6},{0,9},{1,9},{6,5},{6,6},{0,10},{1,10},{7,5},{7,6},{0,11},{1,11},{2,11},{0,12},{1,12},{2,12},{3,11},{3,12},{4,11},{4,12},{5,11},{5,12},{6,11},{6,12},{7,11},{7,12}});	
        put('f', new int[][]{{0,0},{1,0},{0,1},{1,1},{0,0},{2,0},{2,1},{0,2},{1,2},{3,0},{3,1},{0,3},{1,3},{4,0},{4,1},{0,4},{1,4},{5,0},{5,1},{0,5},{1,5},{2,5},{6,0},{6,1},{0,6},{1,6},{2,6},{3,5},{3,6},{7,0},{7,1},{0,7},{1,7},{4,5},{4,6},{0,8},{1,8},{5,5},{5,6},{0,9},{1,9},{6,5},{6,6},{0,10},{1,10},{7,5},{7,6},{0,11},{1,11},{0,12},{1,12}});	
        put('g', new int[][]{{2,0},{3,0},{2,1},{3,1},{2,0},{4,0},{4,1},{1,2},{2,2},{3,2},{5,0},{5,1},{0,2},{0,3},{1,3},{2,3},{3,3},{6,0},{6,1},{0,4},{1,4},{7,0},{7,1},{0,5},{1,5},{8,0},{8,1},{8,2},{0,6},{1,6},{9,0},{9,1},{9,2},{8,3},{9,3},{0,7},{1,7},{0,8},{1,8},{0,9},{1,9},{2,9},{0,10},{1,10},{2,10},{3,9},{3,10},{2,11},{3,11},{4,11},{2,12},{3,12},{4,12},{5,11},{5,12},{6,11},{6,12},{7,11},{7,12},{8,10},{8,11},{8,12},{8,9},{9,9},{9,10},{9,11},{9,12},{8,8},{9,8},{8,7},{9,7},{7,6},{8,6},{9,6},{6,5},{7,5},{8,5},{6,6},{9,5}});	
        put('h', new int[][]{{0,0},{1,0},{0,1},{1,1},{0,0},{0,2},{1,2},{0,3},{1,3},{0,4},{1,4},{0,5},{1,5},{2,5},{0,6},{1,6},{2,6},{3,5},{3,6},{0,7},{1,7},{4,5},{4,6},{0,8},{1,8},{5,5},{5,6},{0,9},{1,9},{6,5},{6,6},{0,10},{1,10},{7,5},{7,6},{0,11},{1,11},{8,4},{8,5},{8,6},{8,7},{0,12},{1,12},{8,3},{9,3},{9,4},{9,5},{9,6},{9,7},{8,8},{9,8},{8,2},{9,2},{8,9},{9,9},{8,1},{9,1},{8,10},{9,10},{8,0},{9,0},{8,11},{9,11},{8,12},{9,12}});	
        put('i', new int[][]{{0,0},{1,0},{0,1},{1,1},{0,0},{0,2},{1,2},{0,3},{1,3},{0,4},{1,4},{0,5},{1,5},{0,6},{1,6},{0,7},{1,7},{0,8},{1,8},{0,9},{1,9},{0,10},{1,10},{0,11},{1,11},{0,12},{1,12}});	
        put('j', new int[][]{{4,0},{5,0},{4,1},{5,1},{4,0},{4,2},{5,2},{4,3},{5,3},{4,4},{5,4},{4,5},{5,5},{4,6},{5,6},{4,7},{5,7},{4,8},{5,8},{4,9},{5,9},{4,10},{5,10},{3,11},{4,11},{5,11},{2,11},{2,12},{3,12},{4,12},{5,12},{1,11},{1,12},{0,11},{0,12}});	
        put('k', new int[][]{{0,0},{1,0},{0,1},{1,1},{0,0},{0,2},{1,2},{0,3},{1,3},{0,4},{1,4},{0,5},{1,5},{2,5},{0,6},{1,6},{2,6},{3,5},{3,6},{0,7},{1,7},{4,4},{4,7},{0,8},{1,8},{4,3},{5,3},{5,4},{5,7},{4,8},{5,8},{0,9},{1,9},{4,2},{5,2},{6,9},{0,10},{1,10},{6,1},{7,9},{6,10},{7,10},{0,11},{1,11},{6,0},{7,0},{7,1},{6,11},{7,11},{0,12},{1,12},{6,12},{7,12}});	
        put('l', new int[][]{{0,0},{1,0},{0,1},{1,1},{0,0},{0,2},{1,2},{0,3},{1,3},{0,4},{1,4},{0,5},{1,5},{0,6},{1,6},{0,7},{1,7},{0,8},{1,8},{0,9},{1,9},{0,10},{1,10},{0,11},{1,11},{2,11},{0,12},{1,12},{2,12},{3,11},{3,12},{4,11},{4,12},{5,11},{5,12},{6,11},{6,12},{7,11},{7,12}});	
        put('m', new int[][]{{0,0},{1,0},{0,1},{1,1},{0,0},{2,0},{2,1},{0,2},{1,2},{2,2},{3,0},{3,1},{3,2},{0,3},{1,3},{2,3},{3,3},{0,4},{1,4},{4,4},{0,5},{1,5},{5,4},{4,5},{5,5},{0,6},{1,6},{4,6},{5,6},{0,7},{1,7},{6,7},{0,8},{1,8},{7,7},{6,8},{7,8},{0,9},{1,9},{8,7},{8,8},{6,9},{7,9},{8,9},{0,10},{1,10},{9,7},{9,8},{9,9},{6,10},{7,10},{8,10},{9,10},{0,11},{1,11},{10,6},{6,11},{7,11},{8,11},{9,11},{0,12},{1,12},{10,5},{11,5},{11,6},{6,12},{7,12},{8,12},{9,12},{10,4},{11,4},{12,3},{12,2},{13,2},{13,3},{12,1},{13,1},{14,1},{14,2},{14,3},{14,4},{12,0},{13,0},{14,0},{15,0},{15,1},{15,2},{15,3},{15,4},{14,5},{15,5},{14,6},{15,6},{14,7},{15,7},{14,8},{15,8},{14,9},{15,9},{14,10},{15,10},{14,11},{15,11},{14,12},{15,12}});	
        put('n', new int[][]{{0,0},{1,0},{0,1},{1,1},{0,0},{2,0},{2,1},{0,2},{1,2},{2,2},{3,0},{3,1},{3,2},{0,3},{1,3},{2,3},{3,3},{0,4},{1,4},{4,4},{0,5},{1,5},{5,4},{4,5},{5,5},{0,6},{1,6},{4,6},{5,6},{0,7},{1,7},{6,7},{0,8},{1,8},{7,7},{6,8},{7,8},{0,9},{1,9},{8,6},{8,7},{8,8},{6,9},{7,9},{8,9},{0,10},{1,10},{8,5},{9,5},{9,6},{9,7},{9,8},{9,9},{6,10},{7,10},{8,10},{9,10},{0,11},{1,11},{8,4},{9,4},{6,11},{7,11},{8,11},{9,11},{0,12},{1,12},{8,3},{9,3},{6,12},{7,12},{8,12},{9,12},{8,2},{9,2},{8,1},{9,1},{8,0},{9,0}});	
        put('o', new int[][]{{2,0},{3,0},{2,1},{3,1},{2,0},{4,0},{4,1},{1,2},{5,0},{5,1},{0,2},{0,3},{1,3},{6,0},{6,1},{0,4},{1,4},{7,0},{7,1},{0,5},{1,5},{8,2},{0,6},{1,6},{9,2},{8,3},{9,3},{0,7},{1,7},{8,4},{9,4},{0,8},{1,8},{8,5},{9,5},{0,9},{1,9},{8,6},{9,6},{0,10},{1,10},{8,7},{9,7},{2,11},{8,8},{9,8},{3,11},{2,12},{3,12},{8,9},{9,9},{4,11},{4,12},{8,10},{9,10},{5,11},{5,12},{7,11},{6,11},{6,12},{7,12}});	
        put('p', new int[][]{{0,0},{1,0},{0,1},{1,1},{0,0},{2,0},{2,1},{0,2},{3,0},{3,1},{0,3},{4,0},{4,1},{0,4},{5,2},{0,5},{1,5},{6,2},{5,3},{6,3},{0,6},{1,6},{2,5},{2,6},{5,4},{6,4},{0,7},{3,5},{3,6},{4,5},{0,8},{4,6},{0,9},{0,10},{0,11},{0,12}});	
        put('q', new int[][]{{2,0},{3,0},{2,1},{3,1},{2,0},{4,0},{4,1},{1,2},{5,0},{5,1},{0,2},{0,3},{1,3},{6,0},{6,1},{0,4},{1,4},{7,0},{7,1},{0,5},{1,5},{8,2},{0,6},{1,6},{9,2},{8,3},{9,3},{0,7},{1,7},{8,4},{9,4},{0,8},{1,8},{8,5},{9,5},{0,9},{1,9},{8,6},{9,6},{0,10},{1,10},{8,7},{9,7},{0,11},{1,11},{8,8},{9,8},{2,12},{8,9},{9,9},{3,12},{2,13},{3,13},{8,10},{9,10},{4,12},{4,13},{8,11},{9,11},{5,12},{5,13},{7,12},{8,12},{9,12},{6,12},{6,13},{7,13},{8,13},{9,13},{10,14},{11,14},{10,15},{11,15}});	
        put('r', new int[][]{{0,0},{1,0},{0,1},{1,1},{0,0},{2,0},{2,1},{0,2},{1,2},{3,0},{3,1},{0,3},{1,3},{4,0},{4,1},{0,4},{1,4},{5,0},{5,1},{0,5},{1,5},{6,2},{0,6},{1,6},{2,6},{7,2},{6,3},{7,3},{0,7},{1,7},{2,7},{3,6},{3,7},{6,4},{7,4},{0,8},{1,8},{4,6},{4,7},{6,5},{7,5},{0,9},{1,9},{5,6},{5,7},{0,10},{1,10},{6,8},{0,11},{1,11},{7,8},{6,9},{7,9},{0,12},{1,12},{6,10},{7,10},{0,13},{1,13},{6,11},{7,11},{6,12},{7,12},{6,13},{7,13}});	
        put('s', new int[][]{{2,0},{3,0},{2,1},{3,1},{2,0},{4,0},{4,1},{1,2},{5,0},{5,1},{0,2},{0,3},{1,3},{6,0},{6,1},{6,2},{0,4},{1,4},{7,0},{7,1},{7,2},{6,3},{7,3},{0,5},{1,5},{2,6},{3,6},{2,7},{3,7},{4,6},{4,7},{5,6},{5,7},{6,8},{7,8},{6,9},{7,9},{6,10},{7,10},{6,11},{7,11},{5,12},{4,12},{4,13},{5,13},{3,12},{3,13},{2,12},{2,13},{1,11},{1,12},{1,13},{0,10},{1,10},{0,11},{0,12},{0,13}});	
        put('t', new int[][]{{0,0},{1,0},{0,1},{1,1},{0,0},{2,0},{2,1},{3,0},{3,1},{4,0},{4,1},{4,2},{5,0},{5,1},{5,2},{4,3},{5,3},{6,0},{6,1},{4,4},{5,4},{7,0},{7,1},{4,5},{5,5},{8,0},{8,1},{4,6},{5,6},{9,0},{9,1},{4,7},{5,7},{4,8},{5,8},{4,9},{5,9},{4,10},{5,10},{4,11},{5,11},{4,12},{5,12},{4,13},{5,13}});	
        put('v', new int[][]{{0,0},{1,0},{0,1},{1,1},{0,0},{0,2},{1,2},{0,3},{1,3},{0,4},{1,4},{0,5},{1,5},{0,6},{1,6},{0,7},{1,7},{0,8},{1,8},{0,9},{1,9},{0,10},{1,10},{0,11},{1,11},{2,12},{3,12},{2,13},{3,13},{4,12},{4,13},{5,12},{5,13},{6,12},{6,13},{7,12},{7,13},{8,11},{8,10},{9,10},{9,11},{8,9},{9,9},{8,8},{9,8},{8,7},{9,7},{8,6},{9,6},{8,5},{9,5},{8,4},{9,4},{8,3},{9,3},{8,2},{9,2},{8,1},{9,1},{8,0},{9,0}});	
        put('u', new int[][]{{0,0},{1,0},{0,1},{1,1},{0,0},{0,2},{1,2},{2,2},{0,3},{1,3},{2,3},{3,2},{3,3},{2,4},{3,4},{2,5},{3,5},{2,6},{3,6},{2,7},{3,7},{2,8},{3,8},{4,8},{2,9},{3,9},{4,9},{5,8},{5,9},{4,10},{5,10},{6,8},{6,9},{6,10},{4,11},{5,11},{6,11},{7,8},{7,9},{7,10},{7,11},{4,12},{5,12},{6,12},{7,12},{8,7},{4,13},{5,13},{6,13},{7,13},{8,6},{9,6},{9,7},{8,5},{9,5},{8,4},{9,4},{8,3},{9,3},{10,3},{8,2},{9,2},{10,2},{10,1},{10,0}});	
        put('w', new int[][]{{0,0},{1,0},{0,1},{1,1},{0,0},{2,0},{2,1},{2,2},{3,0},{3,1},{3,2},{2,3},{3,3},{2,4},{3,4},{2,5},{3,5},{2,6},{3,6},{2,7},{3,7},{4,8},{5,8},{4,9},{5,9},{6,7},{6,8},{6,9},{4,10},{5,10},{6,10},{6,6},{7,6},{7,7},{7,8},{7,9},{7,10},{4,11},{5,11},{6,11},{7,11},{6,5},{7,5},{8,5},{4,12},{5,12},{6,12},{7,12},{6,4},{7,4},{8,4},{9,4},{9,5},{4,13},{5,13},{6,13},{7,13},{8,3},{9,3},{10,3},{10,4},{10,5},{10,6},{8,2},{9,2},{10,2},{11,2},{11,3},{11,4},{11,5},{11,6},{10,7},{11,7},{8,1},{9,1},{10,1},{11,1},{12,8},{8,0},{9,0},{10,0},{11,0},{13,8},{12,9},{13,9},{14,7},{14,8},{14,9},{12,10},{13,10},{14,10},{14,6},{15,6},{15,7},{15,8},{15,9},{15,10},{12,11},{13,11},{14,11},{15,11},{16,5},{12,12},{13,12},{14,12},{15,12},{16,4},{17,4},{17,5},{12,13},{13,13},{14,13},{15,13},{16,3},{17,3},{16,2},{17,2},{16,1},{17,1},{16,0},{17,0}});	
        put('x', new int[][]{{0,0},{1,0},{0,1},{1,1},{0,0},{2,0},{2,1},{2,2},{3,0},{3,1},{3,2},{2,3},{3,3},{2,4},{3,4},{4,4},{2,5},{3,5},{4,5},{5,4},{5,5},{4,6},{5,6},{6,4},{6,5},{6,6},{4,7},{5,7},{6,7},{7,4},{7,5},{7,6},{7,7},{3,8},{4,8},{5,8},{6,8},{7,8},{8,3},{2,8},{2,9},{3,9},{4,9},{5,9},{6,9},{7,9},{8,2},{9,2},{9,3},{2,10},{3,10},{8,10},{8,1},{9,1},{2,11},{3,11},{9,10},{8,11},{9,11},{8,0},{9,0},{1,12},{8,12},{9,12},{0,12},{0,13},{1,13},{8,13},{9,13}});	
        put('y', new int[][]{{0,0},{1,0},{0,1},{1,1},{0,0},{2,2},{3,2},{2,3},{3,3},{2,4},{3,4},{2,5},{3,5},{4,6},{5,6},{4,7},{5,7},{6,5},{4,8},{5,8},{6,4},{7,4},{7,5},{4,9},{5,9},{6,3},{7,3},{4,10},{5,10},{6,2},{7,2},{4,11},{5,11},{8,1},{4,12},{5,12},{8,0},{9,0},{9,1},{4,13},{5,13}});	
        put('z', new int[][]{{0,0},{1,0},{0,1},{1,1},{0,0},{2,0},{2,1},{3,0},{3,1},{4,0},{4,1},{5,0},{5,1},{6,0},{6,1},{6,2},{7,0},{7,1},{7,2},{6,3},{7,3},{8,0},{8,1},{6,4},{7,4},{9,0},{9,1},{6,5},{7,5},{5,6},{4,6},{4,7},{5,7},{3,8},{2,8},{2,9},{3,9},{2,10},{3,10},{2,11},{3,11},{1,12},{2,12},{3,12},{4,12},{0,12},{0,13},{1,13},{2,13},{3,13},{4,13},{5,12},{5,13},{6,12},{6,13},{7,12},{7,13},{8,12},{8,13},{9,12},{9,13}});	
        put('0', new int[][]{{1,0},{2,0},{1,1},{2,1},{1,0},{3,0},{3,1},{0,2},{4,0},{4,1},{0,3},{5,0},{5,1},{5,2},{0,4},{6,0},{6,1},{6,2},{5,3},{6,3},{0,5},{5,4},{6,4},{0,6},{5,5},{6,5},{0,7},{5,6},{6,6},{0,8},{5,7},{6,7},{0,9},{5,8},{6,8},{0,10},{5,9},{6,9},{0,11},{5,10},{6,10},{0,12},{1,12},{5,11},{6,11},{0,13},{1,13},{2,12},{2,13},{4,12},{3,12},{3,13},{4,13}});	
        put('1', new int[][]{{2,0},{3,0},{2,1},{3,1},{2,0},{4,0},{4,1},{1,2},{4,2},{5,0},{5,1},{5,2},{0,2},{0,3},{1,3},{4,3},{5,3},{4,4},{5,4},{4,5},{5,5},{4,6},{5,6},{4,7},{5,7},{4,8},{5,8},{4,9},{5,9},{4,10},{5,10},{4,11},{5,11},{3,12},{4,12},{5,12},{6,12},{2,12},{2,13},{3,13},{4,13},{5,13},{6,13},{7,12},{7,13},{1,12},{1,13},{0,12},{0,13}});	
        put('2', new int[][]{{2,0},{3,0},{2,1},{3,1},{2,0},{4,0},{4,1},{1,2},{5,0},{5,1},{0,2},{0,3},{1,3},{6,2},{7,2},{6,3},{7,3},{6,4},{7,4},{6,5},{7,5},{5,6},{6,6},{7,6},{4,6},{4,7},{5,7},{6,7},{7,7},{4,8},{5,8},{4,9},{5,9},{3,10},{2,10},{2,11},{3,11},{1,12},{2,12},{3,12},{4,12},{0,12},{0,13},{1,13},{2,13},{3,13},{4,13},{5,12},{5,13},{6,12},{6,13},{7,12},{7,13}});	
        put('3', new int[][]{{0,0},{1,0},{0,1},{1,1},{0,0},{2,0},{2,1},{3,0},{3,1},{4,0},{4,1},{5,0},{5,1},{6,0},{6,1},{6,2},{7,0},{7,1},{7,2},{6,3},{7,3},{6,4},{7,4},{6,5},{7,5},{5,6},{4,6},{4,7},{5,7},{3,6},{3,7},{6,8},{2,6},{2,7},{7,8},{6,9},{7,9},{6,10},{7,10},{6,11},{7,11},{5,12},{4,12},{4,13},{5,13},{3,12},{3,13},{2,12},{2,13},{1,12},{1,13},{0,12},{0,13}});	
        put('4', new int[][]{{4,0},{5,0},{4,1},{5,1},{4,0},{6,0},{6,1},{3,2},{6,2},{7,0},{7,1},{7,2},{2,2},{2,3},{3,3},{6,3},{7,3},{1,4},{2,4},{3,4},{6,4},{7,4},{0,4},{0,5},{1,5},{2,5},{3,5},{6,5},{7,5},{0,6},{1,6},{6,6},{7,6},{0,7},{1,7},{6,7},{7,7},{0,8},{1,8},{2,8},{5,8},{6,8},{7,8},{8,8},{0,9},{1,9},{2,9},{3,8},{3,9},{4,8},{4,9},{5,9},{6,9},{7,9},{8,9},{9,8},{9,9},{6,10},{7,10},{6,11},{7,11},{6,12},{7,12},{6,13},{7,13}});	
        put('5', new int[][]{{0,0},{1,0},{0,1},{1,1},{0,0},{2,0},{2,1},{0,2},{1,2},{3,0},{3,1},{0,3},{1,3},{4,0},{4,1},{0,4},{1,4},{5,0},{5,1},{0,5},{1,5},{6,0},{6,1},{0,6},{1,6},{2,6},{7,0},{7,1},{0,7},{1,7},{2,7},{3,6},{3,7},{4,6},{4,7},{5,6},{5,7},{6,8},{7,8},{6,9},{7,9},{6,10},{7,10},{6,11},{7,11},{5,12},{4,12},{4,13},{5,13},{3,12},{3,13},{2,12},{2,13},{1,12},{1,13},{0,12},{0,13}});	
        put('6', new int[][]{{2,0},{3,0},{2,1},{3,1},{2,0},{4,0},{4,1},{1,2},{5,0},{5,1},{0,2},{0,3},{1,3},{6,0},{6,1},{0,4},{1,4},{7,0},{7,1},{0,5},{1,5},{0,6},{1,6},{2,6},{0,7},{1,7},{2,7},{3,6},{3,7},{0,8},{1,8},{4,6},{4,7},{0,9},{1,9},{5,6},{5,7},{0,10},{1,10},{6,6},{6,7},{6,8},{0,11},{1,11},{7,6},{7,7},{7,8},{6,9},{7,9},{2,12},{6,10},{7,10},{3,12},{2,13},{3,13},{6,11},{7,11},{4,12},{4,13},{5,12},{5,13}});	
        put('7', new int[][]{{0,0},{1,0},{0,1},{1,1},{0,0},{2,0},{2,1},{3,0},{3,1},{4,0},{4,1},{5,0},{5,1},{6,0},{6,1},{6,2},{7,0},{7,1},{7,2},{6,3},{7,3},{8,0},{8,1},{8,2},{8,3},{6,4},{7,4},{9,0},{9,1},{9,2},{9,3},{6,5},{7,5},{5,6},{6,6},{7,6},{4,6},{4,7},{5,7},{6,7},{7,7},{4,8},{5,8},{4,9},{5,9},{3,10},{4,10},{5,10},{2,10},{2,11},{3,11},{4,11},{5,11},{2,12},{3,12},{2,13},{3,13}});	
        put('8', new int[][]{{2,0},{3,0},{2,1},{3,1},{2,0},{4,0},{4,1},{1,2},{5,0},{5,1},{0,2},{0,3},{1,3},{6,0},{6,1},{0,4},{1,4},{7,0},{7,1},{0,5},{1,5},{8,2},{2,6},{9,2},{8,3},{9,3},{3,6},{2,7},{3,7},{8,4},{9,4},{4,6},{4,7},{1,8},{8,5},{9,5},{5,6},{5,7},{0,8},{0,9},{1,9},{7,6},{6,6},{6,7},{0,10},{1,10},{7,7},{0,11},{1,11},{8,8},{2,12},{9,8},{8,9},{9,9},{3,12},{2,13},{3,13},{8,10},{9,10},{4,12},{4,13},{8,11},{9,11},{5,12},{5,13},{7,12},{6,12},{6,13},{7,13}});	
        put('9', new int[][]{{2,0},{3,0},{2,1},{3,1},{2,0},{4,0},{4,1},{1,2},{5,0},{5,1},{0,2},{0,3},{1,3},{6,2},{0,4},{1,4},{7,2},{6,3},{7,3},{0,5},{1,5},{6,4},{7,4},{0,6},{1,6},{2,6},{6,5},{7,5},{0,7},{1,7},{2,7},{3,6},{3,7},{5,6},{6,6},{7,6},{4,6},{4,7},{5,7},{6,7},{7,7},{6,8},{7,8},{6,9},{7,9},{6,10},{7,10},{6,11},{7,11},{5,12},{4,12},{4,13},{5,13},{3,12},{3,13},{2,12},{2,13},{1,12},{1,13},{0,12},{0,13}});
    }};

    public static void drawLine(int[][] grid, int x0, int y0, int x1, int y1, int color) {
        int dx = Math.abs(x1 - x0);
        int dy = -Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx + dy;
        while (true) {
            grid[y0][x0] = color;
            if (y0 == y1 && x0 == x1) break;
            int err2 = err * 2;
            if (err2 >= dy) {
                if (x0 == x1) break;
                err += dy;
                x0 += sx;
            }
            if (err2 <= dx) {
                if (y0 == y1) break;
                err += dx;
                y0 += sy;
            }
        }
    }

    public static void drawRect(int[][] grid, int x0, int y0, int x1, int y1, int color) {
        Renderer.drawLine(grid, x0, y1, x1, y1, color); // top line
        Renderer.drawLine(grid, x1, y1, x1, y0, color); // right line
        Renderer.drawLine(grid, x1, y0, x0, y0, color); // bottom line
        Renderer.drawLine(grid, x0, y0, x0, y1, color); // bottom line
    }

    public static void drawCross(int[][] grid, int x0, int y0, int x1, int y1, int color) {
        Renderer.drawLine(grid, x0, y0, x1, y1, color);
        Renderer.drawLine(grid, x0, y1, x1, y0, color);
    }

    public static void drawText(int[][] grid, String text, int x, int y, int color) {
        text = text.toLowerCase();
        for (int ci = 0; ci < text.length(); ci++) {
            char c = text.charAt(ci);
            if (x >= grid[0].length || y >= grid.length) break;

            int[][] toDraw = letterCoords.get(c);
            if (toDraw == null) {x += 15; continue; }

            int maxX = 0;
            int maxY = 0;

            for(int[] loc : toDraw) {
                if(loc[0] > maxX) maxX = loc[0];
                if(loc[1] > maxY) maxY = loc[1];

                grid[y + loc[1]][x + loc[0]] = color;
            }

            x += maxX + 2;
        }
    }
}
