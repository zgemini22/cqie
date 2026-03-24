package com.zds.user.manager;

import java.util.List;
import java.util.Map;

public interface DictManager {

    Map<String, String> getDictSubsetMap(String groupKey, List<String> dictNames);
}
