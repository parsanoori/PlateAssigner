package MathTools;

import Tools.ObjectsExtensions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectsExtensionsTest {

    @Test
    public void testRequiresNonNulls(){
        String a = null;
        Integer b = 3;
        Float c = 5.9f;

        try {
            ObjectsExtensions.requireNonNulls(a,b,c);
            fail();
        } catch (Exception e){
            assertTrue(e instanceof NullPointerException);
        }
    }

}