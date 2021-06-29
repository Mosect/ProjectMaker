package com.mosect.pm.sdk.res;

import com.mosect.pm.sdk.data.DataEntry;
import com.mosect.pm.sdk.data.DataSet;

public interface ElementFactory {

    Element createElement(String[] args, DataSet dataSet, DataEntry dataEntry);
}
