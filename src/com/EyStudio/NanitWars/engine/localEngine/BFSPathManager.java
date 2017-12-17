package com.EyStudio.NanitWars.engine.localEngine;

import com.EyStudio.NanitWars.engine.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by daneel on 16.04.17.
 */
public class BFSPathManager implements IPathManager {

    ICollisionManager collisionManager;
    IMap map;

    double[][] bfs;
    Vector2D[][] hist;
    int width;
    int height;

    @Override
    public ArrayList<Vector2D> pathToPoint(Unit source, Vector2D point, int distance){
        for (int x=0; x<height; x++)
            for (int y=0; y<width; y++)
                bfs[x][y]=Double.MAX_VALUE;

        UnitModel model  = new UnitModel(source);
        ILevel level = source.getLevel();
        
        int r = model.getRadius();
        int sx = model.getCenter().getX();
        int sy = model.getCenter().getY();
        int ex = point.getX();
        int ey = point.getY();

        UnitModel unitModel = new UnitModel(source);
        bfs[sx][sy] = 0;

        Queue<Vector2D> queue = new LinkedList<>();
        queue.add(new Vector2D(sx,sy));
        boolean found = false;
        int fx = sx, fy = sy;
        int distanceLeftSquared = (sx - ex)*(sx - ex)+(sy - ey)*(sy - ey);
        if (distanceLeftSquared  <= distance * distance)
            found = true;
                                    
        mainDfs:{
            while (!queue.isEmpty() && !found){
                Vector2D pos = queue.poll();
                unitModel.setCenter(pos);
                int x = pos.getX();
                int y = pos.getY();
                double step = bfs[x][y];

                for (int dx=-1; dx<=1; dx++)
                    for (int dy=-1; dy<=1; dy++)
                        if (Math.abs(dx)+Math.abs(dy) != 0) {
                            int nx = x + dx;
                            int ny = y + dy;
                            double ds = Math.hypot(dx,dy);
                            if (nx - r>=0 && ny - r>=0 && nx + r<width && ny + r < height)
                                if (bfs[nx][ny] > step + ds && !collisionManager.isAnyCollide(unitModel, source.getUnitID(), level)){
                                    bfs[nx][ny] = step + ds;
                                    hist[nx][ny] = pos;
                                    queue.add(new Vector2D(nx, ny));
                                    distanceLeftSquared = (nx - ex)*(nx - ex)+(ny - ey)*(ny - ey);
                                    if (distanceLeftSquared  <= distance * distance){
                                        fx = nx;
                                        fy = ny;
                                        found = true;
                                        break mainDfs;
                                    }
                                }
                        }
                }
        }
        

        ArrayList<Vector2D> result = new ArrayList<>();
        if (!found)
            return result;
        int x = fx;
        int y = fy;
        while (x!=sx || y!=sy){
            result.add(new Vector2D(x,y));
            Vector2D pos = hist[x][y];
            x = pos.getX();
            y = pos.getY();
        }
        Collections.reverse(result);
        return result;
    }
    @Override
    public ArrayList<Vector2D> pathToPoint(Unit source, Vector2D point){
        return pathToPoint(source, point, 0);
    }
    
    public BFSPathManager(IEngine engine, IMap map){
        collisionManager = engine.getCollisionManager();
        this.map = map;
        width = map.getWidth();
        height = map.getHeight();
        bfs = new double[width][height];
        hist = new Vector2D[width][height];
    }

}
