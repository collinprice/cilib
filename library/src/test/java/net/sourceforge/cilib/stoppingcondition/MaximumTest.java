/**           __  __
 *    _____ _/ /_/ /_    Computational Intelligence Library (CIlib)
 *   / ___/ / / / __ \   (c) CIRG @ UP
 *  / /__/ / / / /_/ /   http://cilib.net
 *  \___/_/_/_/_.___/
 */
package net.sourceforge.cilib.stoppingcondition;

import static org.junit.Assert.*;
import org.junit.Test;

public class MaximumTest {
    /**
     * Test of getPercentage method, of class Maximum.
     */
    @Test
    public void testGetPercentage() {
        Maximum instance = new Maximum();
        assertEquals(0.0, instance.getPercentage(-1.0, 2.0), 0.0);
        assertEquals(0.0, instance.getPercentage(0.0, 2.0), 0.0);
        assertEquals(0.5, instance.getPercentage(1.0, 2.0), 0.0);
        assertEquals(1.0, instance.getPercentage(2.0, 2.0), 0.0);
        assertEquals(1.0, instance.getPercentage(3.0, 2.0), 0.0);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testGetPercentageFail() {
        Maximum instance = new Maximum();
        instance.getPercentage(1.0, 0.0);
    }

    /**
     * Test of apply method, of class Maximum.
     */
    @Test
    public void testApply() {
        Maximum instance = new Maximum();
        assertTrue(instance.apply(1.0, 0.0));
        assertFalse(instance.apply(-1.0, 2.0));
        assertFalse(instance.apply(0.0, 2.0));
        assertFalse(instance.apply(1.0, 2.0));
        assertTrue(instance.apply(2.0, 2.0));
        assertTrue(instance.apply(3.0, 2.0));
    }
}
