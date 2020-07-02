package Core;

import Exceptions.PlateOutOfBoundExcepetion;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PlateIDProducerTest {
    @Test
    public void getNextPlateTest(){
        PlateIDProducer plateIDProducer = new PlateIDProducer();
        try {
            assertEquals("۱۱آ۱۱۱" , plateIDProducer.getNewPlateID(PlateIDProducer.Scope.Normal));
            assertEquals("۱۱آ۱۱۲" , plateIDProducer.getNewPlateID(PlateIDProducer.Scope.Normal));
        } catch (PlateOutOfBoundExcepetion plateOutOfBoundExcepetion) {
            fail();
        }
    }

    @Test
    public void testPlateAfterLatest() throws NoSuchFieldException, IllegalAccessException {
        PlateIDProducer plateIDProducer = new PlateIDProducer();
        Field field =  plateIDProducer.getClass().getDeclaredField("scopeToInteger");
        field.setAccessible(true);
        HashMap<PlateIDProducer.Scope,Integer> hashMap = (HashMap<PlateIDProducer.Scope, Integer>) field.get(plateIDProducer);
        hashMap.replace(PlateIDProducer.Scope.Normal,99999);
        try {
            assertEquals("۱۱ب۱۱۱" , plateIDProducer.whatIsNextPlate(PlateIDProducer.Scope.Normal));
        } catch (PlateOutOfBoundExcepetion plateOutOfBoundExcepetion) {
            plateOutOfBoundExcepetion.printStackTrace();
            fail();
        }
        plateIDProducer.getClass().getDeclaredField("indexOfNormalPlateLetter").set(plateIDProducer,29);
        try {
            assertEquals("۱۲ب۱۲۳", plateIDProducer.getNewPlateID(PlateIDProducer.Scope.Normal));
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof PlateOutOfBoundExcepetion);
        }
    }
}