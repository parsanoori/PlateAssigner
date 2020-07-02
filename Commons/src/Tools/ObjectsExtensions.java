package Tools;

import java.util.Objects;

public class ObjectsExtensions {


    public static void requireNonNulls(Object ... objects){
        for (Object object: objects)
            Objects.requireNonNull(object);
    }




}
