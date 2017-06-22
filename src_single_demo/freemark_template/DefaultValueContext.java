package freemark_template;
import java.util.HashMap;
import java.util.Map;


public class DefaultValueContext implements ValueContext {
    private final Map root = new HashMap();

    @Override
    public Object get(String key) {
        return root.get(key);
    }

    @Override
    public void put(String key, Object value) {
        root.put(key, value);
    }

}