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
}
