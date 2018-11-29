package sample;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Vertex {

    private Map<String, Point> locations;

    public Vertex(){
        locations = new HashMap();
    }

    public void addVertex(String str, Point pt){
        locations.put(str, pt);
    }

    public void addVertex(String str, int x, int y){
        locations.put(str, new Point(x, y));
    }

    public Point getLocation(String str){
        return locations.get(str);
    }

    public double getDistance(String str1, String str2){
        return getLocation(str1).distance(getLocation(str2));
    }

    public Map<String, Point> getMap(){
        return locations;
    }
}
