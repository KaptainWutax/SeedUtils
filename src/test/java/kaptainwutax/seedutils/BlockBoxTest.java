package kaptainwutax.seedutils;

import kaptainwutax.seedutils.mc.pos.BPos;
import kaptainwutax.seedutils.mc.util.BlockBox;
import kaptainwutax.seedutils.mc.util.Mirror;
import kaptainwutax.seedutils.mc.util.Rotation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlockBoxTest {
    @Test
    @DisplayName("getBoundingBox")
    public void testGetBoundingBox() {
        BPos anchor = new BPos(64, -1, 128);
        Rotation rotation = Rotation.CLOCKWISE_90;
        BPos pivot = new BPos(4, 0, 15);
        Mirror mirror = Mirror.NONE;
        BPos size = new BPos(9, 9, 28);
        BlockBox blockBox = BlockBox.getBoundingBox(anchor, rotation, pivot, mirror, size);
        assertEquals(new BlockBox(56, -1, 139, 83, 7, 147), blockBox, "Incorrect getBoundingbox for shipwreck");
    }

    @Test
    @DisplayName("getBoundingBox2")
    public void testGetBoundingBox2() {
        BPos anchor = new BPos(1296, -2, 992);
        Rotation rotation = Rotation.COUNTERCLOCKWISE_90;
        BPos pivot = new BPos(4, 0, 15);
        Mirror mirror = Mirror.NONE;
        BPos size = new BPos(9, 9, 28);
        BlockBox blockBox = BlockBox.getBoundingBox(anchor, rotation, pivot, mirror, size);
        assertEquals(new BlockBox(1285, -2, 1003, 1312, 6, 1011), blockBox, "Incorrect getBoundingbox for shipwreck");
    }

    @Test
    @DisplayName("rotateBB")
    public void testRotateBB() {
        Rotation rotation = Rotation.COUNTERCLOCKWISE_90;
        BlockBox blockBox = new BlockBox(1285, -2, 1003, 1312, 6, 1011);
        assertEquals(new BlockBox(1285 , -2, 1011, 1312, 6, 1003), blockBox.getRotated(rotation), "Incorrect rotated for shipwreck");
        rotation = Rotation.CLOCKWISE_180;
        blockBox = new BlockBox(1296, -1, 1235, 1304, 7, 1262);
        assertEquals(new BlockBox(1304, -1, 1262, 1296, 7, 1235), blockBox.getRotated(rotation), "Incorrect rotated for shipwreck");
        rotation = Rotation.NONE;
        blockBox = new BlockBox(880, -2, 784, 888, 6, 811);
        assertEquals(new BlockBox(880, -2, 784, 888, 6, 811), blockBox.getRotated(rotation), "Incorrect rotated for shipwreck");
        rotation = Rotation.CLOCKWISE_90;
        blockBox = new BlockBox(1192, -2, 667, 1219, 6, 675);
        assertEquals(new BlockBox(1219, -2, 667, 1192, 6, 675), blockBox.getRotated(rotation), "Incorrect rotated for shipwreck");
    }

    @Test
    @DisplayName("getInside")
    public void testGetInside(){
        BlockBox blockBox=new BlockBox(1285 , -2, 1011, 1312, 6, 1003);
        BPos offset=new BPos(6, 4, 19);
        Rotation rotation = Rotation.COUNTERCLOCKWISE_90;
        assertEquals(new BPos(1304,2,1005),blockBox.getInside(offset,rotation),"The offset was not calculated correctly");
        offset=new BPos(5, 4, 8);
        assertEquals(new BPos(1293,2,1006),blockBox.getInside(offset,rotation),"The offset was not calculated correctly");
        offset=new BPos(3, 3, 24);
        assertEquals(new BPos(1309,1,1008),blockBox.getInside(offset,rotation),"The offset was not calculated correctly");

        rotation = Rotation.CLOCKWISE_180;
        blockBox=new BlockBox(1304, -1, 1262, 1296, 7, 1235);
        offset=new BPos(6, 4, 19);
        assertEquals(new BPos(1298,3,1243),blockBox.getInside(offset,rotation),"The offset was not calculated correctly");
        offset=new BPos(5, 4, 8);
        assertEquals(new BPos(1299,3,1254),blockBox.getInside(offset,rotation),"The offset was not calculated correctly");
        offset=new BPos(3, 3, 24);
        assertEquals(new BPos(1301,2,1238),blockBox.getInside(offset,rotation),"The offset was not calculated correctly");

        rotation = Rotation.NONE;
        blockBox = new BlockBox(880, -2, 784, 888, 6, 811);
        offset=new BPos(6, 4, 19);
        assertEquals(new BPos(886,2,803),blockBox.getInside(offset,rotation),"The offset was not calculated correctly");
        offset=new BPos(5, 4, 8);
        assertEquals(new BPos(885,2,792),blockBox.getInside(offset,rotation),"The offset was not calculated correctly");
        offset=new BPos(3, 3, 24);
        assertEquals(new BPos(883,1,808),blockBox.getInside(offset,rotation),"The offset was not calculated correctly");

        rotation = Rotation.CLOCKWISE_90;
        blockBox = new BlockBox(1219, -2, 667, 1192, 6, 675);
        offset=new BPos(6, 4, 19);
        assertEquals(new BPos(1200,2,673),blockBox.getInside(offset,rotation),"The offset was not calculated correctly");
        offset=new BPos(5, 4, 8);
        assertEquals(new BPos(1211,2,672),blockBox.getInside(offset,rotation),"The offset was not calculated correctly");
        offset=new BPos(3, 3, 24);
        assertEquals(new BPos(1195,1,670),blockBox.getInside(offset,rotation),"The offset was not calculated correctly");

    }
}
