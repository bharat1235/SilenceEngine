package com.shc.silenceengine.collision;

import com.shc.silenceengine.IEngine;
import com.shc.silenceengine.geom2d.Polygon;
import com.shc.silenceengine.geom3d.Polyhedron;
import com.shc.silenceengine.math.Vector2;
import com.shc.silenceengine.math.Vector3;

/**
 * @author Sri Harsha Chilakapati
 */
public final class CollisionEngine implements IEngine
{
    @Override
    public void init()
    {
    }

    @Override
    public void beginFrame()
    {
    }

    @Override
    public void endFrame()
    {
    }

    @Override
    public void dispose()
    {
    }

    public boolean testAABBvsAABB(float x1, float y1, float w1, float h1,
                                  float x2, float y2, float w2, float h2)
    {
        return !(x1 > x2 + w2 || x1 + w1 < x2) &&
                !(y1 > y2 + h2 || y1 + h1 < y2);
    }

    public boolean testAABBvsAABB(Vector2 p1, float w1, float h1,
                                  Vector2 p2, float w2, float h2)
    {
        return testAABBvsAABB(p1.x, p1.y, w1, h1, p2.x, p2.y, w2, h2);
    }

    public boolean testAABBvsAABB(Vector2 p1Min, Vector2 p1Max, Vector2 p2Min, Vector2 p2Max)
    {
        return testAABBvsAABB(p1Min.x, p1Min.y, p1Max.x - p1Min.x, p1Max.y - p1Min.y,
                p2Min.x, p2Min.y, p2Max.x - p2Min.x, p2Max.y - p2Min.y);
    }

    public boolean testAABBvsAABB(float x1, float y1, float z1, float w1, float h1, float t1,
                                  float x2, float y2, float z2, float w2, float h2, float t2)
    {
        return !(x1 > x2 + w2 || x1 + w1 < x2) &&
                !(y1 > y2 + h2 || y1 + h1 < y2) &&
                !(z1 > z2 + t2 || z1 + t1 < z2);
    }

    public boolean testAABBvsAABB(Vector3 p1, float w1, float h1, float t1,
                                  Vector3 p2, float w2, float h2, float t2)
    {
        return testAABBvsAABB(p1.x, p1.y, p1.z, w1, h1, t1, p2.x, p2.y, p2.z, w2, h2, t2);
    }

    public boolean testAABBvsAABB(Vector3 p1Min, Vector3 p1Max, Vector3 p2Min, Vector3 p2Max)
    {
        return testAABBvsAABB(p1Min.x, p1Min.y, p1Min.z, p1Max.x - p1Min.x, p1Max.y - p1Min.y, p1Max.z - p1Min.z,
                p2Min.x, p2Min.y, p1Min.z, p2Max.x - p2Min.x, p2Max.y - p2Min.y, p2Max.z - p2Min.z);
    }

    public boolean testCircleCircle(float x1, float y1, float r1,
                                    float x2, float y2, float r2)
    {
        float distance = (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1);
        float radiusSumSq = (r1 + r2) * (r1 + r2);

        return distance <= radiusSumSq;
    }

    public boolean testCircleCircle(Vector2 p1, float r1, Vector2 p2, float r2)
    {
        return testCircleCircle(p1.x, p1.y, r1, p2.x, p2.y, r2);
    }

    public boolean testSphereSphere(float x1, float y1, float z1, float r1,
                                    float x2, float y2, float z2, float r2)
    {
        float distance = (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1) + (z2 - z1) * (z2 - z1);
        float radiusSumSq = (r1 + r2) * (r1 + r2);

        return distance <= radiusSumSq;
    }

    public boolean testSphereSphere(Vector3 p1, float r1, Vector3 p2, float r2)
    {
        return testSphereSphere(p1.x, p1.y, p1.z, r1, p2.x, p2.y, p2.z, r2);
    }

    public boolean testPolygonPolygon(Polygon p1, Polygon p2)
    {
        return Collision2D.testPolygonCollision(p1, p2, null);
    }

    public boolean testPolyhedronPolyhedron(Polyhedron p1, Polyhedron p2)
    {
        return Collision3D.testPolyhedronCollision(p1, p2);
    }
}
