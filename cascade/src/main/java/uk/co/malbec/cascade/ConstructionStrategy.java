package uk.co.malbec.cascade;

import uk.co.malbec.cascade.utils.Reference;

import java.util.List;

public interface ConstructionStrategy {

    void setup(Class<?> controlClass, Journey journey, Reference<Object> control, Reference<List<Object>> steps);
    
    void tearDown( Reference<Object> control, Reference<List<Object>> steps);

}
