package toxi.test;

import java.util.List;

import processing.core.PApplet;
import toxi.geom.Circle;
import toxi.geom.PointQuadtree;
import toxi.geom.Rect;
import toxi.geom.Vec2D;
import toxi.processing.ToxiclibsSupport;
import toxi.util.DateUtils;

public class QuadtreeTest extends PApplet {

    public static void main(String[] args) {
        PApplet.main(new String[] {
            "toxi.test.QuadtreeTest"
        });
    }

    private ToxiclibsSupport gfx;

    private boolean doSave;

    private PointQuadtree tree;

    public void draw() {
        background(255);
        Circle c = new Circle(new Vec2D(mouseX, mouseY), 40);
        Rect r = new Rect(200, 200, 300, 100);
        stroke(r.intersectsCircle(c, c.getRadius()) ? 0xffff0000 : 0xff000000);
        noFill();
        gfx.ellipse(c);
        gfx.rect(r);
        List<Vec2D> sel = tree.getPointsWithinCircle(c);
        noStroke();
        fill(0);
        for (Vec2D p : sel) {
            gfx.circle(p, 5);
        }
        if (doSave) {
            saveFrame("QuadtreeTest-" + DateUtils.timeStamp() + ".png");
            doSave = false;
        }
    }

    public void keyPressed() {
        switch (key) {
            case ' ':
                doSave = true;
                break;
        }
    }

    public void setup() {
        size(1280, 720, OPENGL);
        gfx = new ToxiclibsSupport(this);
        tree = new PointQuadtree(new Vec2D(), 1024);
        for (int i = 0; i < 5000; i++) {
            tree.addPoint(new Vec2D(random(1024), random(1024)));
        }
    }
}
