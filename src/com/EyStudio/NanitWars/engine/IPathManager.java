package com.EyStudio.NanitWars.engine;

import java.util.ArrayList;

/**
 * Created by daneel on 16.04.17.
 */
public interface IPathManager {
    ArrayList<Vector2D> pathToPoint(Unit source, Vector2D point);
    ArrayList<Vector2D> pathToPoint(Unit source, Vector2D point, int distance);
}
