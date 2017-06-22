package freemark_template;

public interface ValueContext {

	/**
	 * 
	 * 
	 * @param key
	 * @return 如果不存在，则返回null
	 */
	public Object get(String key);
	
	/**
	 * 
	 * 
	 * @param key
	 * @param value
	 */
	public void put(String key,Object value);
}
