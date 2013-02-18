import java.util.HashMap;

public class Table{
	HashMap<Integer,HashMap<Integer,Integer>> map;
	public Table(){
		map = new HashMap<Integer,HashMap<Integer,Integer>>();
	}

	public Integer get(int k1, int k2){
		HashMap<Integer,Integer> tmpMap = map.get(k1);
		if (tmpMap != null){
			Integer result = tmpMap.get(k2);
			result = result != null ? result : 0;
			return result;
		}
		else{
			map.put(k1, new HashMap<Integer,Integer>());
			return -1;
		}
	}

	public HashMap<Integer,Integer> getHashMapFromIndex(int index){
		return map.get(index);
	}

	public void put(int k1, int k2, int value){
		HashMap<Integer,Integer> tmpMap = map.get(k1);
		if (tmpMap == null)
			map.put(k1, new HashMap<Integer,Integer>());
		tmpMap = map.get(k1);
		tmpMap.put(k2,value);
	}
}