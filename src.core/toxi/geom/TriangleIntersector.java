package toxi.geom;

import toxi.math.MathUtils;

public class TriangleIntersector implements Intersector {

    public Triangle triangle;
    private IsectData isectData;

    public TriangleIntersector() {
        this(new Triangle());
    }

    public TriangleIntersector(Triangle t) {
        this.triangle = t;
        this.isectData = new IsectData();
    }

    public IsectData getIntersectionData() {
        return isectData;
    }

    /**
     * @return the triangle
     */
    public Triangle getTriangle() {
        return triangle;
    }

    public boolean intersectsRay(Ray3D ray) {
        isectData.isIntersection = false;
        Vec3D n = triangle.computeNormal();
        float dotprod = n.dot(ray.dir);
        if (dotprod < 0) {
            Vec3D rt = ray.sub(triangle.a);
            float t =
                    -(n.x * rt.x + n.y * rt.y + n.z * rt.z)
                            / (n.x * ray.dir.x + n.y * ray.dir.y + n.z
                                    * ray.dir.z);
            if (t >= MathUtils.EPS) {
                Vec3D pos = ray.getPointAtDistance(t);
                // TODO commented out orientation check since it seems
                // unnecessary and i can't remember why I used it for the Audi
                // project, needs more testing
                // if (isSameClockDir(triangle.a, triangle.b, pos, n)) {
                // if (isSameClockDir(triangle.b, triangle.c, pos, n)) {
                // if (isSameClockDir(triangle.c, triangle.a, pos, n)) {
                isectData.isIntersection = true;
                isectData.pos = pos;
                isectData.normal = n;
                isectData.dist = t;
                isectData.dir = isectData.pos.sub(ray).normalize();
                // }
                // }
                // }
            }
        }
        return isectData.isIntersection;
    }

    protected boolean isSameClockDir(Vec3D a, Vec3D b, Vec3D c, Vec3D norm) {
        float nx, ny, nz;
        nx = ((b.y - a.y) * (c.z - a.z)) - ((c.y - a.y) * (b.z - a.z));
        ny = ((b.z - a.z) * (c.x - a.x)) - ((c.z - a.z) * (b.x - a.x));
        nz = ((b.x - a.x) * (c.y - a.y)) - ((c.x - a.x) * (b.y - a.y));
        float dotprod = nx * norm.x + ny * norm.y + nz * norm.z;
        return dotprod < 0;
    }

    /**
     * @param tri
     *            the triangle to set
     */
    public TriangleIntersector setTriangle(Triangle tri) {
        this.triangle = tri;
        return this;
    }
}