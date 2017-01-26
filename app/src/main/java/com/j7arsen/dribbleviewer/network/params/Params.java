package com.j7arsen.dribbleviewer.network.params;

import com.j7arsen.dribbleviewer.app.Constants;
import com.j7arsen.dribbleviewer.network.Field;

import java.util.HashMap;

/**
 * Created by j7ars on 24.01.2017.
 */

public class Params {

    public static HashMap<String, String> getShotsRequestParams(int page) {
        HashMap<String, String> params = new HashMap<>(3);
        params.put(Field.PAGE, Integer.toString(page));
        params.put(Field.PER_PAGE, Integer.toString(Constants.PER_PAGE_COUNT));
        params.put(Field.SORT, Constants.SORT_RECENT);
        return params;
    }

}
