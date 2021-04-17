package kaptainwutax.seedutils;

import kaptainwutax.seedutils.lcg.rand.JRand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DebuggerTest {
    private JRand rand;
    private JRand.Debugger debugger;

    @BeforeEach
    public void setup() {
        this.rand = new JRand(4506419895L);
        this.debugger = this.rand.asDebugger();
        assertEquals(rand.getSeed(), debugger.getSeed());
    }

    @Test
    @DisplayName("Test Simple Tracking")
    public void testTracking() {
        long value = rand.nextSeed(); // this call is outside of the wrapper and can not be tracked (technically it could tho)
        assertEquals(0, debugger.getGlobalCounter(), "A call was tracked outside of the debugger");
        rand.advance(-1); // rollback the LCG
        long debuggerValue = debugger.nextSeed();
        assertEquals(1, debugger.getGlobalCounter(), "The nextSeed skip more than once");
        assertEquals(value, debuggerValue, "The value obtained where not the same");
    }

    @Test
    @DisplayName("Test nextInt Tracking")
    public void testNextInt() {
        long nextSeed = rand.nextSeed(); // this call is outside of the wrapper and can not be tracked (technically it could tho)
        assertEquals(0, debugger.getGlobalCounter(), "A call was tracked outside of the debugger");
        rand.advance(-1); // rollback the LCG
        debugger.nextInt(9);
        assertEquals(2, debugger.getGlobalCounter(), "The nextInt skip more than twice");
        assertEquals(1, debugger.getNextIntSkip(), "The nextInt skip more than once");
        debugger.advance(-debugger.getNextIntSkip());
        assertEquals(nextSeed, rand.getSeed(), "The value obtained where not the same");
        assertEquals(1, debugger.getGlobalCounter(), "Advance failed");
        debugger.nextInt();
        assertEquals(2, debugger.getGlobalCounter(), "The nextInt skip more than once");
    }

    @Test
    @DisplayName("Test nextLong Tracking")
    public void testNextLong() {
        debugger.nextLong();
        assertEquals(2, debugger.getGlobalCounter(), "The nextLong skip more than twice");
    }

    @Test
    @DisplayName("Test nextFloat Tracking")
    public void testNextFloat() {
        debugger.nextFloat();
        assertEquals(1, debugger.getGlobalCounter(), "The nextFloat skip more than once");
    }

    @Test
    @DisplayName("Test nextBoolean Tracking")
    public void testNextBoolean() {
        debugger.nextBoolean();
        assertEquals(1, debugger.getGlobalCounter(), "The nextBoolean skip more than once");
    }

    @Test
    @DisplayName("Test nextDouble Tracking")
    public void testNextDouble() {
        debugger.nextDouble();
        assertEquals(2, debugger.getGlobalCounter(), "The nextDouble skip more than twice");
    }


    @Test
    @DisplayName("Test nextGaussian Tracking")
    public void testNextGaussian() {
        debugger.nextGaussian(); // this one does 3*4
        assertEquals(12, debugger.getGlobalCounter(), "The nextGaussian failed");
        debugger.nextGaussian();
        assertEquals(12, debugger.getGlobalCounter(), "The nextGaussian failed");
        debugger.nextGaussian();  // this one does 1*4
        assertEquals(16, debugger.getGlobalCounter(), "The nextGaussian failed");
    }

    @Test
    @DisplayName("Test nextBits Tracking")
    public void testNextBits() {
        debugger.nextBits(1000000);
        assertEquals(1, debugger.getGlobalCounter(), "The nextBits failed");
    }
    
}
