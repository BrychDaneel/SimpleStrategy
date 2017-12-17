package com.EyStudio.NanitWars.engine.localEngine;

import com.EyStudio.NanitWars.engine.*;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by daneel on 16.04.17.
 */
public class CollisionManager implements ICollisionManager {
    IMap map;
    IUnitManager unitManager;
    int height, width;
    double[][] bfs;

    @Override
    public boolean isLevelCollide(UnitModel model, ILevel level) {
        int mx = model.getCenter().getX();
        int my = model.getCenter().getY();
        int mr = model.getRadius();
        
        for (int dx = - mr; dx<= mr; dx++)
            for (int dy = - mr; dy<= mr; dy++)
                if (dx*dx+dy*dy <= mr*mr &&  level.isFill(mx+dx,my+dy))
                    return true;
        return false;
    }
    
    @Override
    public boolean isUnitCollide(UnitModel model, int unitID, ILevel level){
        for (Unit unit : unitManager){
            if (unit.isSpawned() && unit.getLevel() == level && unit.getUnitID()!=unitID){
                int r = model.getRadius() + unit.getRadius();
                int distanceSquared = unit.getPosition().distanceSquared(model.getCenter());
                if ( r * r > distanceSquared)
                    return true;
            }
        }
        return false;
    }
    
    @Override
    public Vector2D findFreeSpace(Vector2D startPoint, int radius, ILevel level){
        for (int x=0; x<height; x++)
            for (int y=0; y<width; y++)
                bfs[x][y]=Double.MAX_VALUE;
        Queue<Vector2D> queue = new LinkedList<>();
        queue.add(startPoint);
        UnitModel unitModel = new UnitModel(null, radius);
        
        bfs[startPoint.getX()][startPoint.getY()] = 0;
                
        while(!queue.isEmpty()){
            Vector2D pos = queue.poll();
            unitModel.setCenter(pos);
            if (!isAnyCollide(unitModel, 0, level))
                return pos;
            
            int x = pos.getX();
            int y = pos.getY();
            int r = radius;
            double step = bfs[x][y];
            
            for (int dx=-1; dx<=1; dx++)
                    for (int dy=-1; dy<=1; dy++)
                        if (Math.abs(dx)+Math.abs(dy) != 0) {
                            int nx = x + dx;
                            int ny = y + dy;
                            double ds = Math.hypot(dx,dy);
                            if (nx - r >= 0 && ny - r >= 0 && nx + r < width && ny + r < height)
                                if (bfs[nx][ny] > step + ds){
                                    bfs[nx][ny] = step + ds;
                                    queue.add(new Vector2D(nx, ny));
                                }
                        }
        }
        return null;
    } 
    
    @Override
    public boolean isAnyCollide(UnitModel model, int unitID, ILevel level){
        return isLevelCollide(model, level) || isUnitCollide(model, unitID, level);
    }

    public CollisionManager(IEngine engine){
        map = engine.getMap();
        unitManager = engine.getUnitManager();
        width = map.getWidth();
        height = map.getHeight();
        bfs = new double[width][height];
    }
}
